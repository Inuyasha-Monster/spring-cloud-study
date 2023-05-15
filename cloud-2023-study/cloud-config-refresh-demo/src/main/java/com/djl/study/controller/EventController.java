package com.djl.study.controller;

import com.djl.study.BootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author djl
 */
@RestController
public class EventController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/event")
    public String event() {
        applicationContext.publishEvent(new RefreshEvent(this, null, "just for test"));
        return "send RefreshEvent";
    }


}
