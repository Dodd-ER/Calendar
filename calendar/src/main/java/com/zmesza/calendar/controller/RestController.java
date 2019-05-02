package com.zmesza.calendar.controller;

import com.zmesza.calendar.exception.GeneralException;
import com.zmesza.calendar.model.dto.EventDTO;
import com.zmesza.calendar.service.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.web.bind.annotation.RestController
public class RestController {

  private EventServiceImpl service;

  @Autowired
  public RestController(EventServiceImpl service) {
    this.service = service;
  }

  @GetMapping("api/action")
  public ResponseEntity getAction(
      @RequestParam(defaultValue = "false", required = false) boolean isWorkingDay
      , @RequestParam(defaultValue = "false", required = false) boolean isRestDay
      , @RequestParam String date1
      , @RequestParam(required = false) String date2) throws Exception {

    throw new GeneralException("Something went wrong", HttpStatus.BAD_REQUEST);

  }

  @PostMapping("api/action")
  public void PostAction(
      @RequestParam boolean changeTypeToRest
      , @RequestParam String date) throws Exception {

    try {
      if (service.existsByDate(date)) {
        if (changeTypeToRest && !service.getByDate(date).isRestDay()) {
          service.updateByDate(date, true);
        }  else if (!changeTypeToRest && service.getByDate(date).isRestDay()) {
          service.updateByDate(date, false);
        }
      } else if (changeTypeToRest) {
        service.save(new EventDTO(date, true));
      } else {
        service.save(new EventDTO(date, false));
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new GeneralException("GeneralException", HttpStatus.BAD_REQUEST);
    }
  }
}
