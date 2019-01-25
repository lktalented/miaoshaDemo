package com.imooc.miaosha.serviceImpl;

import com.imooc.miaosha.mapper.GoodsMapper;
import com.imooc.miaosha.service.RedisCatchService;
import com.imooc.miaosha.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * redisCache注解的测试实现类
 * luokai
 * 2019/1/23 0023 下午 5:20
 */
@Service
@Slf4j
public class RedisCatchServiceImpl implements RedisCatchService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * 请求后会缓存，查询用
     * @param test
     * @return
     */
    @Override
    @Cacheable(value = "testAnnotationCacheAble" ,key = "'cacheAbleAnnotation:'+ #p0 ")
    public List<GoodsVo> testAnnotationCacheAble(String test) {

        log.info("进入 testAnnotationCacheAble() 方法，添加指定redis缓存");
        return goodsMapper.listGoodsVo();
    }

    /**
     * 请求后会删除,删除用
     * @param test
     * @return
     */
    @Override
    @CacheEvict(value = "testAnnotationCacheAble" ,key = "'cacheAbleAnnotation:'+ #p0 ")
    public void testAnnotationCacheEvict(String test) {
        log.info("进入 testAnnotationCacheEvict() 方法 ,清除指定redis缓存");
    }


    /**
     * 请求后方法肯定执行，并且存缓存，更新用
     * @param test
     * @return
     */
    @Override
    @CachePut(value = "testAnnotationCacheAble" ,key = "'cacheAbleAnnotation:'+ #p0 ",unless="#result==null")
    public  List<GoodsVo> testAnnotationCachePut(String test) {
        log.info("进入 testAnnotationCachePut() 方法 ,更新指定的redis缓存");
        List<GoodsVo> list = new ArrayList<>();
        GoodsVo goodsVo = new GoodsVo();
        goodsVo.setStockCount(555);
        goodsVo.setId(Long.valueOf(123));
        list.add(goodsVo);
        return list;
    }
}
