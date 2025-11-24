-- 收货地址表
CREATE TABLE `ums_address`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id`        bigint(20)   DEFAULT NULL COMMENT '用户ID',
    `name`           varchar(64)  DEFAULT NULL COMMENT '收货人名称',
    `phone_number`   varchar(64)  DEFAULT NULL COMMENT '收货人电话',
    `default_status` int(1)       DEFAULT NULL COMMENT '是否为默认',
    `province`       varchar(64)  DEFAULT NULL COMMENT '省份/直辖市',
    `city`           varchar(64)  DEFAULT NULL COMMENT '城市',
    `region`         varchar(64)  DEFAULT NULL COMMENT '区',
    `detail_address` varchar(128) DEFAULT NULL COMMENT '详细地址(街道)',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='会员收货地址表';