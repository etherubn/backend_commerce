//package com.catdog.comerce.controller;
//
//import com.catdog.comerce.dto.request.RoleDto;
//import com.catdog.comerce.dto.response.ResponseRoleDto;
//import com.catdog.comerce.enums.RoleType;
//import com.catdog.comerce.service.IRoleService;
//import com.catdog.comerce.utils.MapperUtil;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(RoleController.class)
//public class RoleControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private IRoleService roleService;
//
//    @MockBean
//    private MapperUtil mapperUtil;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//
//    //Datos mockeados
//
//    RoleDto roleDto1 = new RoleDto(1L, RoleType.USER);
//    RoleDto roleDto2 = new RoleDto(2L,RoleType.ADMIN);
//    RoleDto roleDto3 = new RoleDto(3L,RoleType.USER);
//
//    ResponseRoleDto responseRoleDto1 = new ResponseRoleDto(1L,RoleType.USER);
//    ResponseRoleDto responseRoleDto2 = new ResponseRoleDto(2L,RoleType.ADMIN);
//    ResponseRoleDto responseRoleDto3 = new ResponseRoleDto(3L,RoleType.USER);
//
//
//    @Test
//    void findAllRolesTest() throws Exception {
//        List<RoleDto> roleDtos = Arrays.asList(roleDto1,roleDto2,roleDto3);
//        Mockito.when(roleService.findAll()).thenReturn(roleDtos);
//        Mockito.when(mapperUtil.mapList(roleDtos,ResponseRoleDto.class)).thenReturn(Arrays.asList(responseRoleDto1,responseRoleDto2,responseRoleDto3));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/api/v1/role")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(Arrays.asList(responseRoleDto1,responseRoleDto2,responseRoleDto3)))
//                )
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void findAllRolesEmptyTest() throws Exception {
//        List<RoleDto> roleDtos = new ArrayList<>();
//        List<ResponseRoleDto> responseRoleDtos = new ArrayList<>();
//        Mockito.when(roleService.findAll()).thenReturn(roleDtos);
//        Mockito.when(mapperUtil.mapList(roleDtos,ResponseRoleDto.class)).thenReturn(responseRoleDtos);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/v1/role")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(objectMapper.writeValueAsString(Arrays.asList(responseRoleDto1,responseRoleDto2,responseRoleDto3)))
//                )
//                .andExpect(status().isOk());
//    }
//
//}
