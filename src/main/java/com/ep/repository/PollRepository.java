package com.ep.repository;

import com.ep.model.Poll;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface PollRepository extends CrudRepository<Poll, Integer> {
    @Transactional(readOnly = true)
    @Cacheable("polls")
    Collection<Poll> findAll() throws DataAccessException;

}