package com.zmesza.calendar.service.serviceInterface;

import java.util.List;

public interface ReadService<T> {
  List<T> getAll();
  T getById(long id);
  boolean existsById(long id);
  boolean existsByDate(String date);
  T getByDate(String date);
  boolean isRestDay(String date) throws Exception;
  long howManyWorkDaysBetween(String date1, String date2);
  long howManyRestDaysBetween(String date1, String date2);
}
