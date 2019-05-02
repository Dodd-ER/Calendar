package com.zmesza.calendar.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Event {

  @Id
  @GeneratedValue
  private long id;
  private String date;
  private long dayInTheYear;
  private boolean isRestDay;

  public Event() {
  }

  public Event(String date) {
    this.date = date;
    this.dayInTheYear = 0;
    this.isRestDay = false;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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
