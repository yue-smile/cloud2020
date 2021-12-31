# cloud2020
One demo of springcloud
初始化导入 帮助理解学习springcloud及其常用组件
目前已记录：
## 服务注册中心
> eureka  已停更 
>> C:\Windows\System32\drivers\etc修改hosts文件
> zookeeper  
> consul
## 服务调用
> ribbon 
> LoadBalance  负载均衡（客户端负载） 重写负载算法 discoveryClient获取serviceIntance
## 服务调用2
> feign 已弃用
> open feign 包含ribbon
>> 【超时控制】超时默认1s钟就超时 如果服务端处理超过1秒钟，会导致openfeign客户端直接返回超时报错 
>>> ribbon.ReadTimeout 指的是建立连接所用的时间，适用于网络状况正常情况下，两端连接所用的时间
>>> ribbon.ConnectTimeout 指的是建立连接后从服务器读取到可用资源所用的时间
>> 【日志打印】对feign接口的调用情况进行监控和输出
>>> 日志级别包括  NONE不打印任何  BASIC仅记录请求方法地址响应状态码及执行时间 HEADERS包含上一级别所有信息及请求响应的头信息 FULL所有详细信息
>>>> ①config包下新增FeignConfig类 指定打印级别
>>>> ②yml中增加参数logging.level.接口路径：级别
## 服务降级/服务熔断/服务限流
> hystrix（已停更） 服务雪崩  https://github.com/Netflix/Hystrix 官方推荐 resilience4j一般不用
> 是处理分布式系统的延迟和容错的开源库，在分布式系统里，许多依赖会不可避免的调用失败例如超时、异常等等，hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性。
### 服务降级 failback
> failback 不让客户端等待并立刻返回一个友好的提示
> 1.程序运行异常 2.超时 3.服务熔断触发服务降级 4.线程池/信号量打满
### 服务熔断 break
> 类比保险丝达到最大服务访问后，直接拒绝访问，拉闸限电，然后调用服务降级的方法返回友好提示
### 服务限流 flowlimit
> 秒杀高并发等操作，严禁一窝蜂过来拥挤，大家排队，一秒钟N个，有序进行
