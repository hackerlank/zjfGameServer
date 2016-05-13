/*
SQLyog Ultimate v11.24 (32 bit)
MySQL - 5.5.24-log : Database - zjfgame
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`zjfgame` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `zjfgame`;

/*Table structure for table `hero` */

DROP TABLE IF EXISTS `hero`;

CREATE TABLE `hero` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据库id',
  `name` varchar(255) DEFAULT NULL,
  `roleId` int(11) DEFAULT NULL COMMENT '玩家id',
  `heroId` int(11) DEFAULT NULL COMMENT '英雄配置表id',
  `emotion` int(11) DEFAULT NULL COMMENT '心情',
  `hungry` int(11) DEFAULT NULL COMMENT '饥饿度',
  `effective` int(11) DEFAULT NULL COMMENT '效率',
  `skillId` int(11) DEFAULT NULL COMMENT '技能id',
  `talentLv` tinyint(4) DEFAULT NULL COMMENT '天赋等级',
  `realize` int(11) DEFAULT NULL COMMENT '领悟值',
  `age` int(11) DEFAULT NULL COMMENT '每个阶段的年龄值',
  `loveSkillId` int(11) DEFAULT NULL COMMENT '技能喜好',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `kitchen` */

DROP TABLE IF EXISTS `kitchen`;

CREATE TABLE `kitchen` (
  `roleId` int(11) NOT NULL,
  `kitchenSpaceStr` text,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
