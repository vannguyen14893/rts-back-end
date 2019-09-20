package com.cmc.recruitment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.recruitment.entity.AccountGlobal;
import com.cmc.recruitment.repository.AccountGlobalRepository;
import com.cmc.recruitment.service.AccountGlobalService;

@Service
public class AccountGlobalServiceImlp implements AccountGlobalService {

  @Autowired
  AccountGlobalRepository accountGlobalRepository;

  @Override
  public boolean updateAccountGlobal(AccountGlobal accountGlobal) {
    // TODO Auto-generated method stub
    return accountGlobalRepository.save(accountGlobal) != null ? true:false;
  }

  @Override
  public AccountGlobal addAccountGlobal(AccountGlobal accountGlobal) {
    // TODO Auto-generated method stub
    return accountGlobalRepository.save(accountGlobal);
  }

  @Override
  public void deleteAccountGlobal(long accountGlobalId) {
    accountGlobalRepository.delete(accountGlobalId);
    
  }

  @Override
  public AccountGlobal findOne(long id) {
    // TODO Auto-generated method stub
    return accountGlobalRepository.findOne(id);
  }


  
}
