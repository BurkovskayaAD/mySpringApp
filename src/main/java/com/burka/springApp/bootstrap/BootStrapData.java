package com.burka.springApp.bootstrap;

import com.burka.springApp.domain.Author;
import com.burka.springApp.domain.Book;
import com.burka.springApp.domain.Publisher;
import com.burka.springApp.repositories.AuthorRepository;
import com.burka.springApp.repositories.BookRepository;
import com.burka.springApp.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Started in Bootstrap");

        Publisher publisher1 = new Publisher();
        publisher1.setName("АСТ");
        publisher1.setCity("Москва");

        publisherRepository.save(publisher1);

        System.out.println("Publisher Count: " + publisherRepository.count());

        Author author1 = new Author("Пауло", "Коэльо");
        Book book1 = new Book("Вероника решает умереть", "9785170892334");
        author1.getBooks().add(book1);
        book1.getAuthors().add(author1);

        book1.setPublisher(publisher1);
        publisher1.getBooks().add(book1);

        authorRepository.save(author1);
        bookRepository.save(book1);
        publisherRepository.save(publisher1);

        Author author2 = new Author("Дэниел", "Киз");
        Book book2 = new Book("Цветы для Элджернона", "9785699787623");
        author2.getBooks().add(book2);
        book2.getAuthors().add(author2);

        book2.setPublisher(publisher1);
        publisher1.getBooks().add(book2);

        authorRepository.save(author2);
        bookRepository.save(book2);
        publisherRepository.save(publisher1);

        Book book3 = new Book("Алхимик", "9785170879212");
//        author1.getBooks().add(book3);
        book3.getAuthors().add(author1);

        book3.setPublisher(publisher1);
//        publisher1.getBooks().add(book3);

        authorRepository.save(author1);
        bookRepository.save(book3);
        publisherRepository.save(publisher1);


        System.out.println("Number of Books: " + bookRepository.count());
        System.out.println("Publisher Number of Books: " + publisher1.getBooks().size());
    }
}

