package com.back.payload;

public class UserProfile {
    private Long id;
    private String username;
    private Long booksAddedCount;
    private Long booksBoughtCount;


    public UserProfile(Long id, String username, Long booksAddedCount, Long booksBoughtCount) {
        this.id = id;
        this.username = username;
        this.booksAddedCount = booksAddedCount;
        this.booksBoughtCount = booksBoughtCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getBooksAddedCount() {
        return booksAddedCount;
    }

    public void setBooksAddedCount(Long booksAddedCount) {
        this.booksAddedCount = booksAddedCount;
    }

    public Long getBooksBoughtCount() {
        return booksBoughtCount;
    }

    public void setBooksBoughtCount(Long booksBoughtCount) {
        this.booksBoughtCount = booksBoughtCount;
    }
}
