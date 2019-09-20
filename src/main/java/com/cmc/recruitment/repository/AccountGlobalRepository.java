package com.cmc.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.AccountGlobal;
@Repository
public interface AccountGlobalRepository extends JpaRepository<AccountGlobal, Long>, JpaSpecificationExecutor<AccountGlobal>{

}
