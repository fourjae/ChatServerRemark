package com.example.demo.Controller;

import com.example.demo.model.ChatRoom;
import com.example.demo.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Controller : 들어온 요청에 대해 Model을 거쳐 View 반환
 *  RequiredArgsConstructor : 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성 (@Autowired 사용 않고 의존주입)
 *  RequestMapping : 모든 메소드 요청 매핑가능 /chat 경로를 통해 들어감
 * */
@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    private final ChatService chatService;

    /** 채팅 리스트 화면 반환
     * */
    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    /** 모든 채팅방 목록 반환
     * ChatRoom 제네릭 타입의 리스트 형식의 룸 반환*/
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatService.findAllRoom();
    }
    /** 채팅방 생성
     * ChatRoom 타입의 함수 name으로 파라미터 받음*/
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatService.createRoom(name);
    }

    /** 채팅방 입장 화면
     * model타입(hashmap 형식 key,value) 사용, roomid 랜덤경로
     * model에 key value 추가*/
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    /**
     *  채팅방 있는지 조회(UUID를 통해) */
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatService.findById(roomId);
    }
}