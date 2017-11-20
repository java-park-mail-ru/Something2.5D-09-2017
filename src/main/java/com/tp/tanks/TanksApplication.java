package com.tp.tanks;

import com.tp.tanks.websocket.GameWebSocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.handler.PerConnectionWebSocketHandler;

@SpringBootApplication
public class TanksApplication {

    public static void main(String[] args) {

        SpringApplication.run(new Class[]{WebSocketConfig.class, TanksApplication.class}, args);
    }

    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSocketHandler gameWebSocketHandler() {
        return new PerConnectionWebSocketHandler(GameWebSocketHandler.class);
    }
}
