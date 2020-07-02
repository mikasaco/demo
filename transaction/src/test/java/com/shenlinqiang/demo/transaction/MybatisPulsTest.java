package com.shenlinqiang.demo.transaction;

import com.shenlinqiang.demo.transaction.entity.DemoOrder;
import com.shenlinqiang.demo.transaction.entity.DemoStock;
import com.shenlinqiang.demo.transaction.service.DemoOrderService;
import com.shenlinqiang.demo.transaction.service.DemoStockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPulsTest {

    @Autowired
    private DemoOrderService demoOrderService;

    @Autowired
    private DemoStockService demoStockService;

    @Test
    public void testOrderSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<DemoOrder> orderList = demoOrderService.list();
        orderList.forEach(System.out::println);
    }

    @Test
    public void testStackSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<DemoStock> stocks = demoStockService.list();
        stocks.forEach(System.out::println);
    }
}
