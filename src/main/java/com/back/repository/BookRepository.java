package com.back.repository;

import com.back.model.Book;
import com.back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
    Optional<Book> findByAuthorLastName(@Param("authorLastName") String authorLastName);
    List<Book> findByWhoAddedIt(User userid);
    List<Book> findByWhoBoughtIt(User userid);

}
