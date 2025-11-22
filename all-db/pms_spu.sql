-- 商品主表 (SPU)
CREATE TABLE `pms_spu`
(
    `id`             bigint(20) NOT NULL AUTO_INCREMENT,
    `spu_name`       varchar(200)  DEFAULT NULL COMMENT '商品名称',
    `spu_desc`       varchar(1000) DEFAULT NULL COMMENT '商品描述',
    `category_id`    bigint(20) DEFAULT NULL COMMENT '分类ID',
    `brand_id`       bigint(20) DEFAULT NULL COMMENT '品牌ID',
    `publish_status` int(1) DEFAULT '0' COMMENT '上架状态：0->下架；1->上架',
    `create_time`    datetime      DEFAULT NULL,
    `update_time`    datetime      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品信息表';