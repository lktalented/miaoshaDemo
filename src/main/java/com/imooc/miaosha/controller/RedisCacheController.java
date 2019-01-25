package com.imooc.miaosha.controller;

import com.imooc.miaosha.mapper.GoodsMapper;
import com.imooc.miaosha.service.RedisCatchService;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * redisCache缓存测试类
 * luokai
 * 2019/1/23 0023 下午 5:08
 */

@Controller
@RequestMapping("/redisCache")
public class RedisCacheController {

    @Autowired
    private RedisCatchService redisCatchService;

    /**
     * 测试@Cacheable 注解
     * @return
     */
    @RequestMapping(value = "/testAnnotationCacheAble",method = RequestMethod.GET)
    @ResponseBody
    public List<GoodsVo> testAnnotationCacheAble(){
        List<GoodsVo> goods = redisCatchService.testAnnotationCacheAble("test");
        return goods;
    }

    /**
     * 测试@CacheEvict 注解
     * @return
     */
    @RequestMapping(value = "/testAnnotationCacheEvict",method = RequestMethod.GET)
    @ResponseBody
    public void testAnnotationCacheEvict(){
         redisCatchService.testAnnotationCacheEvict("test");
    }

    /**
     * 测试@CachePut 注解
     * @return
     */
    @RequestMapping(value = "/testAnnotationCachePut",method = RequestMethod.GET)
    @ResponseBody
    public void testAnnotationCachePut(){
        redisCatchService.testAnnotationCachePut("test");
    }
}
