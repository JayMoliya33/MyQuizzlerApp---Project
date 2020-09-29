package com.example.myquizapp.subcategories;

public class SubCategoryModel {

    private String sublogo;
    private int sets;
    private String subtitle;

    public SubCategoryModel() {
        // Empty Constructor for Firebase
    }

    public SubCategoryModel(String sublogo, int sets, String subtitle) {
        this.sublogo = sublogo;
        this.sets = sets;
        this.subtitle = subtitle;
    }

    public String getSublogo() {
        return sublogo;
    }

    public void setSublogo(String sublogo) {
        this.sublogo = sublogo;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}
