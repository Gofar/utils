package com.lost.utils;

/**
 * @author lcf
 * @date 2018/5/25 9:44
 * @since 1.0
 */
public class SexMenuEntity {
    private String text;
    private int value;

    public SexMenuEntity(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
