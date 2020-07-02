package com.shenlinqiang.demo.transaction.mq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shenlinqiang.demo.transaction.entity.DemoOrder;
import com.shenlinqiang.demo.transaction.entity.DemoStock;
import com.shenlinqiang.demo.transaction.mapper.DemoOrderMapper;
import lombok.extern.log4j.Log4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Log4j
public class TransactionListenerImpl implements TransactionListener {

    @Autowired
    private DemoOrderMapper orderMapper;


    /**
     * 执行本地事务
     *
     * @param message
     * @param o
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        log.info("执行本地事务");
        LocalTransactionState state = LocalTransactionState.UNKNOW;
        try {
            DemoStock stock = JSON.parseObject(message.getBody(), DemoStock.class);
            DemoOrder order = new DemoOrder();
            order.setStockNum(stock.getStockNum());
            order.setStockId(stock.getId());
            order.setTransactionId(message.getTransactionId());
            order.setCreateTime(new Date());
            orderMapper.insert(order);
            state = LocalTransactionState.COMMIT_MESSAGE;
            log.info("本地事务执行成功");
        } catch (Exception e) {
            state = LocalTransactionState.ROLLBACK_MESSAGE;
            log.error("本地事务执行失败，回滚事务", e);
        }
        return state;
    }

    /**
     * 回查本地事务执行结果
     *
     * @param messageExt
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        log.info("回查本地事务执行结果");
        LocalTransactionState state = LocalTransactionState.UNKNOW;
        try {
            DemoOrder order = new DemoOrder();
            order.setTransactionId(messageExt.getTransactionId());
            Integer count = orderMapper.selectCount(new QueryWrapper<>(order));
            if (count > 0) {
                state = LocalTransactionState.COMMIT_MESSAGE;
            } else {
                state = LocalTransactionState.ROLLBACK_MESSAGE;
            }
        } catch (Exception e) {
            log.error("回查本地事务执行结果异常 ", e);
            state = LocalTransactionState.ROLLBACK_MESSAGE;
        }
        return state;
    }
}
