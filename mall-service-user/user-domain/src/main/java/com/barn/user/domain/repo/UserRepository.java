package com.barn.user.domain.repo;

import com.barn.user.domain.entity.Address;
import com.barn.user.domain.entity.User;

/**
 * packageName com.barn.mapper.domain.repo
 *
 * @author mj
 * @className UserRepository
 * @date 2025/11/24
 * @description TODO
 */
public interface UserRepository {

    /**
     * 根据ID查找用户
     */
    User findById(Long id);

    /**
     * 根据ID查找地址
     */
    Address findAddressById(Long addressId);
}