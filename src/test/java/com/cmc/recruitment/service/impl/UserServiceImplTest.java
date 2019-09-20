//package com.cmc.recruitment.service.impl;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.cmc.recruitment.entity.User;
//import com.cmc.recruitment.service.UserService;
//import com.cmc.recruitment.utils.Constants;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UserServiceImplTest {
//  @Autowired
//  private UserService userService;
//  
//  @Test
//  @Transactional
//  @Rollback(true)
//  public void ChangePassword_Case1() {
//    userService.addUser(new User("userTest", "12345678", "test@cmc.com.vn"));
//    User user = userService.findByUsername("userTest");
//    String newPassword = "123123123";
//    String oldPass = "123456789";
//    int result = userService.updatePassword(user, oldPass, newPassword);
//    assertEquals(result, Constants.CHANGE_PASS_STATUS.INCORRECT_OLD_PASSWORD);
//  }
//
//  @Test
//  @Transactional
//  @Rollback(true)
//  public void ChangePassword_Case2() {
//    userService.addUser(new User("userTest", "12345678", "test@cmc.com.vn", true));
//    User user = userService.findByUsername("userTest");
//    String newPassword = "1234567890123456789012345678901234567890";
//    String oldPass = "12345678";
//    int result = userService.updatePassword(user, oldPass, newPassword);
//    assertEquals(Constants.CHANGE_PASS_STATUS.INVALID_PASS_LENGTH, result);
//  }
//
//  @Test
//  @Transactional
//  @Rollback(true)
//  public void ChangePassword_Case3() {
//    userService.addUser(new User("userTest", "12345678", "test@cmc.com.vn"));
//    User user = userService.findByUsername("userTest");
//    String newPassword = "123";
//    String oldPass = "12345678";
//    int result = userService.updatePassword(user, oldPass, newPassword);
//    assertEquals(result, Constants.CHANGE_PASS_STATUS.INVALID_PASS_LENGTH);
//  }
//
//  @Test
//  @Transactional
//  @Rollback(true)
//  public void ChangePassword_Case4() {
//    userService.addUser(new User("userTest", "12345678", "test@cmc.com.vn"));
//    User user = userService.findByUsername("userTest");
//    String newPassword = "12345678";
//    String oldPass = "12345678";
//    int result = userService.updatePassword(user, oldPass, newPassword);
//    assertEquals(result, Constants.CHANGE_PASS_STATUS.SAME_OLD_PASS);
//  }
//
//  @Test
//  @Transactional
//  @Rollback(true)
//  public void ChangePassword_Case5() {
//    userService.addUser(new User("userTest", "12345678", "test@cmc.com.vn"));
//    User user = userService.findByUsername("userTest");
//    String newPassword = "123456789";
//    String oldPass = "12345678";
//    int result = userService.updatePassword(user, oldPass, newPassword);
//    assertEquals(result, Constants.CHANGE_PASS_STATUS.SUCCESS);
//  }
//  
//  @Test
//  @Transactional
//  @Rollback(true)
//  public void testResetPassword_Case1(){
//    userService.addUser(new User("userTest", "12345678", "test@cmc.com.vn", true));
//    User user = new User();
//    user.setEmail("");
//    assertEquals(userService.resetPassword(user),1); 
//  }
//  
//  @Test
//  @Transactional
//  @Rollback(true)
//  public void testResetPassword_Case2(){
//    userService.addUser(new User("userTest", "12345678", "test@cmc.com.vn", true));
//    
//    User user = new User();
//    user.setEmail("test@cmc.com.vn");
//    assertEquals(userService.resetPassword(user),0); 
//  }
//}
