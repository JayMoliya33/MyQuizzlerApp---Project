package com.example.myquizapp.Categories;

public class CategoryModel {

    private String name;
    private int sets;
    private String url;

    public CategoryModel() {
        // Empty Constructor for Firebase
    }

    public CategoryModel(String name, int sets, String url) {
        this.name = name;
        this.sets = sets;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
