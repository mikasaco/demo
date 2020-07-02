package com.shenlinqiang.demo.transaction.service;

import com.shenlinqiang.demo.transaction.entity.DemoTransaction;

public interface DemoTransactionService {

    void insert(DemoTransaction transaction);

    DemoTransaction selectByTransactionId(String transactionId);

    void update(DemoTransaction transaction);
}
