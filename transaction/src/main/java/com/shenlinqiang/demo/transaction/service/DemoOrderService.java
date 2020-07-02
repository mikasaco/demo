package com.shenlinqiang.demo.transaction.service;

import com.shenlinqiang.demo.transaction.entity.DemoOrder;

import java.util.List;

public interface DemoOrderService {

    List<DemoOrder> list();

    void shop(Integer stackId, Integer stackNum);
}
