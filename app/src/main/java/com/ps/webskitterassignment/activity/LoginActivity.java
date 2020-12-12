package com.ps.webskitterassignment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ps.webskitterassignment.R;
import com.ps.webskitterassignment.databinding.ActivityLoginBinding;
import com.ps.webskitterassignment.network.APIClient;
import com.ps.webskitterassignment.network.APIInterface;
import com.ps.webskitterassignment.prefrences.AppPreferences;
import com.ps.webskitterassignment.response.login_pojo.LoginResponse;
import com.ps.webskitterassignment.utils.AppUtilities;
import com.ps.webskitterassignment.utils.MyValidation;

import org.json.JSONObject;

import java.util.HashMap;

import androidx.databinding.DataBindingUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginBinding.setClickHandler(new LoginClickHandler(this));
    }

    public class LoginClickHandler {
        Context context;

        public LoginClickHandler(Context context) {
            this.context = context;
        }

        public void onLoginClicked(View view) {
            if (AppUtilities.isOnline(LoginActivity.this)) {
                if (new MyValidation(context).validateLogin(loginBinding.etUserNameLogin.getText().toString().trim(),
                        loginBinding.etPasswordLogin.getText().toString())) {
                    callLoginApi();
                }
            } else {
                Toast.makeText(LoginActivity.this, getResources().getString(R.string.txtNetworkNotAvailable), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callLoginApi() {
        /*eve.holt@reqres.in
        cityslicka*/
        showProgressDialog(LoginActivity.this, getResources().getString(R.string.txtLoading));
        HashMap<String, Object> logReq = new HashMap<>();
        logReq.put("username", loginBinding.etUserNameLogin.getText().toString().trim());
        logReq.put("password", loginBinding.etPasswordLogin.getText().toString());
        Call<LoginResponse> response1 = APIClient.getClient().create(APIInterface.class).userLogin(logReq);
        response1.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                hideProgressDialog();
                if (response.code() == 200) {
                    AppPreferences.getInstance().storeUserToken(response.body().getToken());
                    Intent i=new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                }

            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
