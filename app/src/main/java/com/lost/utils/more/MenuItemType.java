package com.lost.utils.more;

import com.lost.utils.R;

/**
 * @author lcf
 * @date 2018/1/9 16:25
 * @since 1.0
 */
public enum MenuItemType {
    TRUE(1, R.string.app_name),;

    int type;
    int value;

    MenuItemType(int type, int value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
