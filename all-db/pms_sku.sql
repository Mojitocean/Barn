-- 商品库存表 (SKU)
CREATE TABLE `pms_sku`
(
    `id`        bigint(20) NOT NULL AUTO_INCREMENT,
    `spu_id`    bigint(20) DEFAULT NULL COMMENT '关联的SPU ID',
    `sku_code`  varchar(64)    DEFAULT NULL COMMENT 'sku编码',
    `price`     decimal(10, 2) DEFAULT NULL COMMENT '销售价格',
    `stock`     int(11) DEFAULT '0' COMMENT '库存',
    `spu_name`  varchar(200)   DEFAULT NULL COMMENT '商品名称快照',
    `spec_json` varchar(500)   DEFAULT NULL COMMENT '规格(JSON格式): {"color":"红", "size":"XL"}',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品库存单元表';