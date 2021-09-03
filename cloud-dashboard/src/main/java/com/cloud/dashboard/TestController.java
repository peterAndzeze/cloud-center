package com.cloud.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @className: TestController
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/9/1
 **/
@RequestMapping
@Controller
public class TestController {
    @RequestMapping("/index")
    public String index(){
        System.out.println("进入首页");
        return "/login";
    }


}
