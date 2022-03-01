package com.back.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String title;

    @NotBlank
    @Size(max = 30)
    private String authorFirstName;

    @NotBlank
    @Size(max = 30)
    private String authorLastName;

    @NotNull
    private Integer theYearOfPublishment;

    @NotNull
    private BigDecimal price;

    private Boolean bought = false;

    @JsonBackReference
    @OneToOne(mappedBy = "book")
    private Order transaction;

    @ManyToOne
    @JoinColumn(name="whoAddedIt_Id")
    @JsonBackReference
    private User whoAddedIt;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "whoBoughtIt")
    private User whoBoughtIt;

    public Book(){}

    public Book(@NotBlank @Size(max = 50) String title, @NotBlank @Size(max = 30) String authorFirstName, @NotBlank @Size(max = 30) String authorLastName, @NotNull Integer theYearOfPublishment, @NotNull BigDecimal price, User whoAddedIt) {
        this.title = title;
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.theYearOfPublishment = theYearOfPublishment;
        this.price = price;
        this.whoAddedIt = whoAddedIt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public Integer getTheYearOfPublishment() {
        return theYearOfPublishment;
    }

    public void setTheYearOfPublishment(Integer theYearOfPublishment) {
        this.theYearOfPublishment = theYearOfPublishment;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getBought() {
        return bought;
    }

    public void setBought(Boolean bought) {
        this.bought = bought;
    }

    public Order getTransaction() {
        return transaction;
    }

    public void setTransaction(Order transaction) {
        this.transaction = transaction;
    }

    public User getWhoAddedIt() {
        return whoAddedIt;
    }

    public void setWhoAddedIt(User whoAddedIt) {
        this.whoAddedIt = whoAddedIt;
    }

    public User getWhoBoughtIt() {
        return whoBoughtIt;
    }

    public void setWhoBoughtIt(User whoBoughtIt) {
        this.whoBoughtIt = whoBoughtIt;
    }
}

