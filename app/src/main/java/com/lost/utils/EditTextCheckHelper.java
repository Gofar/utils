package com.lost.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Author: lcf
 * Description: 监听多个EditText是否有内容
 * Since: 1.0
 * Date: 2017/6/23 14:01
 */
public class EditTextCheckHelper {
    private View mTarget;
    private EditText[] mEditTexts;
    private IEditTextChangeListener mListener;

    public EditTextCheckHelper(View target) {
        this.mTarget = target;
    }

    public void addEditText(EditText... editTexts) {
        this.mEditTexts = editTexts;
        initEditListener();
    }

    public void setEditTextChangeListener(IEditTextChangeListener listener) {
        this.mListener = listener;
    }

    private void initEditListener() {
        for (EditText editText : mEditTexts) {
            editText.addTextChangedListener(new TextChange());
        }
    }

    private boolean checkAllEdit() {
        for (EditText editText : mEditTexts) {
            if (TextUtils.isEmpty(editText.getText())) {
                return false;
            }
        }
        return true;
    }

    private class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (checkAllEdit()) {
                if (mTarget != null) {
                    mTarget.setEnabled(true);
                }
                if (mListener != null) {
                    mListener.onTextChange(true);
                }
            } else {
                if (mTarget != null) {
                    mTarget.setEnabled(false);
                }
                if (mListener != null) {
                    mListener.onTextChange(false);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public interface IEditTextChangeListener {
        void onTextChange(boolean enable);
    }

}
