package com.burka.springApp.controllers;

import com.burka.springApp.domain.Publisher;
import com.burka.springApp.repositories.BookRepository;
import com.burka.springApp.repositories.PublisherRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class PublisherController {
    private final PublisherRepository publisherRepository;
    private final BookRepository bookRepository;

    public PublisherController(PublisherRepository publisherRepository, BookRepository bookRepository) {
        this.publisherRepository = publisherRepository;
        this.bookRepository = bookRepository;
    }
    @RequestMapping("/publishers")
    public String getPublishers(Model model){
        model.addAttribute("publishers", publisherRepository.findAll());
        return "publishers/list";
    }

    @RequestMapping("/publishers/add")
    public String getPublisherForAdd(Model model){
        return "publishers/addNew";
    }

    @PostMapping("/publishers/add")
    public String addPublisher(@RequestParam String name,
                               @RequestParam String addressLine1,
                               @RequestParam String city,
                               @RequestParam String state,
                               @RequestParam String zip,
                               Model model){
        Publisher publisher = new Publisher(name, addressLine1, city, state, zip);
        publisherRepository.save(publisher);
        return "redirect:/publishers";
    }

    @GetMapping("/publishers/{id}/edit")
    public String getPublisherEdit(@PathVariable(value = "id") long id, Model model) {
        Optional<Publisher> publisher = publisherRepository.findById(id);
        ArrayList<Publisher> res = new ArrayList<>();
        publisher.ifPresent(res::add);
        model.addAttribute("publisher", res);
        return "publishers/editForm";
    }

    @PostMapping("/publishers/{id}/edit")
    public String editPublisher(@PathVariable(value = "id") long id,
                                @RequestParam String name,
                                @RequestParam String addressLine1,
                                @RequestParam String city,
                                @RequestParam String state,
                                @RequestParam String zip,
                                Model model){
        Publisher publisher = publisherRepository.findById(id).orElseThrow();
        publisher.setName(name);
        publisher.setAddressLine1(addressLine1);
        publisher.setCity(city);
        publisher.setState(state);
        publisher.setZip(zip);
        publisherRepository.save(publisher);

        return "redirect:/publishers";
    }

    @PostMapping("/publishers/{id}/delete")
    public String deletePublisher(@PathVariable(value = "id") long id,
                                  Model model){
        Publisher publisher = publisherRepository.findById(id).orElseThrow();
        publisherRepository.delete(publisher);
        return "redirect:/publishers";
    }
}