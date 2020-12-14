package com.ps.webskitterassignment.fragment;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.ps.webskitterassignment.R;
import com.ps.webskitterassignment.activity.BaseActivity;
import com.ps.webskitterassignment.activity.LoginActivity;
import com.ps.webskitterassignment.activity.MainActivity;
import com.ps.webskitterassignment.databinding.FragmentLocationBinding;
import com.ps.webskitterassignment.databinding.FragmentProfileBinding;
import com.ps.webskitterassignment.network.APIClient;
import com.ps.webskitterassignment.network.APIInterface;
import com.ps.webskitterassignment.prefrences.AppPreferences;
import com.ps.webskitterassignment.response.login_pojo.LoginResponse;
import com.ps.webskitterassignment.utils.AppUtilities;
import com.ps.webskitterassignment.utils.MyValidation;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    public static final int PICK_IMAGE = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        binding.ivPicUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(getActivity())
                        .withPermissions(
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    pickImage();
                                }
                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // permission is denied permenantly, navigate user to app settings
                                    //finish();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        })
                        .onSameThread()
                        .check();
            }
        });

        binding.btnSubmitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppUtilities.isOnline(getActivity())) {
                    if (new MyValidation(getActivity()).validateLogin(binding.etUserNameProfile.getText().toString().trim(),
                            binding.etPasswordProfile.getText().toString())) {
                        callRegisterApi();
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.txtNetworkNotAvailable), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return binding.getRoot();
    }

    private void callRegisterApi() {
        ((BaseActivity) getActivity()).showProgressDialog(getActivity(), getResources().getString(R.string.txtLoading));
        HashMap<String, Object> logReq = new HashMap<>();
        logReq.put("email", binding.etUserNameProfile.getText().toString().trim());
        logReq.put("password", binding.etPasswordProfile.getText().toString());
        Call<JSONObject> response1 = APIClient.getClient().create(APIInterface.class).userRegister(logReq);
        response1.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                ((BaseActivity) getActivity()).hideProgressDialog();
                if (response.code() == 200) {
                    Toast.makeText(getActivity(), "Register Successfully", Toast.LENGTH_SHORT).show();
                    binding.etUserNameProfile.setText("");
                    binding.etPasswordProfile.setText("");
                    binding.ivPicUser.setImageResource(R.drawable.dummy_circle_profile);
                }else{
                    Toast.makeText(getActivity(), "Register not Successfully", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                ((BaseActivity) getActivity()).hideProgressDialog();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE) {
            //TODO: action
            Bitmap bitmap = null;
            try {
                if (data != null) {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    Glide.with(getActivity()).load(bitmap)
                            .placeholder(R.drawable.dummy_circle_profile).into(binding.ivPicUser);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //brandOnboardingAdapter.setImage(callBackPos, bitmap);
        }
    }

}
