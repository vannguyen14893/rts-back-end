/**
 * 
 */
package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.RecruitmentType;
import com.cmc.recruitment.repository.RecruitmentTypeRepository;
import com.cmc.recruitment.service.RecruitmentTypeService;

/**
 * @description: .
 * @author: Lcnguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
@Service
public class RecruitmentTypeServiceImpl implements RecruitmentTypeService {

	@Autowired
	private RecruitmentTypeRepository recruitmentTypeRepository;

	/**
	 * @description: .
	 * @author: Lcnguyen
	 * @created_date: Mar 6, 2018
	 * @modifier: User
	 * @modifier_date: Mar 6, 2018
	 * @return
	 */
	@Override
	public List<RecruitmentType> findAll() {
		return recruitmentTypeRepository.findAll();
	}

	@Override
	public RecruitmentType findOne(Long id) {
		return recruitmentTypeRepository.findOne(id);
	}

	@Override
	public RecruitmentType save(RecruitmentType recruitmentType) {
		return recruitmentTypeRepository.save(recruitmentType);
	}

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param pageable
   * @return
   */
  @Override
  public Page<RecruitmentType> findAll(Pageable pageable) {
    return recruitmentTypeRepository.findAll(pageable);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param title
   * @return
   */
  @Override
  public RecruitmentType findByTitle(String title) {
    return recruitmentTypeRepository.findByTitle(title);
  }

  /**
   * @description: .
   * @author: VDHoan
   * @created_date: Apr 5, 2018
   * @modifier: User
   * @modifier_date: Apr 5, 2018
   * @param recruitmentType
   * @return
   */
  @Override
  public RecruitmentType createOrUpdate(RecruitmentType recruitmentType) {
    return recruitmentTypeRepository.save(recruitmentType);
  }

}
