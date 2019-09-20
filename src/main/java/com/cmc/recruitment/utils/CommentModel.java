package com.cmc.recruitment.utils;

import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;

@JsonSerialize
@Getter
@Setter
public class CommentModel {

  private Long id;
  private String commentDetail;
  private String userName;
  private String interviewTitle;
  private Date createDate;
}
