package com.tp.tanks.websocket;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tp.tanks.mechanics.base.TankSnap;
//import ru.mail.park.mechanics.base.ServerSnap;
//import ru.mail.park.mechanics.requests.InitGame;
//import ru.mail.park.mechanics.requests.JoinGame;

@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "class")
@JsonSubTypes({
        @Type(TankSnap.class)
//        @Type(JoinGame.Request.class),
//        @Type(InitGame.Request.class),
//        @Type(ServerSnap.class),
        })
public abstract class Message {
}
