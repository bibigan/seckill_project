//package com.java.test;
//
//import com.java.service.OrdersService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.concurrent.BrokenBarrierException;
//import java.util.concurrent.CyclicBarrier;
//
//@RestController
//@Slf4j
//public  class  OrderController  {
//    @Autowired
//    private CuratorDisLockOrderService orderService;
//    /**
//     * 模拟高并发场景，多线程，并发下单
//     * @return
//     */
//    @RequestMapping("/test")
//    public  String  createOrdertTest(){
//        //并发线程数
//        int count = 20;
//        //循环屏障
//        CyclicBarrier cb  = new CyclicBarrier(count);
//        //模拟高并发场景，多线程，创建订单
//        for(int i=0; i<count; i++){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    log.info(Thread.currentThread().getName()+"--我已经准备好了");
//                    try {
//                        //等待所有线程启动准备好，才一起往下执行
//                        cb.await();
//                    } catch (InterruptedException | BrokenBarrierException e) {
//                        e.printStackTrace();
//                    }
//                    //创建订单
//                    System.out.println("####创建订单####");
//                    orderService.createOrder();
//                }
//            }).start();
//        }
//        return "ok";
//    }
//}
