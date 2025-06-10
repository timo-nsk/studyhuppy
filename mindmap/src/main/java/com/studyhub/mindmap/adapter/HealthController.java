package com.studyhub.mindmap.adapter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/mindmap/v1")
public class HealthController {

    @GetMapping("/get-db-health")
    public String getHealth() {
        //TODO: implement
        return "health";
    }
}
