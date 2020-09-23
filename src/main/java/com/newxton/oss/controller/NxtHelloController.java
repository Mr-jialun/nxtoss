package com.newxton.oss.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/7/21
 * @address Shenzhen, China
 */
@RestController
public class NxtHelloController {

    @RequestMapping("/hello")
    public String index() {
        //心跳检查
        return "hello world";
    }

}
