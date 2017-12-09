package com.tp.tanks.controller;

import com.tp.tanks.models.Statistic;
import com.tp.tanks.services.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api")
public class StatisticsController {

    private StatisticsService statisticsService;
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @CrossOrigin
    @RequestMapping(value = "/top", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Statistic> getTop(@PathVariable(name = "limit") Integer limit) {
        if (limit == null) {
            limit = 5;
        }

        final Statistic statistic = statisticsService.getTop(limit);
        if (statistic == null) {
            LOGGER.error("[getTop] top == null");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(statistic, HttpStatus.OK);
    }
}
