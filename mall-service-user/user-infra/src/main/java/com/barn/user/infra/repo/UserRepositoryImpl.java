package com.barn.user.infra.repo;

import com.barn.user.domain.entity.Address;
import com.barn.user.domain.entity.User;
import com.barn.user.domain.repo.UserRepository;
import com.barn.user.infra.mapper.AddressMapper;
import com.barn.user.infra.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * packageName com.barn.mapper.infra.repo
 *
 * @author mj
 * @className UserRepositoryImpl
 * @date 2025/11/24
 * @description TODO
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public User findById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public Address findAddressById(Long addressId) {
        return addressMapper.selectById(addressId);
    }
}