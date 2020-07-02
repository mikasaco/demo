package com.shenlinqiang.demo.transaction.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class DemoOrder {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer stockId;

    private Integer stockNum;

    private String transactionId;

    private Date createTime;

}
