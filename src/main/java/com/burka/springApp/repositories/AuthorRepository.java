package com.burka.springApp.repositories;

import com.burka.springApp.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface AuthorRepository extends CrudRepository<Author, Long> {
    List<Author> findBylastName(String lastName);
}
