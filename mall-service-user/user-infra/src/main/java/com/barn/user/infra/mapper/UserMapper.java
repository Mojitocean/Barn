package com.barn.user.infra.mapper;

import com.barn.user.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * packageName com.barn.mapper.infra.mapper
 *
 * @author mj
 * @className UserMapper
 * @date 2025/11/24
 * @description TODO
 */
@Mapper
public interface UserMapper {
    User selectById(@Param("id") Long id);
}