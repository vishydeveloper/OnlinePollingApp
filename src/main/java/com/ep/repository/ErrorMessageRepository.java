package com.ep.repository;

import com.ep.model.ErrorMessage;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface ErrorMessageRepository extends CrudRepository<ErrorMessage, Integer> {
    @Transactional(readOnly = true)
    @Cacheable("errormessages")
    Collection<ErrorMessage> findAll() throws DataAccessException;

}