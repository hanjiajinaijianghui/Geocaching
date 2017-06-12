package com.zhuoxin.hjj.geocaching.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.zhuoxin.hjj.geocaching.R;

/**
 * Created by Administrator on 2017/6/7.
 */

public class AlertDialogFragment extends DialogFragment{


    private static final String KEY_TITLE = "key_title";
    private static final String KEY_MESSAGE = "key_message";

    /**
     * 展示的对话框视图：官方推荐的两种方式
     * 1.onCreateView()：返回一个对话框的视图，采用搭建一个Layout布局作为展示的对话框
     * 2.onCreateDialog()：直接创建AlterDialog,用show()方法显示
     */

    //需要传递数据，对外提供一个创建方法：在里面通过setArguments()方法
    public static AlertDialogFragment getInstances(String title,String message){
        AlertDialogFragment dialogFragment = new AlertDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE,title);
        bundle.putString(KEY_MESSAGE,message);

        //官方推荐传递数据方法
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //拿到传递的数据
        String title = getArguments().getString(KEY_TITLE);
        String message = getArguments().getString(KEY_MESSAGE);

        return new AlertDialog.Builder(getActivity())
                .setTitle(title)//标题
                .setMessage(message)//信息
                .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();//消失
                    }
                })
                .create();
    }
}
