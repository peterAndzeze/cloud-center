package net.sw.cloud.center.job.contrtoller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: TestControllerr
 * @description: TODO 类描述
 * @author: sw
 * @date: 2021/7/3
 **/
@RestController
@RequestMapping("test")
public class TestController {
    @RequestMapping("hello")
    public String helloWorld(String name){
        System.out.println(name);
        return "helloWorld";
    }
}
