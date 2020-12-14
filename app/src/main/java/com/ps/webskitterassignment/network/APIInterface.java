package com.ps.webskitterassignment.network;
import com.ps.webskitterassignment.response.login_pojo.LoginResponse;
import com.ps.webskitterassignment.response.user_list_pojo.UserListResponse;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {
    @POST(Urls.URL_LOGIN)
    @Headers({"Content-Type: application/json"})
    Call<LoginResponse> userLogin(@Body Map<String, Object> data);

    @POST(Urls.URL_REGISTER)
    @Headers({"Content-Type: application/json"})
    Call<JSONObject> userRegister(@Body Map<String, Object> data);

    @GET(Urls.URL_USER_LIST)
    @Headers({"Content-Type: application/json"})
    Call<UserListResponse> userList();
}
