package com.barn.api.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * packageName com.barn.api.mapper.dto
 * 收货地址 DTO
 * 订单服务需要获取详细地址信息进行快照存储
 *
 * @author mj
 * @className AddressDTO
 * @date 2025/11/24
 * @description TODO
 */
@Data
public class AddressDTO implements Serializable {
    private Long id;
    private Long userId;
    /**
     * 收货人姓名
     */
    private String name;
    /**
     * 收货人电话
     */
    private String phoneNumber;
    /**
     * 是否默认地址 0-否 1-是
     */
    private Integer defaultStatus;

    /**
     * 省份/直辖市
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 区/县
     */
    private String region;
    /**
     * 详细地址(街道门牌)
     */
    private String detailAddress;
}