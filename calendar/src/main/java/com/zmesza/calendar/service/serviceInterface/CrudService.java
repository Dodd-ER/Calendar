package com.zmesza.calendar.service.serviceInterface;

public interface CrudService<T> extends CreateService<T>, UpdateService<T>, ReadService<T>, DeleteService<T>  {
}
