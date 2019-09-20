//package com.cmc.recruitment.rest;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.Matchers.hasSize;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.cmc.recruitment.entity.Request;
//import com.cmc.recruitment.service.RequestService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class RequestControllerTest {
//
//  @Mock
//  private RequestService requestService;
//
//  @InjectMocks
//  private RequestController requestController;
//
//  private MockMvc mockMvc;
//
//  @Before
//  public void setUp() {
//    MockitoAnnotations.initMocks(this);
//    mockMvc = MockMvcBuilders.standaloneSetup(requestController).build();
//  }
////  @SuppressWarnings("unchecked")
////  @Test
////  public void testListRequest() throws Exception {
////    List<Request> requests1 = new ArrayList<Request>();
////    Request request1 = new Request();
////    User user = new User();
////    Department department = new Department();
////
////    department.setId((long) 1);
////    user.setDepartmentId(department);
////    request1.setCreatedBy(user);
////
////    Request request2 = new Request();
////    User user2 = new User();
////    Department department2 = new Department();
////
////    department2.setId((long) 1);
////    user2.setDepartmentId(department2);
////    request1.setCreatedBy(user2);
////
////    requests1.add(request1);
////    requests1.add(request2);
////    @SuppressWarnings("unchecked")
////    Page<Request> requests = (Page<Request>) request1;
////    when(requestService.findRequestByDepartment((long) 1, null))
////        .thenReturn((Page<Request>) requests1);
////    mockMvc.perform(get("/requests/all")).andExpect(status().isOk())
////        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
////        .andExpect(jsonPath("$", hasSize(2)))
////        .andExpect(jsonPath("$[0].user.departmentId.id", is(1)))
////        .andExpect(jsonPath("$[1].user.departmentId.id", is(1)));
////
////  }
//  @Test
//  public void testListRequest() throws Exception {
//    List<Request> requests = new ArrayList<Request>();
//    requests.add(new Request(Long.parseLong("1")));
//    requests.add(new Request(Long.parseLong("2")));
//    when(requestService.getAllRequest()).thenReturn(requests);
//    mockMvc.perform(get("/requests/list-request")).andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//        .andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(1)))
//        .andExpect(jsonPath("$[1].id", is(2)));
//
//  }
//
////  @Test
////  public void testDetailRequest() throws Exception {
////    Request request = new Request(1L, "Tuyển lập trình viên java", 10, "Dev Java");
////
////    when(requestService.findOne(1)).thenReturn(request);
////    mockMvc.perform(get("/requests/detail/1")).andExpect(status().isOk())
////        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
////        .andExpect(jsonPath("$.id", is(1)))
////        .andExpect(jsonPath("$.title", is("Tuyển lập trình viên java")));
////
////  }
//
////  @Test
////  public void test_create_user_success() throws Exception {
////    Set skills = new HashSet<Skill>();
////    skills.add(new Skill(1L));
////    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
////    Date date = formatter.parse("22/02/2018");
////
////    Request request = new Request("Lập trình java", date, 10, "No description", skills);
////      when(requestService.saveRequest(request)).thenReturn(true);
////      mockMvc.perform(
////              post("/request/add")
////                      .contentType(MediaType.APPLICATION_JSON)
////                      .content(asJsonString(request)))
////              .andExpect(status().isCreated());
////  }
//  public static String asJsonString(final Object obj) {
//    try {
//        return new ObjectMapper().writeValueAsString(obj);
//    } catch (Exception e) {
//        throw new RuntimeException(e);
//    }
//}
//}