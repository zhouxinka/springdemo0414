package com.example.entity;

import java.io.Serializable;

/**
 * @author zhou.peng
 * @desc todo
 * @date 2022 08 03 11:44
 */
public abstract class BaseEntity<T> implements Serializable {
    /**
     * 当前实体分页对象
     */
    protected Page<T> page;
    public Page<T> setPage(Page<T> page) {
        this.page = page;
        return page;
    }
}
