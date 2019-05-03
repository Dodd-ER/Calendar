package com.zmesza.calendar.controller;

import com.zmesza.calendar.model.dto.EventDTO;
import com.zmesza.calendar.service.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.zmesza.calendar.service.date.DayManipulator.isValidDate;

@Controller
public class MainController {

  private EventServiceImpl service;

  @Autowired
  public MainController(EventServiceImpl service) {
    this.service = service;
  }

  @GetMapping("/")
  public String getMain(Model model) {
    model.addAttribute("event", new EventDTO());
    return "index";
  }

  @GetMapping("/date/{date}")
  public String getMainWithDate(Model model, @PathVariable(value = "date") String date) {
    if (isValidDate(date)) {
      model.addAttribute("event", service.getByDate(date));
    } else {
      return "redirect:/error?message=form" ;
    }
    return "index";
  }

  @PostMapping("/check")
  public String postCheckType(EventDTO eventDTO) {
    service.save(eventDTO);
    return "redirect:/date/" + eventDTO.getDate();
  }

  @GetMapping("/error")
  public String getErrorMessage(Model model, @RequestParam(value = "message", required= false) String message) {
    if (message.equals("form")) {
      model.addAttribute("message", "Not valid date form");
    } else {
      model.addAttribute("message", "Something went wrong");
    }
    return "error";
  }
}
