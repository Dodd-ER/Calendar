package com.zmesza.calendar.model.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.zmesza.calendar.service.date.DayCounter.getDayCount;

public class EventDTO {

  private String date;
  private long dayInTheYear;
  private boolean isRestDay;

  public EventDTO() {
  }

  public EventDTO(String date) {
    this.date = date;
    this.dayInTheYear = getDayCount(date.substring(0,4).concat(".01.01."), date);
    this.isRestDay = false;
  }

  public EventDTO(String date, boolean isRestDay) {
    this.date = date;
    this.dayInTheYear = getDayCount(date.substring(0,4).concat(".01.01."), date);
    this.isRestDay = isRestDay;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public long getDayInTheYear() {
    return dayInTheYear;
  }

  public void setDayInTheYear(long dayInTheYear) {
    this.dayInTheYear = dayInTheYear;
  }

  public boolean isRestDay() {
    return isRestDay;
  }

  public void setRestDay(boolean restDay) {
    isRestDay = restDay;
  }
}
