package com.shenlinqiang.demo.transaction.controller;

import com.shenlinqiang.demo.transaction.service.DemoOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/order/")
public class OrderController {

    @Autowired
    private DemoOrderService orderService;

    @RequestMapping("shop")
    public String shop(@RequestParam("stackId") Integer stackId, @RequestParam("stackNum") Integer stackNum) {
        orderService.shop(stackId, stackNum);
        return "shop success";
    }
}
