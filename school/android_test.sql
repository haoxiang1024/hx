/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80019 (8.0.19)
 Source Host           : localhost:3306
 Source Schema         : android_test

 Target Server Type    : MySQL
 Target Server Version : 80019 (8.0.19)
 File Encoding         : 65001

 Date: 14/06/2023 20:56:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `photo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `reg_date` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pub_date` datetime NULL DEFAULT NULL,
  `hidden` tinyint(1) NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `posts_id` int NULL DEFAULT NULL,
  `reply_for_id` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id6`(`user_id` ASC) USING BTREE,
  INDEX `posts_id1`(`posts_id` ASC) USING BTREE,
  INDEX `reply_for_id`(`reply_for_id` ASC) USING BTREE,
  CONSTRAINT `posts_id1` FOREIGN KEY (`posts_id`) REFERENCES `posts` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `reply_for_id` FOREIGN KEY (`reply_for_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id6` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, '测试', '2023-05-07 15:01:59', 0, 1, 1, NULL);
INSERT INTO `comment` VALUES (2, '侧视2', '2023-05-07 15:02:24', 0, 1, 1, 1);

-- ----------------------------
-- Table structure for found
-- ----------------------------
DROP TABLE IF EXISTS `found`;
CREATE TABLE `found`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `img` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pub_date` datetime NULL DEFAULT NULL,
  `content` varchar(15000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `place` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `state` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `stick` tinyint(1) NULL DEFAULT NULL,
  `lostfoundtype_id` int NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `lostfoundtype_id2`(`lostfoundtype_id` ASC) USING BTREE,
  INDEX `user_id2`(`user_id` ASC) USING BTREE,
  CONSTRAINT `lostfoundtype_id2` FOREIGN KEY (`lostfoundtype_id`) REFERENCES `lostfoundtype` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of found
-- ----------------------------
INSERT INTO `found` VALUES (1, '富士相机', 'https://i01piccdn.sogoucdn.com/6d4487492efcc0af', '2022-01-01 12:00:00', '在图书馆捡到的', '图书馆', '14350279651', '已认领', 0, 1, 37);
INSERT INTO `found` VALUES (2, '小米手环', 'https://img02.sogoucdn.com/app/a/100520020/b0c492a2adf5a5e4df4059dc0f98130f', '2022-06-06 15:47:21', '在体育馆捡到的', '体育馆', '11292232565', '已认领', 1, 1, 21);
INSERT INTO `found` VALUES (3, '华为路由器', 'https://i01piccdn.sogoucdn.com/3661d6d6b3b28d78', '2023-05-10 08:40:00', '在一教捡到的', '一教', '16169040399', '已认领', 0, 1, 46);
INSERT INTO `found` VALUES (4, '马克杯', 'https://img03.sogoucdn.com/app/a/100520020/ecd916c5e9393012764a27796defc118', '2023-05-28 17:20:00', '在食堂捡到的', '食堂', '19126972389', '未认领', 0, 1, 48);
INSERT INTO `found` VALUES (5, '西装外套', 'https://img02.sogoucdn.com/app/a/100520020/30de8fb57e3b6788414814ffb1854ea2', '2023-05-30 09:15:32', '在教学楼的走廊捡到的', '教学楼', '14843063924', '已认领', 1, 1, 50);
INSERT INTO `found` VALUES (6, '耳机', 'https://img02.sogoucdn.com/app/a/100520020/b0c492a2adf5a5e4df4059dc0f98130f', '2023-06-03 14:30:00', '在教学楼捡到的', '教学楼', '17481553826', '未认领', 0, 1, 40);
INSERT INTO `found` VALUES (7, '钥匙扣', 'https://img02.sogoucdn.com/app/a/100520020/70ebd7f19fce3d15cc2495ca853cc474', '2023-06-04 16:00:00', '在一号楼捡到的', '一号楼', '13300614196', '未认领', 0, 1, 43);
INSERT INTO `found` VALUES (8, '护照', 'https://img03.sogoucdn.com/app/a/100520020/06c89d445c79be53c45a0f2730ee5ed3', '2023-06-05 10:10:10', '在图书馆捡到的', '图书馆', '16169040399', '未认领', 0, 2, 46);
INSERT INTO `found` VALUES (9, '水杯', 'https://i03piccdn.sogoucdn.com/3363f100df25f8b4', '2023-06-06 20:20:20', '在操场捡到的', '操场', '16169040399', '已认领', 1, 3, 46);
INSERT INTO `found` VALUES (10, '笔记本电脑', 'https://i01piccdn.sogoucdn.com/455b8720e20ee2dd', '2023-06-07 01:23:45', '在实验楼捡到的', '实验楼', '14350279651', '未认领', 0, 1, 37);
INSERT INTO `found` VALUES (11, '钥匙扣', 'https://i03piccdn.sogoucdn.com/9a0d94eef9e10861', '2023-05-01 11:11:11', '在二号楼捡到的', '二号楼', '19468331823', '已认领', 1, 5, 10);
INSERT INTO `found` VALUES (12, '墨镜', 'https://img04.sogoucdn.com/app/a/100520020/aff3e1f13023c46db237ed547c39fc56', '2023-05-13 13:00:00', '在草坪捡到的', '草坪', '10481984190', '未认领', 0, 4, 18);
INSERT INTO `found` VALUES (13, '雨伞', 'https://i03piccdn.sogoucdn.com/dca70d6ddcb164b7', '2023-05-20 19:00:00', '在西门捡到的', '西门', '12414423969', '已认领', 1, 3, 51);
INSERT INTO `found` VALUES (14, '钱包', 'https://i02piccdn.sogoucdn.com/6da57361e27b7c27', '2023-05-25 08:30:00', '在行政楼捡到的', '行政楼', '16296242740', '已认领', 1, 2, 49);
INSERT INTO `found` VALUES (15, '瑜伽垫', 'https://img03.sogoucdn.com/app/a/100520020/4f5d9f90d1e14bc9757db969c014131e', '2023-05-26 16:00:00', '在操场捡到的', '操场', '10185636767', '未认领', 0, 3, 35);
INSERT INTO `found` VALUES (16, '钥匙扣', 'https://i03piccdn.sogoucdn.com/9a0d94eef9e10861', '2023-05-28 11:20:00', '在一教捡到的', '一教', '19684380255', '未认领', 0, 1, 42);
INSERT INTO `found` VALUES (17, '英文书籍', 'https://img02.sogoucdn.com/app/a/100520020/70402067f4c0b59cbde63dff31fdeb72', '2023-06-01 09:30:00', '在图书馆捡到的', '图书馆', '13023893077', '已认领', 1, 4, 38);
INSERT INTO `found` VALUES (18, '雨伞', 'https://i01piccdn.sogoucdn.com/0b88c6cb5705eade', '2023-06-02 12:00:00', '在东门口捡到的', '东门口', '13300614196', '已认领', 1, 5, 43);
INSERT INTO `found` VALUES (19, '游戏手柄', 'https://i03piccdn.sogoucdn.com/0ff3899a03c70076', '2023-06-05 14:00:00', '在齐乐网咖捡到的', '齐乐网咖', '10185636767', '未认领', 0, 3, 35);
INSERT INTO `found` VALUES (20, '袜子', 'https://i01piccdn.sogoucdn.com/6ae0daef25d611f2', '2023-06-06 08:00:00', '在洗衣房捡到的', '洗衣房', '10802788799', '已认领', 1, 2, 5);
INSERT INTO `found` VALUES (21, '行李箱', 'https://i01piccdn.sogoucdn.com/b5c917285ab0f69a', '2023-05-01 16:00:00', '在火车站捡到的', '火车站', '10185636767', '已认领', 1, 5, 35);
INSERT INTO `found` VALUES (22, '瑜伽垫', 'https://i01piccdn.sogoucdn.com/e10c289ad5158193', '2023-05-02 18:20:00', '在一号楼捡到的', '一号楼', '11934391401', '已认领', 1, 4, 23);
INSERT INTO `found` VALUES (23, '手提包', 'https://i04piccdn.sogoucdn.com/aeb09e3fafa252af', '2023-05-10 15:30:00', '在实验楼捡到的', '实验楼', '16060383911', '未认领', 0, 3, 12);
INSERT INTO `found` VALUES (24, '墨镜', 'https://img01.sogoucdn.com/app/a/100520020/e1ea29e5aa50152938c8217d66a79dea', '2023-05-13 20:00:00', '在大礼堂捡到的', '大礼堂', '17897210753', '已认领', 1, 2, 26);
INSERT INTO `found` VALUES (25, '钱包', 'https://i02piccdn.sogoucdn.com/49e308b64ea870a4', '2023-05-14 10:00:00', '在行政楼捡到的', '行政楼', '19126972389', '已认领', 1, 5, 48);
INSERT INTO `found` VALUES (26, '水杯', 'https://i03piccdn.sogoucdn.com/eedf063dccdab262', '2023-05-17 15:10:00', '在图书馆捡到的', '图书馆', '14350279651', '未认领', 0, 4, 37);
INSERT INTO `found` VALUES (27, '手套', 'https://i02piccdn.sogoucdn.com/32fc472ec54c6921', '2023-05-22 12:30:00', '在音乐楼捡到的', '音乐楼', '11292232565', '已认领', 1, 2, 21);
INSERT INTO `found` VALUES (28, '小米充电宝', 'https://i02piccdn.sogoucdn.com/ac809e1b92ab05f3', '2023-05-23 14:00:00', '在电子科技大厦捡到的', '电子科技大厦', '16954986375', '未认领', 0, 1, 52);
INSERT INTO `found` VALUES (29, '身份证', 'https://i02piccdn.sogoucdn.com/209624b0921d94e1', '2023-06-03 11:11:11', '在海韵园捡到的', '海韵园', '10481984190', '已认领', 1, 5, 18);
INSERT INTO `found` VALUES (30, 'T恤衫', 'https://i01piccdn.sogoucdn.com/6755fbb153bc71ae', '2023-06-07 08:00:00', '在智能楼捡到的', '智能楼', '13681837802', '未认领', 0, 3, 15);
INSERT INTO `found` VALUES (31, '洗发水', 'https://i03piccdn.sogoucdn.com/ea425bd696b4b512', '2023-05-02 13:00:00', '在二号楼的卫生间捡到的', '二号楼', '14350279651', '未认领', 0, 1, 37);
INSERT INTO `found` VALUES (32, '眼镜', 'https://i04piccdn.sogoucdn.com/60cb65a64fff52d1', '2023-05-07 18:00:00', '在科学实验楼捡到的', '科学实验楼', '14318088405', '已认领', 1, 4, 24);
INSERT INTO `found` VALUES (33, '计算器', 'https://i03piccdn.sogoucdn.com/5a05630abf9a9662', '2023-05-11 16:20:00', '在图书馆二楼捡到的', '图书馆', '18682675515', '已认领', 1, 3, 3);
INSERT INTO `found` VALUES (34, '钥匙扣', 'https://img02.sogoucdn.com/app/a/100520020/c0a94c0e0cbb48ff2076af7074b8bd79', '2023-05-15 09:10:00', '在一号楼的自动售货机旁边捡到的', '一号楼', '10939380459', '未认领', 0, 2, 13);
INSERT INTO `found` VALUES (35, '鼠标', 'https://img03.sogoucdn.com/app/a/100520020/f2ac4e25d8aa236f51828659b9c4bed6', '2023-05-18 10:30:00', '在实验楼B栋捡到的', '实验楼', '10525002053', '已认领', 1, 5, 34);
INSERT INTO `found` VALUES (36, '化妆品', 'https://img02.sogoucdn.com/app/a/100520020/70ebd7f19fce3d15cc2495ca853cc474', '2023-05-21 14:40:00', '在博物馆捡到的', '博物馆', '10481984190', '未认领', 0, 4, 18);
INSERT INTO `found` VALUES (37, '墨镜', 'https://img01.sogoucdn.com/app/a/100520020/3a3a600017b934b1bef0ab6ef3eb1612', '2023-05-24 07:50:00', '在一教捡到的', '一教', '17481553826', '已认领', 1, 3, 40);
INSERT INTO `found` VALUES (38, '口红', 'https://i04piccdn.sogoucdn.com/c465efcaaeeb3f3d', '2023-06-03 13:00:00', '在化妆品专卖店前捡到的', '化妆品专卖店', '10313633788', '已认领', 1, 2, 19);
INSERT INTO `found` VALUES (39, 'U盘', 'https://img02.sogoucdn.com/app/a/100520020/ebdc830ef3ecedcea306c02cba235320', '2023-06-04 16:30:00', '在计算机学院捡到的', '计算机学院', '14528186786', '未认领', 0, 1, 54);
INSERT INTO `found` VALUES (40, '帽子', 'https://img01.sogoucdn.com/app/a/100520020/e47d84cba33edefd02762d77ec7e6334', '2023-06-07 11:20:00', '在一号楼附近的草坪捡到的', '一号楼', '1', '已认领', 1, 5, 4);
INSERT INTO `found` VALUES (41, '红色包包', 'https://i04piccdn.sogoucdn.com/6b77d4ff6898912c', '2023-05-02 09:15:00', '在二号楼澡堂捡到的', '二号楼', '15494122058', '已认领', 1, 4, 53);
INSERT INTO `found` VALUES (42, '身份证', 'https://i01piccdn.sogoucdn.com/e541137a3489da25', '2023-05-08 14:00:00', '在三号楼的卫生间捡到的', '三号楼', '16954986375', '已认领', 1, 3, 52);

-- ----------------------------
-- Table structure for lost
-- ----------------------------
DROP TABLE IF EXISTS `lost`;
CREATE TABLE `lost`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pub_date` datetime NULL DEFAULT NULL,
  `content` varchar(15000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `place` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `state` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `stick` tinyint(1) NULL DEFAULT NULL,
  `lostfoundtype_id` int NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `lostfoundtype_id1`(`lostfoundtype_id` ASC) USING BTREE,
  INDEX `user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `lostfoundtype_id1` FOREIGN KEY (`lostfoundtype_id`) REFERENCES `lostfoundtype` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lost
-- ----------------------------
INSERT INTO `lost` VALUES (1, '手机', 'https://i03piccdn.sogoucdn.com/8752c6a0ef3cda8c', '2023-05-23 00:00:00', '我丢失了一个华为手机', '软件大楼', '13794916353', '未找到', 0, 1, 1);
INSERT INTO `lost` VALUES (2, '电脑', 'https://img02.sogoucdn.com/app/a/100520020/30de8fb57e3b6788414814ffb1854ea2', '2023-06-02 10:34:52', '我丢失了一个点电脑', '软件大楼10楼320', '13794916353', '未找到', 0, 1, 1);
INSERT INTO `lost` VALUES (3, '硬盘', 'https://img02.sogoucdn.com/app/a/100520020/70402067f4c0b59cbde63dff31fdeb72', '2023-06-20 10:38:40', '丢失了一个硬盘', '学院', '15082411712', '未找到', 0, 1, 2);
INSERT INTO `lost` VALUES (4, '钥匙', 'https://i01piccdn.sogoucdn.com/7aee565d9d829b29', '2023-05-30 13:15:00', '在行政楼拾得一串钥匙，请失主尽快联系领取。', '行政楼', '18682675515', '未找到', 0, 3, 3);
INSERT INTO `lost` VALUES (5, '灰色双肩包', 'https://i04piccdn.sogoucdn.com/e19c1efb22971560', '2023-06-02 08:45:00', '在教学大楼B区上丢失一只灰色双肩包，里面有重要物品，请拾到者速与我联系，谢谢。', '教学大楼B区', '1', '已找到', 0, 4, 4);
INSERT INTO `lost` VALUES (6, '钱包', 'https://img03.sogoucdn.com/app/a/100520020/7158ddb5d551266ca8b4fbde666d0982', '2023-05-29 16:20:00', '在行政楼拾得一个红色钱包，里面有身份证、银行卡等，请失主尽快联系。', '行政楼', '10802788799', '未找到', 1, 3, 5);
INSERT INTO `lost` VALUES (7, '蓝色小熊', 'https://img01.sogoucdn.com/app/a/100520020/3dcffd98537a6a1195da1f5f55ca0f50', '2023-05-28 11:50:00', '小孩子爱睡觉时突然不见了他最爱的小熊，希望有拾到者能够联系我，感激不尽。', '行政楼', '11155904369', '未找到', 0, 3, 6);
INSERT INTO `lost` VALUES (8, '银色项链', 'https://img03.sogoucdn.com/app/a/100520020/4f5d9f90d1e14bc9757db969c014131e', '2023-05-31 09:00:00', '在餐厅用餐时不小心掉落了银色项链，希望拾到者能够归还，谢谢！', '学校餐厅', '19260441509', '未找到', 0, 4, 7);
INSERT INTO `lost` VALUES (9, '小狗', 'https://i01piccdn.sogoucdn.com/a83029296616a06d', '2023-05-20 15:30:00', '在操场拾得一只小狗，毛色金黄，颈系红色项圈，希望失主尽快联系领取。', '操场', '16027175827', '未找到', 0, 5, 8);
INSERT INTO `lost` VALUES (10, '学生证', 'https://i02piccdn.sogoucdn.com/3bec202a13382012', '2023-05-26 11:10:00', '在校园篮球场上丢失了一张学生证，上面有很多重要信息，请拾到者联系我。', '高新校园', '17870449127', '未找到', 1, 2, 9);
INSERT INTO `lost` VALUES (11, '背包', 'https://img04.sogoucdn.com/app/a/100520020/b286f506e6aee125e540688e980d4835', '2023-05-19 17:20:00', '在教学大楼B区拾得一个黑色背包，请失主联系我领取。', '教学大楼B区', '19468331823', '未找到', 0, 4, 10);
INSERT INTO `lost` VALUES (12, '手套', 'https://i04piccdn.sogoucdn.com/e07be08df401dbe9', '2023-05-23 14:45:00', '在行政楼遗失一副深蓝色手套，希望有拾到者能够联系我。', '行政楼', '17713690064', '未找到', 0, 4, 11);
INSERT INTO `lost` VALUES (13, '电脑', 'https://img03.sogoucdn.com/app/a/100520020/e2412a3d7fc0fce63c77e4d67e989e9a', '2023-05-27 09:00:00', '在软件大楼等车时丢失一台黑色笔记本电脑，请在拾到者看到时及时联系我。', '软件大楼', '16060383911', '未找到', 0, 1, 12);
INSERT INTO `lost` VALUES (14, '雨伞', 'https://i03piccdn.sogoucdn.com/dca70d6ddcb164b7', '2023-05-25 12:10:00', '在学校拾得一把蓝色雨伞，请失主联系我领取。', '某校园', '10939380459', '未找到', 0, 3, 13);
INSERT INTO `lost` VALUES (15, '红包', 'https://img01.sogoucdn.com/app/a/100520020/3dcffd98537a6a1195da1f5f55ca0f50', '2023-06-02 13:30:00', '在软件大楼上不慎遗失一个红色小红包，内有钱财，请拾到者速与我联系，谢谢！', '软件大楼', '16166175424', '未找到', 0, 3, 14);
INSERT INTO `lost` VALUES (16, '钥匙串', 'https://i01piccdn.sogoucdn.com/6a01dfcf13cbef0b', '2023-05-23 16:20:00', '在学生公寓3拾得一串钥匙，请失主联系我领取。', '学生公寓3', '13681837802', '未找到', 0, 3, 15);
INSERT INTO `lost` VALUES (17, '童车', 'https://img04.sogoucdn.com/app/a/100520020/b286f506e6aee125e540688e980d4835', '2023-05-19 09:00:00', '在学生公寓3丢失一辆红色童车，敬请拾到者联系我，感谢！', '学生公寓3', '11066576765', '未找到', 0, 5, 16);
INSERT INTO `lost` VALUES (18, '紫色背包', 'https://img03.sogoucdn.com/app/a/100520020/06c89d445c79be53c45a0f2730ee5ed3', '2023-05-31 12:30:00', '在教学大楼B区丢失一只紫色背包，请拾到者归还，感谢！', '教学大楼B区', '12631694074', '未找到', 0, 4, 17);
INSERT INTO `lost` VALUES (19, '大熊猫', 'https://i03piccdn.sogoucdn.com/f939a925786fd0ff', '2023-05-24 14:50:00', '在公园拾得一只大熊猫，失主请快联系我领取。', '温江公园', '10481984190', '未找到', 0, 5, 18);
INSERT INTO `lost` VALUES (20, '音响', 'https://i04piccdn.sogoucdn.com/23f7ed9f625347e0', '2023-05-20 15:20:00', '在派对上遗失了一只音响，请拾到者尽快联系我，谢谢！', '某聚会场所', '10313633788', '未找到', 0, 1, 19);
INSERT INTO `lost` VALUES (21, '手机', 'https://img03.sogoucdn.com/app/a/100520020/ecd916c5e9393012764a27796defc118', '2022-05-10 00:00:00', '在学校图书馆丢失了一部iPhone11，有拾到者请联系我', '软件大楼', '13789259851', '未找到', 0, 1, 20);
INSERT INTO `lost` VALUES (22, '钱包', 'https://img03.sogoucdn.com/app/a/100520020/ba6deda77b333b7fb3ed677835744d35', '2022-05-12 00:00:00', '在教学楼A区捡到一个钱包，内有身份证和银行卡，请失主与我联系', '教学楼A区', '11292232565', '未找到', 0, 3, 21);
INSERT INTO `lost` VALUES (23, 'U盘', 'https://img02.sogoucdn.com/app/a/100520020/70ebd7f19fce3d15cc2495ca853cc474', '2022-05-14 00:00:00', '在教学楼A区丢失了一个白色U盘，里面存有重要文件，请拾到者归还，谢谢', '教学楼A区', '15841745484', '未找到', 0, 1, 22);
INSERT INTO `lost` VALUES (24, '钥匙串', 'https://i02piccdn.sogoucdn.com/9956b683efec8ba1', '2022-05-16 00:00:00', '在教学楼A区拾到一串钥匙，请失主与我联系', '教学楼A区', '11934391401', '未找到', 0, 3, 23);
INSERT INTO `lost` VALUES (25, '护照', 'https://img02.sogoucdn.com/app/a/100520020/30de8fb57e3b6788414814ffb1854ea2', '2022-05-20 00:00:00', '在益食堂上遗失了一本护照，请拾到者与我联系，感激不尽', '益食堂', '14318088405', '未找到', 0, 2, 24);
INSERT INTO `lost` VALUES (26, '电子手表', 'https://img03.sogoucdn.com/app/a/100520020/a852be285106f2e9d76eafb661333327', '2022-05-22 00:00:00', '在捡到一块电子手表，请失主与我联系', '益食堂', '16898622832', '未找到', 0, 1, 25);
INSERT INTO `lost` VALUES (27, '行李', 'https://i02piccdn.sogoucdn.com/d0b854b7f2c5c999', '2022-05-24 00:00:00', '益食堂遗失了一件黑色旅行箱，请拾到者归还，谢谢', '益食堂', '17897210753', '未找到', 0, 5, 26);
INSERT INTO `lost` VALUES (28, '耳机', 'https://i04piccdn.sogoucdn.com/fbe75bb3e38ca04e', '2022-05-26 00:00:00', '在学生公寓1里捡到一只黑色耳机，请失主与我联系', '学生公寓1', '16476472203', '未找到', 0, 1, 27);
INSERT INTO `lost` VALUES (29, '手环', 'https://img03.sogoucdn.com/app/a/100520020/4f5d9f90d1e14bc9757db969c014131e', '2022-05-28 00:00:00', '在学生公寓1遗失了一只手环，请有拾到者立即与我联系', '学生公寓1', '19132511855', '未找到', 0, 4, 28);
INSERT INTO `lost` VALUES (30, '猫咪', 'https://img04.sogoucdn.com/app/a/100520020/0d797592ff8ec8ec96a2d30f36d74abc', '2022-05-30 00:00:00', '在体育馆内遇到一只迷路的小猫咪，请它的主人尽快联系我', '体育馆', '10885268347', '未找到', 1, 5, 29);
INSERT INTO `lost` VALUES (31, '钥匙卡', 'https://img02.sogoucdn.com/app/a/100520020/99bc28b162e31d6630eec6b10f7f60bb', '2022-06-02 00:00:00', '在体育馆遗失了一张黑色钥匙卡，请有拾到者速速与我联系', '体育馆', '16884820950', '未找到', 0, 2, 30);
INSERT INTO `lost` VALUES (32, '书包', 'https://i01piccdn.sogoucdn.com/69ef0f23fd380b98', '2022-06-04 00:00:00', '在体育馆上拾到一个蓝色书包，请失主尽快联系我', '体育馆', '18256522492', '未找到', 0, 4, 31);
INSERT INTO `lost` VALUES (33, '身份证', 'https://i04piccdn.sogoucdn.com/2b26d546e7d9bc13', '2022-06-06 00:00:00', '在图书馆遗失了一张身份证，请拾到者联系我，感谢', '图书馆', '18605159603', '未找到', 0, 2, 32);
INSERT INTO `lost` VALUES (34, '眼镜', 'https://img04.sogoucdn.com/app/a/100520020/3470c0a79d5043e240b0b170d6a8fa34', '2022-06-08 00:00:00', '在图书馆捡到一副眼镜，请失主速与我联系', '图书馆', '13117985544', '未找到', 0, 3, 33);
INSERT INTO `lost` VALUES (35, '婴儿车', 'https://i01piccdn.sogoucdn.com/6a97458d700915ce', '2022-06-10 00:00:00', '在星期8超市遗失了一辆红色婴儿车，请有拾到者联系我', '星期8超市', '10525002053', '未找到', 0, 5, 34);
INSERT INTO `lost` VALUES (36, '钱包', 'https://img01.sogoucdn.com/app/a/100520020/109f9ddbf2df587ea555d957412a41b8', '2022-06-12 00:00:00', '在星期8超市捡到一个钱包，请失主速与我联系', '星期8超市', '10185636767', '未找到', 0, 3, 35);
INSERT INTO `lost` VALUES (37, '手提包', 'https://img03.sogoucdn.com/app/a/100520020/06c89d445c79be53c45a0f2730ee5ed3', '2022-06-14 00:00:00', '在钟楼上遗失了一个绿色手提包，请有心人速与我联系', '钟楼', '16822180855', '未找到', 0, 3, 36);
INSERT INTO `lost` VALUES (38, '狗狗', 'https://img03.sogoucdn.com/app/a/100520020/378a2bacaa0a2dfb69b59fd3732e54b0', '2022-06-16 00:00:00', '在钟楼内遇到一只迷路的小狗狗，请主人尽快联系我', '钟楼', '14350279651', '未找到', 0, 5, 37);
INSERT INTO `lost` VALUES (39, '笔记本电脑', 'https://i01piccdn.sogoucdn.com/a58d3e18f17db4b4', '2022-06-18 00:00:00', '在钟楼上遗失了一台苹果笔记本电脑，请拾到者与我联系', '钟楼', '13023893077', '未找到', 0, 1, 38);
INSERT INTO `lost` VALUES (40, '自行车', 'https://img03.sogoucdn.com/app/a/100520020/3acff10bd3d8c617a3a51844479b23fe', '2022-06-20 00:00:00', '在钟楼捡到一辆蓝色山地自行车，请失主与我联系', '钟楼', '14890082310', '未找到', 0, 5, 39);
INSERT INTO `lost` VALUES (41, '手表', 'https://img01.sogoucdn.com/app/a/100520020/a2c005282b3bad02aa8dc457ba45aea4', '2023-06-02 10:34:52', '本人于拾得手表一个，望失主前来认领。', '学术交流中心', '17481553826', '未找到', 1, 1, 40);
INSERT INTO `lost` VALUES (42, 'U盘', 'https://i01piccdn.sogoucdn.com/1513861b0fc4fc49', '2023-06-02 10:34:52', '今日上午于教学楼B403捡到U盘一个，望失主前来认领。', '教学楼B区', '19429875684', '未找到', 0, 1, 41);
INSERT INTO `lost` VALUES (43, '数码相机', 'https://i02piccdn.sogoucdn.com/c9f33770b5a6cbe8', '2023-06-02 10:34:52', '2月11号凌晨10分左右，在食堂门口，一部数码相机遗失在食堂门口桌子上，如有捡到请电话联系13706753298，望失主前来认领。', '二食堂', '19684380255', '未找到', 0, 1, 42);
INSERT INTO `lost` VALUES (44, '笔记本电脑', 'https://img04.sogoucdn.com/app/a/100520020/ee994ae292a71e03cb1676fbbea0a67e', '2023-06-02 10:34:52', '晚上9:30左右在软件大楼前的步道上拾到笔记本电脑一个，锁屏是全家照片，望失主前来认领。', '软件大楼', '13300614196', '未找到', 0, 1, 43);
INSERT INTO `lost` VALUES (45, '相机', 'https://img03.sogoucdn.com/app/a/100520020/a852be285106f2e9d76eafb661333327', '2023-06-02 10:34:52', '今天，行政楼今天捡到高级相机一部，希望找到失主。该相机为佳能6D，价值近万元。望失主看到消息和照片后，与之联系，望失主前来认领。', '行政楼', '14960399805', '未找到', 0, 1, 44);
INSERT INTO `lost` VALUES (46, '鼠标', 'https://img03.sogoucdn.com/app/a/100520020/f7ef86aac285a5d5d7dbccf1cab37b50', '2023-06-02 10:34:52', '本人于今日在软件大楼拾得鼠标一个，望失主前来认领。', '软件大楼', '19688961952', '未找到', 0, 2, 45);
INSERT INTO `lost` VALUES (47, '数据线', 'https://img01.sogoucdn.com/app/a/100520020/a2c005282b3bad02aa8dc457ba45aea4', '2023-06-02 10:34:52', '本人于今日拾得数据线一个，望失主前来认领。', '学术交流中心', '16169040399', '未找到', 0, 1, 46);
INSERT INTO `lost` VALUES (48, '耳机盒', 'https://i03piccdn.sogoucdn.com/e9c14ad7c11fb3cb', '2023-06-02 10:34:52', '本人于拾得耳机盒一个，望失主前来认领。', '操场', '13788644367', '未找到', 0, 1, 47);
INSERT INTO `lost` VALUES (49, '转换器', 'https://i02piccdn.sogoucdn.com/5b93fd3e07454bf0', '2023-06-02 10:34:52', '本人于拾得转换器一个，望失主前来认领。', '图书馆', '19126972389', '未找到', 0, 1, 48);
INSERT INTO `lost` VALUES (50, '驾驶证', 'https://img03.sogoucdn.com/app/a/100520020/e2412a3d7fc0fce63c77e4d67e989e9a', '2023-06-02 10:34:52', '本人于今日拾得驾驶证一个，望失主前来认领。', '学术交流中心', '16296242740', '未找到', 0, 2, 49);
INSERT INTO `lost` VALUES (51, '身份证', 'https://i04piccdn.sogoucdn.com/a077a8d77a80307b', '2023-06-02 10:34:52', '本人于拾得身份证一个，望失主前来认领。', '操场', '14843063924', '未找到', 0, 2, 50);
INSERT INTO `lost` VALUES (52, '学生证', 'https://img03.sogoucdn.com/app/a/100520020/7158ddb5d551266ca8b4fbde666d0982', '2023-06-02 10:34:52', '本人于拾得学生证一个，望失主前来认领。', '图书馆', '12414423969', '未找到', 0, 2, 51);
INSERT INTO `lost` VALUES (53, '身份证', 'https://i03piccdn.sogoucdn.com/3409a2442698ef96', '2023-06-02 10:34:52', '本人于拾得身份证一个，望失主前来认领。', '教学大楼A区', '16954986375', '未找到', 0, 2, 52);
INSERT INTO `lost` VALUES (54, '驾驶证', 'https://i04piccdn.sogoucdn.com/0ec46ea760255823', '2023-06-02 10:34:52', '本人于拾得驾驶证一个，望失主前来认领。', '软件大楼', '15494122058', '未找到', 0, 2, 53);
INSERT INTO `lost` VALUES (55, '戒指', 'https://img01.sogoucdn.com/app/a/100520020/3a3a600017b934b1bef0ab6ef3eb1612', '2023-06-02 10:34:52', '捡到一枚戒指💍本来以为不是，同事捡起来了[允悲]请看到的热心人帮忙转发', '益食堂门口', '14528186786', '未找到', 0, 4, 54);
INSERT INTO `lost` VALUES (56, '手串', 'https://i01piccdn.sogoucdn.com/4080815410b57fae', '2023-06-02 10:34:52', '拾获手串一个', '教学楼B区', '10939380459', '未找到', 0, 4, 13);
INSERT INTO `lost` VALUES (57, '项链', 'https://i01piccdn.sogoucdn.com/fdb311687dce887c', '2023-06-02 10:34:52', '在食堂门口，一条珠子项链遗失在食堂门口桌子上，如有捡到请电话联系13706753298，望失主前来认领。', '二食堂', '16166175424', '未找到', 0, 4, 14);
INSERT INTO `lost` VALUES (58, '手镯', 'https://i02piccdn.sogoucdn.com/34c4de892ce8ec7a', '2023-06-02 10:34:52', '晚上9:30左右在软件大楼前的步道上拾获手镯1只，望失主前来认领。', '软件大楼', '16476472203', '未找到', 0, 4, 27);
INSERT INTO `lost` VALUES (59, '白衬衣', 'https://i02piccdn.sogoucdn.com/68f9a901c3a5489c', '2023-06-02 10:34:52', '今天，行政楼今天捡到白衬衣和雨伞。', '行政楼', '19429875684', '未找到', 0, 4, 41);
INSERT INTO `lost` VALUES (60, '衬衣', 'https://img04.sogoucdn.com/app/a/100520020/31cbb6c1f6e1d1a90dae5fa7d204ed63', '2023-06-02 10:34:52', '本人于今日在软件大楼拾得纯棉衬衣一件，望失主前来认领。', '软件大楼', '16822180855', '未找到', 0, 4, 36);
INSERT INTO `lost` VALUES (61, '帽子', 'https://img04.sogoucdn.com/app/a/100520020/aff3e1f13023c46db237ed547c39fc56', '2023-06-02 10:34:52', '好心人拾获儿童帽子一个，望失主前来认领。', '学术交流中心', '10185636767', '未找到', 0, 4, 35);
INSERT INTO `lost` VALUES (62, '童装购物袋', 'https://i02piccdn.sogoucdn.com/71340cac1aac588a', '2023-06-02 10:34:52', '操场上捡到一个童装购物袋，请到失物招领处领取。', '操场', '12631694074', '未找到', 0, 4, 17);
INSERT INTO `lost` VALUES (63, '外套', 'https://img03.sogoucdn.com/app/a/100520020/f2ee1aca74c98ea933e96b70185a9845', '2023-06-02 10:34:52', '有同学在学校食堂二楼捡到一件校服外套，请丢失的同学到学校食堂二楼领取失物招领点领取。', '二食堂', '16296242740', '未找到', 1, 4, 49);
INSERT INTO `lost` VALUES (64, '短袖', 'https://img01.sogoucdn.com/app/a/100520020/865da08836d4cb99193e3429d241f6b9', '2023-06-02 10:34:52', '本人于拾得短袖一件，望失主前来认领。', '教学大楼A区', '14890082310', '未找到', 0, 4, 39);
INSERT INTO `lost` VALUES (65, '玉扳指', 'https://i02piccdn.sogoucdn.com/e899de03b8dc76c8', '2023-06-02 10:34:52', '本人于拾得玉扳指一个，望失主前来认领。', '软件大楼', '10802788799', '未找到', 0, 4, 5);
INSERT INTO `lost` VALUES (66, '专用章', 'https://img04.sogoucdn.com/app/a/100520020/3470c0a79d5043e240b0b170d6a8fa34', '2023-06-02 10:34:52', '青岛传国物流有限公司提箱专用章。', '益食堂门口', '17897210753', '未找到', 1, 5, 26);
INSERT INTO `lost` VALUES (67, '行李箱', 'https://i03piccdn.sogoucdn.com/65b0c07d0f0bb8b2', '2023-06-02 10:34:52', '在学生公寓门口捡到一个行李箱', '教学楼B区', '17713690064', '未找到', 1, 5, 11);

-- ----------------------------
-- Table structure for lostfoundtype
-- ----------------------------
DROP TABLE IF EXISTS `lostfoundtype`;
CREATE TABLE `lostfoundtype`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of lostfoundtype
-- ----------------------------
INSERT INTO `lostfoundtype` VALUES (1, '数码设备');
INSERT INTO `lostfoundtype` VALUES (2, '证件');
INSERT INTO `lostfoundtype` VALUES (3, '日用品');
INSERT INTO `lostfoundtype` VALUES (4, '服饰');
INSERT INTO `lostfoundtype` VALUES (5, '其他');

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` varchar(15000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `img` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pub_date` datetime NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `poststype_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id5`(`user_id` ASC) USING BTREE,
  INDEX `poststype_id1`(`poststype_id` ASC) USING BTREE,
  CONSTRAINT `poststype_id1` FOREIGN KEY (`poststype_id`) REFERENCES `poststype` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id5` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of posts
-- ----------------------------
INSERT INTO `posts` VALUES (1, '测试1', '测试1的内容', NULL, '2023-05-07 14:11:40', 1, 1);
INSERT INTO `posts` VALUES (2, '侧视2', '侧视2的内容', NULL, '2023-05-07 14:36:13', 1, 2);

-- ----------------------------
-- Table structure for poststype
-- ----------------------------
DROP TABLE IF EXISTS `poststype`;
CREATE TABLE `poststype`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of poststype
-- ----------------------------
INSERT INTO `poststype` VALUES (1, '吐槽');
INSERT INTO `poststype` VALUES (2, '搞笑');
INSERT INTO `poststype` VALUES (3, '探讨');
INSERT INTO `poststype` VALUES (4, '扩列');
INSERT INTO `poststype` VALUES (5, '其他');

-- ----------------------------
-- Table structure for recruit
-- ----------------------------
DROP TABLE IF EXISTS `recruit`;
CREATE TABLE `recruit`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` varchar(15000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `img` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pub_date` datetime NULL DEFAULT NULL,
  `start_time` datetime NULL DEFAULT NULL,
  `reward` decimal(10, 2) NULL DEFAULT NULL,
  `state` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `recruittype_id` int NULL DEFAULT NULL,
  `accept_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id4`(`user_id` ASC) USING BTREE,
  INDEX `recruittype_id`(`recruittype_id` ASC) USING BTREE,
  INDEX `accept_id2`(`accept_id` ASC) USING BTREE,
  CONSTRAINT `accept_id2` FOREIGN KEY (`accept_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `recruittype_id` FOREIGN KEY (`recruittype_id`) REFERENCES `recruittype` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id4` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of recruit
-- ----------------------------
INSERT INTO `recruit` VALUES (1, '代理上课', '星期四代上一节选修课', NULL, '2023-05-08 08:50:54', '2023-05-11 08:50:57', 10.00, '待接收', 1, 1, NULL);

-- ----------------------------
-- Table structure for recruittype
-- ----------------------------
DROP TABLE IF EXISTS `recruittype`;
CREATE TABLE `recruittype`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of recruittype
-- ----------------------------
INSERT INTO `recruittype` VALUES (1, '代扫地');
INSERT INTO `recruittype` VALUES (2, '代签到');
INSERT INTO `recruittype` VALUES (3, '代上课');
INSERT INTO `recruittype` VALUES (4, '其他');

-- ----------------------------
-- Table structure for run
-- ----------------------------
DROP TABLE IF EXISTS `run`;
CREATE TABLE `run`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` varchar(15000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `img` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pub_date` datetime NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `reward` decimal(10, 2) NULL DEFAULT NULL,
  `state` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `delivery_id` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  `runtype_id` int NULL DEFAULT NULL,
  `accept_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id3`(`user_id` ASC) USING BTREE,
  INDEX `runtype_id1`(`runtype_id` ASC) USING BTREE,
  INDEX `accept_id1`(`accept_id` ASC) USING BTREE,
  CONSTRAINT `accept_id1` FOREIGN KEY (`accept_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `runtype_id1` FOREIGN KEY (`runtype_id`) REFERENCES `runtype` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id3` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of run
-- ----------------------------
INSERT INTO `run` VALUES (1, '帮拿外卖', '今天中午帮我拿个外卖', NULL, '2023-05-08 09:48:45', '2023-05-08 09:48:50', 2.00, '待接收', NULL, 1, 3, 2);

-- ----------------------------
-- Table structure for runtype
-- ----------------------------
DROP TABLE IF EXISTS `runtype`;
CREATE TABLE `runtype`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of runtype
-- ----------------------------
INSERT INTO `runtype` VALUES (1, '食堂带饭');
INSERT INTO `runtype` VALUES (2, '拿快递');
INSERT INTO `runtype` VALUES (3, '拿外卖');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `balance` int NULL DEFAULT NULL,
  `prestige` int NULL DEFAULT NULL,
  `reg_date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '小林子', '13794916353', 'a11111111', 'https://img.wxcha.com/m00/86/59/7c6242363084072b82b6957cacc335c7.jpg', '男', 1003, 100, '2023-04-20 22:33:16');
INSERT INTO `user` VALUES (2, '小吴', '15082411712', 'a11111111', 'http://5b0988e595225.cdn.sohucs.com/images/20200325/cbe3f169f20d45b4a482d3b6aeefd7cf.jpeg', '男', 100, 100, '2023-05-08 09:24:31');
INSERT INTO `user` VALUES (3, '哎哟你干嘛～', '18682675515', 'a1111111', 'ikun.jpg', '男', 100, 100, '2023-05-15 20:57:38');
INSERT INTO `user` VALUES (4, '海棠', '1', NULL, '559303e55876sao.jpg', '男', 1000, 100, '2023-06-05 16:09:17');
INSERT INTO `user` VALUES (5, '小蚂蚁', '10802788799', NULL, 'https://i04piccdn.sogoucdn.com/0794666826f9f01a', '男', 1000, 100, '2023-06-07 10:16:04');
INSERT INTO `user` VALUES (6, '小小虎', '11155904369', NULL, 'https://img03.sogoucdn.com/app/a/100520020/378a2bacaa0a2dfb69b59fd3732e54b0', '男', 1000, 100, '2023-06-07 10:16:05');
INSERT INTO `user` VALUES (7, '阿宝', '19260441509', NULL, 'https://img04.sogoucdn.com/app/a/100520020/0d797592ff8ec8ec96a2d30f36d74abc', '男', 1000, 100, '2023-06-07 10:16:06');
INSERT INTO `user` VALUES (8, '小蜜蜂', '16027175827', NULL, 'https://i03piccdn.sogoucdn.com/e489588be3f54895', '男', 1000, 100, '2023-06-07 10:16:06');
INSERT INTO `user` VALUES (9, '小白兔', '17870449127', NULL, 'https://img04.sogoucdn.com/app/a/100520020/ee994ae292a71e03cb1676fbbea0a67e', '男', 1000, 100, '2023-06-07 10:16:06');
INSERT INTO `user` VALUES (10, '乐乐', '19468331823', NULL, 'https://img02.sogoucdn.com/app/a/100520020/99bc28b162e31d6630eec6b10f7f60bb', '男', 1000, 100, '2023-06-07 10:16:06');
INSERT INTO `user` VALUES (11, '蜜蜂', '17713690064', NULL, 'https://i01piccdn.sogoucdn.com/d7abaeca51023c95', '男', 1000, 100, '2023-06-07 10:16:06');
INSERT INTO `user` VALUES (12, '小白兔', '16060383911', NULL, 'https://img03.sogoucdn.com/app/a/100520020/06c89d445c79be53c45a0f2730ee5ed3', '男', 1000, 100, '2023-06-07 10:16:06');
INSERT INTO `user` VALUES (13, '小小虎', '10939380459', NULL, 'https://img04.sogoucdn.com/app/a/100520020/bb81a3f4cb544593f586a9cbbe540b33', '男', 1000, 100, '2023-06-07 10:16:06');
INSERT INTO `user` VALUES (14, '小仙女', '16166175424', NULL, 'https://img02.sogoucdn.com/app/a/100520020/c0a94c0e0cbb48ff2076af7074b8bd79', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (15, '小霸王', '13681837802', NULL, 'https://i03piccdn.sogoucdn.com/e489588be3f54895', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (16, '小懒虫', '11066576765', NULL, 'https://i04piccdn.sogoucdn.com/22bdd8e18468f9aa', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (17, '小汪汪', '12631694074', NULL, 'https://img04.sogoucdn.com/app/a/100520020/40d55737dc1612f9172f36317b3dacb6', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (18, '小猫', '10481984190', NULL, 'https://img04.sogoucdn.com/app/a/100520093/ae588be27ee085c4-fd668f66a830d70e-24b46b127c1c13c86a5320324f49613b.jpg', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (19, '糖糖', '10313633788', NULL, 'https://img04.sogoucdn.com/app/a/100520020/639519ddf2221562953905a08bd20811', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (20, '小瑜', '13789259851', NULL, 'https://i02piccdn.sogoucdn.com/e5cbaa335791cedf', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (21, '小鸟', '11292232565', NULL, 'https://img03.sogoucdn.com/app/a/100520020/a852be285106f2e9d76eafb661333327', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (22, '小瑜', '15841745484', NULL, 'https://img04.sogoucdn.com/app/a/100520020/3470c0a79d5043e240b0b170d6a8fa34', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (23, '小兔', '11934391401', NULL, 'https://img04.sogoucdn.com/app/a/100520093/ae588be27ee085c4-fd668f66a830d70e-b57ab734527bb96d68c5590ecb017b9b.jpg', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (24, '小珍珠', '14318088405', NULL, 'https://img04.sogoucdn.com/app/a/100520020/aff3e1f13023c46db237ed547c39fc56', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (25, '小飞花', '16898622832', NULL, 'https://i04piccdn.sogoucdn.com/22bdd8e18468f9aa', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (26, '小花', '17897210753', NULL, 'https://img03.sogoucdn.com/app/a/100520020/4f5d9f90d1e14bc9757db969c014131e', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (27, '小雪糕', '16476472203', NULL, 'https://img04.sogoucdn.com/app/a/100520093/8379901cc65ba509-45c21ceb904429fc-4ac1f58edc3ca2ad43ac5eea3b406500.jpg', '男', 1000, 100, '2023-06-07 10:16:07');
INSERT INTO `user` VALUES (28, '小苹果', '19132511855', NULL, 'https://img04.sogoucdn.com/app/a/100520093/8379901cc65ba509-45c21ceb904429fc-73b616ee8979c70b0f7bd402eff48cf6.jpg', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (29, '小火腿', '10885268347', NULL, 'https://img02.sogoucdn.com/app/a/100520020/30de8fb57e3b6788414814ffb1854ea2', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (30, '小凡', '16884820950', NULL, 'https://i02piccdn.sogoucdn.com/5d694b778ff4c01d', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (31, '小宝贝', '18256522492', NULL, 'https://i03piccdn.sogoucdn.com/fb28a56f3a4b6e1a', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (32, '小喵喵', '18605159603', NULL, 'https://img04.sogoucdn.com/app/a/100520093/8379901cc65ba509-45c21ceb904429fc-1e16d8c0bc48a98cc6ba4e41d4b88a44.jpg', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (33, '小瑜', '13117985544', NULL, 'https://img01.sogoucdn.com/app/a/100520020/109f9ddbf2df587ea555d957412a41b8', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (34, '小宝贝', '10525002053', NULL, 'https://img01.sogoucdn.com/app/a/100520020/109f9ddbf2df587ea555d957412a41b8', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (35, '小水蜜桃', '10185636767', NULL, 'https://img03.sogoucdn.com/app/a/100520020/24197f1f2c7780534f7ce527080c7251', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (36, '小书虫', '16822180855', NULL, 'https://img04.sogoucdn.com/app/a/100520093/8379901cc65ba509-45c21ceb904429fc-1e16d8c0bc48a98cc6ba4e41d4b88a44.jpg', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (37, '小鱼', '14350279651', NULL, 'https://i01piccdn.sogoucdn.com/e235fa53a74587f7', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (38, '奶糖', '13023893077', NULL, 'https://img04.sogoucdn.com/app/a/100520093/8379901cc65ba509-45c21ceb904429fc-197de0c5207df944e4cf8cf5f4482d4b.jpg', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (39, '笑笑', '14890082310', NULL, 'https://i04piccdn.sogoucdn.com/0794666826f9f01a', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (40, '乐乐', '17481553826', NULL, 'https://img04.sogoucdn.com/app/a/100520093/ae588be27ee085c4-fd668f66a830d70e-bcb76412aab683a7d1f972c04a769065.jpg', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (41, '小海豚', '19429875684', NULL, 'https://img03.sogoucdn.com/app/a/100520020/a852be285106f2e9d76eafb661333327', '男', 1000, 100, '2023-06-07 10:16:08');
INSERT INTO `user` VALUES (42, '小蚂蚁', '19684380255', NULL, 'https://img04.sogoucdn.com/app/a/100520093/ae588be27ee085c4-fd668f66a830d70e-4acefdc9163e3ad357e44e9799bdc20f.jpg', '男', 1000, 100, '2023-06-07 10:16:09');
INSERT INTO `user` VALUES (43, '笑笑', '13300614196', NULL, 'https://img03.sogoucdn.com/app/a/100520020/7158ddb5d551266ca8b4fbde666d0982', '男', 1000, 100, '2023-06-07 10:16:09');
INSERT INTO `user` VALUES (44, '小奶瓶', '14960399805', NULL, 'https://img03.sogoucdn.com/app/a/100520020/a852be285106f2e9d76eafb661333327', '男', 1000, 100, '2023-06-07 10:16:09');
INSERT INTO `user` VALUES (45, '小姐姐', '19688961952', NULL, 'https://img02.sogoucdn.com/app/a/100520020/ebdc830ef3ecedcea306c02cba235320', '男', 1000, 100, '2023-06-07 10:16:09');
INSERT INTO `user` VALUES (46, '小坤', '16169040399', NULL, 'https://i04piccdn.sogoucdn.com/22bdd8e18468f9aa', '男', 1000, 100, '2023-06-07 10:16:09');
INSERT INTO `user` VALUES (47, '娃娃', '13788644367', NULL, 'https://img01.sogoucdn.com/app/a/100520020/e47d84cba33edefd02762d77ec7e6334', '男', 1000, 100, '2023-06-07 10:16:09');
INSERT INTO `user` VALUES (48, '娃娃', '19126972389', NULL, 'https://img02.sogoucdn.com/app/a/100520020/70ebd7f19fce3d15cc2495ca853cc474', '男', 1000, 100, '2023-06-07 10:16:09');
INSERT INTO `user` VALUES (49, '小汪汪', '16296242740', NULL, 'https://img01.sogoucdn.com/app/a/100520020/3dcffd98537a6a1195da1f5f55ca0f50', '男', 1000, 100, '2023-06-07 10:16:09');
INSERT INTO `user` VALUES (50, '小寿司', '14843063924', NULL, 'https://img01.sogoucdn.com/app/a/100520020/24a9ed12eb06692e19b0202a06d526bc', '男', 1000, 100, '2023-06-07 10:16:09');
INSERT INTO `user` VALUES (51, '小小雅', '12414423969', NULL, 'https://img04.sogoucdn.com/app/a/100520020/639519ddf2221562953905a08bd20811', '男', 1000, 100, '2023-06-07 10:16:09');
INSERT INTO `user` VALUES (52, '小鸟', '16954986375', NULL, 'https://img01.sogoucdn.com/app/a/100520020/3a3a600017b934b1bef0ab6ef3eb1612', '男', 1000, 100, '2023-06-07 10:16:09');
INSERT INTO `user` VALUES (53, '小姐姐', '15494122058', NULL, 'https://img03.sogoucdn.com/app/a/100520020/7158ddb5d551266ca8b4fbde666d0982', '男', 1000, 100, '2023-06-07 10:16:09');
INSERT INTO `user` VALUES (54, '小可爱', '14528186786', NULL, 'https://img04.sogoucdn.com/app/a/100520093/8379901cc65ba509-45c21ceb904429fc-3d76954af80d02665d56409b836b4f63.jpg', '男', 1000, 100, '2023-06-07 10:16:09');
INSERT INTO `user` VALUES (55, '小虎', '', NULL, 'https://img01.sogoucdn.com/app/a/100520020/109f9ddbf2df587ea555d957412a41b8', '男', 1000, 100, '2023-06-08 15:34:03');

SET FOREIGN_KEY_CHECKS = 1;
