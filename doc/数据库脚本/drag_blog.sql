/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 10.1.35-MariaDB : Database - mogu_blog
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mogu_blog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mogu_blog`;

/*Table structure for table `t_admin` */

DROP TABLE IF EXISTS `t_admin`;

CREATE TABLE `t_admin` (
  `id` varchar(32) NOT NULL COMMENT '唯一id',
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `pass_word` varchar(255) NOT NULL COMMENT '密码',
  `gender` varchar(1) DEFAULT NULL COMMENT '性别(1:男2:女)',
  `avatar` varchar(100) DEFAULT NULL COMMENT '个人头像',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机',
  `valid_code` varchar(50) DEFAULT NULL COMMENT '邮箱验证码',
  `summary` varchar(200) DEFAULT NULL COMMENT '自我简介最多150字',
  `login_count` int(11) unsigned DEFAULT '0' COMMENT '登录次数',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `last_login_ip` varchar(50) DEFAULT '127.0.0.1' COMMENT '最后登录IP',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '状态',
  `creator` varchar(50) default null comment '创建人',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `updater` varchar(50) default null comment '修改人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `deleter` varchar(50) default null comment '删除人',
  `delete_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '删除时间',
  `is_delete` varchar(1) DEFAULT '0' COMMENT '是否删除',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `qq_number` varchar(255) DEFAULT NULL COMMENT 'QQ号',
  `we_chat` varchar(255) DEFAULT NULL COMMENT '微信号',
  `occupation` varchar(255) DEFAULT NULL COMMENT '职业',
  `github` varchar(255) DEFAULT NULL COMMENT 'github地址',
  `gitee` varchar(255) DEFAULT NULL COMMENT 'gitee地址',
  `role_uid` varchar(32) DEFAULT NULL COMMENT '拥有的角色uid',
  `person_resume` text COMMENT '履历',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

/*Data for the table `t_admin` */

insert  into `t_admin`(`uid`,`user_name`,`pass_word`,`gender`,`avatar`,`email`,`birthday`,`mobile`,`valid_code`,`summary`,`login_count`,`last_login_time`,`last_login_ip`,`status`,`create_time`,`update_time`,`nick_name`,`qq_number`,`we_chat`,`occupation`,`github`,`gitee`,`role_uid`,`person_resume`) values ('1723ad77a1ca06b29a011471036a2dd6','test1','$2a$10$eus6faJ6/X2hNo5Cm.7bK.Z0oP9UQADthoqRaGTwHEdwehnaMj4Lu','1',',70f5b89fe70a28d95e10c19c96bf2e85','1595833114@qq.com',NULL,NULL,NULL,NULL,46,'2020-06-09 18:52:03','192.168.80.1',1,'2020-03-22 10:23:12','2020-06-09 18:52:03','test1',NULL,NULL,NULL,NULL,NULL,'d105da79260f4d6a8a03571e4a2b17bc',NULL),('1f01cd1d2f474743b241d74008b12333','admin','$2a$10$hMxXWDrxPkrqmtPwziYP7.2cPewb9IcwpojeB9r.1N8aHe8v9BvEC','1',',63da0632ccf7f1fde167bdf92ff25b6b','1595833114@qq.com',NULL,NULL,NULL,'一个95后！正在潜心研究机器学习和Java后端技术，一边学习一边积累经验',204,'2020-06-09 19:30:46','192.168.80.1',1,'2020-03-22 08:38:04','2020-06-09 19:30:46','陌溪','1595833114',NULL,'Java开发','https://github.com/moxi624/mogu_blog_v2','https://gitee.com/moxi159753/mogu_blog_v2','434994947c5a4ee3a710cd277357c7c3','<h2>前言</h2>\r\n\r\n<p>目前博客源码已经开源至码云和Github中，感兴趣的小伙伴可以star关注一下下~</p>\r\n\r\n<p>Gitee地址：<a href=\"https://gitee.com/moxi159753/mogu_blog_v2\">https://gitee.com/moxi159753/mogu_blog_v2</a></p>\r\n\r\n<p>Github地址：<a href=\"https://github.com/moxi624/mogu_blog_v2\">https://github.com/moxi624/mogu_blog_v2</a></p>\r\n\r\n<hr />\r\n<p>&nbsp;</p>\r\n\r\n<h2>关于我</h2>\r\n\r\n<p>许志翔，目前就读于桂林电子科技大学，是一名研二的学生，所属计算机信息与安全学院，研究方向是教育大数据，是一名&quot;不顾正业&quot;的研究僧，沉迷于Java和Vue技术开发，梦想着进入BATJ，也将一直为此不断努力了~</p>\r\n\r\n<p>正宗95后，爱编程，爱旅游，爱生活，爱锻炼，从刚研究生入学后，就坚持着两件事，一个是写代码，一个就是每天5公里慢跑</p>\r\n\r\n<pre>\r\n<code>意志力和天生才华，都是人们在事实发生了之后再去赋予某个人的优点：杰森是位不可思议的网球选手，因此，他一定生下来就具有这种才华；杰尼年复一年地练习拉小提琴，每天坚持几个小时，因此，他一定有着令人难以置信的意志力。\r\n\r\n——《刻意练习：从新手到大师》</code></pre>\r\n\r\n<p>最近可能随着研究生生涯过半，已经要着手开始写小论文的事情了，博客项目的更新估计会变的比较缓慢，应该不会存在大版本的更新迭代了</p>\r\n\r\n<p>不过目前博客项目的技术功能也编写的差不多，可能还要做的就是SEO优化，后面一段时间应该注重于网站的稳定性和BUG的解决，要是小伙伴在使用的时候，发现了什么问题，欢迎私聊我，或者在QQ群里提出~</p>\r\n\r\n<p>在今年的8、9月份，也该着手于校招了，后面应该更多的时间沉淀在Java的基础学习了，当然如果有老哥有内推名额的话，欢迎推荐我一下下....&nbsp; &nbsp;卑微.jpg&nbsp;</p>\r\n\r\n<p><img src=\"http://image.moguit.cn/52552ed0efb245a9a67d5c9928d72e14\" /></p>\r\n\r\n<p>回顾2019年，每天也会在项目中，花费一些时间去提交代码，可能有的时候是增加了新功能，有的时候是解决一个BUG，到现在已经成为了一个习惯了，因此博客项目中也添加了比较多的有趣的功能，比如这个文章贡献度</p>\r\n\r\n<p><img src=\"http://image.moguit.cn/aaff67a315c547c5964f0aebb4e8ce23\" /></p>\r\n\r\n<p>哈哈哈，其实它和码云上的代码贡献度是一样的，每次发表一篇博客，就会标记出个点，点越大说明该天发表博客越多，可能是因为自己有些强迫症的原因，不过我也希望能够借此来激励自己养成每天写博客的习惯，通过分享自己学习到的东西，来和各位IT的前辈们共同进步。</p>\r\n\r\n<p>当然闲暇之余也会玩玩游戏，有空的时候会在酒馆搓搓炉石~，玩玩农药，有喜欢的小伙伴也可以一起，虽然我贼菜</p>\r\n\r\n<hr />\r\n<h2>项目起因</h2>\r\n\r\n<p>本博客项目由我和几个小伙伴参与开发的，最开始的搭建蘑菇博客的初心是为了巩固和学习Java开发的一些知识，因此项目的技术选型都是比较新颖的技术，可能这些技术并不一定适用于博客系统，但是我也想着能尽可能把更多的技术融合进来，毕竟通过自己手把手的操作一遍，也能够算是入门了。我也很庆幸我成功将自己的项目开源出来了，并且坚持下来，蘑菇博客起源是2018年9月，到想在也已经度过了1年半的岁月。我也从最开始只会一点点Jsp和Servlet就出去找工作的傻小子慢慢成长了，在读研之前，我也在公司里呆过，算上来好像将近快一年，很感谢之前在公司里的同事和领导，是他们带我入门企业级的项目开发，让我养成了很多Java项目开发的规范。</p>\r\n\r\n<p>起初项目开源在码云上，没有多少人关注，所以自己也是坚持做自己的喜欢的事，有的时候是看看论文，有的时候敲敲代码，在2019年12月14日，蘑菇博客被码云推荐了，上了首页</p>\r\n\r\n<p><img src=\"http://image.moguit.cn/49865d11fd4c4b289d87bf305b2dde0a\" /></p>\r\n\r\n<p>&nbsp;</p>\r\n\r\n<p>然后项目的关注度就开始上升了，有些小伙伴就开始关注项目的运行和部署了，所以我也花费了一些时间，整理了博客的开发、运行、部署的文档，希望每个小伙伴都能够通过本项目一起学习</p>\r\n\r\n<p>目前蘑菇博客已经有300star了，很高兴大家对蘑菇博客项目的认可</p>\r\n\r\n<p><img src=\"http://image.moguit.cn/f55b31b7e00b42eda71e88105c4e147a\" /></p>\r\n\r\n<p>同时因为更新比较勤快，项目也在码云&nbsp; 博客&nbsp; &nbsp;关键字搜索的第一个，不过未来要走得路还很长，我也希望能够认识更多志同道合的小伙伴，然后一起学习和交流</p>\r\n\r\n<p><img src=\"http://image.moguit.cn/36588a0c8bf04e9bb103eac0f432bfa7\" /></p>\r\n');

/*Table structure for table `t_blog` */