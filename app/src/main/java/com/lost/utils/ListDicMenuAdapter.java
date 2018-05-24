package com.lost.utils;

import com.lost.utils.bottom.MenuAdapter;

import java.util.List;

/**
 * @author lcf
 * @date 2018/5/24 14:47
 * @since 1.0
 */
public class ListDicMenuAdapter extends MenuAdapter<ListDicMenuEntity> {
    public ListDicMenuAdapter(List<ListDicMenuEntity> data) {
        super(data);
    }

    @Override
    public String convert(ListDicMenuEntity listMenuEntity) {
        return listMenuEntity.t.getText();
    }
}
