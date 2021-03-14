package com.ep.repository;

import com.ep.model.Event;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface EventRepository extends CrudRepository<Event, Integer> {
    @Transactional(readOnly = true)
    @Cacheable("events")
    Collection<Event> findAll() throws DataAccessException;

}