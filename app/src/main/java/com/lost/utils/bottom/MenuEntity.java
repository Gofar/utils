package com.lost.utils.bottom;

import java.io.Serializable;

/**
 * @author lcf
 * @date 2018/5/24 14:29
 * @since 1.0
 */
public class MenuEntity<T> implements Serializable {
    public T t;
    public String text;

    public MenuEntity(T t) {
        this.t = t;
        this.text = null;
    }

    public MenuEntity(String text) {
        this.text = text;
        this.t = null;
    }
}
