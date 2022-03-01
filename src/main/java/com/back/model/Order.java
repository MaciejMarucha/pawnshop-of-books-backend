package com.back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;

@Table(name="orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId")
    private User userwhobuys;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "bookId")
    private Book book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserwhobuys() {
        return userwhobuys;
    }

    public void setUserwhobuys(User userwhobuys) {
        this.userwhobuys = userwhobuys;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
