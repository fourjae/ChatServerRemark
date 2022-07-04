package com.example.demo.service;

import com.example.demo.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
/**
 * @Service 해당 클래스의 service라는 것을 알림(비즈니스 로직 수행)
 * @Slf4j 레벨별 에러를 나눠주는 로깅 어노테이션, 로그 기록
 * */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {

    /**
     * key(String) value(ChatRoom) 형식의 자료구조 형식인 Map 객체 선언*/
    private Map<String, ChatRoom> chatRooms;


    /**PostContstruct
     * 종속성 주입이 완료된 후 실행되어야 하는 메서드에 사용된다. 이 어노테이션은 다른 리소스에서 호출되지 않아도 수행된다.
     * 1) 생성자가 호출되었을 때, 빈은 초기화되지 않았음(의존성 주입이 이루어지지 않았음)
     * 이럴 때 @PostConstruct를 사용하면 의존성 주입이 끝나고 실행됨이 보장되므로 빈의 초기화에 대해서 걱정할 필요가 없다.
     * 2) bean 의 생애주기에서 오직 한 번만 수행된다는 것을 보장한다. (어플리케이션이 실행될 때 한번만 실행됨)
     * 따라서 bean이 여러 번 초기화되는 걸 방지할 수 있다.*/
    @PostConstruct
    //의존관게 주입완료되면 실행되는 코드
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    //채팅방 불러오기
    public List<ChatRoom> findAllRoom() {
        //채팅방 최근 생성 순으로 반환
        List<ChatRoom> result = new ArrayList<>(chatRooms.values());
        Collections.reverse(result);

        return result;
    }

    //채팅방 하나 불러오기
    public ChatRoom findById(String roomId) {
        return chatRooms.get(roomId);
    }

    //채팅방 생성
    /**
     * Hashmap key(RoomId) : value(룸 이름)에 넣음(채팅 방 생성)*/
    public ChatRoom createRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRooms.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}