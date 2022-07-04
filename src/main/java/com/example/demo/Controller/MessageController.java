package com.example.demo.Controller;

import com.example.demo.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

/** RestController : 들어온 요청에 대해 HTTP Response Body 반환(주로 JSON 형태 객체 데이터 반환)
 *  RequiredArgsConstructor : 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성 (@Autowired 사용 않고 의존주입)
 * */
@RestController
@RequiredArgsConstructor
public class MessageController {
    /**
     * 목적지에 메시지를 보내는 작업 인터페이스인 MessageSendingOperations를 상속받함
     * convertAndSend 프로퍼티는 사실MessageSendingOperations로도 가능
     * SimpMessageSendingOperations의 경우 convertAndSendToUser가 있음
     * 채널에 구독하고 있는 사용자들 중 모두에게가 아닌 특정한 사용자에게 메세지를 보낼 수 있도록 해주는 메소드임
     * convertAndSend는 주어진 Object를 MessageConverter를 사용하여 직렬화 한뒤 주어진 목적지로 보냄
    * 직렬화된 형식 의 페이로드를 Message형식화된 개체로 또는 그 반대로 전환하는 변환기입니다. 메시지 헤더 는 MessageHeaders.CONTENT_TYPE메시지 콘텐츠의 미디어 유형을 지정하는 데 사용할 수 있습니다.
     */
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/chat/message")
    public void enter(ChatMessage message) {

        /**
         * enum 형식의 ENTER와 내가 입장한 타입이 같을 경우(내가 입장하였을 경우)
         * */

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            /**
             * 전송되는 메시지 변경함 */
            message.setMessage(message.getSender()+"님이 입장하였습니다.");
        }
        /**
         * 보낼 메시지 경로, ChatConfig 내의 MessageBroker에서 뿌려야될 경로, topic의 경우 n명에게 보낼 때 붙임
         * 목적지로 보내주면 그 경로에 있는 MessageBroker가 n명에게 뿌려줌*/
        sendingOperations.convertAndSend("/topic/chat/room/"+message.getRoomId(),message);
    }
}