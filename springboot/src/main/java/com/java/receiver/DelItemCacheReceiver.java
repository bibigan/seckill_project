package com.java.receiver;

import com.java.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "delItemCache")
public class DelItemCacheReceiver {
    //消费者
    private static final Logger LOGGER = LoggerFactory.getLogger(DelItemCacheReceiver.class);

    @Autowired
    private ItemService ItemService;

    @RabbitHandler
    public void process(String message) {
        LOGGER.info("DelItemCacheReceiver收到消息: " + message);
        LOGGER.info("DelItemCacheReceiver开始删除缓存: " + message);
        ItemService.delItemCache(Integer.parseInt(message));
    }
}
