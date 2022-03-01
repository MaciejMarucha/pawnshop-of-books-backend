package com.back.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.back.model.Book;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class BookResourceAssembler implements ResourceAssembler<Book, Resource<Book>> {

    @Override
    public Resource<Book> toResource(Book book) {

        // Unconditional links to single-item resource and aggregate root

        Resource<Book> bookResource = new Resource<>(book,
                linkTo(methodOn(BookController.class).getBook(book.getId())).withSelfRel(),
                linkTo(methodOn(BookController.class).getAllBooks()).withRel("books")
        );
        return bookResource;
    }
}
