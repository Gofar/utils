package com.lost.utils;

import com.lost.utils.bottom.MenuAdapter;

import java.util.List;

/**
 * @author lcf
 * @date 2018/5/24 16:02
 * @since 1.0
 */
public class StringMenuAdapter extends MenuAdapter<String>{
    public StringMenuAdapter(List<String> data) {
        super(data);
    }

    @Override
    public String convert(String s) {
        return s;
    }
}
