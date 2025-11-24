package com.barn.user.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * packageName com.barn.mapper.domain.entity
 *
 * @author mj
 * @className User
 * @date 2025/11/24
 * @description TODO
 */
@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String nickname;
    private String mobile;
    private String avatar;
    private String email;
    private Integer status;
    private Long memberLevelId;
    private LocalDateTime createTime;
    private LocalDateTime loginTime;
}