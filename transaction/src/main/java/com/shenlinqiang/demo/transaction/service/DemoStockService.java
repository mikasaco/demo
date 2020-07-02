package com.shenlinqiang.demo.transaction.service;

import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.shenlinqiang.demo.transaction.entity.DemoStock;

import java.util.List;

public interface DemoStockService {

    List<DemoStock> list();

    DemoStock SelectById(Integer id);

    Integer reduceStock(Integer stockId,Integer stockNum);
}
