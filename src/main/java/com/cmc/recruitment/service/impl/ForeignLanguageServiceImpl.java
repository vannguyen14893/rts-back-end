/**
 * 
 */
package com.cmc.recruitment.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.ForeignLanguage;
import com.cmc.recruitment.repository.ForeignLanguageRepository;
import com.cmc.recruitment.service.ForeignlanguageService;

/**
 * @description: .
 * @author: Lcnguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
@Service
public class ForeignLanguageServiceImpl implements ForeignlanguageService {

	@Autowired
	ForeignLanguageRepository foreignLanguageRepository;

	/**
	 * @description: .
	 * @author: Lcnguyen
	 * @created_date: Mar 6, 2018
	 * @modifier: User
	 * @modifier_date: Mar 6, 2018
	 * @return
	 */
	@Override
	public List<ForeignLanguage> findAll() {
		return foreignLanguageRepository.findAll();
	}

	@Override
	public Page<ForeignLanguage> findAll(Pageable pageable) {
		return foreignLanguageRepository.findAll(pageable);
	}

	@Override
	public ForeignLanguage createOrUpdate(ForeignLanguage foreignLanguage) {
		return foreignLanguageRepository.save(foreignLanguage);
	}

	@Override
	public ForeignLanguage findByTitle(String title) {
		return foreignLanguageRepository.findByTitle(title);
	}

}

