package com.tp.tanks.factories;

import com.tp.tanks.mechanics.world.Scores;


public class ScoreFactory {
    public static Scores create() {

        final Integer kills = Generators.geterateInteger();
        final Integer deaths = Generators.geterateInteger();
        final Integer maxKills = Generators.geterateInteger();
        final Integer currentKills = Generators.geterateInteger();
        final String username = null;

        Scores scores = new Scores(username);
        scores.setKills(kills);
        scores.setDeaths(deaths);
        scores.setMaxKills(maxKills);
        scores.setCurrentKills(currentKills);
        return scores;
    }
}
