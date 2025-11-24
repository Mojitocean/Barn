-- 用户表
CREATE TABLE `ums_user`
(
    `id`              bigint(20) NOT NULL AUTO_INCREMENT,
    `username`        varchar(64)  DEFAULT NULL COMMENT '用户名',
    `password`        varchar(64)  DEFAULT NULL COMMENT '密码',
    `nickname`        varchar(64)  DEFAULT NULL COMMENT '昵称',
    `mobile`          varchar(20)  DEFAULT NULL COMMENT '手机号码',
    `email`           varchar(100) DEFAULT NULL COMMENT '邮箱',
    `avatar`          varchar(500) DEFAULT NULL COMMENT '头像',
    `status`          int(1)       DEFAULT '1' COMMENT '帐号启用状态:0->禁用；1->启用',
    `create_time`     datetime     DEFAULT NULL COMMENT '注册时间',
    `login_time`      datetime     DEFAULT NULL COMMENT '最后登录时间',
    `member_level_id` bigint(20)   DEFAULT NULL COMMENT '会员等级ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_username` (`username`),
    UNIQUE KEY `idx_mobile` (`mobile`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='会员表';