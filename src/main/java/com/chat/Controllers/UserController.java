package com.chat.Controllers;

import com.chat.App;
import com.chat.Models.User;
import com.chat.Services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(App.API + "/user")
@RestController
public class UserController {
  private UserService userService;

  @Autowired
  UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/add")
  public Response<Object> AddUser(@RequestBody User user) {
    userService.AddUser(user);

    return new Response<Object>("", ErrorType.OK);
  }

  @GetMapping("/get")
  public Response<Object> FindUser(
    @RequestParam(name = "searchKey", required = true) String searchKey
  ) {
    List<String> res = userService.FindUser(searchKey);

    return new Response<Object>(res, ErrorType.OK);
  }

  @GetMapping("/getuser")
  public Response<Object> GetUser(
    @RequestParam(name = "userName", required = true) String userName
  ) {
    User res = userService.GetUser(userName);

    return new Response<Object>(res, ErrorType.OK);
  }
}
