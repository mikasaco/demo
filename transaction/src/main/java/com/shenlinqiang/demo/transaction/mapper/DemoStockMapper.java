package com.shenlinqiang.demo.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shenlinqiang.demo.transaction.entity.DemoStock;
import org.apache.ibatis.annotations.Param;

public interface DemoStockMapper extends BaseMapper<DemoStock> {

    int reduceStock(@Param("stockId") Integer stockId, @Param("stockNum") Integer stockNum);
}
