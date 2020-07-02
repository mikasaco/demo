package com.shenlinqiang.demo.transaction.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.shenlinqiang.demo.transaction.entity.DemoOrder;
import com.shenlinqiang.demo.transaction.entity.DemoStock;
import com.shenlinqiang.demo.transaction.mapper.DemoOrderMapper;
import com.shenlinqiang.demo.transaction.mq.TransactionProducer;
import com.shenlinqiang.demo.transaction.service.DemoOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
@DS("order")
@Slf4j
public class DemoOrderServiceImpl implements DemoOrderService {

    @Autowired
    private DemoOrderMapper demoOrderMapper;

    @Autowired
    private TransactionProducer producer;


    @Override
    public List<DemoOrder> list() {
        return demoOrderMapper.selectList(null);
    }

    @Override
    public void shop(Integer stackId, Integer stackNum) {
        DemoStock demoStock = new DemoStock();
        demoStock.setId(stackId);
        demoStock.setStockNum(stackNum);
        Message msg = null;
        try {
            msg = new Message("TransanctionMessage",
                    JSON.toJSONString(demoStock).getBytes(RemotingHelper.DEFAULT_CHARSET));
        } catch (UnsupportedEncodingException e) {
            log.error("消息转换失败", e);
        }
        producer.sendMessageInTransaction(msg);
    }
}
