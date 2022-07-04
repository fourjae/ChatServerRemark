package com.example.demo.model;

import lombok.*;

/**NoArgsConstructor 어노테이션은 파라미터가 없는 기본 생성자를 생성해주고,
 * AllArgsConstructor 어노테이션은 모든 필드 값을 파라미터로 받는 생성자를 만들어줍니다.
 * */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    /** 서로 연관된 상수들의 집합 열거형
    * */
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type;
    //채팅방 ID
    private String roomId;
    //보내는 사람
    private String sender;
    //내용
    private String message;
}