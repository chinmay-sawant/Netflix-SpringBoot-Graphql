package com.chinmay.coreservice.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinmay.coreservice.models.Show;
import com.chinmay.coreservice.services.ShowService;

@RestController
public class ShowController {
    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @QueryMapping
    public Show getShowById(@Argument String showId) {
        return showService.getShowById(showId);
    }

    @QueryMapping
    public List<Show> getAllShows() {
        return showService.getAllShows();
    }

    @QueryMapping
    public List<Show> getShowsByType(@Argument String type) {
        return showService.getShowsByType(type);
    }
}

