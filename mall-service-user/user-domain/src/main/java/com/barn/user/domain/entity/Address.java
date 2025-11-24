package com.barn.user.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName com.barn.mapper.domain.entity
 *
 * @author mj
 * @className Address
 * @date 2025/11/24
 * @description TODO
 */
@Getter
@Setter
@NoArgsConstructor
public class Address {
    private Long id;
    private Long userId;
    private String name;
    private String phoneNumber;
    private Integer defaultStatus;
    private String province;
    private String city;
    private String region;
    private String detailAddress;
}