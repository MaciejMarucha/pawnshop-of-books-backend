package com.back;

import com.back.model.Book;
import com.back.model.User;
import com.back.repository.BookRepository;
import com.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EntityScan(basePackageClasses = {
        BackApplication.class,
        Jsr310JpaConverters.class
})
public class BackApplication {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            User user123 = new User("user123", "user123");
            User user1234 = new User("user1234", "user1234");
            user123.setPassword(passwordEncoder.encode(user123.getPassword()));
            user1234.setPassword(passwordEncoder.encode(user1234.getPassword()));

            userRepository.save(user123);
            userRepository.save(user1234);

            List<Book> list = new ArrayList<>();
            list.add(new Book("Laska", "Adam", "Wlazły", 2011, BigDecimal.valueOf(10.00), user123));
            list.add(new Book("Informatyka", "Leszek", "Barnaba", 2001, BigDecimal.valueOf(19.99), user123));
            list.add(new Book("Fizyka", "Ola", "Kuszner", 2000, BigDecimal.valueOf(20.00), user123));
            list.add(new Book("Plastyka", "Patrycja", "Martyniuk", 2010, BigDecimal.valueOf(26.99), user123));
            list.add(new Book("Geografia", "Marek", "Marczyński", 1998, BigDecimal.valueOf(30.99), user123));

            bookRepository.saveAll(list);
        };
    }

}

