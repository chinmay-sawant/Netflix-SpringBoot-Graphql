package com.chinmay.coreservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chinmay.coreservice.models.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, String> {
    // Spring Data JPA provides basic CRUD operations (save, findById, findAll, etc.)
    List<Show> findByType(String type);
} 
