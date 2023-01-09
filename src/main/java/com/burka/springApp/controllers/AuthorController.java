package com.burka.springApp.controllers;

import com.burka.springApp.domain.Author;
import com.burka.springApp.repositories.AuthorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }
    @RequestMapping("/authors")
    public String getAuthors(Model model){
        model.addAttribute("authors", authorRepository.findAll());
        return "authors/list";
    }

    @RequestMapping("/authors/add")
    public String getAuthorForAdd(Model model){
        return "authors/addNew";
    }

    @PostMapping("/authors/add")
    public String addAuthor(@RequestParam String firstName,
                            @RequestParam String lastName,
                            Model model){
        Author author = new Author(firstName, lastName);
        authorRepository.save(author);
        return "redirect:/authors";
    }

    @GetMapping("/authors/{id}/edit")
    public String getAuthorEdit(@PathVariable(value = "id") long id, Model model) {
        Optional<Author> author = authorRepository.findById(id);
        ArrayList<Author> res = new ArrayList<>();
        author.ifPresent(res::add);
        model.addAttribute("author", res);
        return "authors/editForm";
    }

    @PostMapping("/authors/{id}/edit")
    public String editAuthor(@PathVariable(value = "id") long id,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             Model model){
        Author author = authorRepository.findById(id).orElseThrow();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        authorRepository.save(author);

        return "redirect:/authors";
    }

    @PostMapping("/authors/{id}/delete")
    public String deleteAuthor(@PathVariable(value = "id") long id,
                               Model model){
        Author author = authorRepository.findById(id).orElseThrow();
        System.out.println(author);
        authorRepository.delete(author);
        return "redirect:/authors";
    }
}