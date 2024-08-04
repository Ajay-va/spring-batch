package com.batch.processing.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {


    @Id
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String contactNo;
    private String dob;
    private String country;



}
