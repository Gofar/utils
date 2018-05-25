package com.lost.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.lost.utils.bottom.BottomListDialog;
import com.lost.utils.bottom.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcf
 * @date 2018/5/24 15:47
 * @since 1.0
 */
public class MainActivity extends FragmentActivity {

    private DicMenuAdapter mAdapter1;
    private BottomListDialog mDialog1;

    private StringMenuAdapter mAdapter2;
    private BottomListDialog mDialog2;

    private TextView mTvSex;
    private SexMenuAdpater mAdpater3;
    private BottomListDialog mDialog3;
    private int mSex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog1.show(getSupportFragmentManager(), "dic menu");
            }
        });

        findViewById(R.id.btn_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog2.show(getSupportFragmentManager(), "string menu");
            }
        });

        findViewById(R.id.btn_sex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog3.show(getSupportFragmentManager(), "sex menu");
            }
        });
        mTvSex = findViewById(R.id.tv_sex);

        mAdapter1 = new DicMenuAdapter(createDicList());
        mDialog1 = new BottomListDialog();
        mDialog1.setAdapter(mAdapter1);
        mDialog1.setOnMenuItemClickListener(new BottomListDialog.OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuAdapter adapter, int position) {
                Dic dic = mAdapter1.getData().get(position);
                ToastUtils.showShort(MainActivity.this, "menu:" + dic.getText());
            }
        });

        mAdapter2 = new StringMenuAdapter(createStrList());
        mDialog2 = new BottomListDialog();
        mDialog2.setAdapter(mAdapter2);
        mDialog2.setOnMenuItemClickListener(new BottomListDialog.OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuAdapter adapter, int position) {
                String s = mAdapter2.getData().get(position);
                ToastUtils.showShort(MainActivity.this, "menu:" + s);
            }
        });

        mAdpater3 = new SexMenuAdpater(createSexList());
        mDialog3 = new BottomListDialog();
        mDialog3.setAdapter(mAdpater3);
        mDialog3.setOnMenuItemClickListener(new BottomListDialog.OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuAdapter adapter, int position) {
                SexMenuEntity entity = mAdpater3.getData().get(position);
                mTvSex.setText(entity.getText());
                mSex = entity.getValue();
            }
        });
    }

    private List<Dic> createDicList() {
        List<Dic> dicList = new ArrayList<>();
        Dic dic1 = new Dic();
        dic1.setId(1);
        dic1.setText("菜单1");
        Dic dic2 = new Dic();
        dic2.setId(2);
        dic2.setText("菜单2");
        Dic dic3 = new Dic();
        dic3.setId(3);
        dic3.setText("菜单3");
        Dic dic4 = new Dic();
        dic4.setId(4);
        dic4.setText("菜单4");
        Dic dic5 = new Dic();
        dic5.setId(5);
        dic5.setText("菜单5");
        dicList.add(dic1);
        dicList.add(dic2);
        dicList.add(dic3);
        dicList.add(dic4);
        dicList.add(dic5);
        return dicList;
    }

    private List<String> createStrList() {
        List<String> stringList = new ArrayList<>();
        stringList.add("menu1");
        stringList.add("menu2");
        stringList.add("menu3");
        stringList.add("menu4");
        stringList.add("menu5");
        stringList.add("menu1");
        stringList.add("menu2");
        stringList.add("menu3");
        stringList.add("menu4");
        stringList.add("menu5");
        return stringList;
    }

    private List<SexMenuEntity> createSexList() {
        List<SexMenuEntity> sexMenuEntityList = new ArrayList<>();
        sexMenuEntityList.add(new SexMenuEntity("男", 0));
        sexMenuEntityList.add(new SexMenuEntity("女", 1));
        return sexMenuEntityList;
    }
}
