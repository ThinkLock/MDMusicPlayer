package com.yang.mdmusicplayer.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import com.yang.mdmusicplayer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * MD 对话框帮助库
 */
public class DialogUtil {
    /**
     * 进度条
     */
    static ProgressDialog progressDialog;

    public static void getAlertDialog(Context context, String title, String msg,
                                      DialogInterface.OnClickListener posttiveButton,
                                      String postiveText,
                                      DialogInterface.OnClickListener negativeButton,
                                      String negataveText,
                                      Boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(msg).setPositiveButton(postiveText, posttiveButton)
                .setNegativeButton(negataveText, negativeButton).setCancelable(cancelable).create().show();

    }

    public static void getAlertDialog(Context context, int title, int msg,
                                      DialogInterface.OnClickListener posttiveButton,
                                      int postiveText,
                                      DialogInterface.OnClickListener negativeButton,
                                      int negataveText,
                                      Boolean cancelable) {
        getAlertDialog(context, title > 0 ? context.getString(title) : "",
                msg > 0 ? context.getString(msg) : "",
                posttiveButton,
                postiveText > 0 ? context.getString(postiveText) : "",
                negativeButton, negataveText > 0 ? context.getString(negataveText) : "",
                cancelable);
    }


    /**
     * 输入对话框
     *
     * @param context        上下文
     * @param title
     * @param editText
     * @param postiveButton
     * @param postiveText
     * @param negativeButton
     * @param negativeText
     */
    public static void getEditDialog(Context context, String title, EditText editText,
                                     DialogInterface.OnClickListener postiveButton,
                                     String postiveText,
                                     DialogInterface.OnClickListener negativeButton,
                                     String negativeText) {
        int padding = context.getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin);

        editText.setPadding(padding, 0, padding, 0);
        new AlertDialog.Builder(context).setTitle(title).
                setView(editText).
                setPositiveButton(postiveText, postiveButton).
                setNegativeButton(negativeText, negativeButton).
                show();
    }

    /**
     * 列表对话框
     *
     * @param context
     * @param title
     * @param list
     * @param itemClickListener
     * @param postiveButton
     * @param postiveText
     */
    public static void getListDialog(Context context, String title, String[] list,
                                     DialogInterface.OnClickListener itemClickListener,
                                     DialogInterface.OnClickListener postiveButton,
                                     String postiveText,
                                     DialogInterface.OnClickListener negativeButton,
                                     String negativeText) {
        new AlertDialog.Builder(context).setTitle(title).setItems(list, itemClickListener).
                setPositiveButton(postiveText, postiveButton).
                setNegativeButton(negativeText, negativeButton).
                show();
    }

    public static void getListDialog(Context context, String title, String[] list,
                                     DialogInterface.OnClickListener itemClickListener) {
        new AlertDialog.Builder(context).setTitle(title).setItems(list, itemClickListener).
                show();
    }

//    public static void getListDialog(Context context, String[] titles, int[] icons,
//                                     DialogInterface.OnClickListener itemClickListener) {
//
//        if (titles.length != icons.length){
//            throw new IllegalArgumentException("title 和 icon 的长度不一致");
//        }
//
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (int i = 0; i < titles.length; i++) {
//            Map<String,Object> map=new HashMap<>();
//            map.put("image", icons[i]);
//            map.put("title", titles[i]);
//            list.add(map);
//        }
//        final SimpleAdapter adapter = new SimpleAdapter(context, list,
//                R.layout.list_dialog_item, new String[]{"title", "image"}, new int[]{R.id.tv_title, R.id.iv_icon});
//
//        new AlertDialog.Builder(context).setAdapter(adapter, itemClickListener).
//                show();
//    }


    public static void getSingleChoiceDialog(Context context, String title, String[] list,
                                             DialogInterface.OnClickListener itemClickListener,
                                             DialogInterface.OnClickListener postiveButton,
                                             String postiveText,
                                             DialogInterface.OnClickListener negativeButton,
                                             String negativeText) {
        new AlertDialog.Builder(context).setSingleChoiceItems(list, 0, itemClickListener).
                setPositiveButton(postiveText, postiveButton).
                setNegativeButton(negativeText, negativeButton).
                create().show();

    }

    public static void getListDialog(Context context, int title, int list,
                                     DialogInterface.OnClickListener itemClickListener
    ) {
        new AlertDialog.Builder(context).setTitle(context.getString(title)).
                setItems(context.getResources().getStringArray(list), itemClickListener).

                show();
    }


    /**
     * 进度条对话框
     *
     * @param context 上下文
     * @param msg     msg
     * @return
     */
    public static ProgressDialog showProgressDialog(Context context, String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.show();
        return progressDialog;
    }

    public static ProgressDialog showProgressDialog(Context context, int msgId) {
        return showProgressDialog(context, context.getString(msgId));
    }

    public static void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


}
