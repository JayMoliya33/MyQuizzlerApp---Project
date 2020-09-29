package com.example.myquizapp.Categories;

public class CategoryModel {

    private String logo;
    private String title;

    public CategoryModel() {
        // Empty Constructor for Firebase
    }

    public CategoryModel(String logo,String title) {
        this.logo = logo;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
