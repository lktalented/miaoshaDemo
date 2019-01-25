package com.imooc.miaosha.rabbitmq;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MQ接收者
 * luokai
 * 2019/1/15 0015 下午 2:34
 */
@Slf4j
@Service
public class MQReceiver {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveMiaoshaMessage(String message){
        log.info("miaoshaQueue receive message: "+ message);
        MiaoshaMessage mm = RedisService.stringToBean(message,MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goosId = mm.getGoosId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goosId);
        int stock = goods.getStockCount();
        if (stock < 0){
            return;
        }
        //判断是否已经下单成功(若已下单，则无法重复下单)
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goosId);
        if (order != null){
            return;
        }
        //减库存 下订单，写入秒杀订单
        miaoshaService.miaosha(user,goods);

    }


    @RabbitListener(queues = MQConfig.QUEUE)
    public void receive(String message){
        log.info("receive message:{}",message);
    }

   @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message){
        log.info("topic queue1 receive message :{}",message );
   }

    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message){
        log.info("topic queue2 receive message :{}",message );
    }

    @RabbitListener(queues = MQConfig.FANOUT_QUEUE1)
    public void receiveFanout1(String message){
        log.info("fanout.queue1 receive message :{}",message );
    }

    @RabbitListener(queues = MQConfig.FANOUT_QUEUE2)
    public void receiveFanout2(String message){
        log.info("fanout.queue2 receive message :{}",message );
    }
    @RabbitListener(queues=MQConfig.HEADER_QUEUE)
    public void receiveHeaderQueue(byte[] message) {
        log.info(" header.queue receive message:"+new String(message));
    }
}
