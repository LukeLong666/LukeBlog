CREATE DATABASE IF NOT EXISTS luke_blog CHARACTER SET utf8;

CREATE TABLE `tb_user`(  
  `id` VARCHAR(20) NOT NULL COMMENT 'ID',
  `user_name` VARCHAR(32) NOT NULL COMMENT '用户名',
  `password` VARCHAR(32) NOT NULL COMMENT '密码',
  `roles` VARCHAR(100) NOT NULL COMMENT '角色',
  `avatar` VARCHAR(1024) NOT NULL COMMENT '头像地址',
  `email` VARCHAR(100) COMMENT '邮箱地址',
  `sign` VARCHAR(100) COMMENT '签名',
  `state` VARCHAR(1) NOT NULL COMMENT '状态：0表示删除，1表示正常',
  `reg_ip` VARCHAR(32) NOT NULL COMMENT '注册ip',
  `login_ip` VARCHAR(32) NOT NULL COMMENT '登录Ip',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `tb_images` (
  `id` VARCHAR(20) NOT NULL COMMENT 'ID',
  `user_id` VARCHAR(20) NOT NULL COMMENT '用户ID',
  `url` VARCHAR(1024) NOT NULL COMMENT '路径',
  `state` VARCHAR(1) NOT NULL COMMENT '状态（0表示删除，1表正常）',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_user_images_on_user_id` (`user_id`),
  CONSTRAINT `fk_user_images_on_user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `tb_categories`(  
  `id` VARCHAR(20) NOT NULL COMMENT 'ID',
  `name` VARCHAR(64) NOT NULL COMMENT '分类名称',
  `pinyin` VARCHAR(128) NOT NULL COMMENT '拼音',
  `description` TEXT NOT NULL COMMENT '描述',
  `order` INT(11) NOT NULL COMMENT '顺序',
  `status` VARCHAR(1) NOT NULL COMMENT '状态：0表示不使用，1表示正常',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `tb_article` (
  `id` VARCHAR(20) NOT NULL COMMENT 'ID',
  `title` VARCHAR(256) NOT NULL COMMENT '标题',
  `user_id` VARCHAR(20) NOT NULL COMMENT '用户ID',
  `user_avatar` VARCHAR(1024) DEFAULT NULL COMMENT '用户头像',
  `user_name` VARCHAR(32) DEFAULT NULL COMMENT '用户昵称',
  `category_id` VARCHAR(20) NOT NULL COMMENT '分类ID',
  `content` MEDIUMTEXT NOT NULL COMMENT '文章内容',
  `type` VARCHAR(1) NOT NULL COMMENT '类型（0表示富文本，1表示markdown）',
  `state` VARCHAR(1) NOT NULL COMMENT '状态（0表示已发布，1表示草稿，2表示删除）',
  `summary` TEXT NOT NULL COMMENT '摘要',
  `labels` VARCHAR(128) NOT NULL COMMENT '标签',
  `view_count` INT(11) NOT NULL DEFAULT '0' COMMENT '阅读数量',
  `create_time` DATETIME NOT NULL COMMENT '发布时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_user_article_on_user_id` (`user_id`),
  KEY `fk_category_article_on_category_id` (`category_id`),
  CONSTRAINT `fk_category_article_on_category_id` FOREIGN KEY (`category_id`) REFERENCES `tb_categories` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_article_on_user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `tb_comment` (
  `id` VARCHAR(20) NOT NULL COMMENT 'ID',
  `parent_content` TEXT COMMENT '父内容',
  `article_id` VARCHAR(20) NOT NULL COMMENT '文章ID',
  `content` TEXT NOT NULL COMMENT '评论内容',
  `user_id` VARCHAR(20) NOT NULL COMMENT '评论用户的ID',
  `user_avatar` VARCHAR(1024) DEFAULT NULL COMMENT '评论用户的头像',
  `user_name` VARCHAR(32) DEFAULT NULL COMMENT '评论用户的名称',
  `state` VARCHAR(1) NOT NULL COMMENT '状态（0表示删除，1表示正常）',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `fk_user_comment_on_user_id` (`user_id`),
  KEY `fk_article_comment_on_article_id` (`article_id`),
  CONSTRAINT `fk_article_comment_on_article_id` FOREIGN KEY (`article_id`) REFERENCES `tb_article` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_comment_on_user_id` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `tb_looper` (
  `id` VARCHAR(20) NOT NULL COMMENT 'ID',
  `title` VARCHAR(128) NOT NULL COMMENT '轮播图标题',
  `order` INT(11) NOT NULL DEFAULT '0' COMMENT '顺序',
  `state` VARCHAR(1) NOT NULL COMMENT '状态：0表示不可用，1表示正常',
  `target_url` VARCHAR(1024) DEFAULT NULL COMMENT '目标URL',
  `image_url` VARCHAR(2014) NOT NULL COMMENT '图片地址',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `tb_daily_view_count`(  
  `id` VARCHAR(20) NOT NULL COMMENT 'ID',
  `view_count` INT NOT NULL DEFAULT 0 COMMENT '每天浏览量',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `tb_labels`(  
  `id` VARCHAR(20) NOT NULL COMMENT 'ID',
  `name` VARCHAR(32) NOT NULL COMMENT '标签名称',
  `count` INT NOT NULL DEFAULT 0 COMMENT '数量',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `tb_settings`(  
  `id` VARCHAR(20) NOT NULL COMMENT 'ID',
  `key` VARCHAR(32) NOT NULL COMMENT '键',
  `value` VARCHAR(512) NOT NULL COMMENT '值',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;

CREATE TABLE `tb_friends`(  
  `id` VARCHAR(20) NOT NULL COMMENT 'ID',
  `name` VARCHAR(64) NOT NULL COMMENT '友情链接名称',
  `logo` VARCHAR(1024) NOT NULL COMMENT '友情链接logo',
  `url` VARCHAR(1024) NOT NULL COMMENT '友情链接',
  `order` INT(11) NOT NULL DEFAULT 0 COMMENT '顺序',
  `state` VARCHAR(1) NOT NULL COMMENT '友情链接状态:0表示不可用，1表示正常',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB CHARSET=utf8 COLLATE=utf8_general_ci;