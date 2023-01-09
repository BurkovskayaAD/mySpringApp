package com.burka.springApp.repositories;

import com.burka.springApp.domain.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByPublisher(Long publisher_id);
}
