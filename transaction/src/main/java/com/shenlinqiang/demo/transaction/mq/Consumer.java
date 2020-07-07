package com.shenlinqiang.demo.transaction.mq;

import com.alibaba.fastjson.JSON;
import com.shenlinqiang.demo.transaction.entity.DemoStock;
import com.shenlinqiang.demo.transaction.service.DemoStockService;
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
                        DemoStock stock = JSON.parseObject(msg.getBody(), DemoStock.class);
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

}
