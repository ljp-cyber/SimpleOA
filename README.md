# SimpleOA
<p>
  <a href="https://github.com/ljp-cyber/SimpleOA"></a>
<p>

## 项目介绍
这个项目是自动化办公项目
，实现了报销工作流、登录验证模块、部门管理模块、员工管理模块。整个项目使用mysql作为数据库系统、spring框架作容器化管理、springmvc作web快速开发宽架、mybaits作持久化宽架、springs security做登录验证管理。其中报销工作流采用自己开发的流程框架。
1、采用邻接链表存储流程图
2、流程节点间跳转使用策略模式分离解藕，轻松定制各种条件跳转
3、流程构建使用构建者模式降低构建难度
4、开发通用流程节点、跳转策略提高代码重用

## 项目演示
项目在线演示地址：[http://www.shxex.cn/SimpleOA/](http://www.shxex.cn/Simple/)

### 技术选型
技术 | 说明 | 官网
----|----|----
jsp+servlet | 前端页面 | 
mysql| 数据库 |
mybaits| 持久化框架 |
spring+springMVC | web框架 |
spring security | 权限管理框架 |

##项目布局
``` lua
SimpleOA/src/com/ljp/simpleoa -- 源码目录
├── config-- java配置文件
├── controller -- 控制器模块
├── mapper -- mybaits的映射XML文件和java接口
├── model -- 实体模型
├── process -- 流程组件
├── security -- 安全配置模块
├── service -- 服务模块
├── utils -- 工具
├── MybatisGerneratorDo.java --mybaits代码生成工具
├── PaginationPlugin.java --mybaits分页插件
└── Constant.java --常量
``` lua

## 搭建步骤
- 安装jdk8以上开发环境
- 安装mysql5.7以上数据库
- 安装tomcat服务器
- 下载项目把bulid文件加拷贝到tomcat/webapps文件夹下，运行tomcat/bin/start.bat







