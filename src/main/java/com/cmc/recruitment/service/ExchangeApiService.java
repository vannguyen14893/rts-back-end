package com.cmc.recruitment.service;

import java.io.IOException;

public interface ExchangeApiService {

  public String loginRecruit() throws IOException;
  public boolean logoutRecruit() throws Exception;
  public boolean publishRecruitContent(Long requestId) throws Exception;
}
