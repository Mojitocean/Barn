package com.barn.api.user.service;

import com.barn.api.user.dto.AddressDTO;
import com.barn.api.user.dto.UserDTO;
import com.barn.core.domain.R;

/**
 * packageName com.barn.api.mapper.service
 * 用户服务 Dubbo 接口
 *
 * @author mj
 * @className UserServiceApi
 * @date 2025/11/24
 * @description TODO
 */
public interface UserServiceApi {

    /**
     * 根据ID获取用户信息
     * 用于：下单前校验用户状态、获取用户等级计算折扣
     */
    R<UserDTO> getUserById(Long userId);

    /**
     * 根据ID获取收货地址详情
     * 用于：下单时根据 addressId 获取完整地址信息，存入订单表作为快照
     */
    R<AddressDTO> getAddressById(Long addressId);
}