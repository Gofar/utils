package com.lost.utils;

import com.lost.utils.bottom.MenuAdapter;

import java.util.List;

/**
 * @author lcf
 * @date 2018/5/24 15:56
 * @since 1.0
 */
public class DicMenuAdapter extends MenuAdapter<Dic>{
    public DicMenuAdapter(List<Dic> data) {
        super(data);
    }

    @Override
    public String convert(Dic dic) {
        return dic.getText();
    }
}
