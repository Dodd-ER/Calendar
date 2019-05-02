package com.zmesza.calendar.service.serviceInterface;

public interface UpdateService<T> {
  void updateById(long id, T dto);
  void updateByDate(String date, boolean isRestDay);
}
