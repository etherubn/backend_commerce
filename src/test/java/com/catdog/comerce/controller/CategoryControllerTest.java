//package com.catdog.comerce.controller;
//
//import com.catdog.comerce.dto.request.CategoryDto;
//import com.catdog.comerce.dto.response.ResponseCategoryDto;
//import com.catdog.comerce.enums.CategoryType;
//import com.catdog.comerce.exception.NotFoundException;
//import com.catdog.comerce.service.ICategoryService;
//import com.catdog.comerce.utils.MapperUtil;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.Arrays;
//import java.util.List;
//
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.hamcrest.Matchers.is;
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(CategoryController.class)
//public class CategoryControllerTest {
//    //Pruebas unitarias
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ICategoryService categoryService;
//
//    @MockBean
//    private MapperUtil mapperUtil;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    CategoryDto categoryDto1 = new CategoryDto(1L, CategoryType.FOOD);
//    CategoryDto categoryDto2 = new CategoryDto(2L, CategoryType.ACCESORY);
//    CategoryDto categoryDto3 = new CategoryDto(3L, CategoryType.FOOD);
//
//    ResponseCategoryDto responseCategoryDto1= new ResponseCategoryDto(1L, CategoryType.FOOD);
//    ResponseCategoryDto responseCategoryDto2 = new ResponseCategoryDto(2L, CategoryType.ACCESORY);
//    ResponseCategoryDto responseCategoryDto3 = new ResponseCategoryDto(3L, CategoryType.FOOD);
//    //Test para verifica la respuesta
//    @Test
//    void findAllCategorysTest() throws Exception {
//
//        List<CategoryDto> categoryDtos = Arrays.asList(categoryDto1,categoryDto2,categoryDto3);
//        //Seteando el valor de lo que genera el findAll
//        Mockito.when(categoryService.findAll()).thenReturn(categoryDtos);
//        //Seteando la conversión
//        Mockito.when(mapperUtil.mapList(categoryDtos, ResponseCategoryDto.class)).thenReturn(Arrays.asList(responseCategoryDto1,responseCategoryDto2,responseCategoryDto3));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/api/v1/category")
//                .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())//Esto devuelve lo del controlador
//                .andExpect(jsonPath("$.data",hasSize(3))); //Comprueba que haya dentro de la respuesta una variable data;
//    }
//
//    CategoryDto categoryDto4 = new CategoryDto(null, CategoryType.FOOD);
//    ResponseCategoryDto responseCategoryDto4= new ResponseCategoryDto(1L, CategoryType.FOOD);
//
//
//    //Verificando que cree una categoria
//    @Test
//    void createCategoryTest() throws Exception {
//        Mockito.when(categoryService.create(any())).thenReturn(categoryDto1);
//        Mockito.when(mapperUtil.map(categoryDto1,ResponseCategoryDto.class)).thenReturn(responseCategoryDto1);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .post("/api/v1/category")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(responseCategoryDto1)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data",hasSize(1)));
//
//    }
//
//    //Verificando que cree una categoria con valor de categoryType.FOOD
//    @Test
//    void createCategoryTypeTest() throws Exception {
//        Mockito.when(categoryService.create(any())).thenReturn(categoryDto1);
//        Mockito.when(mapperUtil.map(categoryDto1,ResponseCategoryDto.class)).thenReturn(responseCategoryDto1);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/v1/category")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(responseCategoryDto1)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data[0].type",is(CategoryType.FOOD.getValue())));
//    }
//
//    //Verifica que actualize
//    @Test
//    void updateCategoryTest() throws Exception {
//        final Long id = 2L;
//        Mockito.when(categoryService.update(any(),any())).thenReturn(categoryDto1);
//        Mockito.when(mapperUtil.map(categoryDto1,ResponseCategoryDto.class)).thenReturn(responseCategoryDto1);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/v1/category/"+id)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(responseCategoryDto1)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data[0].type",is(CategoryType.FOOD.getValue())));
//    }
//
//    //Verifica el not found
//    @Test
//    void updateErrorNotFoundTest() throws Exception {
//        final Long ID = 1000L;
//
//        Mockito.when(categoryService.update(any(),any())).thenThrow(new NotFoundException("category",ID));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/v1/category/"+ID)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(responseCategoryDto1)))
//                        .andExpect(status().isNotFound());
////                .andExpect(jsonPath("$.data[0].type",is(CategoryType.FOOD.getValue())));
//    }
//
//
//    //Comprueba que elimine, dando el no content
//    @Test
//    void deleteCategoryTest() throws Exception {
//        final Long ID= 1L;
//
//        Mockito.doNothing().when(categoryService).delete(ID);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .delete("/api/v1/category/"+ID))
//                .andExpect(status().isNoContent());
//    }
//
//
//    //Verifica el error not found
//    @Test
//    void deleteCategoryErrorTest() throws Exception {
//        final Long ID= 1L;
//
//        Mockito.doThrow(new NotFoundException("category",ID)).when(categoryService).delete(ID);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/api/v1/category/"+ID))
//                .andExpect(status().isNotFound());
//    }
//
//}
