//package com.cmc.recruitment.service.impl;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.cmc.recruitment.entity.Cv;
//import com.cmc.recruitment.repository.CvRepository;
//import com.cmc.recruitment.service.CvService;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class CvServiceImplTest {
//  
////  @TestConfiguration
////  static class CvServiceImplTestContextConfiguration {
////
////    @Bean
////    public CvService cvService() {
////      return new CvServiceImpl();
////    }
////  }
//
//  @Autowired
//  private CvService cvService;
//
//  @Mock
//  CvRepository      cvRepository;
//
//  private Cv        objCv;
//
//  @Before
//  public void setUp() {
//    // mock data
//    // Cv cv = new Cv();
//    // Mockito.when(cvRepository.saveand(empalex.getName())).thenReturn(objcv);
//    String json = "{" + "    \"firstName\": \"Xuan Hoan\"," + "   \"lastName\": \"Trần\","
//        + "   \"dob\": \"02/02/1992\"," + "    \"gender\": false,"
//        + "    \"phone\": \"0123923913\"," + "   \"email\": \"nhphong1890090aa@cmc.com.vn\","
//        + "   \"profileImg\": \"url_img.jpg\"," + "   \"address\": \"BGiang\","
//        + "   \"education\": \"Đại học\"," + "    \"workExperience\": \"chưa có KN\","
//        + "   \"cvUrl.pdf\": \"cv_url\"," + "   \"skillCollection\": [" + "       {"
//        + "            \"id\": 5  " + "       }," + "       {" + "            \"id\": 1"
//        + "       }" + "    ]," + "    \"statusId\": {" + "       \"id\": 1" + "    }" + " }";
//
//    Gson gson = new Gson();
//    gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
//    Cv cv = gson.fromJson(json, Cv.class);
//    Cv resultCv = new Cv();
//    Mockito.when(cvRepository.saveAndFlush(cv)).thenReturn(resultCv);
//    System.out.println(resultCv);
//  }
//
//  @Test
//  @Rollback(true)
//  public void testCvDao() {
//    System.out.println(objCv);
//    String json = "{" + "    \"firstName\": \"Xuan Hoan\"," + "   \"lastName\": \"Trần\","
//        + "   \"dob\": \"02/02/1992\"," + "    \"gender\": false,"
//        + "    \"phone\": \"0123923913\"," + "   \"email\": \"nhphong1890090aa@cmc.com.vn\","
//        + "   \"profileImg\": \"url_img.jpg\"," + "   \"address\": \"BGiang\","
//        + "   \"education\": \"Đại học\"," + "    \"workExperience\": \"chưa có KN\","
//        + "   \"cvUrl.pdf\": \"cv_url\"," + "   \"skillCollection\": [" + "       {"
//        + "            \"id\": 5  " + "       }," + "       {" + "            \"id\": 1"
//        + "       }" + "    ]," + "    \"statusId\": {" + "       \"id\": 1" + "    }" + " }";
//
//    Gson gson = new Gson();
//    gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
//    objCv = gson.fromJson(json, Cv.class);
//
//    assertEquals(cvService.addCv(objCv), true);
//  }
//}