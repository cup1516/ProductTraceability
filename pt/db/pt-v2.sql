/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 8.0.17 : Database - pt-v2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`pt-v2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `pt-v2`;

/*Table structure for table `center_code_info` */

DROP TABLE IF EXISTS `center_code_info`;

CREATE TABLE `center_code_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(45) DEFAULT NULL,
  `times` varchar(45) DEFAULT NULL,
  `product` varchar(45) DEFAULT NULL,
  `company` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `center_code_info` */

insert  into `center_code_info`(`id`,`code`,`times`,`product`,`company`) values (1,'avc','0','苹果','绿叶水果'),(2,'ead','0','香蕉','企业B');

/*Table structure for table `center_company` */

DROP TABLE IF EXISTS `center_company`;

CREATE TABLE `center_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `person` varchar(45) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `number` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `validity` varchar(45) DEFAULT '0',
  `company_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  UNIQUE KEY `number_UNIQUE` (`number`),
  KEY `UNIQUE` (`company_id`),
  CONSTRAINT `UNIQUE` FOREIGN KEY (`company_id`) REFERENCES `center_companyid` (`company_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `center_company` */

insert  into `center_company`(`id`,`name`,`person`,`code`,`number`,`address`,`username`,`validity`,`company_id`) values (1,'绿叶水果','张老板','1235','5101','合肥','admin','1',NULL),(2,'阿发水果店','阿发','3210','112233','北京',NULL,NULL,NULL),(3,'水果企业A','小A','1230812','1280321','北京',NULL,NULL,NULL),(6,'水果企业B','法人小明','123870','071389','上海',NULL,NULL,NULL),(8,'京东','刘强东','17569','95187','合肥',NULL,NULL,NULL);

/*Table structure for table `center_company_account` */

DROP TABLE IF EXISTS `center_company_account`;

CREATE TABLE `center_company_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `code` varchar(45) DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `validity` varchar(45) DEFAULT NULL,
  `website` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `center_company_account` */

insert  into `center_company_account`(`id`,`name`,`code`,`username`,`password`,`validity`,`website`) values (1,'水果测试','1284','admin',NULL,NULL,NULL),(2,'水果测试1','7410',NULL,NULL,'1','www.baidu.com');

/*Table structure for table `center_companyid` */

DROP TABLE IF EXISTS `center_companyid`;

CREATE TABLE `center_companyid` (
  `company_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `company_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '0-manor 1-dealer 2-warehouse',
  `url` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`company_id`,`url`),
  UNIQUE KEY `UNIQUE_COMPANY_ID` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `center_companyid` */

insert  into `center_companyid`(`company_id`,`company_name`,`type`,`url`) values ('1','test','0','test'),('2','dealer','1','dealer'),('3','manor','2','manor'),('4','warehouse','3','warehouse');

/*Table structure for table `center_consumer` */

DROP TABLE IF EXISTS `center_consumer`;

CREATE TABLE `center_consumer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `query_time` varchar(45) DEFAULT NULL,
  `query_address` varchar(45) DEFAULT NULL,
  `query_code` varchar(45) DEFAULT NULL,
  `product` varchar(45) DEFAULT NULL,
  `product_company` varchar(45) DEFAULT NULL,
  `points` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `center_consumer` */

insert  into `center_consumer`(`id`,`name`,`phone`,`query_time`,`query_address`,`query_code`,`product`,`product_company`,`points`) values (1,'张三','12354','2020-07-12','北京','zxc235','苹果','苹果公司','1'),(2,'李四','1823','20200102','北京','a2asd1','苹果','APPLE COMPANY','0');

/*Table structure for table `center_dept` */

DROP TABLE IF EXISTS `center_dept`;

CREATE TABLE `center_dept` (
  `dept_id` varchar(32) NOT NULL COMMENT '部门id',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父部门id',
  `ancestors` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

/*Data for the table `center_dept` */

insert  into `center_dept`(`dept_id`,`parent_id`,`ancestors`,`dept_name`,`order_num`,`leader`,`phone`,`email`,`status`,`del_flag`,`create_by`,`create_time`,`update_by`,`update_time`) values ('100','0','0','若依科技',0,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),('101','100','0,100','深圳总公司',1,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),('102','100','0,100','长沙分公司',2,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),('103','101','0,100,101','研发部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),('104','101','0,100,101','市场部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),('105','101','0,100,101','测试部门',3,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),('106','101','0,100,101','财务部门',4,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),('107','101','0,100,101','运维部门',5,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),('108','102','0,100,102','市场部门',1,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00'),('109','102','0,100,102','财务部门',2,'若依','15888888888','ry@qq.com','0','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00');

/*Table structure for table `center_menu` */

DROP TABLE IF EXISTS `center_menu`;

CREATE TABLE `center_menu` (
  `menu_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单ID',
  `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

/*Data for the table `center_menu` */

insert  into `center_menu`(`menu_id`,`menu_name`,`parent_id`,`order_num`,`path`,`component`,`is_frame`,`menu_type`,`visible`,`status`,`perms`,`icon`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values ('06b247e6a8f943eeba20d17e83fc5518','通告管理','4afd8122b6534073b80c7f84ffd310c9',4,'noticeManagement','portal/admin/content/noticeManagement',1,'C','0','0',NULL,'#','管理员-王','2020-07-09 21:56:59','',NULL,''),('1','系统管理','0',1,'/system','Layout',1,'M','0','0','','system','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统管理目录'),('100','用户管理','1',1,'user','system/user/index',1,'C','0','0','system:user:list','user','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','用户管理菜单'),('1001','用户查询','100',1,'','',1,'F','0','0','system:user:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1002','用户新增','100',2,'','',1,'F','0','0','system:user:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1003','用户修改','100',3,'','',1,'F','0','0','system:user:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1004','用户删除','100',4,'','',1,'F','0','0','system:user:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1005','用户导出','100',5,'','',1,'F','0','0','system:user:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1006','用户导入','100',6,'','',1,'F','0','0','system:user:import','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1007','重置密码','100',7,'','',1,'F','0','0','system:user:resetPwd','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1008','角色查询','101',1,'','',1,'F','0','0','system:role:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1009','角色新增','101',2,'','',1,'F','0','0','system:role:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('101','角色管理','1',2,'role','system/role/index',1,'C','0','0','system:role:list','peoples','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','角色管理菜单'),('1010','角色修改','101',3,'','',1,'F','0','0','system:role:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1011','角色删除','101',4,'','',1,'F','0','0','system:role:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1012','角色导出','101',5,'','',1,'F','0','0','system:role:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1013','菜单查询','102',1,'','',1,'F','0','0','system:menu:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1014','菜单新增','102',2,'','',1,'F','0','0','system:menu:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1015','菜单修改','102',3,'','',1,'F','0','0','system:menu:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1016','菜单删除','102',4,'','',1,'F','0','0','system:menu:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1017','部门查询','103',1,'','',1,'F','0','0','system:dept:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1018','部门新增','103',2,'','',1,'F','0','0','system:dept:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1019','部门修改','103',3,'','',1,'F','0','0','system:dept:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('102','菜单管理','1',3,'menu','system/menu/index',1,'C','0','0','system:menu:list','tree-table','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','菜单管理菜单'),('1020','部门删除','103',4,'','',1,'F','0','0','system:dept:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1021','岗位查询','104',1,'','',1,'F','0','0','system:post:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1022','岗位新增','104',2,'','',1,'F','0','0','system:post:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1023','岗位修改','104',3,'','',1,'F','0','0','system:post:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1024','岗位删除','104',4,'','',1,'F','0','0','system:post:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1025','岗位导出','104',5,'','',1,'F','0','0','system:post:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1026','字典查询','105',1,'#','',1,'F','0','0','system:dict:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1027','字典新增','105',2,'#','',1,'F','0','0','system:dict:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1028','字典修改','105',3,'#','',1,'F','0','0','system:dict:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1029','字典删除','105',4,'#','',1,'F','0','0','system:dict:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('103','部门管理','1',4,'dept','system/dept/index',1,'C','0','0','system:dept:list','tree','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','部门管理菜单'),('1030','字典导出','105',5,'#','',1,'F','0','0','system:dict:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1031','参数查询','106',1,'#','',1,'F','0','0','system:config:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1032','参数新增','106',2,'#','',1,'F','0','0','system:config:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1033','参数修改','106',3,'#','',1,'F','0','0','system:config:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1034','参数删除','106',4,'#','',1,'F','0','0','system:config:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1035','参数导出','106',5,'#','',1,'F','0','0','system:config:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1036','公告查询','107',1,'#','',1,'F','0','0','system:notice:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1037','公告新增','107',2,'#','',1,'F','0','0','system:notice:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1038','公告修改','107',3,'#','',1,'F','0','0','system:notice:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1039','公告删除','107',4,'#','',1,'F','0','0','system:notice:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('104','岗位管理','1',5,'post','system/post/index',1,'C','0','0','system:post:list','post','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','岗位管理菜单'),('1040','操作查询','500',1,'#','',1,'F','0','0','monitor:operlog:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1041','操作删除','500',2,'#','',1,'F','0','0','monitor:operlog:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1042','日志导出','500',4,'#','',1,'F','0','0','monitor:operlog:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1043','登录查询','501',1,'#','',1,'F','0','0','monitor:logininfor:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1044','登录删除','501',2,'#','',1,'F','0','0','monitor:logininfor:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1045','日志导出','501',3,'#','',1,'F','0','0','monitor:logininfor:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1046','在线查询','109',1,'#','',1,'F','0','0','monitor:online:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1047','批量强退','109',2,'#','',1,'F','0','0','monitor:online:batchLogout','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1048','单条强退','109',3,'#','',1,'F','0','0','monitor:online:forceLogout','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1049','任务查询','110',1,'#','',1,'F','0','0','monitor:job:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('105','字典管理','1',6,'dict','system/dict/index',1,'C','0','0','system:dict:list','dict','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','字典管理菜单'),('1050','任务新增','110',2,'#','',1,'F','0','0','monitor:job:add','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1051','任务修改','110',3,'#','',1,'F','0','0','monitor:job:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1052','任务删除','110',4,'#','',1,'F','0','0','monitor:job:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1053','状态修改','110',5,'#','',1,'F','0','0','monitor:job:changeStatus','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1054','任务导出','110',7,'#','',1,'F','0','0','monitor:job:export','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1055','生成查询','114',1,'#','',1,'F','0','0','tool:gen:query','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1056','生成修改','114',2,'#','',1,'F','0','0','tool:gen:edit','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1057','生成删除','114',3,'#','',1,'F','0','0','tool:gen:remove','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1058','导入代码','114',2,'#','',1,'F','0','0','tool:gen:import','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('1059','预览代码','114',4,'#','',1,'F','0','0','tool:gen:preview','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('106','参数设置','1',7,'config','system/config/index',1,'C','0','0','system:config:list','edit','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','参数设置菜单'),('1060','生成代码','114',5,'#','',1,'F','0','0','tool:gen:code','#','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('107','通知公告','1',8,'notice','system/notice/index',1,'C','0','0','system:notice:list','message','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','通知公告菜单'),('108','日志管理','1',9,'log','system/log/index',1,'M','0','0','','log','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','日志管理菜单'),('109','在线用户','2',1,'online','monitor/online/index',1,'C','0','0','monitor:online:list','online','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','在线用户菜单'),('110','定时任务','2',2,'job','monitor/job/index',1,'C','0','0','monitor:job:list','job','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','定时任务菜单'),('111','数据监控','2',3,'druid','monitor/druid/index',1,'C','0','0','monitor:druid:list','druid','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','数据监控菜单'),('112','服务监控','2',4,'server','monitor/server/index',1,'C','0','0','monitor:server:list','server','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','服务监控菜单'),('113','表单构建','3',1,'build','tool/build/index',1,'C','0','0','tool:build:list','build','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','表单构建菜单'),('114','代码生成','3',2,'gen','tool/gen/index',1,'C','0','0','tool:gen:list','code','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','代码生成菜单'),('115','系统接口','3',3,'swagger','tool/swagger/index',1,'C','0','0','tool:swagger:list','swagger','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统接口菜单'),('128f06486cd2405784df544491dbc5dc','个人中心','c6f2a1e491af44f9b8ef4c97a98f42db',1,'CompanyInformation','center/company/CompanyInformation',1,'C','0','0',NULL,'#','管理员-王','2020-07-10 16:13:01','',NULL,''),('2','系统监控','0',2,'/monitor','Layout',1,'M','0','0','','monitor','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统监控目录'),('257f0b882a9c45dea40b2fe8b31f24cf','公共论坛管理','4afd8122b6534073b80c7f84ffd310c9',6,'blogManagement','portal/admin/content/blogManagement',1,'C','0','0',NULL,'#','管理员-王','2020-07-09 21:58:55','',NULL,''),('2857845749dc4410a9a8f2e273f6328b','内容管理','0',1,'/center','Layout',1,'M','0','0',NULL,'#','管理员-王','2020-06-21 11:18:40','',NULL,''),('3','系统工具','0',3,'/tool','Layout',1,'M','0','0','','tool','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统工具目录'),('4','若依官网','0',4,'http://ruoyi.vip','Layout',0,'M','0','0','','guide','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','若依官网地址'),('4afd8122b6534073b80c7f84ffd310c9','新媒体管理','0',5,'/portal','Layout',1,'M','0','0',NULL,'#','管理员-王','2020-07-09 21:38:43','',NULL,''),('500','操作日志','108',1,'operlog','monitor/operlog/index',1,'C','0','0','monitor:operlog:list','form','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','操作日志菜单'),('501','登录日志','108',2,'logininfor','monitor/logininfor/index',1,'C','0','0','monitor:logininfor:list','logininfor','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','登录日志菜单'),('72d87f9e87ea4009895c9b39392e646d','公告管理','4afd8122b6534073b80c7f84ffd310c9',3,'announcement','portal/admin/content/announceManagement',1,'C','0','0',NULL,'code','管理员-王','2020-07-09 21:55:42','',NULL,''),('878c71e29b2747b88967dc448627ff72','订单管理','2857845749dc4410a9a8f2e273f6328b',1,'index','center/index/index',1,'C','0','0','center:index:list','#','管理员-王','2020-06-21 11:26:45','管理员-王','2020-06-21 16:32:43',''),('936371a7d87c49aaa0f520c513ecabdc','审核管理','4afd8122b6534073b80c7f84ffd310c9',1,'check','Layout',1,'M','0','0',NULL,'date','管理员-王','2020-07-09 22:20:17','',NULL,''),('b5b21945208c471d8620f6624ada6303','新闻管理','4afd8122b6534073b80c7f84ffd310c9',2,'newsManagement','portal/admin/content/newsManagement',1,'C','0','0',NULL,'drag','管理员-王','2020-07-09 21:52:39','',NULL,''),('b77d6c168d5549238d583b89fa559b45','未审核通告','936371a7d87c49aaa0f520c513ecabdc',2,'notCheck','portal/admin/content/notverifyNoticeManagement',1,'C','0','0',NULL,'date','管理员-王','2020-07-09 22:22:40','',NULL,''),('c5385c8faff54ab8ac65c46ddc2283ff','防伪码管理','2857845749dc4410a9a8f2e273f6328b',4,'code','center/code/CodeManagement',1,'C','0','0','center:code:list','#','管理员-王','2020-06-21 16:49:55','',NULL,''),('c6f2a1e491af44f9b8ef4c97a98f42db','企业信息','0',6,'/company','Layout',1,'M','0','0','','#','管理员-王','2020-07-10 16:11:58','管理员-王','2020-07-10 16:13:49',''),('d02a3fe98f3248a5a2904c3204c0322b','消费者管理','2857845749dc4410a9a8f2e273f6328b',3,'ConsumerManagement','center/consumer/ConsumerManagement',1,'C','0','0','center:consumer:list','#','管理员-王','2020-06-21 16:48:26','',NULL,''),('d1474b11f7eb4100b820b94879295edf','已审核通告','936371a7d87c49aaa0f520c513ecabdc',1,'toCheck','portal/admin/content/verifyNoticeManagement',1,'C','0','0',NULL,'chart','管理员-王','2020-07-09 22:21:33','',NULL,''),('e28b217680c64ff4bd7c2d218b010095','评论管理','4afd8122b6534073b80c7f84ffd310c9',5,'commentManagement','portal/admin/content/commentManagement',1,'C','0','0',NULL,'#','管理员-王','2020-07-09 21:57:56','',NULL,''),('ffcc855359e04ce6aec0e1e2148ebd51','企业管理','2857845749dc4410a9a8f2e273f6328b',2,'CompanyManagement','center/company/CompanyManagement',1,'C','0','0','center:company:list','#','管理员-王','2020-06-21 16:36:38','管理员-王','2020-06-21 16:36:55','');

/*Table structure for table `center_order` */

DROP TABLE IF EXISTS `center_order`;

CREATE TABLE `center_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(45) DEFAULT NULL,
  `company_name` varchar(45) DEFAULT NULL,
  `company_code` varchar(45) DEFAULT NULL,
  `product` varchar(45) DEFAULT NULL,
  `quantity` varchar(45) DEFAULT NULL,
  `singleprice` varchar(45) DEFAULT NULL,
  `totalprice` varchar(45) DEFAULT NULL,
  `create_time` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `auditor` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `center_order` */

insert  into `center_order`(`id`,`number`,`company_name`,`company_code`,`product`,`quantity`,`singleprice`,`totalprice`,`create_time`,`state`,`auditor`) values (1,'sdw56','果蔬企业A','6541','菠萝','20000','0.01','200','2020年6月15日','0','lisi'),(2,'asdz321','APPLE COMPANY','asd1258','苹果','10000','0.01','100','2020-07-15',NULL,'admin');

/*Table structure for table `center_post` */

DROP TABLE IF EXISTS `center_post`;

CREATE TABLE `center_post` (
  `post_id` varchar(32) NOT NULL COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int(2) NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位信息表';

/*Data for the table `center_post` */

insert  into `center_post`(`post_id`,`post_code`,`post_name`,`post_sort`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values ('1','ceo','董事长',1,'0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('2','se','项目经理',2,'0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00',''),('3','hr','人力资源',3,'0','admin','2018-03-16 11:33:00','管理员-王','2020-06-08 15:56:01','11');

/*Table structure for table `center_role` */

DROP TABLE IF EXISTS `center_role`;

CREATE TABLE `center_role` (
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色权限',
  `role_sort` int(2) DEFAULT NULL COMMENT '显示顺序',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` char(1) DEFAULT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `center_role` */

insert  into `center_role`(`role_id`,`role_name`,`role_code`,`role_sort`,`create_by`,`create_time`,`status`,`del_flag`) values ('0','开发管理员','dev',0,NULL,'2020-07-23 20:40:50','0','0'),('2b3d6d99ff7d4836a0466e54e25c4863','信息中心员工','center_staff',1,'admin','2020-07-10 01:56:21','0','0'),('2dfde145251c4d2199dd1f4005cb0d0f','游客','VISITOR',1,'ADMIN','2020-06-05 00:00:00','0','0'),('a55a346ad8004deb9bffaeecf151e0f0','管理员','ADMIN',0,'ADMIN','2020-06-05 00:00:00','0','0'),('b0e5c6a752904990888e46a18a1176f5','企业员工',NULL,1,'admin','2020-07-10 17:40:42','0','0');

/*Table structure for table `center_role_menu` */

DROP TABLE IF EXISTS `center_role_menu`;

CREATE TABLE `center_role_menu` (
  `role_id` varchar(255) NOT NULL COMMENT '角色ID',
  `menu_id` varchar(255) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

/*Data for the table `center_role_menu` */

insert  into `center_role_menu`(`role_id`,`menu_id`) values ('0','1'),('0','1013'),('0','1014'),('0','1015'),('0','1016'),('0','102'),('2b3d6d99ff7d4836a0466e54e25c4863','2857845749dc4410a9a8f2e273f6328b'),('2b3d6d99ff7d4836a0466e54e25c4863','878c71e29b2747b88967dc448627ff72'),('2b3d6d99ff7d4836a0466e54e25c4863','c5385c8faff54ab8ac65c46ddc2283ff'),('2b3d6d99ff7d4836a0466e54e25c4863','d02a3fe98f3248a5a2904c3204c0322b'),('2b3d6d99ff7d4836a0466e54e25c4863','ffcc855359e04ce6aec0e1e2148ebd51'),('2dfde145251c4d2199dd1f4005cb0d0f','100'),('a55a346ad8004deb9bffaeecf151e0f0','1'),('a55a346ad8004deb9bffaeecf151e0f0','100'),('a55a346ad8004deb9bffaeecf151e0f0','1001'),('a55a346ad8004deb9bffaeecf151e0f0','1002'),('a55a346ad8004deb9bffaeecf151e0f0','1003'),('a55a346ad8004deb9bffaeecf151e0f0','1004'),('a55a346ad8004deb9bffaeecf151e0f0','1005'),('a55a346ad8004deb9bffaeecf151e0f0','1006'),('a55a346ad8004deb9bffaeecf151e0f0','1007'),('a55a346ad8004deb9bffaeecf151e0f0','1008'),('a55a346ad8004deb9bffaeecf151e0f0','1009'),('a55a346ad8004deb9bffaeecf151e0f0','101'),('a55a346ad8004deb9bffaeecf151e0f0','1010'),('a55a346ad8004deb9bffaeecf151e0f0','1011'),('a55a346ad8004deb9bffaeecf151e0f0','1012'),('a55a346ad8004deb9bffaeecf151e0f0','1013'),('a55a346ad8004deb9bffaeecf151e0f0','1014'),('a55a346ad8004deb9bffaeecf151e0f0','1015'),('a55a346ad8004deb9bffaeecf151e0f0','1016'),('a55a346ad8004deb9bffaeecf151e0f0','1017'),('a55a346ad8004deb9bffaeecf151e0f0','1018'),('a55a346ad8004deb9bffaeecf151e0f0','1019'),('a55a346ad8004deb9bffaeecf151e0f0','102'),('a55a346ad8004deb9bffaeecf151e0f0','1020'),('a55a346ad8004deb9bffaeecf151e0f0','1021'),('a55a346ad8004deb9bffaeecf151e0f0','1022'),('a55a346ad8004deb9bffaeecf151e0f0','1023'),('a55a346ad8004deb9bffaeecf151e0f0','1024'),('a55a346ad8004deb9bffaeecf151e0f0','1025'),('a55a346ad8004deb9bffaeecf151e0f0','1026'),('a55a346ad8004deb9bffaeecf151e0f0','1027'),('a55a346ad8004deb9bffaeecf151e0f0','1028'),('a55a346ad8004deb9bffaeecf151e0f0','1029'),('a55a346ad8004deb9bffaeecf151e0f0','103'),('a55a346ad8004deb9bffaeecf151e0f0','1030'),('a55a346ad8004deb9bffaeecf151e0f0','1031'),('a55a346ad8004deb9bffaeecf151e0f0','1032'),('a55a346ad8004deb9bffaeecf151e0f0','1033'),('a55a346ad8004deb9bffaeecf151e0f0','1034'),('a55a346ad8004deb9bffaeecf151e0f0','1035'),('a55a346ad8004deb9bffaeecf151e0f0','1036'),('a55a346ad8004deb9bffaeecf151e0f0','1037'),('a55a346ad8004deb9bffaeecf151e0f0','1038'),('a55a346ad8004deb9bffaeecf151e0f0','1039'),('a55a346ad8004deb9bffaeecf151e0f0','104'),('a55a346ad8004deb9bffaeecf151e0f0','1040'),('a55a346ad8004deb9bffaeecf151e0f0','1041'),('a55a346ad8004deb9bffaeecf151e0f0','1042'),('a55a346ad8004deb9bffaeecf151e0f0','1043'),('a55a346ad8004deb9bffaeecf151e0f0','1044'),('a55a346ad8004deb9bffaeecf151e0f0','1045'),('a55a346ad8004deb9bffaeecf151e0f0','1046'),('a55a346ad8004deb9bffaeecf151e0f0','1047'),('a55a346ad8004deb9bffaeecf151e0f0','1048'),('a55a346ad8004deb9bffaeecf151e0f0','1049'),('a55a346ad8004deb9bffaeecf151e0f0','105'),('a55a346ad8004deb9bffaeecf151e0f0','1050'),('a55a346ad8004deb9bffaeecf151e0f0','1051'),('a55a346ad8004deb9bffaeecf151e0f0','1052'),('a55a346ad8004deb9bffaeecf151e0f0','1053'),('a55a346ad8004deb9bffaeecf151e0f0','1054'),('a55a346ad8004deb9bffaeecf151e0f0','1055'),('a55a346ad8004deb9bffaeecf151e0f0','1056'),('a55a346ad8004deb9bffaeecf151e0f0','1057'),('a55a346ad8004deb9bffaeecf151e0f0','1058'),('a55a346ad8004deb9bffaeecf151e0f0','1059'),('a55a346ad8004deb9bffaeecf151e0f0','106'),('a55a346ad8004deb9bffaeecf151e0f0','1060'),('a55a346ad8004deb9bffaeecf151e0f0','107'),('a55a346ad8004deb9bffaeecf151e0f0','108'),('a55a346ad8004deb9bffaeecf151e0f0','109'),('a55a346ad8004deb9bffaeecf151e0f0','110'),('a55a346ad8004deb9bffaeecf151e0f0','111'),('a55a346ad8004deb9bffaeecf151e0f0','112'),('a55a346ad8004deb9bffaeecf151e0f0','113'),('a55a346ad8004deb9bffaeecf151e0f0','114'),('a55a346ad8004deb9bffaeecf151e0f0','115'),('a55a346ad8004deb9bffaeecf151e0f0','2'),('a55a346ad8004deb9bffaeecf151e0f0','3'),('a55a346ad8004deb9bffaeecf151e0f0','4'),('a55a346ad8004deb9bffaeecf151e0f0','500'),('a55a346ad8004deb9bffaeecf151e0f0','501'),('b0e5c6a752904990888e46a18a1176f5','128f06486cd2405784df544491dbc5dc'),('b0e5c6a752904990888e46a18a1176f5','c6f2a1e491af44f9b8ef4c97a98f42db');

/*Table structure for table `center_user` */

DROP TABLE IF EXISTS `center_user`;

CREATE TABLE `center_user` (
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户账号',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户密码',
  `email` varchar(255) DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(13) DEFAULT NULL COMMENT '用户手机号码',
  `sex` char(1) DEFAULT '2' COMMENT '性别 0-男 1-女 2-未指定',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `status` char(1) DEFAULT '0' COMMENT '状态 0-正常 1-停用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标志 0-正常 1-已删除',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `center_user` */

insert  into `center_user`(`user_id`,`dept_id`,`user_name`,`nick_name`,`password`,`email`,`phone`,`sex`,`avatar`,`status`,`create_time`,`del_flag`,`remark`) values ('0','0','admin0','开发管理员','$2a$10$mC3UHi0fywGB8oUlCXadz.Tx3QMhprJ5SCbDCZ0pPZOxMeBTNZ3ey','703@qq.com','13269997145','2',NULL,'0','2020-07-23 20:40:25','0',NULL),('0d7f0606545b45dbb7ae24b8d1c7e7b1','100','zhangsan05','zhangsan05','$2a$10$kh.oniUBOiIIC4kVRS0JEegCjDTMzEK7JQYMxy78GP32fjXN2Ib/a','7701@qq.com','15012345687','2',NULL,'0','2020-07-10 17:40:17','0',NULL),('1','103','admin','管理员-王','$2a$10$mC3UHi0fywGB8oUlCXadz.Tx3QMhprJ5SCbDCZ0pPZOxMeBTNZ3ey','836591036@qq.com','13269997166','2',NULL,'0','2020-06-03 10:11:14','0',NULL),('2','102','user','游客-王','$2a$10$mC3UHi0fywGB8oUlCXadz.Tx3QMhprJ5SCbDCZ0pPZOxMeBTNZ3ey',NULL,NULL,'2',NULL,'0','2020-06-03 10:11:16','0',NULL),('2c344e060ea5476294c0ae77dbe98af8','100','zhangsanfeng2','张三丰2','$2a$10$6xQLfX2KkkDst91cciV/puwkvag70sqFkjcxJAMl3o9LD1j65d5DS','123010@qq.com','13200002345','2',NULL,'0','2020-07-12 21:54:04','0',NULL),('39078e1d20134f798e8e07e24c572ff0',NULL,'zs0','张三00','$2a$10$Vl2Hj6180mwQN5JAfgDuvub7kJ1K3.T7fsPIZAqyAJGrke62P1e4K',NULL,'13200002300','2',NULL,'0','2020-07-15 12:15:57','0',NULL),('674d87f026884c029dbc7f47891d79aa','100','zhangsan','张三','$2a$10$XWL3eN7Z2TK0UDM9VlqDXeNgHBRKc37JcGhDVf2FVeW3mVnQJA.Li','123@qq.com','13901234560','2',NULL,'0','2020-07-10 01:54:15','0',NULL),('682118dd526e47718d061765dbadc62c','100','zhangsan9','张三9','$2a$10$pQeaBc/EkscmVZszX237JOJ4BiQpphleJCQ0D1Q2NdLHAWkJHGhJK','79810@qq.com','13200018345','0',NULL,'0','2020-07-15 12:00:14','0',NULL),('8e9862624a494f49a7446fd0de6af94b',NULL,'zhangsanfeng','张三丰','$2a$10$fnj79W8Fn0M.O.jY92RHgummORYgLuuO6Ikyz/cJzVaac.iDIdUjK',NULL,'13200012345','2',NULL,'0','2020-07-12 21:47:58','0',NULL),('946c4b35c0bb4916a600ede84480f248','100','zhangsan06','张三6','$2a$10$98geSYO8mjPavYeWGEFnBumt4RPOb0CpgNvk9pnUZibzsb.sRBu86','15001@qq.com','15001234567','2',NULL,'0','2020-07-10 17:47:13','0',NULL),('97c06c95682344bebe3198f64017097e','100','zhangsan10','张三10','$2a$10$7LKk7da/F6s.Dp/x./N1wOMZoEEHtic0A1k8K/eAZVfWfOAPD4Q9q','10012@qq.com','13200010345','0',NULL,'0','2020-07-15 12:05:40','0',NULL),('d4800d8991394aba8364aca5575f7f6f','100','zhangsan07','张三07','$2a$10$WhIuapD/Atk8FYVJ1lLQAusX4SOObj.UI.Gncw54O3y/DM7WJfJp6','75603@qq.com','15801234567','2',NULL,'0','2020-07-12 18:18:23','0',NULL),('d8c361ad8b3a45abb34f2fa8ca9e0539',NULL,NULL,'wanglei','$2a$10$j9KEA9OKs2eGZHP73V4u3eK42iAcXND1dR5HDbrgRnmBMFexczN8.',NULL,'13269997113','2',NULL,'0','2020-07-12 21:42:08','0',NULL);

/*Table structure for table `center_user_post` */

DROP TABLE IF EXISTS `center_user_post`;

CREATE TABLE `center_user_post` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `post_id` varchar(32) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与岗位关联表';

/*Data for the table `center_user_post` */

insert  into `center_user_post`(`user_id`,`post_id`) values ('1','1'),('2','2'),('2c344e060ea5476294c0ae77dbe98af8','1'),('39078e1d20134f798e8e07e24c572ff0','1'),('682118dd526e47718d061765dbadc62c','1'),('8e9862624a494f49a7446fd0de6af94b','2'),('97c06c95682344bebe3198f64017097e','1'),('d8c361ad8b3a45abb34f2fa8ca9e0539','1'),('f581b220a23d4b5bae0851f42cb4b4b2','2');

/*Table structure for table `center_user_role` */

DROP TABLE IF EXISTS `center_user_role`;

CREATE TABLE `center_user_role` (
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `center_user_role` */

insert  into `center_user_role`(`user_id`,`role_id`) values ('0','0'),('0d7f0606545b45dbb7ae24b8d1c7e7b1','b0e5c6a752904990888e46a18a1176f5'),('1','2dfde145251c4d2199dd1f4005cb0d0f'),('1','a55a346ad8004deb9bffaeecf151e0f0'),('2c344e060ea5476294c0ae77dbe98af8','2b3d6d99ff7d4836a0466e54e25c4863'),('39078e1d20134f798e8e07e24c572ff0','1'),('674d87f026884c029dbc7f47891d79aa','2b3d6d99ff7d4836a0466e54e25c4863'),('682118dd526e47718d061765dbadc62c','b0e5c6a752904990888e46a18a1176f5'),('8e9862624a494f49a7446fd0de6af94b','2'),('946c4b35c0bb4916a600ede84480f248','b0e5c6a752904990888e46a18a1176f5'),('97c06c95682344bebe3198f64017097e','2b3d6d99ff7d4836a0466e54e25c4863'),('d4800d8991394aba8364aca5575f7f6f','b0e5c6a752904990888e46a18a1176f5'),('d8c361ad8b3a45abb34f2fa8ca9e0539','1');

/*Table structure for table `oauth_client_details` */

DROP TABLE IF EXISTS `oauth_client_details`;

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(128) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `oauth_client_details` */

insert  into `oauth_client_details`(`client_id`,`resource_ids`,`client_secret`,`scope`,`authorized_grant_types`,`web_server_redirect_uri`,`authorities`,`access_token_validity`,`refresh_token_validity`,`additional_information`,`autoapprove`) values ('client',NULL,'$2a$10$NFa0OR/Pj8vPjYgr.5K6J.P6qffapWV/pbJss/QbBrO0GmMSOm3oS','all','authorization_code,refresh_token,password',NULL,NULL,3600,36000,NULL,'1');

/*Table structure for table `sys_dict_data` */

DROP TABLE IF EXISTS `sys_dict_data`;

CREATE TABLE `sys_dict_data` (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8 COMMENT='字典数据表';

/*Data for the table `sys_dict_data` */

insert  into `sys_dict_data`(`dict_code`,`dict_sort`,`dict_label`,`dict_value`,`dict_type`,`css_class`,`list_class`,`is_default`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values (1,1,'男','0','sys_user_sex','','','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','性别男'),(2,2,'女','1','sys_user_sex','','','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','性别女'),(3,3,'未知','2','sys_user_sex','','','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','性别未知'),(4,1,'显示','0','sys_show_hide','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','显示菜单'),(5,2,'隐藏','1','sys_show_hide','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','隐藏菜单'),(6,1,'正常','0','sys_normal_disable','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),(7,2,'停用','1','sys_normal_disable','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','停用状态'),(8,1,'正常','0','sys_job_status','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),(9,2,'暂停','1','sys_job_status','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','停用状态'),(10,1,'默认','DEFAULT','sys_job_group','','','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','默认分组'),(11,2,'系统','SYSTEM','sys_job_group','','','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统分组'),(12,1,'是','Y','sys_yes_no','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统默认是'),(13,2,'否','N','sys_yes_no','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统默认否'),(14,1,'通知','1','sys_notice_type','','warning','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','通知'),(15,2,'公告','2','sys_notice_type','','success','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','公告'),(16,1,'正常','0','sys_notice_status','','primary','Y','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),(17,2,'关闭','1','sys_notice_status','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','关闭状态'),(18,1,'新增','1','sys_oper_type','','info','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','新增操作'),(19,2,'修改','2','sys_oper_type','','info','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','修改操作'),(20,3,'删除','3','sys_oper_type','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','删除操作'),(21,4,'授权','4','sys_oper_type','','primary','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','授权操作'),(22,5,'导出','5','sys_oper_type','','warning','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','导出操作'),(23,6,'导入','6','sys_oper_type','','warning','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','导入操作'),(24,7,'强退','7','sys_oper_type','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','强退操作'),(25,8,'生成代码','8','sys_oper_type','','warning','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','生成操作'),(26,9,'清空数据','9','sys_oper_type','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','清空操作'),(27,1,'成功','0','sys_common_status','','primary','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','正常状态'),(28,2,'失败','1','sys_common_status','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','停用状态'),(29,1,'未完成','0','dealer_process_status','','primary','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','未完成状态'),(30,2,'已完成','1','dealer_process_status','','danger','N','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','已完成状态'),(31,1,'未送审','0','dealer_check_status','','','N','0','',NULL,'',NULL,NULL),(32,2,'已送审','1','dealer_check_status','','','N','0','',NULL,'',NULL,NULL),(33,3,'已通过','2','dealer_check_status','','','N','0','',NULL,'',NULL,NULL),(34,4,'拒绝','3','dealer_check_status','','','N','0','',NULL,'',NULL,NULL),(35,1,'数据中心','0','sys_client_type',NULL,NULL,'N','0','',NULL,'',NULL,NULL),(36,2,'庄园','1','sys_client_type',NULL,NULL,'N','0','',NULL,'',NULL,NULL),(37,3,'经销商','2','sys_client_type',NULL,NULL,'N','0','',NULL,'',NULL,NULL),(38,4,'仓储','3','sys_client_type',NULL,NULL,'N','0','',NULL,'',NULL,NULL),(39,1,'基础菜单','0','sys_menu_type',NULL,NULL,'N','0','',NULL,'',NULL,NULL),(40,2,'业务菜单','1','sys_menu_type',NULL,NULL,'N','0','',NULL,'',NULL,NULL);

/*Table structure for table `sys_dict_type` */

DROP TABLE IF EXISTS `sys_dict_type`;

CREATE TABLE `sys_dict_type` (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8 COMMENT='字典类型表';

/*Data for the table `sys_dict_type` */

insert  into `sys_dict_type`(`dict_id`,`dict_name`,`dict_type`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values (1,'用户性别','sys_user_sex','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','用户性别列表'),(2,'菜单状态','sys_show_hide','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','菜单状态列表'),(3,'系统开关','sys_normal_disable','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统开关列表'),(4,'任务状态','sys_job_status','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','任务状态列表'),(5,'任务分组','sys_job_group','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','任务分组列表'),(6,'系统是否','sys_yes_no','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','系统是否列表'),(7,'通知类型','sys_notice_type','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','通知类型列表'),(8,'通知状态','sys_notice_status','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','通知状态列表'),(9,'操作类型','sys_oper_type','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','操作类型列表'),(10,'系统状态','sys_common_status','0','admin','2018-03-16 11:33:00','ry','2018-03-16 11:33:00','登录状态列表');

/*Table structure for table `system_dept` */

DROP TABLE IF EXISTS `system_dept`;

CREATE TABLE `system_dept` (
  `dept_id` varchar(32) NOT NULL COMMENT '部门id',
  `company_id` varchar(32) DEFAULT NULL,
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父部门id',
  `ancestors` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '' COMMENT '部门名称',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '联系电话',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

/*Data for the table `system_dept` */

insert  into `system_dept`(`dept_id`,`company_id`,`parent_id`,`ancestors`,`dept_name`,`order_num`,`leader`,`phone`,`email`,`status`,`del_flag`,`create_by`,`create_time`,`update_by`,`update_time`) values ('111','2','0','0','经销公司总部门',0,'阿萨德','13269997166','1@qq.cxom','0','0','',NULL,'','2020-07-25 15:39:32'),('6042272c5e1f4fc3a11ef52983bced92','2','111','0,111','12323',2,'123213','13269997166','123@qq.com','0','1','','2020-07-24 14:23:04','',NULL);

/*Table structure for table `system_menu` */

DROP TABLE IF EXISTS `system_menu`;

CREATE TABLE `system_menu` (
  `menu_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单ID',
  `menu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int(4) DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组件路径',
  `is_frame` int(1) DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `client_type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '0-数据中心 1-庄园 2-经销商 3-仓储',
  `type` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '0-基础菜单 1-业务菜单',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础菜单权限表';

/*Data for the table `system_menu` */

insert  into `system_menu`(`menu_id`,`menu_name`,`parent_id`,`order_num`,`path`,`component`,`is_frame`,`menu_type`,`client_type`,`type`,`visible`,`status`,`perms`,`icon`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values ('1','系统管理','0',1,'/system','Layout',1,'M','','0','0','0','','system','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','系统管理目录'),('100','用户管理','1',1,'user','system/user/index',1,'C','','0','0','0','system:user:list','user','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','用户管理菜单'),('1001','用户查询','100',1,'','',1,'F','','0','0','0','system:user:query','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1002','用户新增','100',2,'','',1,'F','','0','0','0','system:user:add','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1003','用户修改','100',3,'','',1,'F','','0','0','0','system:user:edit','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1004','用户删除','100',4,'','',1,'F','','0','0','0','system:user:remove','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1005','用户导出','100',5,'','',1,'F','','0','0','0','system:user:export','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1006','用户导入','100',6,'','',1,'F','','0','0','0','system:user:import','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1007','重置密码','100',7,'','',1,'F','','0','0','0','system:user:resetPwd','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1008','角色查询','101',1,'','',1,'F','','0','0','0','system:role:query','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1009','角色新增','101',2,'','',1,'F','','0','0','0','system:role:add','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('101','角色管理','1',2,'role','system/role/index',1,'C','','0','0','0','system:role:list','peoples','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','角色管理菜单'),('1010','角色修改','101',3,'','',1,'F','','0','0','0','system:role:edit','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1011','角色删除','101',4,'','',1,'F','','0','0','0','system:role:remove','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1012','角色导出','101',5,'','',1,'F','','0','0','0','system:role:export','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1013','菜单查询','102',1,'','',1,'F','','0','0','0','system:menu:query','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1014','菜单新增','102',2,'','',1,'F','','0','0','0','system:menu:add','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1015','菜单修改','102',3,'','',1,'F','','0','0','0','system:menu:edit','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1016','菜单删除','102',4,'','',1,'F','','0','0','0','system:menu:remove','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1017','部门查询','103',1,'','',1,'F','','0','0','0','system:dept:query','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1018','部门新增','103',2,'','',1,'F','','0','0','0','system:dept:add','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1019','部门修改','103',3,'','',1,'F','','0','0','0','system:dept:edit','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('102','菜单管理','1',3,'menu','system/menu/index',1,'C','','0','0','0','system:menu:list','tree-table','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','菜单管理菜单'),('1020','部门删除','103',4,'','',1,'F','','0','0','0','system:dept:remove','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1021','岗位查询','104',1,'','',1,'F','','0','0','0','system:post:query','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1022','岗位新增','104',2,'','',1,'F','','0','0','0','system:post:add','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1023','岗位修改','104',3,'','',1,'F','','0','0','0','system:post:edit','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1024','岗位删除','104',4,'','',1,'F','','0','0','0','system:post:remove','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1025','岗位导出','104',5,'','',1,'F','','0','0','0','system:post:export','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('103','部门管理','1',4,'dept','system/dept/index',1,'C','','0','0','0','system:dept:list','tree','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','部门管理菜单'),('104','岗位管理','1',5,'post','system/post/index',1,'C','','0','0','0','system:post:list','post','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','岗位管理菜单'),('11110','岗位管理0','0',5,'post','system/post/index',1,'C','0','1','0','0','system:post:list','post','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','岗位管理菜单'),('11111','岗位管理1','0',5,'post','system/post/index',1,'C','1','1','0','0','system:post:list','post','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','岗位管理菜单'),('11112','岗位管理2','0',5,'post','system/post/index',1,'C','2','1','0','0','system:post:list','post','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','岗位管理菜单'),('11113','岗位管理3','0',5,'post','system/post/index',1,'C','3','1','0','0','system:post:list','post','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','岗位管理菜单');

/*Table structure for table `system_post` */

DROP TABLE IF EXISTS `system_post`;

CREATE TABLE `system_post` (
  `post_id` varchar(32) NOT NULL COMMENT '岗位ID',
  `company_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int(2) NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`post_id`,`company_id`,`post_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位信息表';

/*Data for the table `system_post` */

insert  into `system_post`(`post_id`,`company_id`,`post_code`,`post_name`,`post_sort`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`,`del_flag`) values ('1','2','admin','管理员',0,'0','管理员','2020-07-25 12:17:42','管理员-经销','2020-07-25 15:40:34','1234','0');

/*Table structure for table `system_role` */

DROP TABLE IF EXISTS `system_role`;

CREATE TABLE `system_role` (
  `role_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色id',
  `company_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色权限',
  `role_sort` int(2) DEFAULT NULL COMMENT '显示顺序',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标志',
  PRIMARY KEY (`role_id`,`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_role` */

insert  into `system_role`(`role_id`,`company_id`,`role_name`,`role_code`,`role_sort`,`create_by`,`create_time`,`status`,`del_flag`) values ('1','2','经销商管理员','ADMIN',0,'0','2020-07-24 10:14:12','0','0'),('1386e29b73734746a0dcf7b9b5f00fe5','2','员工','worker',2,'dealer','2020-07-25 14:46:15','0','0');

/*Table structure for table `system_role_menu` */

DROP TABLE IF EXISTS `system_role_menu`;

CREATE TABLE `system_role_menu` (
  `role_id` varchar(255) NOT NULL COMMENT '角色ID',
  `company_id` varchar(32) NOT NULL,
  `menu_id` varchar(255) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`,`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

/*Data for the table `system_role_menu` */

insert  into `system_role_menu`(`role_id`,`company_id`,`menu_id`) values ('1','2','1'),('1','2','100'),('1','2','1001'),('1','2','1002'),('1','2','1003'),('1','2','1004'),('1','2','1005'),('1','2','1006'),('1','2','1007'),('1','2','1008'),('1','2','1009'),('1','2','101'),('1','2','1010'),('1','2','1011'),('1','2','1012'),('1','2','1017'),('1','2','1018'),('1','2','1019'),('1','2','1020'),('1','2','1021'),('1','2','1022'),('1','2','1023'),('1','2','1024'),('1','2','1025'),('1','2','103'),('1','2','104'),('1','2','11111'),('1386e29b73734746a0dcf7b9b5f00fe5','2','11111');

/*Table structure for table `system_user` */

DROP TABLE IF EXISTS `system_user`;

CREATE TABLE `system_user` (
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户id',
  `company_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dept_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '部门id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '用户昵称',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户密码',
  `email` varchar(255) DEFAULT NULL COMMENT '用户邮箱',
  `phone` varchar(13) DEFAULT NULL COMMENT '用户手机号码',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '性别 0-男 1-女',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `status` char(1) DEFAULT '0' COMMENT '状态 0-正常 1-停用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `del_flag` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT '0' COMMENT '删除标志 0-正常 1-已删除',
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`company_id`,`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_user` */

insert  into `system_user`(`user_id`,`company_id`,`dept_id`,`user_name`,`nick_name`,`password`,`email`,`phone`,`sex`,`avatar`,`status`,`create_time`,`del_flag`,`remark`) values ('0','0','#','admin0','开发管理员','$2a$10$mC3UHi0fywGB8oUlCXadz.Tx3QMhprJ5SCbDCZ0pPZOxMeBTNZ3ey',NULL,'100','0',NULL,'0','2020-07-23 16:40:00','0',NULL),('1','1','100','admin','管理员-王','$2a$10$mC3UHi0fywGB8oUlCXadz.Tx3QMhprJ5SCbDCZ0pPZOxMeBTNZ3ey','836591036@qq.com','13269997163','1',NULL,'0','2020-06-03 10:11:14','0',NULL),('1','2','111','dealer','管理员-经销','$2a$10$mhluDTOXlcFoQscOnc8eveVPRXKVXOZdJmJcRmSTcaOjw/aoLxdZW','836591036@qq.com','13269997164','1',NULL,'0','2020-06-03 10:11:14','0',NULL),('1','3','100','manor','管理员-庄园','$2a$10$mC3UHi0fywGB8oUlCXadz.Tx3QMhprJ5SCbDCZ0pPZOxMeBTNZ3ey','836591036@qq.com','13269997163','1',NULL,'0','2020-06-03 10:11:14','0',NULL),('1','4','100','warehouse','管理员-仓储','$2a$10$mC3UHi0fywGB8oUlCXadz.Tx3QMhprJ5SCbDCZ0pPZOxMeBTNZ3ey','836591036@qq.com','13269997163','1',NULL,'0','2020-06-03 10:11:14','0',NULL),('63d4db8a62a245e2aed575bc4a1833d5','2','111','dealer1','员工-经销商','$2a$10$mYtj5U6t7YTAi7TGBOMgb.wJKLN6.sM.qOCU4k8zJ99Y4Vj75bFxu','1@qq.com','13269997155','0',NULL,'0','2020-07-25 14:00:16','0','1234565');

/*Table structure for table `system_user_post` */

DROP TABLE IF EXISTS `system_user_post`;

CREATE TABLE `system_user_post` (
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `post_id` varchar(32) NOT NULL COMMENT '岗位ID',
  `company_id` varbinary(32) NOT NULL,
  PRIMARY KEY (`user_id`,`post_id`,`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与岗位关联表';

/*Data for the table `system_user_post` */

insert  into `system_user_post`(`user_id`,`post_id`,`company_id`) values ('1','1','2'),('63d4db8a62a245e2aed575bc4a1833d5','1','2');

/*Table structure for table `system_user_role` */

DROP TABLE IF EXISTS `system_user_role`;

CREATE TABLE `system_user_role` (
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  `company_id` varchar(32) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`,`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_user_role` */

insert  into `system_user_role`(`user_id`,`role_id`,`company_id`) values ('1','1','2'),('63d4db8a62a245e2aed575bc4a1833d5','1386e29b73734746a0dcf7b9b5f00fe5','2');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
