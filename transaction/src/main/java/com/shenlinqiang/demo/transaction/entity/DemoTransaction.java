package com.shenlinqiang.demo.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class DemoTransaction {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String transactionId;

    private Integer consumeTime;

    private Integer status;

    private String content;
}
