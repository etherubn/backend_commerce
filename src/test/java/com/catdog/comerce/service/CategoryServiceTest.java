//package com.catdog.comerce.service;
//
//import com.catdog.comerce.dto.request.CategoryDto;
//import com.catdog.comerce.entity.Category;
//import com.catdog.comerce.enums.CategoryType;
//import com.catdog.comerce.repository.CategoryRepo;
//import com.catdog.comerce.service.impl.CategoryServiceImpl;
//import com.catdog.comerce.utils.MapperUtil;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.ApplicationContext;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.Assertions;
//
//import static org.mockito.ArgumentMatchers.any;
//
//@ExtendWith(SpringExtension.class)//Para traer la prubeas
//public class CategoryServiceTest {
//    //Como el servicios depende del repo debemos traer un mock
//    @MockBean
//    private CategoryRepo categoryRepo;
//
//    @MockBean
//    private ApplicationContext applicationContext;
//
//    @MockBean
//    private MapperUtil mapperUtil;
//
//    @MockBean
//    private ICategoryService categoryService;
//
//    private CategoryDto categoryDto1;
//    private CategoryDto categoryDto2;
//    private CategoryDto categoryDto3;
//
//    private Category category1;
//    private Category category2;
//    private Category category3;
//
//    private List<CategoryDto> categoryDtos;
//    private List<Category> categoryList;
//
//    @BeforeEach
//    void setup(){
//        this.categoryService = new CategoryServiceImpl(mapperUtil,categoryRepo);
//
//        categoryDto1 = new CategoryDto(1L, CategoryType.ACCESORY);
//        categoryDto2 = new CategoryDto(2L, CategoryType.FOOD);
//        categoryDto3 = new CategoryDto(3L, CategoryType.FOOD);
//
//        category1 = new Category(1L,CategoryType.ACCESORY);
//        category2 = new Category(2L,CategoryType.FOOD);
//        category3 = new Category(3L,CategoryType.FOOD);
//
//        categoryDtos = Arrays.asList(categoryDto1,categoryDto2,categoryDto3);
//        categoryList = Arrays.asList(category1,category2,category3);
//    }
//
//    @Test
//    void findAllTest(){
//        Mockito.when(categoryService.findAll()).thenReturn(categoryDtos);
//
//        List<CategoryDto> categoryDtos = categoryService.findAll();
//        Assertions.assertNotNull(categoryDtos);
//        Assertions.assertFalse(categoryDtos.isEmpty());
//        Assertions.assertEquals(categoryDtos.size(),3);
//    }
//
//    @Test
//    void createTest(){
//
//        Mockito.when(categoryRepo.save(any(Category.class))).thenReturn(category1);
//
//        CategoryDto result = categoryService.create(categoryDto1);
//
//        Assertions.assertNotNull(result);
//        Assertions.assertEquals(categoryDto1.getIdCategory(), result.getIdCategory());
//        Assertions.assertEquals(categoryDto1.getType(), result.getType());
//
//        // Verificar que se llamaron los métodos correctos en el repositorio y MapperUtil
//
//    }
//}
