package com.catdog.comerce.service.impl;

import com.catdog.comerce.dto.request.SellingDto;
import com.catdog.comerce.dto.request.SellingProductDto;
import com.catdog.comerce.dto.request.SellingStateDto;
import com.catdog.comerce.dto.response.ResponseProductSellingDto;
import com.catdog.comerce.dto.response.ResponseSelling;
import com.catdog.comerce.dto.response.ResponseUserSellingDto;
import com.catdog.comerce.entity.Product;
import com.catdog.comerce.entity.Selling;

import com.catdog.comerce.entity.SellingProduct;
import com.catdog.comerce.entity.User;
import com.catdog.comerce.enums.SellingState;
import com.catdog.comerce.exception.*;
import com.catdog.comerce.repository.*;
import com.catdog.comerce.service.IEmailService;
import com.catdog.comerce.service.ISellingService;
import com.catdog.comerce.service.IShippingService;
import com.catdog.comerce.utils.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


@Service
public class SellingServiceImpl extends CrudServiceImpl<SellingDto, Selling,Long> implements ISellingService {
    private final SellingRepo sellingRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final IEmailService emailService;
    private final IShippingService shippingService;


    public SellingServiceImpl(MapperUtil mapperUtil, SellingRepo sellingRepo, ProductRepo productRepo, UserRepo userRepo, IEmailService emailService, IShippingService shippingService) {
        super(mapperUtil);
        this.sellingRepo = sellingRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
        this.emailService = emailService;
        this.shippingService = shippingService;
    }

    @Override
    protected RepoGeneric<Selling, Long> getRepo() {
        return sellingRepo;
    }

    @Override
    protected Class<Selling> getEntityClass() {
        return Selling.class;
    }

    @Override
    protected Class<SellingDto> getDtoClass() {
        return SellingDto.class;
    }

    @Override
    protected void setId(Selling entity, Long aLong) {
        entity.setIdSelling(aLong);
    }



    @Transactional
    public  ResponseSelling createSelling(SellingDto sellingDto) {

        User user = userRepo.findById(sellingDto.getUser().getIdUser()).orElseThrow(()->new NotFoundException("user",sellingDto.getUser().getIdUser()));

        if (!user.isEnableBuyer()){
            throw new NotEnabledBuyerException("User not allowed to buy");
        }

        ResponseUserSellingDto responseUserSellingDto = mapperUtil.map(user,ResponseUserSellingDto.class);

        BigDecimal total = BigDecimal.ZERO;

        List<ResponseProductSellingDto> responseProductSellingDtos = new ArrayList<>();
        List<SellingProduct> sellingProducts = new ArrayList<>();
        Set<Long> longSet = new HashSet<>();

        for (SellingProductDto sellingProductDto:sellingDto.getSellingProducts()){
            Product product = productRepo.findById(sellingProductDto.getProduct().getIdProduct())
                    .orElseThrow(()-> new NotFoundException("product",sellingProductDto.getProduct().getIdProduct()));

            //Verify that json contains a product once
            if (!longSet.add(product.getIdProduct())){
                throw new RepeatedException("product",product.getName());
            }

            //Verify stock
            if (product.getStock()<sellingProductDto.getQuantity()){
                throw new NotStockException("There is not stock for "+product.getName());
            }

            //Update Stock
            product.setStock(product.getStock()-sellingProductDto.getQuantity());

            //Update sales: Vamos a actualizar la cantidad de ventas, sin importar las fechas
            product.setQuantitySale(product.getQuantitySale()+sellingProductDto.getQuantity());


            //Calculate subtotal
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(sellingProductDto.getQuantity())).setScale(2);

            ResponseProductSellingDto responseProductSellingDto = ResponseProductSellingDto.builder()
                    .idProduct(product.getIdProduct())
                    .product(product.getName())
                    .price(product.getPrice())
                    .quantity(sellingProductDto.getQuantity())
                    .subtotal(subtotal)
                    .build();

            responseProductSellingDtos.add(responseProductSellingDto);

            //Add subtotal to total
            total = total.add(subtotal);

            //Creando info de entidad de tabla intermedia, falta agregarle createdSelling
            SellingProduct sellingProduct = new SellingProduct();
            sellingProduct.setProduct(product);
            sellingProduct.setQuantity(sellingProductDto.getQuantity());
            sellingProduct.setSubtotal(subtotal);

            sellingProducts.add(sellingProduct);
        }

        //Actualizando el valor de envio
        BigDecimal shippingPrice =  shippingService.calculateShipping(total);
        total = total.add(shippingPrice);

        Selling createdSelling = new Selling();
        createdSelling.setShippingPrice(shippingPrice);
        createdSelling.setUser(user);
        createdSelling.setTotal(total);
        sellingRepo.save(createdSelling);

        for (SellingProduct sellingProduct:sellingProducts){
            sellingProduct.setSelling(createdSelling);
        }

        createdSelling.setSellingProducts(sellingProducts);
        Selling selling =  sellingRepo.save(createdSelling);

        ResponseSelling responseSelling = ResponseSelling.builder()
                .idSelling(selling.getIdSelling())
                .creationSelling(selling.getCreationSelling())
                .responseUserSellingDto(responseUserSellingDto)
                .total(total)
                .responseProductSellingDtos(responseProductSellingDtos)
                .build();

        emailService.sendEmailBySelling(responseSelling,"Compra exitosa","success");
        return responseSelling;
    }

    @Override
    public List<ResponseSelling> findAllSellings() {

        List<ResponseSelling> responseSellings = sellingRepo.findAll().stream().map(
                selling -> {
                    ResponseSelling responseSelling = new ResponseSelling();
                    responseSelling.setShippingPrice(selling.getShippingPrice());
                    responseSelling.setIdSelling(selling.getIdSelling());
                    responseSelling.setCreationSelling(selling.getCreationSelling());
                    responseSelling.setTotal(selling.getTotal());
                    responseSelling.setSellingState(selling.getSellingState());

                    ResponseUserSellingDto responseUserSellingDto = mapperUtil.map(selling.getUser(),ResponseUserSellingDto.class);
                    responseSelling.setResponseUserSellingDto(responseUserSellingDto);

                    List<ResponseProductSellingDto> responseProductSellingDtos= selling.getSellingProducts().stream().map(
                            sellingProduct -> {
                                ResponseProductSellingDto responseProductSellingDto = new ResponseProductSellingDto();
                                responseProductSellingDto.setIdProduct(sellingProduct.getIdSellingProduct().getIdProduct());
                                responseProductSellingDto.setProductCode(sellingProduct.getProduct().getProductCode());
                                responseProductSellingDto.setProduct(sellingProduct.getProduct().getName());
                                responseProductSellingDto.setPrice(sellingProduct.getProduct().getPrice());
                                responseProductSellingDto.setQuantity(sellingProduct.getQuantity());
                                responseProductSellingDto.setSubtotal(sellingProduct.getSubtotal());
                                return responseProductSellingDto;
                            }
                    ).toList();
                    responseSelling.setResponseProductSellingDtos(responseProductSellingDtos);
                    return responseSelling;
                }
        ).toList();
        return responseSellings;
    }

    @Override
    public ResponseSelling updateStateSelling(SellingStateDto sellingStateDto,Long id) {
        Selling selling = sellingRepo.findById(id)
                .orElseThrow(()-> new NotFoundException("selling",id));

        if (!validateUpdateSellingState(selling.getSellingState(),sellingStateDto.getSellingState())){
            throw new InvalidUpdateSellingState(selling.getSellingState().getValue()+" to "+sellingStateDto.getSellingState());
        }

        selling.setSellingState(sellingStateDto.getSellingState());

        Selling updatedSelling = sellingRepo.save(selling);

        ResponseSelling responseSelling = new ResponseSelling();
        responseSelling.setShippingPrice(updatedSelling.getShippingPrice());
        responseSelling.setIdSelling(updatedSelling.getIdSelling());
        responseSelling.setCreationSelling(updatedSelling.getCreationSelling());
        ResponseUserSellingDto responseUserSellingDto = mapperUtil.map(updatedSelling.getUser(),ResponseUserSellingDto.class);
        responseSelling.setResponseUserSellingDto(responseUserSellingDto);
        List<ResponseProductSellingDto> responseProductSellingDtos = updatedSelling.getSellingProducts().stream()
                .map(sellingProduct->{
                    ResponseProductSellingDto responseProductSellingDto = new ResponseProductSellingDto();
                    responseProductSellingDto.setIdProduct(sellingProduct.getIdSellingProduct().getIdProduct());
                    responseProductSellingDto.setProductCode(sellingProduct.getProduct().getProductCode());
                    responseProductSellingDto.setProduct(sellingProduct.getProduct().getName());
                    responseProductSellingDto.setPrice(sellingProduct.getProduct().getPrice());
                    responseProductSellingDto.setQuantity(sellingProduct.getQuantity());
                    responseProductSellingDto.setSubtotal(sellingProduct.getSubtotal());
                    return responseProductSellingDto;
                }).toList();


        return responseSelling;
    }

    private boolean validateUpdateSellingState(SellingState initialState,SellingState finalState) {
        if (initialState.getValue().equals(SellingState.PROCESSING_ORDER.getValue()) && finalState.getValue().equals(SellingState.SENDING_ORDER.getValue())){
            return true;
        }
        if (initialState.getValue().equals(SellingState.SENDING_ORDER.getValue()) && finalState.getValue().equals(SellingState.ORDER_DELIVERED.getValue())){
            return true;
        }
        return false;
    }


}
