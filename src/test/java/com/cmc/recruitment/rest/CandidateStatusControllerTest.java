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
//import com.cmc.recruitment.entity.CandidateStatus;
//import com.cmc.recruitment.service.CandidateStatusService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class CandidateStatusControllerTest {
//
//	@Mock
//	private CandidateStatusService candidateStatusService;
//	
//	@InjectMocks
//	private CandidateStatusController candidateStatusController;
//	
//	private MockMvc mockMvc;
//	
//	@Before
//	public void setUp() {
//		MockitoAnnotations.initMocks(this);
//		mockMvc = MockMvcBuilders.standaloneSetup(candidateStatusController).build();
//	}
//	
//	@Test
//	public void getListStatus() throws Exception {
//		List<CandidateStatus> listStatus = new ArrayList<CandidateStatus>();
//		listStatus.add(new CandidateStatus(1L));
//		listStatus.add(new CandidateStatus(2L));
//		
//		when(candidateStatusService.getAllStatus()).thenReturn(listStatus);
//		
//		mockMvc.perform(get("/candidate-status/find-all-status"))
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id", is(1)))
//				.andExpect(jsonPath("$[1].id", is(2)));
//	}
//}
