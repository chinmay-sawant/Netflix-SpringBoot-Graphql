package com.chinmay.coreservice.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinmay.coreservice.models.Show;
import com.chinmay.coreservice.repository.ShowRepository;

@Service
public class ShowService {
    
        @Autowired
        private ShowRepository showRepository;

        public Show getShowById(String showId) {
            Optional<Show> show = showRepository.findById(showId);
            return show.orElse(null);
        }

        public List<Show> getAllShows() {
            return showRepository.findAll();
        }

        public List<Show> getShowsByType(String type) {
            return showRepository.findByType(type);
        }
    }

