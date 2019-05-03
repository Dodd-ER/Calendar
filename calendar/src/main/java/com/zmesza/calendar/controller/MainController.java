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
    model.addAttribute("event1", new EventDTO());
    model.addAttribute("event2", new EventDTO());
    model.addAttribute("event3", new EventDTO());
    return "index";
  }

  @GetMapping("/date/{date}")
  public String getMainWithDate(Model model, @PathVariable(value = "date") String date) {
    if (isValidDate(date)) {
      model.addAttribute("event1", service.getByDate(date));
      model.addAttribute("event2", new EventDTO());
      model.addAttribute("event3", new EventDTO());
    } else {
      return "redirect:/error?message=form" ;
    }
    return "index";
  }

  @GetMapping("/dates/{date1}/{date2}")
  public String getMainWithDate(Model model,
                                @PathVariable(value = "date1") String date1,
                                @PathVariable(value = "date2") String date2) {
    if (isValidDate(date1) && isValidDate(date2)) {
      model.addAttribute("event1", new EventDTO());
      model.addAttribute("event2", service.getByDate(date1));
      model.addAttribute("event3", service.getByDate(date2));
      model.addAttribute("restday", service.howManyRestDaysBetween(date1, date2));
      model.addAttribute("workday", service.howManyWorkDaysBetween(date1, date2));
    } else {
      return "redirect:/error?message=form" ;
    }
    return  "index";
  }

  @PostMapping("/check")
  public String postCheckType(EventDTO eventDTO) {
    service.save(eventDTO);
    return "redirect:/date/" + eventDTO.getDate();
  }

  @PostMapping("/check2")
  public String postCalculateDays(EventDTO eventDTO) {
    EventDTO eventDTO1 = new EventDTO(eventDTO.getDate().substring(0,11));
    EventDTO eventDTO2 = new EventDTO(eventDTO.getDate().substring(12,23));
    service.save(eventDTO1);
    service.save(eventDTO2);
    return "redirect:/dates/" + eventDTO1.getDate() + "/" + eventDTO2.getDate();
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
