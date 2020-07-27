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

/*Table structure for table `system_company_info` */

DROP TABLE IF EXISTS `system_company_info`;

CREATE TABLE `system_company_info` (
  `company_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `company_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '0-manor 1-dealer 2-warehouse',
  `url` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`company_id`,`url`),
  UNIQUE KEY `UNIQUE_COMPANY_ID` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `system_company_info` */

insert  into `system_company_info`(`company_id`,`company_name`,`type`,`url`) values ('#','center','0',''),('2','dealer','1','dealer'),('3','manor','2','manor'),('4','warehouse','3','warehouse');

/*Table structure for table `system_dept` */

DROP TABLE IF EXISTS `system_dept`;

CREATE TABLE `system_dept` (
  `dept_id` varchar(32) NOT NULL COMMENT '部门id',
  `company_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
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
  PRIMARY KEY (`dept_id`,`company_id`)
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

insert  into `system_menu`(`menu_id`,`menu_name`,`parent_id`,`order_num`,`path`,`component`,`is_frame`,`menu_type`,`client_type`,`type`,`visible`,`status`,`perms`,`icon`,`create_by`,`create_time`,`update_by`,`update_time`,`remark`) values ('1','系统管理','0',1,'/system','Layout',1,'M','','0','0','0','','system','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','系统管理目录'),('100','用户管理','1',1,'user','system/user/index',1,'C','','0','0','0','system:user:list','user','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','用户管理菜单'),('1001','用户查询','100',1,'','',1,'F','','0','0','0','system:user:query','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1002','用户新增','100',2,'','',1,'F','','0','0','0','system:user:add','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1003','用户修改','100',3,'','',1,'F','','0','0','0','system:user:edit','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1004','用户删除','100',4,'','',1,'F','','0','0','0','system:user:remove','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1005','用户导出','100',5,'','',1,'F','','0','0','0','system:user:export','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1006','用户导入','100',6,'','',1,'F','','0','0','0','system:user:import','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1007','重置密码','100',7,'','',1,'F','','0','0','0','system:user:resetPwd','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1008','角色查询','101',1,'','',1,'F','','0','0','0','system:role:query','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1009','角色新增','101',2,'','',1,'F','','0','0','0','system:role:add','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('101','角色管理','1',2,'role','system/role/index',1,'C','','0','0','0','system:role:list','peoples','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','角色管理菜单'),('1010','角色修改','101',3,'','',1,'F','','0','0','0','system:role:edit','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1011','角色删除','101',4,'','',1,'F','','0','0','0','system:role:remove','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1012','角色导出','101',5,'','',1,'F','','0','0','0','system:role:export','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1013','菜单查询','102',1,'','',1,'F','','0','0','0','system:menu:query','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1014','菜单新增','102',2,'','',1,'F','','0','0','0','system:menu:add','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1015','菜单修改','102',3,'','',1,'F','','0','0','0','system:menu:edit','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1016','菜单删除','102',4,'','',1,'F','','0','0','0','system:menu:remove','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1017','部门查询','103',1,'','',1,'F','','0','0','0','system:dept:query','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1018','部门新增','103',2,'','',1,'F','','0','0','0','system:dept:add','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1019','部门修改','103',3,'','',1,'F','','0','0','0','system:dept:edit','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('102','菜单管理','1',3,'menu','system/menu/index',1,'C','','0','0','0','system:menu:list','tree-table','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','菜单管理菜单'),('1020','部门删除','103',4,'','',1,'F','','0','0','0','system:dept:remove','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1021','岗位查询','104',1,'','',1,'F','','0','0','0','system:post:query','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1022','岗位新增','104',2,'','',1,'F','','0','0','0','system:post:add','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1023','岗位修改','104',3,'','',1,'F','','0','0','0','system:post:edit','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1024','岗位删除','104',4,'','',1,'F','','0','0','0','system:post:remove','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('1025','岗位导出','104',5,'','',1,'F','','0','0','0','system:post:export','#','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00',''),('103','部门管理','1',4,'dept','system/dept/index',1,'C','','0','0','0','system:dept:list','tree','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','部门管理菜单'),('104','岗位管理','1',5,'post','system/post/index',1,'C','','0','0','0','system:post:list','post','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','岗位管理菜单'),('11110','岗位管理0','0',5,'post','system/post/index',1,'C','0','1','0','0','system:post:list','post','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','岗位管理菜单'),('11111','岗位管理1','0',5,'post','system/post/index',1,'C','1','1','0','0','system:post:list','post','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','岗位管理菜单'),('11112','岗位管理21321','0',5,'post','system/post/index',1,'C','2','1','0','0','system:post:list','post','admin','2018-03-16 11:33:00','admin','2020-07-27 10:15:58','岗位管理菜单'),('11113','岗位管理3','0',5,'post','system/post/index',1,'C','3','1','0','1','system:post:list','post','admin','2018-03-16 11:33:00','admin','2018-03-16 11:33:00','岗位管理菜单');

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

insert  into `system_role`(`role_id`,`company_id`,`role_name`,`role_code`,`role_sort`,`create_by`,`create_time`,`status`,`del_flag`) values ('0','#','数据中心管理员','admin',0,NULL,NULL,'0','0'),('1','2','经销商管理员','ADMIN',0,'0','2020-07-24 10:14:12','0','0'),('1386e29b73734746a0dcf7b9b5f00fe5','2','员工','worker',2,'dealer','2020-07-25 14:46:15','0','0');

/*Table structure for table `system_role_menu` */

DROP TABLE IF EXISTS `system_role_menu`;

CREATE TABLE `system_role_menu` (
  `role_id` varchar(255) NOT NULL COMMENT '角色ID',
  `company_id` varchar(32) NOT NULL,
  `menu_id` varchar(255) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`,`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色和菜单关联表';

/*Data for the table `system_role_menu` */

insert  into `system_role_menu`(`role_id`,`company_id`,`menu_id`) values ('0','#','1'),('0','#','1013'),('0','#','1014'),('0','#','1015'),('0','#','1016'),('0','#','102'),('1','2','1'),('1','2','100'),('1','2','1001'),('1','2','1002'),('1','2','1003'),('1','2','1004'),('1','2','1005'),('1','2','1006'),('1','2','1007'),('1','2','1008'),('1','2','1009'),('1','2','101'),('1','2','1010'),('1','2','1011'),('1','2','1012'),('1','2','1017'),('1','2','1018'),('1','2','1019'),('1','2','1020'),('1','2','1021'),('1','2','1022'),('1','2','1023'),('1','2','1024'),('1','2','1025'),('1','2','103'),('1','2','104'),('1','2','11111'),('1','2','11114'),('1386e29b73734746a0dcf7b9b5f00fe5','2','11111');

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

insert  into `system_user`(`user_id`,`company_id`,`dept_id`,`user_name`,`nick_name`,`password`,`email`,`phone`,`sex`,`avatar`,`status`,`create_time`,`del_flag`,`remark`) values ('0','#','100','dev',NULL,'$2a$10$mYtj5U6t7YTAi7TGBOMgb.wJKLN6.sM.qOCU4k8zJ99Y4Vj75bFxu',NULL,NULL,'0',NULL,'0',NULL,'0',NULL),('1','1','100','admin','管理员-王','$2a$10$mC3UHi0fywGB8oUlCXadz.Tx3QMhprJ5SCbDCZ0pPZOxMeBTNZ3ey','836591036@qq.com','13269997163','1',NULL,'0','2020-06-03 10:11:14','0',NULL),('1','2','111','dealer','管理员-经销','$2a$10$mhluDTOXlcFoQscOnc8eveVPRXKVXOZdJmJcRmSTcaOjw/aoLxdZW','836591036@qq.com','13269997164','1',NULL,'0','2020-06-03 10:11:14','0',NULL),('1','3','100','manor','管理员-庄园','$2a$10$mC3UHi0fywGB8oUlCXadz.Tx3QMhprJ5SCbDCZ0pPZOxMeBTNZ3ey','836591036@qq.com','13269997163','1',NULL,'0','2020-06-03 10:11:14','0',NULL),('1','4','100','warehouse','管理员-仓储','$2a$10$mC3UHi0fywGB8oUlCXadz.Tx3QMhprJ5SCbDCZ0pPZOxMeBTNZ3ey','836591036@qq.com','13269997163','1',NULL,'0','2020-06-03 10:11:14','0',NULL),('63d4db8a62a245e2aed575bc4a1833d5','2','111','dealer1','员工-经销商','$2a$10$mYtj5U6t7YTAi7TGBOMgb.wJKLN6.sM.qOCU4k8zJ99Y4Vj75bFxu','1@qq.com','13269997155','0',NULL,'0','2020-07-25 14:00:16','0','1234565');

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

insert  into `system_user_role`(`user_id`,`role_id`,`company_id`) values ('0','0','#'),('1','1','2'),('63d4db8a62a245e2aed575bc4a1833d5','1386e29b73734746a0dcf7b9b5f00fe5','2');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
