package com.ps.webskitterassignment.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ps.webskitterassignment.R;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

public class MyDialog {

    static ProgressDialog progressDoalog;
    static LayoutInflater layoutInflater;

    public static void showOkCancelPopup(String message, String positive, String negative, Context ctx,
                                         final OkCancelPopupCallback obj) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(positive,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        obj.onPositiveClick();

                    }
                });

        alertDialog.setNegativeButton(negative,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = alertDialog.create();
        alert.show();
        alert.getWindow().getAttributes();

        TextView textView = (TextView) alert.findViewById(android.R.id.message);
        textView.setTextSize(18);
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#696969"));
        /*AlertDialog alert=alertDialog.create();
        TextView msgTxt = (TextView) alert.findViewById(android.R.id.message);
        msgTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, ctx.getResources().getDimension(R.dimen._50sdp));*/


    }

    public static void showOkPopupCallBack(String message, String positive, Context ctx,
                                           final OkCancelPopupCallback obj) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ctx);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton(positive,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        obj.onPositiveClick();

                    }
                });
        alertDialog.show();
        //alertDialog.get(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#00b5f1"));
        /*AlertDialog alert=alertDialog.create();
        TextView msgTxt = (TextView) alert.findViewById(android.R.id.message);
        msgTxt.setTextSize(TypedValue.COMPLEX_UNIT_PX, ctx.getResources().getDimension(R.dimen._50sdp));*/


    }

    public static void defaultShowErrorPopup(final Context ctx, String msg, String btnText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle("Error..");
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#00b5f1"));
    }

    public static void defaultShowInfoPopup(final Context ctx, String title, String msg, String btnText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(btnText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#00b5f1"));
    }

    public static void showProgressLoader(final Context ctx, String msg) {
        //progressDoalog = new ProgressDialog(ctx, R.style.MyProgressDialogTheme);
        progressDoalog = new ProgressDialog(ctx);
        progressDoalog.setMessage(msg);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Drawable drawable = new ProgressBar(ctx).getIndeterminateDrawable().mutate();
        drawable.setColorFilter(ContextCompat.getColor(ctx, R.color.design_default_color_on_primary),
                PorterDuff.Mode.SRC_IN);
        //progressDoalog.setIndeterminateDrawable(drawable);
        //progressDoalog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDoalog.show();
    }

    public static void dismissProgressLoader() {
        if (progressDoalog != null && progressDoalog.isShowing()) {
            progressDoalog.dismiss();
        }
    }

    public interface OkCancelPopupCallback {
        void onPositiveClick();

        void onNegativeClick();
    }

    public interface CustomEditTextOkCancelPopupCallback {
        void onDoneClick(String val, AlertDialog alertDialog);

        void onCancelClick(AlertDialog alertDialog);
    }
}
