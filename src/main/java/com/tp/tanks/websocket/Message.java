package com.tp.tanks.websocket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tp.tanks.mechanics.base.Coordinate;
import com.tp.tanks.mechanics.base.ServerSnap;
import com.tp.tanks.mechanics.base.TankSnap;
import com.tp.tanks.mechanics.requests.JoinGame;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "class")
@JsonSubTypes({
        @Type(TankSnap.class),
        @Type(JoinGame.Request.class),
        @Type(ServerSnap.class),
        @Type(Coordinate.class)
        })
public abstract class Message {
}
