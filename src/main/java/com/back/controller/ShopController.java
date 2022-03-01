package com.back.controller;

import com.back.repository.BookRepository;
import com.back.repository.OrderRepository;
import com.back.repository.UserRepository;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/buy")
@RestController
public class ShopController {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    public ShopController(BookRepository bookRepository, UserRepository userRepository,OrderRepository orderRepository){
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

//    @PostMapping("/{bookId}")
//    public ResponseEntity<?> buyBook(@Valid @RequestBody BookRequest bookRequest, @PathVariable Long bookId, @CurrentUser UserPrincipal currentUser) {
//        System.out.println(bookRequest.getTitle());
//        Optional<Book> optionalBook = bookRepository.findByTitle(bookRequest.getTitle());
//        if(!optionalBook.isPresent()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"not found book"));
//        }
//        Book book = optionalBook.get();
//        //sprawdzenie danych ksiazki a konkretnie whoAddad createdBy
//        System.out.println("wyswietlenie stringa ksiazki == "+book);
//        Order order = new Order();
//
//        if(book.getWhoAddedIt().getId().equals(currentUser.getId())){
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false,"it is my book"));
//        }
//        book.setBought(true);
//        User user = userRepository.getOne(currentUser.getId());
//        book.setWhoBoughtIt(user);
//        book.setTransaction(order);
//
//        order.setBook(book);
//        order.setUserwhobuys(user);
//
//        orderRepository.save(order);
//        bookRepository.save(book);
//
//        return new ResponseEntity(new ApiResponse(true, "\n" +
//                "The book was bought"),HttpStatus.OK);
//    }
}
