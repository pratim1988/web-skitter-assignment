package com.ps.webskitterassignment.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.ps.webskitterassignment.R;

public class MyValidation {

    Context context;

    public MyValidation(Context context) {
        this.context = context;
    }

    public boolean validateLogin(String userName, String pwd) {
        Boolean is_error = false;
        if (userName.toString().trim().length() == 0) {
            is_error = true;
            Toast.makeText(context,context.getResources().getString(R.string.txtEmailId)+" "+
                    context.getResources().getString(R.string.txtCantEmpty), Toast.LENGTH_SHORT).show();
        }else if(!isValidEmail(userName)){
            is_error = true;
            Toast.makeText(context,context.getResources().getString(R.string.txtEmailId)+" "+
                    "Not valid",Toast.LENGTH_SHORT).show();
        }else if (pwd.toString().trim().length() == 0) {
            is_error = true;
            Toast.makeText(context,context.getResources().getString(R.string.txtPassword)+" "+
                    context.getResources().getString(R.string.txtCantEmpty), Toast.LENGTH_SHORT).show();
        }
        if (!is_error) {
            return true;
        } else {
            return false;
        }
    }

    Boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
        }
    }
}
