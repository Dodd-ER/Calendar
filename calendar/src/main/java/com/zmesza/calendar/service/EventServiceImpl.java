package com.zmesza.calendar.service;

import com.zmesza.calendar.exception.GeneralException;
import com.zmesza.calendar.model.entity.Event;
import com.zmesza.calendar.service.date.GregorianDateMatcher;
import org.modelmapper.ModelMapper;
import com.zmesza.calendar.model.dto.EventDTO;
import com.zmesza.calendar.repostiory.EventRepository;
import com.zmesza.calendar.service.serviceInterface.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements CrudService<EventDTO>{

private EventRepository repository;
private ModelMapper mapper;
private GregorianDateMatcher gregorianDateMatcher;

@Autowired
  public EventServiceImpl(EventRepository repository, ModelMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
    this.gregorianDateMatcher = new GregorianDateMatcher();
  }

  @Override
  public void save(EventDTO dto){
    if (gregorianDateMatcher.matches(dto.getDate()) && !repository.existsByDate(dto.getDate())){
      repository.save(mapper.map(dto, Event.class));
    }
  }

  @Override
  public void deleteById(long id) {
    repository.deleteById(id);
  }

  @Override
  public List<EventDTO> getAll() {
    List<EventDTO> eventDTOs = new ArrayList<>();
    List<Event> events = repository.findAll();

    for (Event event : events) {
      eventDTOs.add(mapper.map(event, EventDTO.class));
    }

    return eventDTOs;
  }

  @Override
  public EventDTO getById(long id) {
    return mapper.map(repository.findById(id), EventDTO.class);

  }

  @Override
  public boolean existsById(long id) {
    return repository.existsById(id);
  }

  @Override
  public EventDTO getByDate(String date) {
    return mapper.map(repository.findByDate(date), EventDTO.class);
  }

  @Override
  public void updateById(long id, EventDTO dto) {
    Event event = repository.findById(id);
    mapper.map(dto, event);
    repository.save(event);
  }

  @Override
  public void updateByDate(String date, boolean isRestDay) {
    Event event = repository.findByDate(date);
    event.setRestDay(isRestDay);
    repository.save(event);
  }

  public boolean existsByDate(String date) {
    return repository.existsByDate(date);
  }

}
