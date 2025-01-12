package com.example.performance_appraisal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "rating")
    private String rating;



    public Employee(){

    }

    public Employee(String name, String rating){
        this.name = name;
        this.rating = rating;

    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getRating(){
        return rating;
    }

    public void setRating(String rating){
        this.rating = rating;
    }



    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', rating='" + rating + "'}";
    }




}
