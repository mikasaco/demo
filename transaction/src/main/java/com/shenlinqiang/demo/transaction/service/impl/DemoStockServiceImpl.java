package com.shenlinqiang.demo.transaction.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.shenlinqiang.demo.transaction.entity.DemoStock;
import com.shenlinqiang.demo.transaction.mapper.DemoStockMapper;
import com.shenlinqiang.demo.transaction.service.DemoStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("stock")
public class DemoStockServiceImpl implements DemoStockService {

    @Autowired
    private DemoStockMapper stockMapper;


    @Override
    public List<DemoStock> list() {
        return stockMapper.selectList(null);
    }

    @Override
    public DemoStock SelectById(Integer id) {
        return stockMapper.selectById(id);
    }

    @Override
    public Integer reduceStock(Integer stockId, Integer stockNum) {
        return stockMapper.reduceStock(stockId, stockNum);
    }
}
