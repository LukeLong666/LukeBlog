# 数据库设计

## 字段设计

### 用户表	tb_user

| FIeld       | Type                   | Comment                   |
| ----------- | ---------------------- | ------------------------- |
| id          | varchar(20) NOT NULL   | ID                        |
| user_name   | varchar(32) NOT NULL   | 用户名                    |
| password    | varchar(32) NOT NULL   | 密码                      |
| roles       | varchar(100) NOT NULL  | 角色                      |
| avatar      | varchar(1024) NOT NULL | 头像地址                  |
| email       | varchar(100) NOT NULL  | 邮箱                      |
| sign        | varchar(100) NOT NULL  | 签名                      |
| state       | varchar(1) NOT NULL    | 状态(0表示删除,1表示正常) |
| reg_ip      | varchar(32) NOT NULL   | 注册ip                    |
| login_ip    | varchar(32) NOT NULL   | 登录ip                    |
| create_time | datetime NOT NULL      | 创建时间                  |
| update_time | datetime NOT NULL      | 更新时间                  |

### 文章分类表	tb_categories

| Filed       | Type                  | Comment  |
| ----------- | --------------------- | -------- |
| id          | varchar(20) NOT NULL  | ID       |
| name        | varchar(64) NOT NULL  | 分类名称 |
| pinyin      | varchar(128) NOT NULL | 名称拼音 |
| description | text NOT NULL         | 分类描述 |
| order       | int(11) NOT NULL      | 顺序     |
| status      | varchar(1) NOT NULL   | 状态     |
| create_time | datetime NOT NULL     | 创建时间 |
| update_time | datetime NOT NULL     | 更新时间 |

### 文章表	tb_article

| Filed       | Type                  | Comment                               |
| ----------- | --------------------- | ------------------------------------- |
| id          | varchar(20) NOT NULL  | ID                                    |
| title       | varchar(256) NOT NULL | 标题                                  |
| user_id     | varchar(20) NOT NULL  | 用户ID                                |
| user_avatar     | varchar(1024) NOT NULL  | 用户头像                               |
| user_name     | varchar(32) NOT NULL  | 用户昵称                                |
| category_id | varchar(20) NOT NULL  | 分类ID                                |
| content     | mediumtext NOT NULL   | 内容                                  |
| type        | varchar(1) NOT NULL   | 类型(0表示富文本,1表示MD)             |
| state       | varchar(1) NOT NULL   | 状态(0表示已发布,1表示草稿,2表示删除) |
| summary     | text NOT NULL         | 摘要                                  |
| labels      | varchar(128) NOT NULL | 标签                                  |
| view_count  | int(11) NOT NULL      | 浏览量                                |
| create_time | datetime NOT NULL     | 发布时间                              |
| update_time | datetime NOT NULL     | 更新时间                              |

### 图片表	tb_images

| Filed       | Type                   | Comment                   |
| ----------- | ---------------------- | ------------------------- |
| id          | varchar(20) NOT NULL   | ID                        |
| user_id     | varchar(20) NOT NULL   | 用户ID                    |
| url         | varchar(1024) NOT NULL | 路径                      |
| state       | varchar(1) NOT NULL    | 状态(0表示删除,1表示正常) |
| create_time | datetime NOT NULL      | 创建时间                  |
| update_time | datetime NOT NULL      | 更新时间                  |



### 评论表	tb_comment

| Filed          | Type | Comment                   |
| -------------- | ---- | ------------------------- |
| id             | varchar(20) NOT NULL | ID                        |
| article_id     | varchar(20) NOT NULL | 文章ID                       |
| parent_content | text NULL | 被评论内容-子评论         |
| content        | text NOT NULL | 评论内容                  |
| user_id        | varchar(20) NOT NULL | 评论人用户ID              |
| user_avatar    | varchar(1024) NULL | 评论人头像                |
| user_name      | varchar(32) NULL | 评论人名称                |
| state          | varchar(1) NOT NULL | 状态(0表示删除,1表示正常) |
| create_time    | datetime NOT NULL | 创建时间                  |
| update_time    | datetime NOT NULL | 更新时间                  |

### 标签统计表	tb_labels

| Filed       | Type                 | Comment  |
| ----------- | -------------------- | -------- |
| id          | varchar(20) NOT NULL | ID       |
| name        | varchar(32) NOT NULL | 标签名称 |
| count       | int(11) NOT NULL     | 数量     |
| create_time | datetime NOT NULL    | 创建时间 |
| update_time | datetime NOT NULL    | 更新时间 |

### 轮播图表	tb_looper

| Filed       | Type                   | Comment                     |
| ----------- | ---------------------- | --------------------------- |
| id          | varchar(20) NOT NULL   | ID                          |
| title       | varchar(128) NOT NULL  | 轮播图标题                  |
| order       | int(11) NOT NULL       | 顺序                        |
| state       | varchar(1) NOT NULL    | 状态(0表示不可用,1表示正常) |
| target_url  | varchar(1024) NULL     | 目标链接                    |
| image_url   | varchar(2014) NOT NULL | 图片路径                    |
| create_time | datetime NOT NULL      | 创建时间                    |
| update_time | datetime NOT NULL      | 更新时间                    |

### 每天的访问量	tb_daily_view_count

| Filed       | Type                 | Comment  |
| ----------- | -------------------- | -------- |
| id          | varchar(20) NOT NULL | ID       |
| view_count  | int(11) NOT NULL     | 浏览量   |
| create_time | datetime NOT NULL    | 创建时间 |
| update_time | datetime NOT NULL    | 更新时间 |

### 友情链接表	tb_friends

| Filed       | Type                   | Comment                             |
| ----------- | ---------------------- | ----------------------------------- |
| id          | varchar(20) NOT NULL   | ID                                  |
| name        | varchar(64) NOT NULL   | 友情链接名称                        |
| logo        | varchar(1024) NOT NULL | 友情链接logo                        |
| url         | varchar(1024) NOT NULL | 友情链接                            |
| order       | int(11) NOT NULL       | 顺序                                |
| state       | varchar(1) NOT NULL    | 友情链接状态(0表示不可用,1表示正常) |
| create_time | datetime NOT NULL      | 创建时间                            |
| update_time | datetime NOT NULL      | 更新时间                            |

### 网站信息表	tb_settings

| Filed       | Type                  | Comment  |
| ----------- | --------------------- | -------- |
| id          | varchar(20) NOT NULL  | ID       |
| key         | varchar(32) NOT NULL  | 键       |
| value       | varchar(512) NOT NULL | 值       |
| create_time | datetime NOT NULL     | 创建时间 |
| update_time | datetime NOT NULL     | 更新时间 |

