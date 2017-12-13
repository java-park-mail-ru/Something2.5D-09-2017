package com.tp.tanks.controller;

import com.tp.tanks.factories.Generators;
import com.tp.tanks.factories.ScoreFactory;
import com.tp.tanks.factories.UserFactory;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

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
            assertEquals(HttpStatus.CREATED, responseUser.getStatusCode());

            statisticsService.saveStatistics(responseUser.getBody().getId(), ScoreFactory.create());
        }

        ResponseEntity<List<Statistic>> response = getTop(limit);

        List<Statistic> top = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull(top);
        Assert.assertTrue(top.size() >= 5);
    }

    private Map<String, String> generateUserMap(User user) {
        final Map<String, String> parts = new HashMap<>();
        parts.put("username", user.getUsername());
        parts.put("email", user.getEmail());
        parts.put("password", user.getPassword());
        return parts;
    }

    private ResponseEntity<User> signUp(User user) {
        return restTemplate.postForEntity("/api/signUp", generateUserMap(user), User.class);
    }
}
