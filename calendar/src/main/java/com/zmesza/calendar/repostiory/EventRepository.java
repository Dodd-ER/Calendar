package com.zmesza.calendar.repostiory;

import com.zmesza.calendar.model.entity.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
  boolean existsByDate(String date);
  List<Event> findAll();
  Event findById(long id);
  Event findByDate(String date);
}
