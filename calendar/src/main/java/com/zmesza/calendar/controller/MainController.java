package com.zmesza.calendar.controller;

import com.zmesza.calendar.service.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  private EventServiceImpl service;

  @Autowired

  public MainController(EventServiceImpl service) {
    this.service = service;
  }

  @GetMapping("/")
  public String getMain() {
    return "index";
  }
}
