package com.back.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookRequest {

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
    private String price;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}
