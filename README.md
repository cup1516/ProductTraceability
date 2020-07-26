# 部署运行

## 组织结构

```
|-- pt -- 系统后端
|   |-- db -- 数据库
|   |-- pt-auth -- 认证中心(端口:9001)
|   |-- pt-center -- 数据中心微服务
|   |-- pt-chat -- 聊天系统微服务
|   |-- pt-common -- 系统公共模块
|   |-- pt-dealer -- 经销商微服务
|   |-- pt-eureka -- 注册中心(端口:9000)
|   |-- pt-gateway -- 网关(端口:9998)
|   |-- pt-manor -- 庄园微服务
|   |-- pt-order -- 订单微服务
|   |-- pt-portal -- 门户微服务
|   |-- pt-swagger -- api文档微服务
|   |-- pt-upload -- 文件上传微服务
|   |-- pt-user -- 用户权限管理微服务(端口:9005)
|    -- pt-warehouse -- 仓储微服务
`-- pt-vue  -- 系统前端(端口:8080)
```

## 技术选型

#### 后端技术：

|       技术       | 名称 |
| :--: | :--: |
| Spring Eureka | 服务发现框架 |
| Spring Security | 安全框架 |
| Spring Gateway | 网关 |
| MyBatis | ORM框架 |
| MyBatis-Plus | MyBatis增强工具 |
| Redis | 分布式缓存数据库 |
| Swagger2 | 接口测试框架 |

#### 前端技术:

|       技术       | 名称 |
| :--: | :--: |
| Vue | 界面构建框架 |

## 环境搭建

#### 开发工具:

- MySql: 数据库
- SQLyog：数据库客户端
- IntelliJ IDEA: 开发IDE
- Another.Redis.Desktop.Manager：Redis客户端

#### 开发环境：

- Jdk1.8
- Mysql 8.0+
- Redis 3.0+

## 开发指南:

### 启动（后台）

* 新建数据库，导入db文件夹下的pt-v2.sql
* 根据个人配置环境修改pt-auth、pt-user以及其他微服务中的`src/main/resources/application.yml`文件，修改当中的数据库及redis配置
* 依次启动pt-eureka、pt-gateway、pt-auth、pt-user(顺序可不完全相同)，即可完成核心服务启动

### 启动（前端）

* 执行`npm run install `,安装前端必要依赖

* 执行`npm run dev`,运行成功会有如下显示

  ```
   DONE  Compiled successfully in 941ms     
   
    App running at:
    - Local:   http://localhost:8080/    
    - Network: http://192.168.0.101:8080/
  ```

  