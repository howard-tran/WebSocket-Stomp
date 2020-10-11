package com.chat.Controllers;

import com.chat.App;
import com.chat.LogManager.LogUtils;
import com.chat.Models.User;
import com.chat.Services.UserService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
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

  @PostMapping(path = "/add")
  public Response<Object> AddUser(@RequestBody User user) {
    Optional<Object> res = userService.AddUser(user);

    if (res.isEmpty()) {
      return new Response<Object>("", ErrorType.INTERNAL_SERVER_ERROR);
    } else {
      return new Response<Object>(res.get(), ErrorType.OK);
    }
  }

  @GetMapping(path = "/find")
  public Response<Object> FindUser(
    @RequestParam(name = "searchkey", required = true) String searchKey
  ) {
    Optional<List<String>> res = userService.FindUser(searchKey);

    if (res.isEmpty()) {
      return new Response<Object>("", ErrorType.INTERNAL_SERVER_ERROR);
    } else {
      return new Response<Object>(res.get(), ErrorType.OK);
    }
  }

  @GetMapping(path = "/get")
  public Response<Object> GetUser(
    @RequestParam(name = "username", required = true) String userName
  ) {
    Optional<User> res = userService.GetUser(userName);

    if (res.isEmpty()) {
      return new Response<Object>("", ErrorType.INTERNAL_SERVER_ERROR);
    } else {
      return new Response<Object>(res.get(), ErrorType.OK);
    }
  }
}
