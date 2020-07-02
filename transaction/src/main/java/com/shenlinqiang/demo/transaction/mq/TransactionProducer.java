package com.shenlinqiang.demo.transaction.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

@Component
@Slf4j
public class TransactionProducer implements InitializingBean {

    private TransactionMQProducer producer = new TransactionMQProducer("demo");

    @Autowired
    private TransactionListenerImpl transactionListener;

    @Override
    public void afterPropertiesSet() throws Exception {
        if ("8082".equals(System.getProperty("server.port"))) {
            return;
        }
        producer.setNamesrvAddr("127.0.0.1:9876");
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 5, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("transaction-check-thread");
                return thread;
            }
        });
        //这个线程池是回查本地事务处理结果的
        producer.setExecutorService(poolExecutor);
        producer.setTransactionListener(transactionListener);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageInTransaction(Message msg) {
        try {
            TransactionSendResult sendResult = producer.sendMessageInTransaction(msg, null);
            log.info("prepare事务消息发送结果:" + sendResult.getSendStatus());
        } catch (MQClientException e) {
            log.error("prepare事务消息发送失败", e);
        }
    }
}
