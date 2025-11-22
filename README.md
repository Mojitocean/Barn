# Barn

DDD 经典四层架构：
+---------------------+
|     Application     | ← 应用层（用例编排、事务控制）
+---------------------+
|      Domain         | ← 领域层（核心业务逻辑、实体、值对象、领域服务）
+---------------------+
|   Infrastructure    | ← 基础设施层（数据库、缓存、消息队列、第三方服务）
+---------------------+
|      Interfaces     | ← 接口适配层（Controller、DTO、API 网关）
+---------------------+

项目类型：
Barn (Root Project)
├── pom.xml                                     # [Root POM] 管理全局版本、依赖管理
├── .gitignore
│
├── mall-common/                                # [公共模块]
│   ├── pom.xml
│   └── src/main/java/com/barn/common/          # Result, Enum, Utils, Exception
│
├── mall-api/                                   # [契约层] 存放 Dubbo 接口
│   ├── pom.xml
│   ├── mall-api-order/                         # 订单服务接口
│   │   ├── pom.xml
│   │   └── src/main/java/com/barn/api/order/   # OrderServiceApi.java, OrderDTO.java
│   └── mall-api-product/                       # 商品服务接口
│       ├── pom.xml
│       └── src/main/java/com/barn/api/product/
│
├── mall-service-order/                         # [订单微服务] 父工程
│   ├── pom.xml                                 # 管理内部 5 个子模块的依赖关系
│   │
│   ├── mall-service-order-domain/              # 1. [领域层] 纯 Java，无框架依赖
│   │   ├── pom.xml
│   │   └── src/main/java/com/barn/order/domain/
│   │       ├── entity/                         # Order (聚合根)
│   │       ├── valobj/                         # Address (值对象)
│   │       ├── repo/                           # OrderRepository (接口)
│   │       └── service/                        # OrderDomainService (领域服务)
│   │
│   ├── mall-service-order-infra/               # 2. [基础设施层]
│   │   ├── pom.xml                             # 依赖: domain, mybatis, redis, rocketmq
│   │   ├── src/main/java/com/barn/order/infra/
│   │   │   ├── mapper/                         # MyBatis Mapper 接口
│   │   │   ├── repo/                           # OrderRepositoryImpl
│   │   │   └── util/
│   │   └── src/main/resources/
│   │       └── mapper/                         # OrderMapper.xml
│   │
│   ├── mall-service-order-app/                 # 3. [应用层]
│   │   ├── pom.xml                             # 依赖: domain, api-product(RPC)
│   │   └── src/main/java/com/barn/order/app/
│   │       ├── service/                        # OrderAppService (编排)
│   │       └── assembler/                      # DTO <-> Entity 转换
│   │
│   ├── mall-service-order-adapter/             # 4. [适配层]
│   │   ├── pom.xml                             # 依赖: app, api-order(Impl)
│   │   └── src/main/java/com/barn/order/adapter/
│   │       ├── web/                            # OrderController
│   │       ├── rpc/                            # OrderServiceProvider (Dubbo Impl)
│   │       └── mq/                             # RocketMQ Consumer
│   │
│   └── mall-service-order-start/               # 5. [启动层]
│       ├── pom.xml                             # 依赖: adapter, infra
│       ├── src/main/java/com/barn/order/       # OrderApplication.java
│       └── src/main/resources/                 # application.yml, logback.xml
│
└── mall-service-product/                       # [商品微服务] (结构同上，省略)
mall-service-product/                       # [商品微服务] 父工程
├── pom.xml                                 # 管理内部 5 个子模块的依赖与版本
│
├── mall-service-product-domain/            # 1. [领域层] 核心业务逻辑 (纯Java)
│   ├── pom.xml
│   └── src/main/java/com/barn/product/domain/
│       ├── entity/                         # Spu(商品), Sku(规格品), Category(分类), Brand(品牌)
│       ├── valobj/                         # ProductSpec(规格值对象,如颜色:红), Stock(库存)
│       ├── repo/                           # ProductRepository, StockRepository (接口)
│       └── service/                        # ProductDomainService (复杂领域逻辑,如库存扣减校验)
│
├── mall-service-product-infra/             # 2. [基础设施层] 数据持久化
│   ├── pom.xml                             # 依赖: domain, mybatis, mysql, redis
│   ├── src/main/java/com/barn/product/infra/
│   │   ├── mapper/                         # MyBatis Mapper 接口 (SpuMapper, SkuMapper)
│   │   ├── repo/                           # ProductRepositoryImpl (实现Domain接口)
│   │   └── util/                           # 辅助工具
│   └── src/main/resources/
│       └── mapper/                         # SpuMapper.xml, SkuMapper.xml (手写SQL)
│
├── mall-service-product-app/               # 3. [应用层] 业务编排
│   ├── pom.xml                             # 依赖: domain
│   └── src/main/java/com/barn/product/app/
│       ├── service/                        # ProductAppService (上架、下架、锁库存编排)
│       ├── query/                          # ProductQueryService (专门处理复杂的CQRS查询)
│       └── assembler/                      # DTO <-> Entity 转换器
│
├── mall-service-product-adapter/           # 4. [适配层] 对外入口
│   ├── pom.xml                             # 依赖: app, mall-api-product(接口定义)
│   └── src/main/java/com/barn/product/adapter/
│       ├── web/                            # AdminProductController (后台管理API), AppProductController (C端API)
│       ├── rpc/                            # ProductServiceProvider (Dubbo 实现类)
│       └── job/                            # StockSyncJob (如: 定时同步缓存库存任务)
│
└── mall-service-product-start/             # 5. [启动层]
├── pom.xml                             # 依赖: adapter, infra
├── src/main/java/com/barn/product/     # ProductApplication.java
└── src/main/resources/                 # application.yml (DB配置, Dubbo配置)
