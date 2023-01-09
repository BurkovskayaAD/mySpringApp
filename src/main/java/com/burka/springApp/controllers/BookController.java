package com.burka.springApp.controllers;

import com.burka.springApp.domain.Author;
import com.burka.springApp.domain.Book;
import com.burka.springApp.domain.Publisher;
import com.burka.springApp.repositories.AuthorRepository;
import com.burka.springApp.repositories.BookRepository;
import com.burka.springApp.repositories.PublisherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookController(PublisherRepository publisherRepository, AuthorRepository authorRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @RequestMapping("/books")
    public String getBooks(Model model){
        model.addAttribute("books", bookRepository.findAll());
        return "books/list";
    }

    @RequestMapping("/books/add")
    public String getPublisherAndAuthorForAdd(Model model){
        model.addAttribute("publishers", publisherRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
        return "books/addNew";
    }

    @PostMapping("/books/add")
    public String addNewBook(@RequestParam String nameBook,
                             @RequestParam String isbnBook,
                             @RequestParam String authorBook,
                             @RequestParam String publisherBook,
                             Model model){
        Book book = new Book(nameBook, isbnBook);
        List<Publisher> findPublisher = publisherRepository.findByName(publisherBook);
        String[] words = authorBook.split("\\s");
        List<Author> findAuthor = authorRepository.findBylastName(words[1]);
        book.getAuthors().add(findAuthor.get(0));
        book.setPublisher(findPublisher.get(0));
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/edit")
    public String getBookEdit(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("publishers", publisherRepository.findAll());
        model.addAttribute("authors", authorRepository.findAll());
        Optional<Book> book = bookRepository.findById(id);
        ArrayList<Book> res = new ArrayList<>();
        book.ifPresent(res::add);
        model.addAttribute("book", res);
        return "books/editForm";
    }

    @PostMapping("/books/{id}/edit")
    public String editBook(@PathVariable(value = "id") long id,
                           @RequestParam String nameBook,
                           @RequestParam String isbnBook,
                           @RequestParam String authorBook,
                           @RequestParam String publisherBook,
                           Model model){
        Book book = bookRepository.findById(id).orElseThrow();
        book.setTitle(nameBook);
        book.setIsbn(isbnBook);
        List<Publisher> findPublisher = publisherRepository.findByName(publisherBook);

        String[] words = authorBook.split("\\s");
        List<Author> findAuthor = authorRepository.findBylastName(words[1]);
        book.getAuthors().clear();
        book.getAuthors().add(findAuthor.get(0));

        book.setPublisher(findPublisher.get(0));
        bookRepository.save(book);

        return "redirect:/books";
    }

    @PostMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable(value = "id") long id,
                             Model model){
        Book book = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(book);
        return "redirect:/books";
    }
}
