高并发实现:<br>
=====
##用到的技术<br>
![](https://github.com/dcg123/miaosha/blob/master/src/main/resources/static/img/technical.png)<br>
##实现的模块<br>
 ![](https://github.com/dcg123/miaosha/blob/master/src/main/resources/static/img/function.png)<br>
##集成redis:<br>

  >>1：添加redis依赖,添加Fastjson依赖（用于序列化）<br> 
  >>2:加载redis配置文件(RedisConfig 使用@ConfigurationProperties来读取配置文件)<br> 
  >>3:通过RedisConfig获取JedisPool池 吧JedisPool注入到spring容器中 供RedisService使用<br> 
  >>4:为了防止多人开发项目下key被别人覆盖（同意使用前缀 不同模块使用不同的前缀 比如说用户模块使用用户模块的前缀）<br>
    >> 通过key封装结构：实现类-》抽象类-》接口<br> 
##两次MD5：<br> 

  >>1：用户端：PASS=MD5(明文+固定+Salt)<br> 
  >>2: 服务端：PASS=MD5(用户输入+随机Salt)<br> 
  
##JSR303参数校验+全局异常处理器<br> 
  >>JSR303参数校验(自定义注解进行手机号格式验证)\<br> 
  >>定义全局异常处理某一类异常从而能够减少代码重复率和复杂度（@ExceptionHandler）<br> 
  >>@ExceptionHandler：统一处理某一类异常，从而能够减少代码重复率和复杂度<br> 
  >>@ControllerAdvice：异常集中处理，更好的使业务逻辑与异常处理剥离开<br> 
  >>@ResponseStatus：可以将某种异常映射为HTTP状态码<br> 
  
##redis分布式session:<br> 
  >>通过UUIDUtil获取随机数作为token  吧唯一token作为用户的唯一标识符保存到redis中 通过Cookie吧token返回给客户端<br> 
  >>通过重写WebMvcConfigurerAdapter下的方法 在进入到Controller前在redis中获取用户信息 这样减少代码的冗余 在需要用户信息的方法中注入用户实体类<br> 
  
##页面高并发优化<br> 
  >>页面缓存 <br> 
  >>解决超卖(数据库增加唯一索引：防止用户重复购买,sql加库存数量判断：防止库存边负数)<br> 
  >>秒杀静态化<br> 
  >>对象缓存<br> 
##接口优化<br>
  >>Redis 预减库存减少对数据库访问<br>
  >>内存标记减少Redis<br>
  >>请求先入队缓冲（RabbitMQ） 异步下单 增强用户体验<br>
  >>nginx水平扩展<br>
  >>分库分表中间件(Mycat)<br>
  >>思路：减少数据库访问<br>
  >>1:系统初始化，吧商品库存数量加载到Redis<br>
  >>2:收到请求 Redis预减库存，库存不足，直接返回，否则进入3<br>
  >>3:请求入队，立即返回排队中<br>
  >>4:请求出队，生成订单，减少库存<br>
  >>5:客户端轮询，是否秒杀成功<br>
  
##参考文档<br>
  
秒杀系统思路：
https://blog.csdn.net/goldenfish1919/article/details/78898465

redis缓存更新的方法：
https://www.cnblogs.com/westboy/p/8696607.html

秒杀优化：
1.页面缓存:
2.对象缓存：
3.页面静态化：
https://blog.csdn.net/fmwind/article/details/81235401
4.rebbitmq
5.隐藏秒杀地址
6.限流防刷


github秒杀项目：
https://github.com/dcg123/miaosha


2次加密：
https://blog.csdn.net/weixin_38035852/article/details/81052431

shiro:
https://www.cnblogs.com/maofa/p/6407102.html

分布式session:
https://blog.csdn.net/u011213044/article/details/80525997

SpringBoot中通过实现WebMvcConfigurer完成参数校验：
https://blog.csdn.net/qq_38439885/article/details/80238813

rabbitmq：
rabbitmq及kafka的比对：
https://blog.csdn.net/yunfeng482/article/details/72856762

rabbitmq的应用场景：
https://blog.csdn.net/qq_14901335/article/details/80451052

秒杀系统的设计：
https://www.jianshu.com/p/d789ea15d060

问题解疑：
1.如何解决卖超问题
--在sql加上判断防止数据边为负数 
--数据库加唯一索引防止用户重复购买
--redis预减库存减少数据库访问　内存标记减少redis访问　请求先入队列缓冲，异步下单，增强用户体验

2.订单处理队列rabbitmq
--请求先入队缓冲，异步下单，增强用户体验
--请求出队，生成订单，减少库存
--客户端定时轮询检查是否秒杀成功

3.解决分布式session
--生成随机的uuid作为cookie返回并redis内存写入 
--拦截器每次拦截方法，来重新获根据cookie获取对象
--下一个页面拿到key重新获取对象
--HandlerMethodArgumentResolver 方法 supportsParameter 如果为true 执行 resolveArgument 方法获取miaoshauser对象

4.秒杀安全 -- 安全性设计
--秒杀接口隐藏
--数字公式验证码
--接口防刷限流(通用 注解，拦截器方式)

5.redis的库存如何与数据库的库存保持一致
--redis的数量不是库存,他的作用仅仅只是为了阻挡多余的请求透穿到DB，起到一个保护的作用
--因为秒杀的商品有限，比如10个，让1万个请求区访问DB是没有意义的，因为最多也就只能10个
--请求下单成功，所有这个是一个伪命题，我们是不需要保持一致的

6.redis 预减成功，DB扣减库存失败怎么办
--对用户而言，秒杀不中是正常现象，秒杀中才是意外，单个用户秒杀中本来就是小概率事件，出现这种情况对于用户而言没有任何影响
--对于商户而言，本来就是为了活动拉流量人气的，卖不完还可以省一部分费用，但是活动还参与了，也就没有了任何影响
--对网站而言，最重要的是体验，只要网站不崩溃，对用户而言没有任何影响

7.rabbitmq如何做到消息不重复不丢失即使服务器重启
--exchange持久化
--queue持久化
--发送消息设置MessageDeliveryMode.persisent这个也是默认的行为
--手动确认

8.秒杀类似场景sql的写法注意事项
1).一定要避免全表扫描，如果扫一张大表的数据就会造成慢查询，导致数据的连接池直接塞满,导致事故
 首先考虑在where和order by 设计的列上建立索引
 例如：
 1. where 子句中对字段进行 null 值判断 . 
 2. 应尽量避免在 where 子句中使用!=或<>操作符 
 3. 应尽量避免在 where 子句中使用 or 来连接条件
 4. in 和 not in 也要慎用，否则会导致全表扫描( 如果索引 会优先走索引 不会导致全表扫描 
    字段上建了索引后，使用in不会全表扫描，而用not in 会全表扫描 低版本的mysql是两种情况都会全表扫描。
    5.5版本后以修。而且在优化大表连接查询的时候，有一个方法就是将join操作拆分为in查询)
 5. select id from t where name like '%abc%' 或者
 6.select id from t where name like '%abc' 或者
 7. 若要提高效率，可以考虑全文检索。 
 8.而select id from t where name like 'abc%' 才用到索引 慢查询一般在测试环境不容易复现
 9.应尽量避免在 where 子句中对字段进行表达式操作 where num/2 ,num=100*2
2).合理的使用索引:索引并不是越多越好，使用不当会造成性能开销
3).尽量避免大事务操作，提高系统并发能力
4).尽量避免客户端返回大量数据，如果返回则要考虑是否需求合理


 