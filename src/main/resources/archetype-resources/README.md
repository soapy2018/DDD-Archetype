#set( $P = '#' )
$P$P ${rootArtifactId}

$P$P 项目说明

* ${rootArtifactId}-service

    1. 后台核心服务 <br>
    2. 端口：

* ${rootArtifactId}-api

  api 客户端，包括DTO,枚举，客户端调用等


$P$P 项目结构

```
${rootArtifactId}
│ 
├─${rootArtifactId}-service  服务后台
│    ├─src/main/java  模块
│	 │ 	│  
│	 │ 	├─adapter    主要存放用户接口层与前端交互、展现数据相关的代码
│	 │	│  ├─web    处理web请求
│	 │	│  │  ├─assembler    实现 DTO 与领域对象之间的相互转换和数据交换
│	 │	│  │  │           
│	 │	│  │  ├─controller   http restful提供的粗粒度的接口，将用户请求委派给一个或多个应用服务进行处理。
│	 │	│  │  │      
│	 │	│  │  └─dto  数据传输的载体，内部不存在任何业务逻辑，通过DTO屏蔽领域对象
│	 │	│  │ 
│	 │	│  ├─mobile  处理移动端请求
│	 │	│  │  ├─assembler
│	 │	│  │  │            
│	 │	│  │  ├─controller
│	 │	│  │  │      
│	 │	│  │  └─dto
│	 │	│  │
│	 │	│  └─client  api接口
│	 │	│
│	 │	│             
│	 │	├─application   主要存放应用层服务组合和编排相关的代码
│	 │	│  ├─event    主要存放事件相关的代码
│	 │	│  │  ├─publish  事件发布，定义需要发布的领域事件
│	 │	│  │  │      
│	 │	│  │  └─subscribe  事件订阅相关
│	 │	│  │          
│	 │	│  ├─service  应用服务，对多个领域服务进行封装、编排和组合，外部微服务调用也放置于此，极薄的一层，没有业务逻辑
│	 │	│  │
│	 │	│  └─query  读写分离，查询服务可以绕过领域层操作基础设施层  
│	 │	│   
│	 │	│          
│	 │	├─domain        主要存放领域层核心业务逻辑相关的代码
│	 │	│  ├─aggregateXXX  聚合根目录，根据聚合名称命名
│	 │	│  │    ├─model   存放聚合根、实体、值对象相关代码，实体类采用充血模型
│	 │	│  │    │      
│	 │	│  │    ├─repository  聚合对应的仓储接口
│	 │	│  │    │      
│	 │	│  │    └─service  存放领域服务、工厂模式（Factory），用于创建领域对象、非领域对象本身行为等，向上供应用服务调用
│	 │	│  │
│	 │	│  ├─... 多个聚合平行放置于domain下
│	 │	│  │    ├─model
│	 │	│  │    │      
│	 │	│  │    ├─repository
│	 │	│  │    │      
│	 │	│  │    └─service
│	 │	│  
│	 │	│              
│	 │	├─infrastructure  主要存放基础资源服务相关的代码
│	 │	│  ├─aggregateXXX  聚合根目录，根据聚合名称命名，存放聚合内仓储实现及po对象
│	 │	│  │      
│	 │	│  ├─config  配置相关，共性的需要移至xxx-common
│	 │	│  │      
│	 │	│  ├─util    公共服务，共性的需要移至xxx-common
│	 │	│  │   ├─cache  抽象封装缓存中间件
│	 │	│  │   │      
│	 │	│  │   ├─job    抽象封装任务中间件
│	 │	│  │   │      
│	 │	│  │   ├─mq     抽象封装消息中间件
│	 │	│  │   │   
│	 │	│  │   └─...  其它
│    │ 
│    └─src/main/resources 
│        ├─sql                数据库初始化文件
│        ├─i18n               国际化翻译文件
│        └─application.yml    全局配置文件
│       
│ 
├─${rootArtifactId}-api   API服务
|    ├─client   client 客户端调用,对外暴露的openFeign接口  
|    ├─dto      DTO 数据传输对象
|    ├─common   枚举类
|
```

$P$P 启动

* 基于appliccation 类启动

  >  配置jvm 参数：-Dspring.profiles.active=dev
* 基于springBoot maven 插件

  > spring-boot:run -Dspring-boot.run.profiles=dev
* IDEA 以类的main方式启动

  > 添加启动参数：--spring.profiles.active=dev


$P$P 依赖说明
`依赖详细使用方式请见各项目地址`

|功能|Maven坐标|项目地址|项目地址|
|---|---|---|---|
|supOS SDK|com.bluetron.sdk:bluetron-java-sdk|http://gitlab.nb.bluetron.cn/bluetron-app-middle/bluetron-app-middle-sdk|封装了supOS鉴权、用户、对象等部分接口|
|supOS SDK starter|com.bluetron.app:bluetron-app-starter-sdk|http://gitlab.nb.bluetron.cn/bluetron-app-starter/bluetron-app-starter-sdk|supOS SDK starter|
|spring web相关配置|bluetron-nb-common:bluetron-nb-common-sb-starter|http://gitlab.bluetron.com/bluetron-framework/bluetron-nb-common/bluetron-nb-common-sb-starter|app自动配置，异常拦截，全局SpringContextHolder,Web日志拦截等|
|缓存|bluetron-nb-common:bluetron-nb-common-redis-starter|http://gitlab.bluetron.com/bluetron-framework/bluetron-nb-common/bluetron-nb-common-redis-starter|封装了redis|
|数据库配置|bluetron-nb-common:bluetron-nb-common-db-starte|http://gitlab.bluetron.com/bluetron-framework/bluetron-nb-common/bluetron-nb-common-db-starter|Mybatis数据库配置|
|分布式任务|bluetron-nb-common:bluetron-nb-common-job-starte|http://gitlab.bluetron.com/bluetron-framework/bluetron-nb-common/bluetron-nb-common-job-starter|xxlJob配置|
|swagger2配置|bluetron-nb-common:bluetron-nb-common-swagger2-starte|http://gitlab.bluetron.com/bluetron-framework/bluetron-nb-common/bluetron-nb-common-swagger2-starter|swagger2配置|
|常用工具类|bluetron-nb-common:bluetron-nb-common-util|http://gitlab.bluetron.com/bluetron-framework/bluetron-nb-common/bluetron-nb-common-util|常用工具类|
|基础包|bluetron-nb-common:bluetron-nb-common-base|http://gitlab.bluetron.com/bluetron-framework/bluetron-nb-common/bluetron-nb-common-base|底层entity、各种父类等|


本项目创建自[DDD-archetype](http://gitlab.nb.bluetron.cn/shiming/bss-app-archetype)

$P$P arthas
```shell script
docker exec -it $(docker ps|grep ${rootArtifactId}|grep java|awk '{print $1}') /bin/sh -c "wget -O/dev/null http://192.168.8.74/tool/arthas/arthas-bin.tar && tar -vxf arthas-bin.tar >dev/null && java -jar ./arthas-bin/arthas-boot.jar"
```
