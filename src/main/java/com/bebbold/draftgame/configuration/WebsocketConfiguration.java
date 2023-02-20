package com.bebbold.draftgame.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

    @Configuration
    @EnableWebSocketMessageBroker
    public class WebsocketConfiguration implements WebSocketMessageBrokerConfigurer {
        @Override
        public void registerStompEndpoints(StompEndpointRegistry registry) {
           // registry.addEndpoint("/gameplay").setAllowedOrigins("*").withSockJS();
            //client/server are on the same machine so I dont need to allow any origin
            registry.addEndpoint("/gameplay").withSockJS();
        }

        @Override
        public void configureMessageBroker(MessageBrokerRegistry registry) {
            registry.setApplicationDestinationPrefixes("/app").enableSimpleBroker("/topic");
        }
    }

