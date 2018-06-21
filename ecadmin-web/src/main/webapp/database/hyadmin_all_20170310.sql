/*
Navicat MySQL Data Transfer

Source Server         : 本机root
Source Server Version : 50541
Source Host           : localhost:3306
Source Database       : hyadmin

Target Server Type    : MYSQL
Target Server Version : 50541
File Encoding         : 65001

Date: 2017-03-10 14:14:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hy_album
-- ----------------------------
DROP TABLE IF EXISTS `hy_album`;
CREATE TABLE `hy_album` (
  `ALBUM_ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '相册ID',
  `ALBUM_NAME` varchar(50) NOT NULL COMMENT '相册名称',
  `ALBUM_COVER` int(10) DEFAULT NULL COMMENT '相册封面',
  `ALBUM_TYPE` tinyint(2) NOT NULL DEFAULT '0' COMMENT '相册类型 0系统相册(默认)',
  `CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `CREATE_USER` int(10) NOT NULL COMMENT '创建人',
  `CREATE_USER_NAME` varchar(50) NOT NULL,
  `ALBUM_STATUS` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态 1有效(默认) 0无效',
  PRIMARY KEY (`ALBUM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='相册表';

-- ----------------------------
-- Records of hy_album
-- ----------------------------
INSERT INTO `hy_album` VALUES ('1', '美女图片', '71', '0', '2016-03-22 09:28:52', '1', 'admin', '1');
INSERT INTO `hy_album` VALUES ('2', '大自然相册2', '31', '0', '2016-03-22 09:52:08', '1', 'admin', '0');
INSERT INTO `hy_album` VALUES ('3', '混杂图片', '65', '0', '2016-03-22 09:53:05', '1', 'admin', '1');
INSERT INTO `hy_album` VALUES ('4', '其他相册3', '59', '0', '2016-03-22 09:59:12', '1', 'admin', '0');
INSERT INTO `hy_album` VALUES ('5', '2222', null, '0', '2016-03-23 10:57:23', '1', 'admin', '0');
INSERT INTO `hy_album` VALUES ('6', '232123222', null, '0', '2016-03-23 10:58:57', '1', 'admin', '0');

-- ----------------------------
-- Table structure for hy_album_img
-- ----------------------------
DROP TABLE IF EXISTS `hy_album_img`;
CREATE TABLE `hy_album_img` (
  `IMG_ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `ALBUM_ID` int(10) NOT NULL COMMENT '相册ID',
  `IMG_PATH` varchar(500) NOT NULL COMMENT '图片路劲',
  `IMG_NAME` varchar(255) NOT NULL,
  `IMG_SIZE` int(10) NOT NULL COMMENT '图片大小 KB',
  `UPLOAD_TIME` datetime NOT NULL COMMENT '上传时间',
  `UPLOAD_USER` int(10) NOT NULL COMMENT '上传人',
  `IMG_STATUS` tinyint(2) NOT NULL DEFAULT '1' COMMENT '图片状态 默认1有效',
  PRIMARY KEY (`IMG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8 COMMENT='相册图片信息';

-- ----------------------------
-- Records of hy_album_img
-- ----------------------------
INSERT INTO `hy_album_img` VALUES ('1', '1', 'ec/album/1/1458628546424_original.jpeg', '1458628546424_original.jpeg', '47', '2016-03-22 14:35:50', '1', '0');
INSERT INTO `hy_album_img` VALUES ('2', '1', 'ec/album/1/1458628567552_original.jpeg', '1458628567552_original.jpeg', '50', '2016-03-22 14:36:07', '1', '0');
INSERT INTO `hy_album_img` VALUES ('3', '2', 'ec/album/2/1458628720600_original.jpeg', '1458628720600_original.jpeg', '57', '2016-03-22 14:38:40', '1', '0');
INSERT INTO `hy_album_img` VALUES ('4', '2', 'ec/album/2/1458628720803_original.jpeg', '1458628720803_original.jpeg', '55', '2016-03-22 14:38:40', '1', '0');
INSERT INTO `hy_album_img` VALUES ('5', '2', 'ec/album/2/1458628720838_original.jpeg', '1458628720838_original.jpeg', '58', '2016-03-22 14:38:40', '1', '0');
INSERT INTO `hy_album_img` VALUES ('6', '2', 'ec/album/2/1458628720972_original.jpeg', '1458628720972_original.jpeg', '64', '2016-03-22 14:38:41', '1', '0');
INSERT INTO `hy_album_img` VALUES ('7', '2', 'ec/album/2/1458628721039_original.jpeg', '1458628721039_original.jpeg', '112', '2016-03-22 14:38:41', '1', '0');
INSERT INTO `hy_album_img` VALUES ('8', '2', 'ec/album/2/1458628721084_original.jpeg', '1458628721084_original.jpeg', '66', '2016-03-22 14:38:41', '1', '0');
INSERT INTO `hy_album_img` VALUES ('9', '2', 'ec/album/2/1458628721194_original.jpeg', '1458628721194_original.jpeg', '47', '2016-03-22 14:38:41', '1', '0');
INSERT INTO `hy_album_img` VALUES ('10', '3', 'ec/album/3/1458629159186_original.jpeg', '1458629159186_original.jpeg', '47', '2016-03-22 14:45:59', '1', '0');
INSERT INTO `hy_album_img` VALUES ('11', '3', 'ec/album/3/1458629159446_original.jpeg', '1458629159446_original.jpeg', '61', '2016-03-22 14:45:59', '1', '0');
INSERT INTO `hy_album_img` VALUES ('12', '3', 'ec/album/3/1458629159501_original.jpeg', '1458629159501_original.jpeg', '142', '2016-03-22 14:45:59', '1', '0');
INSERT INTO `hy_album_img` VALUES ('13', '3', 'ec/album/3/1458629159628_original.jpeg', '1458629159628_original.jpeg', '55', '2016-03-22 14:45:59', '1', '0');
INSERT INTO `hy_album_img` VALUES ('14', '3', 'ec/album/3/1458629159681_original.jpeg', '1458629159681_original.jpeg', '58', '2016-03-22 14:45:59', '1', '0');
INSERT INTO `hy_album_img` VALUES ('15', '3', 'ec/album/3/1458629159815_original.jpeg', '1458629159815_original.jpeg', '64', '2016-03-22 14:45:59', '1', '0');
INSERT INTO `hy_album_img` VALUES ('16', '3', 'ec/album/3/1458629159885_original.jpeg', '1458629159885_original.jpeg', '112', '2016-03-22 14:45:59', '1', '0');
INSERT INTO `hy_album_img` VALUES ('17', '3', 'ec/album/3/1458629160002_original.jpeg', '1458629160002_original.jpeg', '66', '2016-03-22 14:46:00', '1', '0');
INSERT INTO `hy_album_img` VALUES ('18', '3', 'ec/album/3/1458629160099_original.jpeg', '1458629160099_original.jpeg', '47', '2016-03-22 14:46:00', '1', '0');
INSERT INTO `hy_album_img` VALUES ('19', '3', 'ec/album/3/1458629160205_original.jpeg', '1458629160205_original.jpeg', '112', '2016-03-22 14:46:00', '1', '0');
INSERT INTO `hy_album_img` VALUES ('20', '3', 'ec/album/3/1458629160269_original.jpeg', '1458629160269_original.jpeg', '73', '2016-03-22 14:46:00', '1', '0');
INSERT INTO `hy_album_img` VALUES ('21', '3', 'ec/album/3/1458629160302_original.jpeg', '1458629160302_original.jpeg', '77', '2016-03-22 14:46:00', '1', '0');
INSERT INTO `hy_album_img` VALUES ('22', '3', 'ec/album/3/1458629160405_original.jpeg', '1458629160405_original.jpeg', '64', '2016-03-22 14:46:00', '1', '0');
INSERT INTO `hy_album_img` VALUES ('23', '3', 'ec/album/3/1458629160499_original.jpeg', '1458629160499_original.jpeg', '82', '2016-03-22 14:46:00', '1', '0');
INSERT INTO `hy_album_img` VALUES ('24', '2', 'ec/album/2/1458629404218_original.jpeg', '1458629404218_original.jpeg', '718', '2016-03-22 14:50:04', '1', '0');
INSERT INTO `hy_album_img` VALUES ('25', '2', 'ec/album/2/1458629404343_original.jpeg', '1458629404343_original.jpeg', '421', '2016-03-22 14:50:04', '1', '0');
INSERT INTO `hy_album_img` VALUES ('26', '2', 'ec/album/2/1458629404456_original.jpeg', '1458629404456_original.jpeg', '489', '2016-03-22 14:50:04', '1', '0');
INSERT INTO `hy_album_img` VALUES ('27', '2', 'ec/album/2/1458629404605_original.jpeg', '1458629404605_original.jpeg', '431', '2016-03-22 14:50:04', '1', '0');
INSERT INTO `hy_album_img` VALUES ('28', '2', 'ec/album/2/1458629404698_original.jpeg', '1458629404698_original.jpeg', '621', '2016-03-22 14:50:04', '1', '0');
INSERT INTO `hy_album_img` VALUES ('29', '2', 'ec/album/2/1458629404846_original.jpeg', '1458629404846_original.jpeg', '975', '2016-03-22 14:50:04', '1', '0');
INSERT INTO `hy_album_img` VALUES ('30', '2', 'ec/album/2/1458629404956_original.jpeg', '1458629404956_original.jpeg', '736', '2016-03-22 14:50:05', '1', '0');
INSERT INTO `hy_album_img` VALUES ('31', '2', 'ec/album/2/1458629405062_original.jpeg', '1458629405062_original.jpeg', '507', '2016-03-22 14:50:05', '1', '0');
INSERT INTO `hy_album_img` VALUES ('32', '2', 'ec/album/2/1458630693820_original.jpeg', '1458630693820_original.jpeg', '659', '2016-03-22 15:11:33', '1', '0');
INSERT INTO `hy_album_img` VALUES ('33', '2', 'ec/album/2/1458630693944_original.jpeg', '1458630693944_original.jpeg', '310', '2016-03-22 15:11:33', '1', '0');
INSERT INTO `hy_album_img` VALUES ('34', '2', 'ec/album/2/1458630694077_original.jpeg', '1458630694077_original.jpeg', '324', '2016-03-22 15:11:34', '1', '0');
INSERT INTO `hy_album_img` VALUES ('35', '2', 'ec/album/2/1458630694169_original.jpeg', '1458630694169_original.jpeg', '273', '2016-03-22 15:11:34', '1', '0');
INSERT INTO `hy_album_img` VALUES ('36', '2', 'ec/album/2/1458630694258_original.jpeg', '1458630694258_original.jpeg', '129', '2016-03-22 15:11:34', '1', '0');
INSERT INTO `hy_album_img` VALUES ('37', '2', 'ec/album/2/1458630694378_original.jpeg', '1458630694378_original.jpeg', '138', '2016-03-22 15:11:34', '1', '0');
INSERT INTO `hy_album_img` VALUES ('38', '2', 'ec/album/2/1458630694495_original.jpeg', '1458630694495_original.jpeg', '817', '2016-03-22 15:11:34', '1', '0');
INSERT INTO `hy_album_img` VALUES ('39', '1', 'ec/album/1/1458630766820_original.jpeg', '1458630766820_original.jpeg', '718', '2016-03-22 15:12:46', '1', '0');
INSERT INTO `hy_album_img` VALUES ('40', '1', 'ec/album/1/1458630766925_original.jpeg', '1458630766925_original.jpeg', '421', '2016-03-22 15:12:46', '1', '0');
INSERT INTO `hy_album_img` VALUES ('41', '1', 'ec/album/1/1458630767098_original.jpeg', '1458630767098_original.jpeg', '489', '2016-03-22 15:12:47', '1', '0');
INSERT INTO `hy_album_img` VALUES ('42', '1', 'ec/album/1/1458630767164_original.jpeg', '1458630767164_original.jpeg', '431', '2016-03-22 15:12:47', '1', '0');
INSERT INTO `hy_album_img` VALUES ('43', '1', 'ec/album/1/1458630767285_original.jpeg', '1458630767285_original.jpeg', '621', '2016-03-22 15:12:47', '1', '0');
INSERT INTO `hy_album_img` VALUES ('44', '1', 'ec/album/1/1458630767454_original.jpeg', '1458630767454_original.jpeg', '975', '2016-03-22 15:12:47', '1', '0');
INSERT INTO `hy_album_img` VALUES ('45', '1', 'ec/album/1/1458630767585_original.jpeg', '1458630767585_original.jpeg', '736', '2016-03-22 15:12:47', '1', '0');
INSERT INTO `hy_album_img` VALUES ('46', '1', 'ec/album/1/1458630767687_original.jpeg', '1458630767687_original.jpeg', '507', '2016-03-22 15:12:47', '1', '0');
INSERT INTO `hy_album_img` VALUES ('47', '1', 'ec/album/1/1458630845874_original.jpeg', '1458630845874_original.jpeg', '744', '2016-03-22 15:14:05', '1', '0');
INSERT INTO `hy_album_img` VALUES ('48', '1', 'ec/album/1/1458630845986_original.jpeg', '1458630845986_original.jpeg', '435', '2016-03-22 15:14:06', '1', '0');
INSERT INTO `hy_album_img` VALUES ('49', '1', 'ec/album/1/1458630846085_original.jpeg', '1458630846085_original.jpeg', '478', '2016-03-22 15:14:06', '1', '0');
INSERT INTO `hy_album_img` VALUES ('50', '1', 'ec/album/1/1458630846295_original.jpeg', '1458630846295_original.jpeg', '815', '2016-03-22 15:14:06', '1', '0');
INSERT INTO `hy_album_img` VALUES ('51', '1', 'ec/album/1/1458630846429_original.jpeg', '1458630846429_original.jpeg', '659', '2016-03-22 15:14:06', '1', '0');
INSERT INTO `hy_album_img` VALUES ('52', '1', 'ec/album/1/1458630846534_original.jpeg', '1458630846534_original.jpeg', '310', '2016-03-22 15:14:06', '1', '0');
INSERT INTO `hy_album_img` VALUES ('53', '1', 'ec/album/1/1458630846607_original.jpeg', '1458630846607_original.jpeg', '324', '2016-03-22 15:14:06', '1', '0');
INSERT INTO `hy_album_img` VALUES ('54', '1', 'ec/album/1/1458630846729_original.jpeg', '1458630846729_original.jpeg', '273', '2016-03-22 15:14:06', '1', '0');
INSERT INTO `hy_album_img` VALUES ('55', '1', 'ec/album/1/1458630846805_original.jpeg', '1458630846805_original.jpeg', '129', '2016-03-22 15:14:06', '1', '0');
INSERT INTO `hy_album_img` VALUES ('56', '1', 'ec/album/1/1458630846864_original.jpeg', '1458630846864_original.jpeg', '138', '2016-03-22 15:14:06', '1', '0');
INSERT INTO `hy_album_img` VALUES ('57', '1', 'ec/album/1/1458630847029_original.jpeg', '1458630847029_original.jpeg', '817', '2016-03-22 15:14:07', '1', '0');
INSERT INTO `hy_album_img` VALUES ('58', '3', 'ec/album/3/1458695596415_original.jpeg', '1458695596415_original.jpeg', '410', '2016-03-23 09:13:16', '1', '0');
INSERT INTO `hy_album_img` VALUES ('59', '4', 'ec/album/4/1458698830755_original.jpeg', '1458698830755_original.jpeg', '310', '2016-03-23 10:07:10', '1', '0');
INSERT INTO `hy_album_img` VALUES ('60', '4', 'ec/album/4/1458698830854_original.jpeg', '1458698830854_original.jpeg', '324', '2016-03-23 10:07:10', '1', '0');
INSERT INTO `hy_album_img` VALUES ('61', '1', 'ec/album/1/1460356634877_original.jpeg', '1460356634877_original.jpeg', '40', '2016-04-11 14:37:14', '1', '1');
INSERT INTO `hy_album_img` VALUES ('62', '3', 'ec/album/3/1460357103082_original.jpeg', '1460357103082_original.jpeg', '291', '2016-04-11 14:45:03', '1', '1');
INSERT INTO `hy_album_img` VALUES ('63', '3', 'ec/album/3/1460357103173_original.jpeg', '1460357103173_original.jpeg', '465', '2016-04-11 14:45:03', '1', '1');
INSERT INTO `hy_album_img` VALUES ('64', '3', 'ec/album/3/1460357157244_original.jpeg', '1460357157244_original.jpeg', '235', '2016-04-11 14:45:57', '1', '1');
INSERT INTO `hy_album_img` VALUES ('65', '3', 'ec/album/3/1460357157386_original.jpeg', '1460357157386_original.jpeg', '225', '2016-04-11 14:45:57', '1', '1');
INSERT INTO `hy_album_img` VALUES ('66', '3', 'ec/album/3/1460357157502_original.jpeg', '1460357157502_original.jpeg', '180', '2016-04-11 14:45:57', '1', '1');
INSERT INTO `hy_album_img` VALUES ('67', '1', 'ec/album/1/1460357217988_original.jpeg', '1460357217988_original.jpeg', '253', '2016-04-11 14:46:58', '1', '1');
INSERT INTO `hy_album_img` VALUES ('68', '1', 'ec/album/1/1460357218639_original.jpeg', '1460357218639_original.jpeg', '404', '2016-04-11 14:46:58', '1', '1');
INSERT INTO `hy_album_img` VALUES ('69', '1', 'ec/album/1/1460357218769_original.jpeg', '1460357218769_original.jpeg', '218', '2016-04-11 14:46:58', '1', '1');
INSERT INTO `hy_album_img` VALUES ('70', '1', 'ec/album/1/1460357218911_original.jpeg', '1460357218911_original.jpeg', '291', '2016-04-11 14:46:58', '1', '1');
INSERT INTO `hy_album_img` VALUES ('71', '1', 'ec/album/1/1460357219068_original.jpeg', '1460357219068_original.jpeg', '321', '2016-04-11 14:46:59', '1', '1');
INSERT INTO `hy_album_img` VALUES ('72', '1', 'ec/album/1/1460357219197_original.jpeg', '1460357219197_original.jpeg', '232', '2016-04-11 14:46:59', '1', '1');
INSERT INTO `hy_album_img` VALUES ('73', '1', 'ec/album/1/1476853890646_original.png', '1476853890646_original.png', '231', '2016-10-19 13:11:30', '1', '0');

-- ----------------------------
-- Table structure for hy_app_code
-- ----------------------------
DROP TABLE IF EXISTS `hy_app_code`;
CREATE TABLE `hy_app_code` (
  `CODE_STRING` varchar(20) NOT NULL COMMENT '码表类别 如:SEX',
  `CODE_NAME` varchar(50) NOT NULL COMMENT '码表名称 如:性别',
  `CODE_VALUE` varchar(6) NOT NULL COMMENT '码表值 如:1',
  `CODE_VALUE_NAME` varchar(50) NOT NULL COMMENT '码表值的名称 如:SEX:1 表示 男',
  `CODE_TYPE` tinyint(2) NOT NULL DEFAULT '0' COMMENT '码表类型 0系统码表 1业务码表 2其他码表',
  `CODE_CREATEER` int(10) NOT NULL DEFAULT '0' COMMENT '创建人 0系统默认',
  `CODE_CREATE_TIME` datetime NOT NULL COMMENT '码表创建时间',
  `CODE_STATUS` tinyint(2) NOT NULL DEFAULT '1' COMMENT '码表状态1 有效 0 无效'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统码表';

-- ----------------------------
-- Records of hy_app_code
-- ----------------------------
INSERT INTO `hy_app_code` VALUES ('COMMON_STATUS', '是否有效标志', '1', '有效', '0', '0', '2016-02-02 09:13:07', '1');
INSERT INTO `hy_app_code` VALUES ('COMMON_STATUS', '是否有效标志', '0', '无效', '0', '0', '2016-02-02 09:13:07', '1');
INSERT INTO `hy_app_code` VALUES ('IS_FORCE_LOGOUT', '是否强制退出', '1', '是', '0', '0', '2016-02-02 09:15:04', '1');
INSERT INTO `hy_app_code` VALUES ('IS_FORCE_LOGOUT', '是否强制退出', '0', '否', '0', '0', '2016-02-02 09:15:04', '1');
INSERT INTO `hy_app_code` VALUES ('USER_SEX', '性别', '0', '未知', '0', '0', '2016-02-02 09:16:03', '1');
INSERT INTO `hy_app_code` VALUES ('USER_SEX', '性别', '1', '男', '0', '0', '2016-02-02 09:16:03', '1');
INSERT INTO `hy_app_code` VALUES ('USER_SEX', '性别', '2', '女', '0', '0', '2016-02-02 09:16:03', '1');
INSERT INTO `hy_app_code` VALUES ('USER_LOCK', '用户锁定标志', '1', '未锁定', '0', '0', '2016-02-02 09:17:33', '1');
INSERT INTO `hy_app_code` VALUES ('USER_LOCK', '用户锁定标志', '0', '已锁定', '0', '0', '2016-02-02 09:17:33', '1');
INSERT INTO `hy_app_code` VALUES ('ROLE_TYPE', '角色类型', '1', '系统默认角色', '0', '1', '2016-04-11 13:03:45', '1');
INSERT INTO `hy_app_code` VALUES ('ROLE_TYPE', '角色类型', '2', '应用业务角色', '0', '0', '2016-02-02 09:18:30', '1');
INSERT INTO `hy_app_code` VALUES ('ROLE_TYPE', '角色类型', '3', '其他角色', '0', '1', '2016-04-11 13:47:47', '1');
INSERT INTO `hy_app_code` VALUES ('CODE_TYPE', '码表类型', '0', '系统码表', '0', '0', '2016-02-02 13:03:33', '1');
INSERT INTO `hy_app_code` VALUES ('CODE_TYPE', '码表类型', '1', '业务码表', '0', '0', '2016-02-02 13:03:33', '1');
INSERT INTO `hy_app_code` VALUES ('CODE_TYPE', '码表类型', '2', '其他码表', '0', '0', '2016-02-02 13:03:33', '1');
INSERT INTO `hy_app_code` VALUES ('ORG_TYPE', '组织类型', '1', '机构', '0', '1', '2016-04-25 10:54:17', '1');
INSERT INTO `hy_app_code` VALUES ('ORG_TYPE', '组织类型', '2', '集团(总公司)', '0', '1', '2016-04-25 10:54:50', '1');
INSERT INTO `hy_app_code` VALUES ('ORG_TYPE', '组织类型', '3', '子公司', '0', '1', '2016-04-25 10:55:05', '1');
INSERT INTO `hy_app_code` VALUES ('ORG_TYPE', '组织类型', '4', '部门', '0', '1', '2016-04-25 10:55:16', '1');
INSERT INTO `hy_app_code` VALUES ('ORG_TYPE', '组织类型', '5', '小组(团队)', '0', '1', '2016-09-01 16:07:12', '1');

-- ----------------------------
-- Table structure for hy_data_authority_create
-- ----------------------------
DROP TABLE IF EXISTS `hy_data_authority_create`;
CREATE TABLE `hy_data_authority_create` (
  `ROLE_ID` int(10) NOT NULL,
  `TABLE_NAME` varchar(64) NOT NULL,
  `TABLE_SCHEMA` varchar(64) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限(新增)';

-- ----------------------------
-- Records of hy_data_authority_create
-- ----------------------------

-- ----------------------------
-- Table structure for hy_data_authority_delete
-- ----------------------------
DROP TABLE IF EXISTS `hy_data_authority_delete`;
CREATE TABLE `hy_data_authority_delete` (
  `ROLE_ID` int(10) NOT NULL,
  `TABLE_NAME` varchar(64) NOT NULL,
  `TABLE_SCHEMA` varchar(64) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限(删除)';

-- ----------------------------
-- Records of hy_data_authority_delete
-- ----------------------------

-- ----------------------------
-- Table structure for hy_data_authority_query
-- ----------------------------
DROP TABLE IF EXISTS `hy_data_authority_query`;
CREATE TABLE `hy_data_authority_query` (
  `ROLE_ID` int(10) NOT NULL,
  `TABLE_NAME` varchar(64) NOT NULL,
  `TABLE_SCHEMA` varchar(64) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限(查询)';

-- ----------------------------
-- Records of hy_data_authority_query
-- ----------------------------

-- ----------------------------
-- Table structure for hy_data_authority_update
-- ----------------------------
DROP TABLE IF EXISTS `hy_data_authority_update`;
CREATE TABLE `hy_data_authority_update` (
  `ROLE_ID` int(10) NOT NULL,
  `TABLE_NAME` varchar(64) NOT NULL,
  `TABLE_SCHEMA` varchar(64) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据权限(修改)';

-- ----------------------------
-- Records of hy_data_authority_update
-- ----------------------------

-- ----------------------------
-- Table structure for hy_menu
-- ----------------------------
DROP TABLE IF EXISTS `hy_menu`;
CREATE TABLE `hy_menu` (
  `MENU_ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '菜单主键',
  `PARENT_ID` int(10) NOT NULL DEFAULT '0' COMMENT '上级菜单ID(为0时表示是顶级菜单)',
  `MENU_NAME` varchar(50) NOT NULL COMMENT '菜单名称',
  `MENU_URL` varchar(50) DEFAULT NULL COMMENT '菜单路劲',
  `MENU_ICON` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `MENU_STATUS` tinyint(2) NOT NULL DEFAULT '1' COMMENT '有效性 1有效 0无效',
  `MENU_CREATER` int(10) DEFAULT NULL COMMENT '创建人 0表示系统默认',
  `MENU_CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='菜单';

-- ----------------------------
-- Records of hy_menu
-- ----------------------------
INSERT INTO `hy_menu` VALUES ('4', '0', '系统管理', '', 'bars', '1', '0', '2016-01-12 15:05:57');
INSERT INTO `hy_menu` VALUES ('5', '4', '用户管理', 'system/user/userManager/default', null, '1', '0', '2016-01-12 15:07:34');
INSERT INTO `hy_menu` VALUES ('6', '4', '菜单管理', 'system/menu/menuManager/default', null, '1', '0', '2016-01-18 15:51:04');
INSERT INTO `hy_menu` VALUES ('10', '4', '角色管理', 'system/role/roleManager/default', null, '1', '1', '2016-01-20 09:21:45');
INSERT INTO `hy_menu` VALUES ('12', '4', '在线用户', 'system/user/onlineManager/default', null, '0', '1', '2016-01-25 14:54:34');
INSERT INTO `hy_menu` VALUES ('14', '4', 'Druid监控', 'druid/index.html', null, '1', '1', '2016-03-04 09:46:15');
INSERT INTO `hy_menu` VALUES ('15', '4', '相册管理', 'system/album/albumManager/default', null, '1', '1', '2016-03-21 16:51:58');
INSERT INTO `hy_menu` VALUES ('20', '4', '码表管理', 'system/appcode/appcodeManager/default', null, '1', '1', '2016-04-11 09:48:58');
INSERT INTO `hy_menu` VALUES ('22', '4', '组织管理', 'system/org/orgManager/default', null, '1', '1', '2016-04-26 11:45:52');
INSERT INTO `hy_menu` VALUES ('23', '4', '定时任务', 'system/quartz/taskManager/default', null, '1', '1', '2016-09-12 16:57:41');

-- ----------------------------
-- Table structure for hy_menu_auth
-- ----------------------------
DROP TABLE IF EXISTS `hy_menu_auth`;
CREATE TABLE `hy_menu_auth` (
  `AUTH_ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ROLE_ID` int(10) NOT NULL COMMENT '岗位ID',
  `MENU_ID` int(10) NOT NULL COMMENT '菜单(权限ID)',
  `ROLE_CREATE` tinyint(2) NOT NULL DEFAULT '1' COMMENT '新增权限 1有 0 无',
  `ROLE_QUERY` tinyint(2) NOT NULL DEFAULT '1' COMMENT '查询权限 1有 0无',
  `ROLE_UPDATE` tinyint(2) NOT NULL DEFAULT '1' COMMENT '修改权限 1有 0无',
  `ROLE_DELETE` tinyint(2) NOT NULL DEFAULT '1' COMMENT '删除权限 1有 0无',
  `IS_PARENT` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否是父级菜单 1是 0不是',
  `ROLE_STRING` varchar(10000) DEFAULT NULL COMMENT '权限字符串,通过Controller的路劲生成,以逗号隔开',
  PRIMARY KEY (`AUTH_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=452 DEFAULT CHARSET=utf8 COMMENT='菜单权限详情';

-- ----------------------------
-- Records of hy_menu_auth
-- ----------------------------
INSERT INTO `hy_menu_auth` VALUES ('443', '1', '4', '1', '1', '1', '1', '1', null);
INSERT INTO `hy_menu_auth` VALUES ('444', '1', '5', '1', '1', '1', '1', '0', 'system:user:userManager:default,system:user:userManager:updateResetPassword,system:user:userManager:createUser,system:user:userManager:queryUserList,system:user:userManager:saveUser,system:user:userManager:updateUser');
INSERT INTO `hy_menu_auth` VALUES ('445', '1', '6', '1', '1', '1', '1', '0', 'system:menu:menuManager:default,system:menu:menuManager:deleteMenu,system:menu:menuManager:queryMenuList,system:menu:menuManager:saveMenu');
INSERT INTO `hy_menu_auth` VALUES ('446', '1', '10', '1', '1', '1', '1', '0', 'system:role:roleManager:default,system:role:roleManager:updateRoleUser,system:role:roleManager:saveRoleAuthority,system:role:roleManager:updateAuthorityOne,system:role:roleManager:createRole,system:role:roleManager:saveRole,system:role:roleManager:deleteRole,system:role:roleManager:updateRole,system:role:roleManager:saveRoleDataAuthority,system:role:roleManager:viewRoleAuthorityDetail,system:role:roleManager:viewAllUsers,system:role:roleManager:queryRoleList,system:role:roleManager:editDataAuthority,system:role:roleManager:viewAuthorityList,system:role:roleManager:queryRoleUsers,system:role:roleManager:queryMenuAuthorityTree,system:role:roleManager:queryAllRoleUsers,system:role:roleManager:queryMenuAuthorityIds');
INSERT INTO `hy_menu_auth` VALUES ('447', '1', '14', '1', '1', '1', '1', '0', null);
INSERT INTO `hy_menu_auth` VALUES ('448', '1', '15', '1', '1', '1', '1', '0', null);
INSERT INTO `hy_menu_auth` VALUES ('449', '1', '20', '1', '1', '1', '1', '0', null);
INSERT INTO `hy_menu_auth` VALUES ('450', '1', '22', '1', '1', '1', '1', '0', null);
INSERT INTO `hy_menu_auth` VALUES ('451', '1', '23', '1', '1', '1', '1', '0', null);

-- ----------------------------
-- Table structure for hy_online
-- ----------------------------
DROP TABLE IF EXISTS `hy_online`;
CREATE TABLE `hy_online` (
  `USER_ID` int(10) NOT NULL COMMENT '在线用户ID',
  `SESSIONID` varchar(50) NOT NULL COMMENT 'sessionid',
  `LOGIN_TIME` datetime NOT NULL COMMENT '登陆时间',
  `LOGIN_IP` varchar(50) NOT NULL COMMENT '登陆IP'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='在线成员';

-- ----------------------------
-- Records of hy_online
-- ----------------------------

-- ----------------------------
-- Table structure for hy_online_log
-- ----------------------------
DROP TABLE IF EXISTS `hy_online_log`;
CREATE TABLE `hy_online_log` (
  `LOG_ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID` int(10) NOT NULL COMMENT '用户ID',
  `SESSIONID` varchar(50) NOT NULL COMMENT 'sessionid',
  `LOGIN_IP` varchar(50) NOT NULL COMMENT '登陆IP',
  `LOGIN_TIME` datetime NOT NULL COMMENT '登陆时间',
  `LOGOUT_TIME` datetime DEFAULT NULL COMMENT '登出时间',
  `IS_FORCE_LOGOUT` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否强制退出 1是 0否 ',
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='用户登陆历史记录';

-- ----------------------------
-- Records of hy_online_log
-- ----------------------------
INSERT INTO `hy_online_log` VALUES ('1', '1', '8e2dd875-0cf4-4c6b-8f60-1d8fe9bb6746', '0:0:0:0:0:0:0:1', '2016-09-01 16:05:59', null, '0');
INSERT INTO `hy_online_log` VALUES ('2', '1', '956615c3-6caa-4a5e-b9dd-72e15591c642', '0:0:0:0:0:0:0:1', '2016-09-05 15:45:26', null, '0');
INSERT INTO `hy_online_log` VALUES ('3', '1', 'c6107594-39bc-49cd-99a8-9f26e609d48d', '0:0:0:0:0:0:0:1', '2016-09-06 12:56:44', '2016-09-06 12:57:09', '0');
INSERT INTO `hy_online_log` VALUES ('4', '1', 'a03005b3-11ba-445f-ba91-73193d76e6d4', '192.168.2.15', '2016-09-12 17:23:45', '2016-09-12 17:24:15', '0');
INSERT INTO `hy_online_log` VALUES ('5', '1', 'b234ae9f-2b28-4f67-a013-b787391de390', '0:0:0:0:0:0:0:1', '2016-09-14 11:20:47', null, '0');
INSERT INTO `hy_online_log` VALUES ('6', '1', '941111f6-5de3-445b-bf8d-0aa9437db2a1', '0:0:0:0:0:0:0:1', '2016-09-14 13:17:17', null, '0');
INSERT INTO `hy_online_log` VALUES ('7', '1', 'dfe43d38-92a0-43f6-ae4c-64e191d7bcd1', '0:0:0:0:0:0:0:1', '2016-09-14 13:33:11', null, '0');
INSERT INTO `hy_online_log` VALUES ('8', '1', '252c22b9-62d4-490d-93b5-ee998e296922', '0:0:0:0:0:0:0:1', '2016-09-14 13:51:13', '2016-09-14 14:10:56', '0');
INSERT INTO `hy_online_log` VALUES ('9', '1', '6436760d-34aa-43eb-aa74-aa82c9245105', '0:0:0:0:0:0:0:1', '2016-09-14 14:11:01', null, '0');
INSERT INTO `hy_online_log` VALUES ('10', '1', '1d139ad9-edb2-4128-954a-b1279c84f136', '0:0:0:0:0:0:0:1', '2016-09-14 14:58:57', null, '0');
INSERT INTO `hy_online_log` VALUES ('11', '1', 'cff66e21-0ab3-4065-b356-5f327d38570f', '0:0:0:0:0:0:0:1', '2016-10-19 13:10:42', '2016-10-19 13:14:03', '0');
INSERT INTO `hy_online_log` VALUES ('12', '1', '894cfd58-b523-4e0f-807b-f891a852a1af', '0:0:0:0:0:0:0:1', '2016-10-19 13:31:36', '2016-10-19 13:32:17', '0');
INSERT INTO `hy_online_log` VALUES ('13', '1', '56f68aa3-4e58-4afc-9912-901e1cb72436', '0:0:0:0:0:0:0:1', '2016-11-02 10:36:20', '2016-11-02 10:37:14', '0');
INSERT INTO `hy_online_log` VALUES ('14', '1', 'c3467ca5-79f9-487f-906c-80760edaa4aa', '0:0:0:0:0:0:0:1', '2016-11-02 10:37:33', '2016-11-02 10:37:49', '0');
INSERT INTO `hy_online_log` VALUES ('15', '1', 'ada6d424-fec6-45af-8ca6-b716aca9c7e0', '0:0:0:0:0:0:0:1', '2016-11-02 10:37:59', '2016-11-02 10:38:02', '0');
INSERT INTO `hy_online_log` VALUES ('16', '1', '31e431bb-2a76-413c-95f3-843e28781c9e', '0:0:0:0:0:0:0:1', '2016-11-02 10:38:11', '2016-11-02 10:38:14', '0');
INSERT INTO `hy_online_log` VALUES ('17', '1', '4eeb6a5d-63fd-45db-8acd-801e80866db0', '0:0:0:0:0:0:0:1', '2016-11-02 11:52:31', '2016-11-02 12:39:01', '0');
INSERT INTO `hy_online_log` VALUES ('18', '1', '4c99ac8d-0cb2-4fb5-835f-ed042c50097a', '0:0:0:0:0:0:0:1', '2016-11-02 12:39:16', '2016-11-02 12:46:02', '0');
INSERT INTO `hy_online_log` VALUES ('19', '1', 'cffc4eed-cfef-4b7c-95eb-15fa30f63d8c', '0:0:0:0:0:0:0:1', '2016-11-02 12:46:24', '2016-11-02 13:08:21', '0');
INSERT INTO `hy_online_log` VALUES ('20', '1', 'c6d932f6-6fa6-457f-84e6-ddd03ee7a141', '0:0:0:0:0:0:0:1', '2016-11-02 13:08:30', '2016-11-02 13:10:26', '0');
INSERT INTO `hy_online_log` VALUES ('21', '1', '3345f02b-f938-486d-a867-836d8ef97117', '0:0:0:0:0:0:0:1', '2016-11-02 13:10:57', '2016-11-02 13:11:01', '0');
INSERT INTO `hy_online_log` VALUES ('22', '1', '70f954fd-e258-46b0-be4c-81946b970540', '0:0:0:0:0:0:0:1', '2016-11-02 13:15:26', '2016-11-02 13:16:14', '0');
INSERT INTO `hy_online_log` VALUES ('23', '1', '3249b0d1-061a-4fea-b57d-05e9be5307a4', '0:0:0:0:0:0:0:1', '2016-11-02 15:46:16', '2016-11-02 15:49:01', '0');
INSERT INTO `hy_online_log` VALUES ('24', '1', 'e24557ed-1e70-4230-94c5-28ecb8ad2591', '0:0:0:0:0:0:0:1', '2016-11-02 15:50:14', '2016-11-02 15:50:50', '0');
INSERT INTO `hy_online_log` VALUES ('25', '1', '15b4316f-3610-431e-b596-fdf63a82092c', '0:0:0:0:0:0:0:1', '2016-11-02 15:51:32', '2016-11-02 16:17:27', '0');
INSERT INTO `hy_online_log` VALUES ('26', '1', '8025b000-8447-4425-b9c5-6bda6ac267e0', '0:0:0:0:0:0:0:1', '2016-11-02 16:17:37', '2016-11-02 16:17:45', '0');
INSERT INTO `hy_online_log` VALUES ('27', '1', '6c6df8c8-20aa-41af-8d02-87c52fa534c2', '0:0:0:0:0:0:0:1', '2016-11-02 17:10:55', null, '0');
INSERT INTO `hy_online_log` VALUES ('28', '1', '4ca9768b-34a7-4171-a594-b7f20f0b7218', '0:0:0:0:0:0:0:1', '2016-11-03 10:24:30', '2016-11-03 10:25:25', '0');
INSERT INTO `hy_online_log` VALUES ('29', '1', '24024c69-9d97-4208-9544-4244d66c0187', '0:0:0:0:0:0:0:1', '2016-11-03 10:25:32', null, '0');
INSERT INTO `hy_online_log` VALUES ('30', '1', '01dc32c4-9bed-4c16-a1ac-13bcb25f3632', '0:0:0:0:0:0:0:1', '2016-11-03 11:22:47', null, '0');
INSERT INTO `hy_online_log` VALUES ('31', '1', '01dc32c4-9bed-4c16-a1ac-13bcb25f3632', '0:0:0:0:0:0:0:1', '2016-11-03 11:24:03', null, '0');
INSERT INTO `hy_online_log` VALUES ('32', '1', '416d4450-6e9d-4cea-b2cb-0f4f1d16845c', '0:0:0:0:0:0:0:1', '2016-11-03 13:56:27', '2016-11-03 14:14:48', '0');
INSERT INTO `hy_online_log` VALUES ('33', '1', '5487b121-084c-4591-9627-c678ca21336a', '0:0:0:0:0:0:0:1', '2016-11-03 14:14:52', null, '0');
INSERT INTO `hy_online_log` VALUES ('34', '1', '99faea7b-91a2-41bb-91dc-1669d545af98', '0:0:0:0:0:0:0:1', '2016-11-04 11:29:49', '2016-11-04 11:34:12', '0');
INSERT INTO `hy_online_log` VALUES ('35', '1', '7f4981d7-5283-4d9b-9734-1f4297cc4b28', '0:0:0:0:0:0:0:1', '2016-11-09 16:56:54', null, '0');
INSERT INTO `hy_online_log` VALUES ('36', '1', 'ee3b4891-52a6-4262-a27d-0b3b88d3fdee', '0:0:0:0:0:0:0:1', '2016-11-11 15:51:12', '2016-11-11 15:54:23', '0');
INSERT INTO `hy_online_log` VALUES ('37', '1', '4e1e337b-320e-405f-82b3-a2f0e9490558', '0:0:0:0:0:0:0:1', '2016-11-11 15:58:04', '2016-11-11 15:58:10', '0');
INSERT INTO `hy_online_log` VALUES ('38', '1', '3c91a0e0-1006-41bc-b811-cf3e89bee7ca', '0:0:0:0:0:0:0:1', '2016-11-11 15:58:20', null, '0');
INSERT INTO `hy_online_log` VALUES ('39', '1', 'e787f341-2278-450b-9607-37da873ce0c1', '192.168.29.84', '2016-11-28 09:14:39', null, '0');
INSERT INTO `hy_online_log` VALUES ('40', '1', 'ff4a21ea-d8c6-4abd-87c8-b661dbd67e43', '0:0:0:0:0:0:0:1', '2017-02-21 13:49:46', null, '0');
INSERT INTO `hy_online_log` VALUES ('41', '1', '04c14164-28af-4edd-8759-0105245c3316', '0:0:0:0:0:0:0:1', '2017-02-21 13:52:28', null, '0');
INSERT INTO `hy_online_log` VALUES ('42', '1', '0ab4ac60-ce58-4bed-9931-c8eb6972811e', '0:0:0:0:0:0:0:1', '2017-02-21 16:40:24', null, '0');
INSERT INTO `hy_online_log` VALUES ('43', '1', '65e1760b-2835-42a9-8cb4-6a76d6b751f7', '0:0:0:0:0:0:0:1', '2017-03-09 14:49:17', null, '0');
INSERT INTO `hy_online_log` VALUES ('44', '1', '2a812a4c-472e-470e-a524-653423acbefc', '0:0:0:0:0:0:0:1', '2017-03-09 14:57:55', null, '0');
INSERT INTO `hy_online_log` VALUES ('45', '1', 'ef2eb8be-ec30-4d4e-8e18-942567b4236d', '0:0:0:0:0:0:0:1', '2017-03-09 15:29:10', null, '0');
INSERT INTO `hy_online_log` VALUES ('46', '1', 'be4cf22c-e5d2-46aa-8f95-367db120f2b7', '0:0:0:0:0:0:0:1', '2017-03-09 15:41:45', '2017-03-09 15:42:01', '0');
INSERT INTO `hy_online_log` VALUES ('47', '1', '12e76c88-4590-459f-bab9-42d2e231f78e', '0:0:0:0:0:0:0:1', '2017-03-09 15:42:07', '2017-03-09 15:43:27', '0');
INSERT INTO `hy_online_log` VALUES ('48', '1', '10a4de30-713b-440a-b8c6-c61f02b2835f', '0:0:0:0:0:0:0:1', '2017-03-09 16:02:15', '2017-03-09 16:02:42', '0');
INSERT INTO `hy_online_log` VALUES ('49', '1', '6b7aa102-2c83-4f64-a54a-a66d2c19fa79', '0:0:0:0:0:0:0:1', '2017-03-09 16:02:47', null, '0');
INSERT INTO `hy_online_log` VALUES ('50', '1', 'cd8f74fb-9bc7-4fe5-bfba-0d349d686048', '0:0:0:0:0:0:0:1', '2017-03-09 17:23:59', null, '0');
INSERT INTO `hy_online_log` VALUES ('51', '1', 'dbc27a95-9abe-483b-be3f-ff9fde99e778', '0:0:0:0:0:0:0:1', '2017-03-10 10:21:16', '2017-03-10 10:21:49', '0');
INSERT INTO `hy_online_log` VALUES ('52', '1', '0d613638-6a4c-4a3f-9b05-484c9a60a76c', '0:0:0:0:0:0:0:1', '2017-03-10 10:24:37', null, '0');
INSERT INTO `hy_online_log` VALUES ('53', '1', '0d613638-6a4c-4a3f-9b05-484c9a60a76c', '0:0:0:0:0:0:0:1', '2017-03-10 10:26:31', null, '0');
INSERT INTO `hy_online_log` VALUES ('54', '1', '4af141b5-27fc-494e-a3f7-82495331fe5f', '0:0:0:0:0:0:0:1', '2017-03-10 10:29:42', '2017-03-10 10:39:49', '0');
INSERT INTO `hy_online_log` VALUES ('55', '1', '86bda0d6-3bb3-49ca-b4fe-77862f36d6fc', '0:0:0:0:0:0:0:1', '2017-03-10 10:39:51', '2017-03-10 10:53:04', '0');
INSERT INTO `hy_online_log` VALUES ('56', '1', 'c9a8e621-c6cb-4b98-b722-30b8cb59a4bf', '0:0:0:0:0:0:0:1', '2017-03-10 10:53:06', null, '0');
INSERT INTO `hy_online_log` VALUES ('57', '1', '7acc9aa0-33a4-4b6a-8698-8967d48b5333', '0:0:0:0:0:0:0:1', '2017-03-10 11:04:13', null, '0');
INSERT INTO `hy_online_log` VALUES ('58', '1', '90dcbdcd-f1ab-42cb-9992-8497bf0448c3', '0:0:0:0:0:0:0:1', '2017-03-10 11:07:20', '2017-03-10 11:08:20', '0');
INSERT INTO `hy_online_log` VALUES ('59', '1', '3ff8ed72-7dcc-420a-ac83-c3c807329ef5', '0:0:0:0:0:0:0:1', '2017-03-10 11:08:21', '2017-03-10 11:13:09', '0');
INSERT INTO `hy_online_log` VALUES ('60', '1', '9a1f29df-9a51-4c73-b4cb-f9555a28b4ce', '0:0:0:0:0:0:0:1', '2017-03-10 11:13:10', null, '0');
INSERT INTO `hy_online_log` VALUES ('61', '1', '4c898856-8f8a-4ec8-a8a1-d32808a4a395', '0:0:0:0:0:0:0:1', '2017-03-10 11:21:23', null, '0');
INSERT INTO `hy_online_log` VALUES ('62', '1', 'f1060b67-3d9d-42a0-a3d0-937c18b764d4', '0:0:0:0:0:0:0:1', '2017-03-10 11:27:51', null, '0');
INSERT INTO `hy_online_log` VALUES ('63', '1', '0920b9ce-4c96-46ee-aaec-389b2dfb857c', '0:0:0:0:0:0:0:1', '2017-03-10 13:20:40', null, '0');
INSERT INTO `hy_online_log` VALUES ('64', '1', '5f225ab1-d793-4b8e-b741-d351104c52f9', '0:0:0:0:0:0:0:1', '2017-03-10 14:01:16', null, '0');

-- ----------------------------
-- Table structure for hy_org
-- ----------------------------
DROP TABLE IF EXISTS `hy_org`;
CREATE TABLE `hy_org` (
  `ORG_ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `PARENT_ORG_ID` int(10) NOT NULL DEFAULT '0' COMMENT '父级ID 默认0顶级',
  `ORG_NAME` varchar(60) NOT NULL COMMENT '部门名称',
  `COSTOMNO` varchar(10) DEFAULT NULL COMMENT '自定义编码(预留)',
  `ORG_TYPE` tinyint(2) DEFAULT NULL COMMENT '组织类型 1机构 2集团(总公司) 3子公司 4部门 5组',
  `ORG_ID_PATH` varchar(255) NOT NULL COMMENT '组织id路径',
  `ORG_NAME_PATH` varchar(1024) NOT NULL COMMENT '组织名称路径',
  `ORG_LEVEL` int(10) NOT NULL COMMENT '组织等级',
  `SORT` int(11) DEFAULT NULL COMMENT '排序号',
  `USER_CREATER` int(10) NOT NULL COMMENT '创建人 0表示系统默认',
  `USER_CREATE_TIME` datetime NOT NULL COMMENT '创建时间',
  `ORG_STATUS` tinyint(2) NOT NULL DEFAULT '1' COMMENT '有效标志 1有效 0无效',
  `IS_LEAF` tinyint(2) NOT NULL DEFAULT '0' COMMENT '是否存在下级 1有 0无 默认0',
  PRIMARY KEY (`ORG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='组织结构表';

-- ----------------------------
-- Records of hy_org
-- ----------------------------
INSERT INTO `hy_org` VALUES ('1', '0', '总公司', '', null, '1', '总公司', '1', null, '1', '2016-04-27 15:04:27', '1', '1');
INSERT INTO `hy_org` VALUES ('2', '1', '技术研发', null, null, '1/2', '总公司/技术研发', '2', null, '1', '2016-04-28 09:30:23', '1', '0');
INSERT INTO `hy_org` VALUES ('3', '1', '财务部', null, null, '1/3', '总公司/财务部', '2', null, '1', '2016-04-28 09:30:36', '1', '0');
INSERT INTO `hy_org` VALUES ('5', '1', '人力资源', null, null, '1/5', '总公司/人力资源', '2', null, '1', '2016-04-28 09:30:51', '1', '0');
INSERT INTO `hy_org` VALUES ('6', '1', '管理部门', null, null, '1/6', '总公司/管理部门', '2', null, '1', '2016-04-28 09:31:15', '1', '0');
INSERT INTO `hy_org` VALUES ('7', '1', '互联网部门', null, null, '1/7', '总公司/互联网部门', '2', null, '1', '2016-04-28 09:31:31', '1', '0');
INSERT INTO `hy_org` VALUES ('8', '1', '子公司', null, null, '1/8', '总公司/子公司', '2', null, '1', '2016-04-28 09:31:47', '1', '1');
INSERT INTO `hy_org` VALUES ('9', '8', '行政', null, null, '1/8/9', '总公司/子公司/行政', '3', null, '1', '2016-04-28 09:31:55', '1', '0');
INSERT INTO `hy_org` VALUES ('10', '8', '技术部门', null, null, '1/8/10', '总公司/子公司/技术部门', '3', null, '1', '2016-04-28 09:32:05', '1', '0');
INSERT INTO `hy_org` VALUES ('11', '1', '行政', null, null, '1/11', '总公司/行政', '2', null, '1', '2016-04-28 09:32:21', '1', '0');
INSERT INTO `hy_org` VALUES ('12', '9', 'new node1', null, null, '1/8/9/12', '总公司/子公司/行政/new node1', '4', null, '1', '2016-05-12 21:48:30', '0', '0');

-- ----------------------------
-- Table structure for hy_org_roles
-- ----------------------------
DROP TABLE IF EXISTS `hy_org_roles`;
CREATE TABLE `hy_org_roles` (
  `ORG_ID` int(10) NOT NULL COMMENT '部门ID',
  `ROLE_ID` int(10) NOT NULL COMMENT '角色ID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门角色表';

-- ----------------------------
-- Records of hy_org_roles
-- ----------------------------
INSERT INTO `hy_org_roles` VALUES ('1', '1');

-- ----------------------------
-- Table structure for hy_user
-- ----------------------------
DROP TABLE IF EXISTS `hy_user`;
CREATE TABLE `hy_user` (
  `USER_ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '人员id',
  `USER_NAME` varchar(50) NOT NULL COMMENT '真实姓名(匿名)',
  `USER_SEX` tinyint(2) DEFAULT NULL COMMENT '性别 0未知 1男 2女 ',
  `USER_LOGIN` varchar(50) NOT NULL COMMENT '登陆账号',
  `USER_PWD` varchar(50) NOT NULL COMMENT '密码',
  `USER_PWD_WRONG_TIMES` tinyint(2) DEFAULT '0' COMMENT '口令输入错误次数',
  `USER_PWD_UPDATE_TIME` datetime DEFAULT NULL COMMENT '口令最后修改时间',
  `USER_LOCK` tinyint(2) NOT NULL DEFAULT '1' COMMENT '锁定标志 1未锁定 0锁定 ',
  `USER_STATUS` tinyint(2) NOT NULL DEFAULT '1' COMMENT '有效标志 1有效 0无效',
  `USER_PHONE` varchar(11) DEFAULT NULL COMMENT '手机号',
  `USER_EMAIL` varchar(50) DEFAULT NULL COMMENT '用户邮箱',
  `USER_HEADER_PIC` varchar(100) DEFAULT NULL COMMENT '头像地址',
  `USER_CREATER` int(10) DEFAULT NULL COMMENT '创建人 0表示系统默认',
  `USER_CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `ORG_ID` int(10) NOT NULL COMMENT '部门ID',
  `ORG_NAME` varchar(60) NOT NULL COMMENT '部门名称',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of hy_user
-- ----------------------------
INSERT INTO `hy_user` VALUES ('1', 'admin', '0', 'admin', '4d53a6d6caa317a0d8b0446e36eaa065', '0', '2016-09-13 10:11:16', '1', '1', '13699473921', '13699473921@163.com', 'ec/user/header/1489114868558_original.jpeg', '1', '2015-12-30 15:45:34', '1', '总公司');

-- ----------------------------
-- Table structure for hy_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `hy_user_roles`;
CREATE TABLE `hy_user_roles` (
  `ROLE_ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `ROLE_NAME` varchar(50) NOT NULL COMMENT '角色名称',
  `ROLE_STRING` varchar(50) NOT NULL COMMENT '角色验证字符串 如''admin'',''developer'',''user''',
  `ROLE_TYPE` tinyint(2) NOT NULL DEFAULT '1' COMMENT '角色类型 1系统默认角色 2应用业务角色 3其他角色',
  `ROLE_STATUS` tinyint(2) NOT NULL DEFAULT '1' COMMENT '角色状态 1有效 0无效',
  `ROLE_CREATER` int(10) NOT NULL DEFAULT '0' COMMENT '角色创建人 0表示系统默认',
  `ROLE_CREATE_TIME` datetime NOT NULL COMMENT '角色创建时间',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
-- Records of hy_user_roles
-- ----------------------------
INSERT INTO `hy_user_roles` VALUES ('1', '系统管理员', 'admin', '1', '1', '0', '2015-12-30 15:46:04');

-- ----------------------------
-- Table structure for hy_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `hy_user_role_relation`;
CREATE TABLE `hy_user_role_relation` (
  `ID` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `USER_ID` int(10) NOT NULL COMMENT '用户ID',
  `ROLE_ID` int(10) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`ID`),
  KEY `FK_USER_ID` (`USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户角色关联关系表';

-- ----------------------------
-- Records of hy_user_role_relation
-- ----------------------------
INSERT INTO `hy_user_role_relation` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('ECScheduler', 'TRIGGER_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('schedulerFactory', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
