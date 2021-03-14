package com.ep.repository;

import com.ep.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface UserRepository extends CrudRepository<User, Integer> {
    @Transactional(readOnly = true)
    @Cacheable("users")
    Collection<User> findAll() throws DataAccessException;
}