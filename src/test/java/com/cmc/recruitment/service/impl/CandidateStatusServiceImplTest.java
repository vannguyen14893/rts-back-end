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
//import com.cmc.recruitment.entity.CandidateStatus;
//import com.cmc.recruitment.repository.CandidateStatusRepository;
//import com.cmc.recruitment.service.CandidateStatusService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//public class CandidateStatusServiceImplTest {
//
//	@TestConfiguration
//	static class CandidateStatusImplTestContextConfiguration {
//
//		@Bean
//		public CandidateStatusService candidateStatusService() {
//			return new CandidateStatusServiceImpl();
//		}
//	}
//
//	@Autowired
//	private CandidateStatusService candidateStatusService;
//
//	@MockBean
//	private CandidateStatusRepository CandidateStatusRepository;
//
//	@Before
//	public void setUp() {
//		List<CandidateStatus> candidateStatuss = new ArrayList<CandidateStatus>();
//		CandidateStatus candidateStatus = new CandidateStatus(1L);
//		candidateStatuss.add(candidateStatus);
//
//		Mockito.when(CandidateStatusRepository.findAll()).thenReturn(candidateStatuss);
//
//		Mockito.when(CandidateStatusRepository.findById(candidateStatus.getId())).thenReturn(candidateStatus);
//	}
//
//	@Test
//	public void testGetAllStatus() {
//		Long id = 1L;
//		List<CandidateStatus> candidateStatuss = candidateStatusService.getAllStatus();
//		assertThat(candidateStatuss.get(0).getId()).isEqualTo(id);
//	}
//
//	@Test
//	public void testFindOneById() {
//		Long id = 1L;
//		CandidateStatus found = candidateStatusService.findOneById(id);
//
//		assertThat(found.getId()).isEqualTo(id);
//	}
//}
