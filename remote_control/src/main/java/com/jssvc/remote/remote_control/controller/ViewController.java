package com.jssvc.remote.remote_control.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author:lrb
 * @Date:2019/4/10_22:50
 * @Vserion:1.0
 */
@Controller
public class ViewController {
    @RequestMapping("/pc")
    public String getPC() {
        return "pc";
    }
    @RequestMapping("/mobile")
    public String getMobile() {
        return "mobile";
    }
}
