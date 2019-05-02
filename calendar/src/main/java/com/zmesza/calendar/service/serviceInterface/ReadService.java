package com.zmesza.calendar.service.serviceInterface;

import java.util.List;

public interface ReadService<T> {
  List<T> getAll();
  T getById(long id);
  boolean existsById(long id);

  T getByDate(String date);
}
