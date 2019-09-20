//package com.cmc.recruitment.service.impl;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.cmc.recruitment.entity.Request;
//import com.cmc.recruitment.repository.RequestRepository;
//import com.cmc.recruitment.service.RequestService;
//import com.cmc.recruitment.service.impl.RequestServiceImpl;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//public class RequestServiceImplTest {
//  public static List<Request> requests = new ArrayList<Request>();
//  
//  @TestConfiguration
//  static class RequestServiceImplTestContextConfiguration {
//    
//
//    @Bean
//    public RequestService requestService() {
//      return new RequestServiceImpl();
//    }
//  }
//
//  @Autowired
//  private RequestService requestService;
//
//  @MockBean
//  private RequestRepository requestRepository;
//
//  @Before
//  public void setUp() {
//    Request request = new Request(1L, "Java dev", 10, "No description");
//    requests.add(request);
//    Mockito.when(requestRepository.findAll()).thenReturn(requests);
//    Mockito.when(requestRepository.findOne(1L)).thenReturn(request);
//  }
//
//  
//  @Test
//  public void testGetAllRequest() {
//    Long id = 1L;
//    List<Request> requests = requestService.getAllRequest();
//    assertThat(requests.get(0).getId()).isEqualTo(id);
//  }
//
//  @Test
//  public void testFindOne() {
//    Request request = requestService.findOne(1L);
//    assertThat(request.getTitle()).isEqualTo("Java dev");
//  }
////  @Test
////  public void testSave() {
////    Request request = new Request(2L, "DotNet dev", 20, "No description");
////    try {
////      System.out.println("***************************************");
////      System.out.println(requestRepository.saveAndFlush(request));
////      boolean isSaved = requestService.saveRequest(request);
////      System.out.println(isSaved);
////      assertThat(isSaved).isEqualTo(true);
////    } catch (Exception e) {
////      System.out.println(e);
////    }
////  }
//}
