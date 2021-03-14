package com.ep.repository;

import com.ep.model.Result;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface ResultRepository extends CrudRepository<Result, Integer> {
    @Transactional(readOnly = true)
    @Cacheable("results")
    Collection<Result> findAll() throws DataAccessException;

}