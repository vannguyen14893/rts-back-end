package com.cmc.recruitment.security.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.recruitment.repository.UserRepository;
import com.cmc.recruitment.security.exception.UserNotActivatedException;
import com.cmc.recruitment.entity.Role;
import com.cmc.recruitment.entity.User;

@Component("userDetailsService")
public class UserDetailsService
    implements org.springframework.security.core.userdetails.UserDetailsService {

  private final Logger   log = LoggerFactory.getLogger(UserDetailsService.class);

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(final String login) {

    log.debug("Authenticating {}", login);
    String lowercaseLogin = login.toLowerCase();

    User userFromDatabase;
    userFromDatabase = userRepository.findByUserNameCaseInsensitive(lowercaseLogin);

    if (userFromDatabase == null) {
      throw new UsernameNotFoundException(
          "User " + lowercaseLogin + " was not found in the database");
    } else if (userFromDatabase.getIsActive() == false) {
      throw new UserNotActivatedException("User " + lowercaseLogin + " is not activated");
    }
    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    for (Role role : userFromDatabase.getRoleCollection()) {
      GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRoleName());
      grantedAuthorities.add(grantedAuthority);
    }
    return new org.springframework.security.core.userdetails.User(userFromDatabase.getUsername(),
        userFromDatabase.getPassword(), grantedAuthorities);
  }

}
