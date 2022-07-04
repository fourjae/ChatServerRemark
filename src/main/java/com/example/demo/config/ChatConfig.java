package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/** Configuration 이 JAVA 클래스는 Spring 환경 설정 관련임 내부적으로 Spring의 빈 스캐닝을 이용해 Bean을 등록
 * @Bean 어노테이션을 사용하면 싱글톤(어플리케이션이 시작될 때 어떤 클래스가 최초 한번만 메모리를 할당하고(static) 그 메모리에 인스턴스를 만들어 사용하는 디자인 패턴)으로 관리함.
 * EnableWebSocketMessageBroker @Configuration클래스에 추가하여 상위 수준 메시징 하위 프로토콜을 사용하여 WebSocket을 통한 브로커 지원 메시징을 활성화.
 *
 * 이를 사용하여 registerStompEndpoints 오버라이드드 * */
@Configuration
@EnableWebSocketMessageBroker
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * registerStompEndpoints 클라에서 websocket에 접속하는 endpoint(API 경로) 등록
     * 공식문서 코드 그대로 +  Access-Control-Allow-Origin  are 브라우저로부터 어떤 패턴으로 Http cross-origin 요청 허용
     * withSockJS 대체 옵션을 활성화합니다.
     * */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /** 메세지브로커를 등록하는 코드
         * 보통 /topic 과 /queue 를 사용,
         * /topic 은 한명이 message 를 발행했을 때 해당 토픽을 구독하고 있는 n명에게 메세지를 뿌려야 하는 경우에 사용
         * /queue 는 한명이 message 를 발행했을 때 발행한 한 명에게 다시 정보를 보내는 경우에 사용
         */
        registry.enableSimpleBroker("/topic", "/queue");



        registry.enableSimpleBroker("/queue", "/topic");

        /**클라이언트로부터 메세지를 받을 api prefix, 이 경로에서 메시지를 받음
         * 공식문서 코드 그대로
         * */
        registry.setApplicationDestinationPrefixes("/app");
    }
}