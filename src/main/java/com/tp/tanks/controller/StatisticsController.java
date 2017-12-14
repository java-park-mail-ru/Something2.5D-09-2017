package com.tp.tanks.controller;

import com.tp.tanks.models.Statistic;
import com.tp.tanks.services.StatisticsService;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping(value = "/api")
public class StatisticsController {

    private StatisticsService statisticsService;
    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsController.class);
    private static final Integer DEFAULT_LIMIT = 5;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @CrossOrigin
    @RequestMapping(value = "/top", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getTop(@RequestParam(value = "limit", required = false) Integer limit) throws JSONException {
        if (limit == null) {
            limit = DEFAULT_LIMIT;
        }

        final List<Statistic> responseStatistics = statisticsService.getTop(limit);
        if (responseStatistics == null) {
            LOGGER.error("[getTop] top == null");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(Statistic.getJsonArray(responseStatistics).toString(), HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value = "/statistic", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getStatistic(HttpSession session) throws JSONException {

        try {
            final Object sessionObject = session.getAttribute("userId");

            if (sessionObject == null) {
                LOGGER.info("[getStatistic] Can't find userId in session");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Long userId = (Long) sessionObject;

            final Statistic responseStatistics = statisticsService.statistic(userId);
            if (responseStatistics == null) {
                LOGGER.info("[getStatistic] responseStatistics == null");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(responseStatistics.getJson().toString(), HttpStatus.OK);

        } catch (ClassCastException ex) {
            LOGGER.error("[getStatistic] ClassCastException exception" + ex);
            session.removeAttribute("userId");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
