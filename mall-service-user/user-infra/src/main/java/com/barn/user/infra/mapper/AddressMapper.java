package com.barn.user.infra.mapper;

import com.barn.user.domain.entity.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName com.barn.mapper.infra.mapper
 *
 * @author mj
 * @className AddressMapper
 * @date 2025/11/24
 * @description TODO
 */
@Mapper
public interface AddressMapper {
    Address selectById(@Param("id") Long id);
}