/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50524
Source Host           : localhost:3306
Source Database       : zjfgame

Target Server Type    : MYSQL
Target Server Version : 50524
File Encoding         : 65001

Date: 2016-05-22 23:56:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `hero`
-- ----------------------------
DROP TABLE IF EXISTS `hero`;
CREATE TABLE `hero` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据库id',
  `roleId` int(11) DEFAULT NULL COMMENT '玩家id',
  `emotion` int(11) DEFAULT NULL COMMENT '心情',
  `hungry` int(11) DEFAULT NULL COMMENT '饥饿度',
  `effective` int(11) DEFAULT NULL COMMENT '效率',
  `skillId` int(11) DEFAULT NULL COMMENT '技能id',
  `talentLv` tinyint(4) DEFAULT NULL COMMENT '天赋等级',
  `realize` int(11) DEFAULT NULL COMMENT '领悟值',
  `age` int(11) DEFAULT NULL COMMENT '每个阶段的年龄值',
  `loveSkillId` int(11) DEFAULT NULL COMMENT '技能喜好',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hero
-- ----------------------------

-- ----------------------------
-- Table structure for `kitchen`
-- ----------------------------
DROP TABLE IF EXISTS `kitchen`;
CREATE TABLE `kitchen` (
  `roleId` int(11) NOT NULL,
  `kitchenSpaceStr` text,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kitchen
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
