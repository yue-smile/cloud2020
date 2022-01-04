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
## 启用
> 主启动类  服务端@EnableCircuitBreaker  客户端@EnableHystrix  
> 服务类 @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {  
>            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")  
>    })  
>    所有属性在类HystrixPropertiesManager  HystrixCommandProperties中

### 服务降级 failback
> fallback 不让客户端等待并立刻返回一个友好的提示  
> 1.程序运行异常 2.超时 3.服务熔断触发服务降级 4.线程池/信号量打满  
> 客户端服务降级（防止服务端出现问题）、服务端服务降级（自查）
> @HystrixCommand  
> 全局fallback @DefaultProperties(defaultFallback = "")  
> 类级别的服务降级  
> ①@FeignClient(value = "cloud-payment-service",fallback = PaymentFallbackService.class)  
> 增加属性 fallback   
> ②PaymentFallbackService 实现PaymentFeignService接口并在其中做具体实现

### 服务熔断 break
> 参考博客 martinfowler.com/bliki/CircuitBreaker.html  
> 类比保险丝达到最大服务访问后，直接拒绝访问，拉闸限电，然后调用服务降级的方法返回友好提示，当检测到该节点微服务调用响应正常后，恢复调用链路
> 服务降级--服务熔断--服务恢复  
> 断路器开启或关闭的条件  
>> 当请求数满足一定阀值的时候（默认10秒内请求超过20次）  
>> 当失败率达到一定阀值的时候（默认10秒内超过50%请求失败）  
>>>  达到以上阀值时断路器会开启，所有的请求都不会进行转发  
>> 一段时间后（默认是5秒），断路器会进入半开状态，会转发其中一个请求，如果成功断路器会关闭，放行请求，如果失败则再次打开断路器，5秒之后再尝试
### 服务限流 flowlimit
> 秒杀高并发等操作，严禁一窝蜂过来拥挤，大家排队，一秒钟N个，有序进行  
> 目前主流用阿里的sentinel
### 服务监控
> hystrixDashboard 需要新建一个工程 增加注解@EnableHystrixDashboard   
> 被监控端需要@EnableCircuitBreaker 另外为了处理兼容性问题需要覆盖一个实例 ServletRegistrationBean  
> 监控地址是 http://127.0.0.1:8001/hystrix.stream 七色一圈一线 七色代表七种状态 一圈代表流量及状态   
## 微服务网关
### zuul  
>  zuul1.0网飞公司内部分歧已弃用，不支持非阻塞IO，基于servlet2.X。基于servlet之上的一个阻塞式处理模型，即实现了处理所有请求的servlet（DispatcherServlet）并由该servlet阻塞式处理。
>  zuul2.0出来太慢
### gateway 新一代网关  
>  由springcloud开发，建立在SpringFramework5.0/ProjectReactor/SpringBoot2之上，webflux框架，使用非阻塞API，支持websocket长连接。底层是webflux+netty  
>  1 Route 路由：构建网关的基本模块，由ID、目标URI、一系列的断言和过滤器组成，如果断言为true则匹配该路由   
>  2 Predicate 断言：参考Java8的java.util.function.Predicate 开发人员可以匹配HTTP请求中的所有内容（请求头、请求参数、请求体）如果请求与断言匹配则进行路由    
>  3 Filter 过滤：指的是Spring框架中GatewayFilter的实例，使用过滤器，可以在请求被路由之前或之后对请求进行修改   
#### 工作流程：客户端向Gateway发出请求。然后在Gateway Handler Mapping中（根据断言）找到与请求相匹配的路由，将其发送到 GateWay Web Handler，Handler再通过指定的过滤器链来将我们的实际服务执行业务逻辑，然后返回。过滤器之间用虚线分开是因为过滤器可能会在发送代理请求之前（pre）或之后（post）执行业务逻辑   
pre中可以做：参数校验、权限校验、流量监控、日志输出、协议转换等等  
post中可以做：响应内容响应头修改、日志输出、流量监控等等  
#### gateway+ribbon 实现负载均衡策略切换 健康检查
> 参考博客 https://blog.csdn.net/qq_39415129/article/details/106097496
