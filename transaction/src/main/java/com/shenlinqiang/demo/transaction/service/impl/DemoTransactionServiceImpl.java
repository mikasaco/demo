package com.shenlinqiang.demo.transaction.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shenlinqiang.demo.transaction.entity.DemoTransaction;
import com.shenlinqiang.demo.transaction.mapper.DemoTransactionMapper;
import com.shenlinqiang.demo.transaction.service.DemoTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@DS("stock")
public class DemoTransactionServiceImpl implements DemoTransactionService {

    @Autowired
    private DemoTransactionMapper transactionMapper;


    @Override
    public void insert(DemoTransaction transaction) {
        transaction.setConsumeTime(1);
        transaction.setStatus(0);
        transactionMapper.insert(transaction);
    }

    @Override
    public DemoTransaction selectByTransactionId(String transactionId) {
        DemoTransaction transaction = new DemoTransaction();
        transaction.setTransactionId(transactionId);
        return transactionMapper.selectById(new QueryWrapper<>(transaction));
    }

    @Override
    public void update(DemoTransaction transaction) {
        transactionMapper.updateById(transaction);
    }
}
