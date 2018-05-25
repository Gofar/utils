package com.lost.utils;

import com.lost.utils.bottom.MenuAdapter;

import java.util.List;

/**
 * @author lcf
 * @date 2018/5/25 10:40
 * @since 1.0
 */
public class SexMenuAdpater extends MenuAdapter<SexMenuEntity>{
    public SexMenuAdpater(List<SexMenuEntity> data) {
        super(data);
    }

    @Override
    public String convert(SexMenuEntity sexMenuEntity) {
        return sexMenuEntity.getText();
    }
}
