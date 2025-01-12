package com.example.performance_appraisal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "rating_category")
public class RatingCategory {

    @Id
    private String rating;

    private String category;
    private double standardPercentage;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating){
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public double getStandardPercentage() {
        return standardPercentage;
    }

    public void setStandardPercentage(double standardPercentage){
        this.standardPercentage = standardPercentage;
    }
}
