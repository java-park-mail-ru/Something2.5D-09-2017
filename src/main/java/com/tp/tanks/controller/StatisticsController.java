package com.tp.tanks.controller;

import com.tp.tanks.models.Statistic;
import com.tp.tanks.models.User;
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

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @CrossOrigin
    @RequestMapping(value = "/top", method = RequestMethod.GET, produces = "application/json")
//    public ResponseEntity<Statistic> getTop(@PathVariable(name = "limit") Integer limit) {
    public ResponseEntity<?> getTop() throws JSONException {
//        if (limit == null) {
//            limit = 5;
//        }
        Integer limit = 5;

        final List<Statistic> responseStatistics = statisticsService.getTop(limit);
        if (responseStatistics == null) {
            LOGGER.error("[getTop] top == null");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(responseStatistics, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value = "/position", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getPosition(HttpSession session) throws JSONException {

        try {
            final Object sessionObject = session.getAttribute("userId");

            if (sessionObject == null) {
                LOGGER.debug("[getPosition] Can't find userId in session");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Long userId = (Long) sessionObject;

            final Statistic responseStatistics = statisticsService.position(userId);
            if (responseStatistics == null) {
                LOGGER.error("[getPosition] responseStatistics == null");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            return new ResponseEntity<>(responseStatistics, HttpStatus.OK);

        } catch (ClassCastException ex) {
            LOGGER.error("[getProfile] ClassCastException exception" + ex);
            session.removeAttribute("userId");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
