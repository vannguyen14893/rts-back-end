package com.cmc.recruitment.service;

import com.cmc.recruitment.entity.AccountGlobal;

public interface AccountGlobalService {
  
  boolean updateAccountGlobal(AccountGlobal accountGlobal);
  
  AccountGlobal addAccountGlobal(AccountGlobal accountGlobal);
  
  void deleteAccountGlobal(long accountGlobalId);
  
  AccountGlobal findOne(long id);
}
