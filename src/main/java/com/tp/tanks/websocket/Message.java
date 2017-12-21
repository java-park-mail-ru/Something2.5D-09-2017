package com.tp.tanks.websocket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tp.tanks.mechanics.base.SpawnSnap;
import com.tp.tanks.mechanics.base.ServerSnap;
import com.tp.tanks.mechanics.base.StatisticsSnap;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.requests.JoinGame;
import com.tp.tanks.mechanics.requests.SpawnRequest;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "class")
@JsonSubTypes({
        @Type(TankSnap.class),
        @Type(JoinGame.Request.class),
        @Type(ServerSnap.class),
        @Type(SpawnSnap.class),
        @Type(SpawnRequest.class),
        @Type(StatisticsSnap.class)
        })
public abstract class Message {
}
