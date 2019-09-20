package com.cmc.recruitment.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account_global")
public class AccountGlobal implements Serializable {
  private static final long serialVersionUID = 2058138951259242655L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @NotEmpty
  @Column(name = "name")
  private String name;

  @NotEmpty
  @Column(name = "pass")
  private String pass;

  @Column(name = "csrf_token")
  private String csrfToken;

  @Column(name = "logout_token")
  private String logoutToken;

  @NotEmpty
  @Column(name = "base_url")
  private String baseUrl;

  public AccountGlobal(String username, String password) {
    this.name = username;
    this.pass = password;
  }
  public AccountGlobal(Long id, String username, String password, String baseUrl) {
    this.id = id;
    this.name = username;
    this.pass = password;
    this.baseUrl = baseUrl;
  }
  public AccountGlobal(Long id, String username, String password, String csrfToken,
      String logoutToken, String baseUrl) {
    this.id = id;
    this.name = username;
    this.pass = password;
    this.csrfToken = csrfToken;
    this.logoutToken = logoutToken;
    this.baseUrl = baseUrl;
  }
}
