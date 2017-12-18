package com.tp.tanks.controller;

import com.tp.tanks.factories.Generators;
import com.tp.tanks.factories.ScoreFactory;
import com.tp.tanks.factories.UserFactory;
import com.tp.tanks.mechanics.world.Scores;
import com.tp.tanks.models.Statistic;
import com.tp.tanks.models.User;
import com.tp.tanks.services.StatisticsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public class StatisticControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticControllerTest.class);

    @Autowired
    private StatisticsService statisticsService;

    private List<String> getCookie(ResponseEntity<User> user) {
        List<String> cookie = user.getHeaders().get("Set-Cookie");

        if (cookie == null) {
            cookie = new ArrayList<>();
        }
        return cookie;
    }

    private HttpEntity getEntity(List<String> cookie) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put(HttpHeaders.COOKIE, cookie);

        return new HttpEntity(requestHeaders);
    }

    private Map<String, String> generateUserMap(User user) {
        final Map<String, String> parts = new HashMap<>();
        parts.put("username", user.getUsername());
        parts.put("email", user.getEmail());
        parts.put("password", user.getPassword());
        parts.put("mouseControlEnabled", user.getMouseControlEnabled().toString());
        return parts;
    }

    private ResponseEntity<User> signUp(User user) {
        return restTemplate.postForEntity("/api/signUp", generateUserMap(user), User.class);
    }

    private ResponseEntity<Statistic> getStatistic(List<String> cookie) {
        ResponseEntity<Statistic> response = restTemplate.exchange("/api/statistic", HttpMethod.GET, getEntity(cookie), Statistic.class);
        return response;
    }

    private ResponseEntity<List<Statistic>> getTop(Integer limit) {
        final Class<List<Statistic>> cls = (Class<List<Statistic>>) (Object) List.class;

        Map<String, String>  queryParam = new HashMap<>();
        queryParam.put("limit", limit.toString());
        return restTemplate.getForEntity("/api/top", cls, limit);
    }

    @Test
    public void getTopTest() {
        Integer limit = Generators.geterateInteger();

        for (int i = 0; i < 5; ++i) {
            final User user = UserFactory.create();
            ResponseEntity<User> responseUser = signUp(user);

            statisticsService.saveStatistics(responseUser.getBody().getId(), ScoreFactory.create());
        }

        ResponseEntity<List<Statistic>> response = getTop(limit);

        List<Statistic> top = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(top);
        Assert.assertTrue(top.size() >= 5);
    }

    @Test
    public void getStatisticTest() {
        final User user = UserFactory.create();
        ResponseEntity<User> response = signUp(user);

        Scores scores = ScoreFactory.create();
        statisticsService.saveStatistics(response.getBody().getId(), scores);

        ResponseEntity<Statistic> responseStatistic = getStatistic(getCookie(response));
        Statistic statistic = responseStatistic.getBody();

        Assert.assertNotNull(statistic);
        Assert.assertEquals(HttpStatus.OK, responseStatistic.getStatusCode());
        Assert.assertEquals(scores.getKills(), statistic.getKills());
        Assert.assertEquals(scores.getDeaths(), statistic.getDeaths());
        Assert.assertEquals(scores.getMaxKills(), statistic.getMaxKills());
    }

    @Test
    public void getStatisticsNotFound() {
        final User user = UserFactory.create();
        ResponseEntity<User> response = signUp(user);

        ResponseEntity<Statistic> responseStatistic = getStatistic(getCookie(response));
        Statistic statistic = responseStatistic.getBody();

        Assert.assertNull(statistic);
        Assert.assertEquals(HttpStatus.NOT_FOUND, responseStatistic.getStatusCode());
    }

    @Test
    public void getStatisticsEmptySession() {
        ResponseEntity<Statistic> responseStatistic = getStatistic(new ArrayList<>());
        Statistic statistic = responseStatistic.getBody();

        Assert.assertNull(statistic);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, responseStatistic.getStatusCode());
    }
}
