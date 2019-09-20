package com.cmc.recruitment.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cmc.recruitment.entity.Skill;
import com.cmc.recruitment.response.BaseResponse;
import com.cmc.recruitment.service.SkillService;
import com.cmc.recruitment.utils.Constants;
import com.cmc.recruitment.utils.ConstantsUrl;

@RestController
public class SkillController {

	@Autowired
	SkillService skillService;

	@GetMapping(ConstantsUrl.Skill.ALL_UNLIMIT)
	public ResponseEntity<List<Skill>> getListSkills() {
		return new ResponseEntity<List<Skill>>(skillService.findAll(), HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//	@GetMapping("/skills/find-all-skill")
//	public ResponseEntity<List<Skill>> getListSkillByParrent() {
//		List<Skill> list = skillService.listAllSkill();
//		return new ResponseEntity<List<Skill>>(list, HttpStatus.OK);
//	}

//	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
//	@GetMapping("/skills/title")
//	public ResponseEntity<Skill> getListSkillByTitile(Skill skill) {
//		Skill list = skillService.getSkillByTitle(skill.getTitle());
//		return new ResponseEntity<Skill>(list, HttpStatus.OK);
//	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER','HR_MEMBER')")
	@PostMapping(ConstantsUrl.Skill.ADD)
	public ResponseEntity<BaseResponse> saveSkill(@RequestBody Skill skill) {
		if(skill.getTitle().length() == 0) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SKILL_TITLE_NOT_BLANK),
					HttpStatus.BAD_REQUEST);
		}
		try {
			boolean isExist = skillService.checkIfExist(skill);
			if(isExist == true)
				return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SKILL_WAS_EXISTED),
						HttpStatus.BAD_REQUEST);
			boolean result = skillService.addSkill(skill);
			if(result == false)
				return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.INTERNAL_ERROR_SERVICE),
						HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.INTERNAL_ERROR_SERVICE),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SUCCESS_MESSAGE), HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER','HR_MEMBER')")
	@PutMapping(ConstantsUrl.Skill.UPDATE)
	public ResponseEntity<BaseResponse> updateSkill(@RequestBody Skill skill) {
		if(skill.getTitle().length() == 0) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SKILL_TITLE_NOT_BLANK),
					HttpStatus.BAD_REQUEST);
		}
		try {
			boolean result = skillService.addSkill(skill);
			if(result == false)
				return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.INTERNAL_ERROR_SERVICE),
						HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.INTERNAL_ERROR_SERVICE),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.SUCCESS_MESSAGE), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER','HR_MEMBER')")
	@GetMapping(ConstantsUrl.Skill.DETAIL)
	public ResponseEntity<?> detailUser(@PathVariable int skillId) {
		Skill skill = null;
		try {
			skill = skillService.findOne(skillId);
			if (skill == null) {
				return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.NOT_FOUND_CODE,
						Constants.RESPONSE.NOT_FOUND, Constants.RESPONSE.NOT_FOUND), HttpStatus.NO_CONTENT);
			}
		} catch (Exception e) {
			return new ResponseEntity<BaseResponse>(new BaseResponse(Constants.RESPONSE.INTERNAL_SERVER_ERROR,
					Constants.RESPONSE.EXITS_CODE, Constants.RESPONSE.NOT_EXIST), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Skill>(skill, HttpStatus.OK);
	}
	
	/**
	 * @description: "/find-all".
	 * @author: VDHoan
	 * @created_date: Mar 23, 2018
	 * @modifier: User
	 * @modifier_date: Mar 23, 2018
	 * @param pageable
	 * @return
	 */
	@GetMapping(ConstantsUrl.Skill.ALL)
	public ResponseEntity<?> findAll(Pageable pageable) {
	  try {
	    Page<Skill> list = skillService.findAll(pageable);
	    if (list.getSize() > 0)
	      return new ResponseEntity<Page<Skill>>(list, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
	}
	
	/**
	 * @description: "/create-or-update".
	 * @author: VDHoan
	 * @created_date: Mar 23, 2018
	 * @modifier: User
	 * @modifier_date: Mar 23, 2018
	 * @param skill
	 * @param result
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER','HR_MEMBER')")
	@PostMapping(ConstantsUrl.Skill.SAVE)
	public ResponseEntity<?> createOrUpdate(@RequestBody @Validated Skill skill, BindingResult result) {
	  try {
	    if (result.hasErrors())
	      return new ResponseEntity<String>(Constants.RESPONSE.WRONG_INPUT, HttpStatus.NOT_ACCEPTABLE);
	    skill.setIsDelete(false);
	    skill = skillService.createOrUpdate(skill);
	    if (skill != null)
	      return new ResponseEntity<Skill>(skill, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<String>(Constants.RESPONSE.ERROR_SERVER, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  return new ResponseEntity<String>(Constants.RESPONSE.NOT_SAVE_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * @description: "/find-by-title".
	 * @author: VDHoan
	 * @created_date: Mar 23, 2018
	 * @modifier: User
	 * @modifier_date: Mar 23, 2018
	 * @param title
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','HR_MANAGER','HR_MEMBER')")
	@GetMapping(ConstantsUrl.Skill.FIND_BY_TITLE)
	public ResponseEntity<?> findByTitle(@RequestParam(Constants.PARAM.TITLE_PARAM) String title) {
	  try {
	    Skill skill = skillService.getSkillByTitle(title);
	    if (skill != null)
	      return new ResponseEntity<Skill>(skill, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<String>(Constants.RESPONSE.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  return new ResponseEntity<String>(Constants.RESPONSE.NO_CONTENT, HttpStatus.NO_CONTENT);
	}
}
