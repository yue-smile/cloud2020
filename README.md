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
#### GateWay中的Filter  
> 生命周期：pre和post  
> 种类： GatewayFilter（31种）和GlobalFilter（10种），自定义全局的filter需要实现GlobalFilter,Ordered   
>> GlobalFilter接口中含有filter(ServerWebExchange exchange, GatewayFilterChain chain)方法 exchange请求对象，chain.filter(exchange)放行  
>> Ordered 返回一个整数表示加载的优先级，整数小优先级越大  
## 配置中心
### springCloud Config
## 消息总线
### springCloud Bus  
> 目前只支持 rabbitMQ（https://www.rabbitmq.com/getstarted.html） 和 kafuka  
> rabbitmq  erlang 网页端管理插件 rabbitmq-plugins enable rabbitmq_management  
> 与config集成，由configCenter负责通知其他微服务，configCenter需要暴露bus-refresh，每次有文件更新，只需要post通知配置中心，由配置中心分发消息  curl -X POST "http://localhost:3344/actuator/bus-refresh"  
> ConfigClient实例都监听同一个topic（默认是springCloudBus）当服务刷新数据时，它会把这个消息放入topic中，这样其他监听同一个topic的服务就能得到通知，然后去更新自身配置  
> 全局通知 curl -X POST "http://localhost:3344/actuator/bus-refresh"  
> 定点通知 curl -X POST "http://localhost:3344/actuator/bus-refresh/{destination}"  
## cloud Stream（rabbitMQ和kafka）
>  屏蔽底层消息中间件的差异，降低切换成本，统一消息的编程模型  
>  应用程序通过inputs或者outputs来与SpringcloudStream中的binder对象交互  
>  定义绑定器Binder作为中间层，实现了应用程序与消息中间件细节之间的隔离 生产者input  消费者output  
>  Stream中的消息通信方式遵循了发布-订阅模式  topic主题进行广播  对应 rabbitmq中的exchange kafka中的topic  
>>  消息生产者类注解 @EnableBinding(Source.class) 注入org.springframework.messaging.MessageChannel通道对象，用于消息发送  
>>  消息消费者类注解 @EnableBinding(Sink.class) 监听方法上注解 @StreamListener(Sink.INPUT) 输入参数有 org.springframework.messaging.Message对象，用于消息接收  
>  存在重复消费的问题，需要使用分组解决，同一分组的多个消费者是竞争关系，能够保证消息只被消费一次。不同分组的消息是可以全面消费的。  
>  重复消费问题：默认分组group是不同的，组流水号不同，可以重复消费  
>  消息丢失问题：添加了group属性之后，默认开启持久化功能，当服务重启后会继续消费未被消费的消息  
## SpringCloud Sleuth链路跟踪监控    
> Sleuth负责监控、zipkin负责展现  https://repo1.maven.org/maven2/io/zipkin/java/zipkin-server/1.20.1/zipkin-server-2.12.9-exec.jar  
> 安装运行 java -jar zipkin-server-2.12.9-exec.jar  http://localhost:9411/zipkin/
> pom引入spring-cloud-starter-zipkin  yml配置zipkin路径和sleuth采样率
# springCloud alibaba 
## nacos 注册中心、配置中心、服务总线
### 注册中心  discorvey  
>  Consistency  一致性  所有节点在同一时间看到的数据都是一致的  
>  Availability  可用性  所有的请求都会收到响应  
>  Partition tolerance  分区容错性（一定存在）  
>  nacos可在AP和CP中进行切换  curl -X PUT "ip:8848/nacos/v1/ns/operator/switches?entry=serverMode&value=CP" 
>  安装启动nacos即可使用 pom spring-cloud-starter-alibaba-nacos-discovery yml spring.cloud.nacos.discorvey.server-addr: localhost:8848 
###  配置中心  config  
>  pom spring-cloud-starter-alibaba-nacos-config  
>  yml spring.cloud.nacos.config.server-addr: localhost:8848和file-extension    
>  管理台文件名 ${spring.application.name}-${spring.profile.active}.${spring.cloud.nacos.config.file-extension}  
### nacos集群和持久化
> nacos内嵌derby数据库，目前可兼容mysql数据库，在application.properties中修改，配置连接信息
> 集群至少三台，配置cluster.conf 集群上层用nginx代理，nginx配置upstream  proxy_pass
## Sentinel
> 1 单独一个组件，可以独立出来 2 直接界面化的细粒度统一配置，少写代码   
> pom引入spring-cloud-starter-alibaba-sentinel sentinel-core sentinel-datasource-nacos  
> yml配置spring.cloud.sentinel.transport.dashboard  
> sentinel使用懒加载机制，注册之后，需要先访问几次
### 流控规则（默认直接-快速失败）   
> 阀值类型：①QPS（每秒请求数）  ②线程数（处理请求的线程数）  
> 流控模式：①直接  ②关联（当指定关联的资源达到阀值时，就限流自己）  ③链路（只记录指定链路上的流量，指定资源从入口进来的流量，如果达到阀值就进行限流，api级别）  
> 流控效果：①快速失败 ②WarmUp(预热机制) ③排队 严格控制请求通过的间隔时间，也即是让请求以均匀的速度通过，对应的是漏桶算法，不支持QPS大于1000的情况    
> warmup公式：阀值除以冷加载因子coldFactor（默认值3），经过预热时长（秒）后才会达到阀值，即请求QPS从threshold/3开始，经预热时长逐渐升至设定的QPS阀值。应用于冷启动，慢慢达到峰值。即动态QPS阀值  
### 降级规则 1.8以后有半开状态
#### RT(平均响应时间 秒)1.8以后记作慢调用比例
>  平均响应时间 超出阀值 且 在时间窗口内通过的请求数>=5  触发降级
>  窗口期过后关闭断路器
>  RT最大为4900秒 （更大需要通过 -Dcsp.sentinel.statistic.max.rt=XXXXX才能生效）
>  (1.8以后还需设定慢调用数量)选择以慢调用比例作为阈值，需要设置允许的慢调用 RT（即最大的响应时间），请求的响应时间大于该值则统计为慢调用。当单位统计时长（statIntervalMs）内请求数目大于设置的最小请求数目，并且慢调用的比例大于阈值，则接下来的熔断时长内请求会自动被熔断。经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状态），若接下来的一个请求响应时间小于设置的慢调用 RT 则结束熔断，若大于设置的慢调用 RT 则会再次被熔断。
#### 异常比例
>  QPS>=5 且异常比例超过阀值时触发降级；时间窗口期结束后，关闭降级
#### 异常数 
>  当单位统计时长内的异常数目超过阈值之后会自动进行熔断。经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状态），若接下来的一个请求成功完成（没有错误）则结束熔断，否则会再次被熔断。
#### 注意：
注意异常降级仅针对业务异常，对 Sentinel 限流降级本身的异常（BlockException）不生效。为了统计异常比例或异常数，需要通过 Tracer.trace(ex) 记录业务异常。开源整合模块，如 Sentinel Dubbo Adapter, Sentinel Web Servlet Filter 或 @SentinelResource 注解会自动统计业务异常，无需手动调用。  
### 热点参数限流
>  从 @HystrixCommand 到 @SentinelResource
>  @SentinelResource 中fallback注意默认为本类的方法，参数需要一致

