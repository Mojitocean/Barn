package com.barn.core.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * packageName com.barn.core.domain
 *
 * @author mj
 * @className PageEntity
 * @date 2025/5/31
 * @description TODO
 */
@Data
@JsonFilter("pageEntityFilter")
@EqualsAndHashCode(callSuper = true)
public abstract class PageEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 分页参数
     * 页码不能为空
     * 页码不能小于0
     */
    private Integer pageNum = 1;
    /**
     * 分页参数
     * 条数不能为空
     * 分页最小为1
     */
    private Integer pageSize = 10;
}