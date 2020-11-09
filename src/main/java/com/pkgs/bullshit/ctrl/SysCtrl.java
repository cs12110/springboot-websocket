package com.pkgs.bullshit.ctrl;

import com.pkgs.bullshit.entity.response.BasicResponse;
import com.pkgs.bullshit.socket.WebsocketEndpointService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author huapeng.huang
 * @version V1.0
 * @since 2020-11-09 16:48
 */
@RestController
@RequestMapping("/sys")
public class SysCtrl {

    @Resource
    private WebsocketEndpointService websocketEndpointService;

    @RequestMapping("/ping")
    public Object ping() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("data", "pong");
        map.put("date", new Date());

        return map;
    }

    @RequestMapping("/point/{token}")
    public BasicResponse point(@PathVariable("token") String token, String message) {
        websocketEndpointService.point(token, message);
        return BasicResponse.success("ok");
    }

    @RequestMapping("/broadcast")
    public BasicResponse broadcast(String message) {
        websocketEndpointService.broadcast(message);
        return BasicResponse.success("ok");
    }
}
