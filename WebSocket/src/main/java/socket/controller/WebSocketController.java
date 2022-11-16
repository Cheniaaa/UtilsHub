package socket.controller;

import cn.hutool.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import socket.dto.Message;
import socket.server.WebSocketServer;

import java.util.List;

/**
 * @Description :
 * @ProjectName : UtilsHub
 * @Author : 聂晨
 * @Time: 2022/10/31 01:59:59
 * @Version : 1.0
 */
@RestController
@RequestMapping("/ws")
public class WebSocketController {

    @GetMapping("/push/{message}")
    public ResponseEntity<String> push(@PathVariable(name = "message") String message) {
        WebSocketServer.batchSendInfo(message);
        return ResponseEntity.ok("WebSocket 推送消息给所有人");
    }

    @GetMapping("/pushToOne/{userName}")
    public String pushToOne(@PathVariable(name = "userName") String userName) {
        Message message = new Message();
        message.setUserName(userName);
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("name", "聂晨");
        jsonObject.set("age", 20);
        jsonObject.set("GirlFriend", "HJN");
        message.setData(jsonObject);
        WebSocketServer.sendInfo(userName, jsonObject);
        return "消息推送成功";
    }


    @GetMapping("/getList")
    public JSONObject getList() {
        List<String> ids = WebSocketServer.getIds();
        int count = WebSocketServer.getUserCount();
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("users", ids).set("count",count);
        return jsonObject;
    }

}
