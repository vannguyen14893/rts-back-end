package com.cmc.recruitment.utils;

import java.util.List;

import com.cmc.recruitment.entity.Request;

public class ReportDTO {
  private String title;
  private List<Request> requestCollections;
  private int orderNumber;
  private int orderHrNumber;
  private int contacting;
  private int interview;
  private int passed;
  private int offer;
  private int onboard;
  private int lackNumber;
  private double ratio;

  public ReportDTO() {
  }

  public ReportDTO(String title, List<Request> requestCollections, int orderNumber,
      int orderHrNumber, int contacting, int interview, int passed, int offer, int onboard,
      int lack, double ratio) {
    this.title = title;
    this.requestCollections = requestCollections;
    this.orderNumber = orderNumber;
    this.orderHrNumber = orderHrNumber;
    this.contacting = contacting;
    this.interview = interview;
    this.passed = passed;
    this.offer = offer;
    this.onboard = onboard;
    this.lackNumber = lack;
    this.ratio = ratio;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<Request> getRequestCollections() {
    return requestCollections;
  }

  public void setRequestCollections(List<Request> requestCollections) {
    this.requestCollections = requestCollections;
  }

  public int getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(int orderNumber) {
    this.orderNumber = orderNumber;
  }

  public int getRequestHRNumber() {
    return orderHrNumber;
  }

  public void setRequestHRNumber(int orderHrNumber) {
    this.orderHrNumber = orderHrNumber;
  }

  public int getContacting() {
    return contacting;
  }

  public void setContacting(int contacting) {
    this.contacting = contacting;
  }

  public int getInterview() {
    return interview;
  }

  public void setInterview(int interview) {
    this.interview = interview;
  }

  public int getPassed() {
    return passed;
  }

  public void setPassed(int passed) {
    this.passed = passed;
  }

  public int getOffer() {
    return offer;
  }

  public void setOffer(int offer) {
    this.offer = offer;
  }

  public int getOnboard() {
    return onboard;
  }

  public void setOnboard(int onboard) {
    this.onboard = onboard;
  }

  public int getLack() {
    return lackNumber;
  }

  public void setLack(int lack) {
    this.lackNumber = lack;
  }

  public double getRatio() {
    return ratio;
  }

  public void setRatio(double ratio) {
    this.ratio = ratio;
  }

}
