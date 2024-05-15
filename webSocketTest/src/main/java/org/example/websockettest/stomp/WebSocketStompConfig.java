package org.example.websockettest.stomp;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/stomp")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        /**
         * 발행자가 메시지를 보낼 때 해당 메시지의 목적지를 설정한다.
         * /pub를 프리픽스로 사용하면 클라이언트는 /pub 뒤에 추가적인 경로를 지정하여 메시지를 보낼 수 있다.
         * 예를 들어, 클라이언트가 /pub/chat으로 메시지를 보내면 이 메시지는 /chat 주제로 전송된다.
         *
         * 뿐만 아니라, 클라이언트에서 구독 후 서버로부터 데이터를 가져오려고 하는 경우에도 사용한다.
         * 기존에 /sub이라는 이름의 경로를 구독중이라면 최초 구독 시 /sub을 통해 서버에서 데이터를 가져오기 위해서는 /sub 경로를 추가해야 한다.
         */
        registry.setApplicationDestinationPrefixes("/pub", "/sub");

        /**
         * 클라이언트 간에 메시지를 교환하고 전달하는 역할을 담당하는 간단한 메시지 브로커를 활성화한다.
         * /sub를 구독(subscribe)하면 해당 주제(topic)로 전달되는 메시지를 수신할 수 있다.
         * 예를 들어, 클라이언트가 /sub/chat을 구독하면 /chat 주제로 전달되는 메시지를 수신할 수 있다.
         */
        registry.enableSimpleBroker("/sub");
    }
}
