package com.barn.api.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * packageName com.barn.api.mapper.dto
 * 用户信息 DTO
 *
 * @author mj
 * @className UserDTO
 * @date 2025/11/24
 * @description TODO
 */
@Data
public class UserDTO implements Serializable {
    private Long id;
    private String username;
    private String nickname;
    private String mobile;
    private String avatar;
    private String email;
    /**
     * 状态 1:启用 0:禁用
     */
    private Integer status;
    /**
     * 会员等级ID
     */
    private Long memberLevelId;
}