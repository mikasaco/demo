package com.shenlinqiang.demo.transaction.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shenlinqiang.demo.transaction.entity.DemoOrder;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface DemoOrderMapper extends BaseMapper<DemoOrder> {
}
