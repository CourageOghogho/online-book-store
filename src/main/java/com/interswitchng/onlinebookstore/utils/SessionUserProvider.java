package com.interswitchng.onlinebookstore.utils;

import com.interswitchng.onlinebookstore.model.User;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

@Service
public class SessionUserProvider {
  @Value("${simulation.loggedIn.user}")
  private Integer loggedInUserId;

  //this should ideally get the user from userService management/passport service
  public User getSessionUser(){

    var user= new User();
    user.setUserName("Courage");
    user.setId(loggedInUserId);
    user.setCreatedDate(new Date());
    return  user;
  }

}
