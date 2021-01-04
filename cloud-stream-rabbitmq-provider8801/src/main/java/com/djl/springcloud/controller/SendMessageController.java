package com.djl.springcloud.controller;

import com.djl.springcloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author djl
 * @create 2021/1/4 14:43
 */
@RestController
public class SendMessageController {

    private final IMessageProvider messageProvider;


    public SendMessageController(IMessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

    @GetMapping("/sendMessage")
    public String sendMessage() {
        return messageProvider.send();
    }
}
