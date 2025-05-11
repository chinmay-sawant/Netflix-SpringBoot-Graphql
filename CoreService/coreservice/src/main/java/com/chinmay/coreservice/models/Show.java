package com.chinmay.coreservice.models;

import java.time.LocalDate; // Use jakarta.persistence for Spring Boot 3+

import jakarta.persistence.Column; // Or java.util.Date
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shows") // Good practice to explicitly name the table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Show {

    @Id
    private String showId;

    private String type;
    private String title;
    private String director;

    @Column(name = "`cast`", columnDefinition = "TEXT") // Escaping the column name
    private String cast;

    private String country;
    private LocalDate dateAdded;
    private Integer releaseYear;
    private String rating;
    private String duration;
    private String listedIn;

    @Column(columnDefinition = "TEXT")
    private String description;
}
