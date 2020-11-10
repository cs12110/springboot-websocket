package com.pkgs.bullshit.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author huapeng.huang
 * @version V1.0
 * @since 2020-11-09 16:25
 */
@Slf4j
@Configuration
public class WebsocketConfig {

    @Bean("serverEndpointExporter")
    public ServerEndpointExporter createServerEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
