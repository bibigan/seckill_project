//package com.java.test;
//
//import com.java.pojo.Orders;
//import com.java.service.OrdersService;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.recipes.locks.InterProcessMutex;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//@Slf4j
//@Service
//public class CuratorDisLockOrderService{
////    private static OrderCodeGenerator codeGenerator = new OrderCodeGenerator();
//    private static String LOCK_PATH = "/distribute-lock";
//    @Autowired
//    private CuratorFramework curatorFramework;
//    public String createOrder() {
//         String orderCode = "";
//         InterProcessMutex lock = new InterProcessMutex(curatorFramework, LOCK_PATH);
//         try {
//                lock.acquire();
//                //生成订单编号
//                orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
//                System.out.println(Thread.currentThread().getName()+"-->获取锁成功-->生成订单编号："+orderCode);
//         } catch (Exception e) {
//                e.printStackTrace();
//         } finally {
//                try {
//                    lock.release();
//                    System.out.println(Thread.currentThread().getName() + "-->释放锁成功！");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//         }
//         return orderCode;
//    }
//}
