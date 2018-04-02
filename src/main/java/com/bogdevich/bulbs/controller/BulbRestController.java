package com.bogdevich.bulbs.controller;

import com.bogdevich.bulbs.model.Bulb;
import com.bogdevich.bulbs.service.IBulbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        value = "/bulbs",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class BulbRestController {

    private final IBulbService bulbService;

    @Autowired
    public BulbRestController(IBulbService bulbService) {
        this.bulbService = bulbService;
    }

    @GetMapping
    public List<Bulb> getBulbs() {
        return bulbService.findAll();
    }
}
