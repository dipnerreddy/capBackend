package com.capstonebackend.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web")
public class WebController {
    public String r(){
        return "Hello it is working good";
    }
}
