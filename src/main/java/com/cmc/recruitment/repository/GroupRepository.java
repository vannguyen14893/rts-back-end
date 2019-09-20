/**
 * 
 */
package com.cmc.recruitment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cmc.recruitment.entity.Group;

/**
 * @description: .
 * @author: LCNguyen
 * @created_date: Mar 6, 2018
 * @modifier: User
 * @modifier_date: Mar 6, 2018
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
