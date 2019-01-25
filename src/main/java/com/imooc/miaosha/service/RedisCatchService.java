package com.imooc.miaosha.service;

import com.imooc.miaosha.vo.GoodsVo;

import java.util.List;

/**
 * redis缓存测试接口
 * luokai
 * 2019/1/23 0023 下午 5:17
 * @author Administrator
 */
public interface RedisCatchService {

     List<GoodsVo> testAnnotationCacheAble(String test);

     void testAnnotationCacheEvict(String test);

     List<GoodsVo> testAnnotationCachePut(String test);
}
