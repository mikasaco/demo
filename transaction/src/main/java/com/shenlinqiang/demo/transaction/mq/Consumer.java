package com.shenlinqiang.demo.transaction.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.shenlinqiang.demo.transaction.entity.DemoStock;
import com.shenlinqiang.demo.transaction.entity.DemoTransaction;
import com.shenlinqiang.demo.transaction.mapper.DemoStockMapper;
import com.shenlinqiang.demo.transaction.service.DemoStockService;
import com.shenlinqiang.demo.transaction.service.DemoTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class Consumer implements InitializingBean {

    @Autowired
    private DemoStockService stockService;

    @Autowired
    private DemoTransactionService transactionService;


    @Override
    public void afterPropertiesSet() throws Exception {
        if ("8081".equals(System.getProperty("server.port"))) {
            return;
        }
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("demo");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("TransanctionMessage", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    try {
                        log.info("收到消息," + new String(msg.getBody()));

                        if (!msgValite(msg)) {
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                        DemoStock stock = JSON.parseObject(msg.getBody(), DemoStock.class);
                        DemoTransaction transaction = new DemoTransaction();
                        transaction.setTransactionId(msg.getTransactionId());
                        transaction.setContent(new String(msg.getBody()));
                        transactionService.insert(transaction);
                        DemoStock demoStock = stockService.SelectById(stock.getId());
                        if (demoStock == null) {
                            log.error("库存id不存在，传入的id为", stock.getId());
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                        if (demoStock.getStockNum() - stock.getStockNum() < 0) {
                            log.error("超过剩余库存，剩余库存:{},本次操作减去的库存:{}",
                                    demoStock.getStockNum(), stock.getStockNum());
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                        stockService.reduceStock(stock.getId(), stock.getStockNum());
                    } catch (Exception e) {
                        log.error("消息处理异常", e);
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        log.info("consumer 启动成功");

    }

    private boolean msgValite(MessageExt msg) {
        DemoTransaction transaction = transactionService.selectByTransactionId(msg.getMsgId());
        if (transaction == null) {
            return true;
        }
        if (1 == transaction.getStatus()) {
            log.info(msg.getMsgId() + " ,消息已经处理过了");
            return false;
        }
        if (transaction.getConsumeTime() >= 3) {
            log.info(msg.getMsgId() + " ,消息处理多次未成功，进行补偿");
            return true;
        }
        transaction.setConsumeTime(transaction.getConsumeTime() + 1);
        transactionService.update(transaction);
        return true;

    }
}
