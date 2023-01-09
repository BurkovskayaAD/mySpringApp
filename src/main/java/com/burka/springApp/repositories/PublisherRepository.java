package com.burka.springApp.repositories;

import com.burka.springApp.domain.Publisher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
    List<Publisher> findByName(String name);
}
