package com.hlkt123.uplus.util;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 文字输入框的文字改变监听器
 * @author Dkalan
 * @date 2014-10-16
 *
 */
public class TextWatcherWazert implements TextWatcher {
    private CharSequence temp;
    private int editStart ;
    private int editEnd ;
    
    private EditText mEditText;
    private Context mCnt;
    private String  mWarning;
    private int mMaxLength;
    /**
     * 
     * @param _mCnt      上下文环境
     * @param _editText  输入框 
     * @param _warning   超出范围后的提醒文字
     * @param _textLength 文字输入的最大长度
     */
    public TextWatcherWazert(Context _mCnt,EditText _editText,String _warning,int _textLength)
    {
    	mEditText=_editText;
    	mCnt=_mCnt;
    	mWarning=_warning;
    	mMaxLength=_textLength;
    }
    
    @Override
    public void beforeTextChanged(CharSequence s, int arg1, int arg2,
            int arg3) {
        temp = s;
    }
   
    @Override
    public void onTextChanged(CharSequence s, int arg1, int arg2,
            int arg3) {
        
    }
   
    @Override
    public void afterTextChanged(Editable s) {
        editStart = mEditText.getSelectionStart();
        editEnd = mEditText.getSelectionEnd();
        if (temp.length() >mMaxLength) {
            Toast.makeText(mCnt,
            		mWarning, Toast.LENGTH_SHORT)
                    .show();
            
            s.delete(editStart-1, editEnd);
            int tempSelection = editStart;
            mEditText.setText(s);
            mEditText.setSelection(tempSelection);
        }
    }

	
};
