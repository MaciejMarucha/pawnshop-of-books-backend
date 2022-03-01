package com.back.controller;

import com.back.AppConstants;
import com.back.model.Book;
import com.back.repository.BookRepository;
import com.back.repository.UserRepository;
import com.back.security.UserPrincipal;
import com.back.exception.BookNotFoundException;
import com.back.payload.BookRequest;
import com.back.security.CurrentUser;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@CrossOrigin(origins = "http://localhost:3001/books")
@RestController
@RequestMapping("/books")
public class BookController {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BookResourceAssembler assembler;

    public BookController(BookRepository bookRepository, UserRepository userRepository, BookResourceAssembler assembler) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.assembler = assembler;
    }

    @GetMapping()
    public Resources<Resource<Book>> getAllBooks() {

        List<Resource<Book>> books = bookRepository.findAll().stream()
                .filter(e -> !e.getBought())
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(books,
                linkTo(methodOn(BookController.class).getAllBooks()).withSelfRel());
    }

    @GetMapping("/{bookId}")
    public Resource<Book> getBook(@PathVariable Long bookId) {
        return assembler.toResource(
                bookRepository.findById(bookId)
                        .orElseThrow(() -> new BookNotFoundException(bookId)));
    }

    @PostMapping("/new")
    public ResponseEntity<?> addBook(@Valid @RequestBody BookRequest bookRequest, @CurrentUser UserPrincipal currentUser) {

        if (validation(bookRequest)) {
            //to remove 00.XX to 0.XX or 000.XX to 0.XX
            float parsedPrice = Float.parseFloat(bookRequest.getPrice());
            BigDecimal properPrice = BigDecimal.valueOf(parsedPrice);
            Book book = new Book();
            book.setTitle(bookRequest.getTitle());
            book.setAuthorFirstName(bookRequest.getAuthorFirstName());
            book.setAuthorLastName(bookRequest.getAuthorLastName());
            book.setTheYearOfPublishment(bookRequest.getTheYearOfPublishment());
            book.setPrice(properPrice);
            book.setWhoAddedIt(userRepository.findById(currentUser.getId()).get());
            bookRepository.save(book);
            return ResponseEntity
                    .created(linkTo(methodOn(BookController.class).getBook(book.getId())).toUri())
                    .body(assembler.toResource(book));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect data!");
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable Long bookId, @CurrentUser UserPrincipal currentUser) {

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        if (!book.getWhoAddedIt().getId().equals(currentUser.getId())) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("This is not my book");
        }
        bookRepository.deleteById(bookId);
        return ResponseEntity.ok().body("Book deleted");
    }

    @PutMapping("/{bookid}")
    public ResponseEntity<?> updateBook(@Valid @RequestBody BookRequest bookRequest,
                                        @PathVariable Long bookid,
                                        @CurrentUser UserPrincipal currentUser) throws URISyntaxException {
        Optional<Book> updatedBook = bookRepository.findById(bookid);
        if (updatedBook.isPresent()) {
            Book book = updatedBook.get();
            if (book.getWhoAddedIt().getId().equals(currentUser.getId())) {
                if (validation(bookRequest)) {
                    book.setTitle(bookRequest.getTitle());
                    book.setAuthorFirstName(bookRequest.getAuthorFirstName());
                    book.setAuthorLastName(bookRequest.getAuthorLastName());
                    book.setTheYearOfPublishment(bookRequest.getTheYearOfPublishment());
                    float parsedPrice = Float.parseFloat(bookRequest.getPrice());
                    book.setPrice(BigDecimal.valueOf(parsedPrice));
                    bookRepository.save(book);

                    Resource<Book> resource = assembler.toResource(book);
                    return ResponseEntity
                            .created(new URI(resource.getId().expand().getHref()))
                            .body("Book updated!");
                } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect data!");
            } else {
                return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("This is not my book!");
            }
        } else
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body("There is no such book!");
    }

    private boolean validation(BookRequest bookRequest) {
        String regexpTitle = "^([a-zA-Z0-9ĄĆĘŁŃÓŚŹŻąćęłńóśźż]+[\\s]?)+$";
        String regexpFirstName = "^[a-zA-Z0-9ĆŁŃŚŹŻąćęłńóśźż]+$";
        String regexpLastName = "^[a-zA-Z0-9ĆŁŃŚŹŻąćęłńóśźż]+$";
        String regexpPrice = "^[0-9]{1,3}[.]?[0-9]{1,2}$";
        boolean bookRequestIsValid = true;
        if (!bookRequest.getTitle().matches(regexpTitle) || bookRequest.getTitle().length() < AppConstants.MIN_TITLE_LENGTH || bookRequest.getTitle().length() > AppConstants.MAX_TITLE_LENGTH) {
            bookRequestIsValid = false;
        } else if (!bookRequest.getAuthorFirstName().matches(regexpFirstName) || bookRequest.getAuthorFirstName().length() < AppConstants.MIN_FIRSTNAME_LENGTH || bookRequest.getAuthorFirstName().length() > AppConstants.MAX_FIRSTNAME_LENGTH) {
            bookRequestIsValid = false;
        } else if (!bookRequest.getAuthorLastName().matches(regexpLastName) || bookRequest.getAuthorLastName().length() < AppConstants.MIN_LASTNAME_LENGTH || bookRequest.getAuthorLastName().length() > AppConstants.MAX_LASTNAME_LENGTH) {
            bookRequestIsValid = false;
        } else if (bookRequest.getTheYearOfPublishment() > AppConstants.CURRENT_YEAR || bookRequest.getTheYearOfPublishment() < 1900) {
            bookRequestIsValid = false;
        } else if (!bookRequest.getPrice().matches(regexpPrice)) {
            bookRequestIsValid = false;
        } else if (Float.parseFloat(bookRequest.getPrice()) > 200.00 || Float.parseFloat(bookRequest.getPrice()) < 0.01) {
            bookRequestIsValid = false;
        }
        return bookRequestIsValid;
    }
}
