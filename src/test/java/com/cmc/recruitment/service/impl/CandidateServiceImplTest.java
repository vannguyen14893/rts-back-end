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
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.cmc.recruitment.entity.Candidate;
//import com.cmc.recruitment.entity.CandidateStatus;
//import com.cmc.recruitment.entity.Cv;
//import com.cmc.recruitment.entity.Request;
//import com.cmc.recruitment.repository.CandidateRepository;
//import com.cmc.recruitment.repository.CandidateStatusRepository;
//import com.cmc.recruitment.repository.CvRepository;
//import com.cmc.recruitment.repository.RequestRepository;
//import com.cmc.recruitment.service.CandidateService;
//import com.cmc.recruitment.service.CandidateStatusService;
//import com.cmc.recruitment.service.CvService;
//import com.cmc.recruitment.service.RequestService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
//public class CandidateServiceImplTest {
//	private static List<Candidate> candidates = new ArrayList<Candidate>();
//
//	@TestConfiguration
//	static class CandidateServiceImplTestContextConfiguration {
//
//		@Bean
//		public CandidateService candidateService() {
//			return new CandidateServiceImpl();
//		}
//	}
//
//	@Autowired
//	private CandidateService candidateService;
//
//	@Autowired
//	private RequestService requestService;
//
//	@Autowired
//	private CvService cvService;
//
//	@Autowired
//	private CandidateStatusService candidateStatusService;
//
//	@MockBean
//	private CandidateRepository candidateRepository;
//
//	@MockBean
//	private RequestRepository requestRepository;
//
//	@MockBean
//	private CvRepository cvRepository;
//
//	@MockBean
//	private CandidateStatusRepository CandidateStatusRepository;
//
//	@Before
//	public void setUp() {
//		Request request = new Request(1L);
//		Cv cv = new Cv(1L);
//		CandidateStatus candidateStatus = new CandidateStatus(1L);
//		Candidate candidate = new Candidate(1L);
//		candidate.setCvId(new Cv(1L));
//		candidate.setRequestId(new Request(1L));
//		candidate.setStatusId(new CandidateStatus(1L));
//
//		candidates.add(candidate);
//
//		candidates.add(candidate);
//		Mockito.when(requestRepository.findById(request.getId())).thenReturn(request);
//		Mockito.when(cvRepository.findById(cv.getId())).thenReturn(cv);
//		Mockito.when(CandidateStatusRepository.findById(candidateStatus.getId())).thenReturn(candidateStatus);
//		Mockito.when(candidateRepository.findById(candidate.getId())).thenReturn(candidate);
//		Mockito.when(candidateRepository.saveAndFlush(candidate)).thenReturn(candidates.get(0));
//		System.out.println("=====");
//		System.out.println(candidates.get(0).getRequestId().getId());
//	}
//
//	@Test
//	public void testIsCandidateExist() {
//		Boolean expect = true;
//		Candidate candidate = new Candidate(1L);
//		boolean found = candidateService.isCandidateExist(candidate);
//		assertThat(found).isEqualTo(expect);
//	}
//}