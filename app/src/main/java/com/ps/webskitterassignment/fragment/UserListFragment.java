package com.ps.webskitterassignment.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.ps.webskitterassignment.R;
import com.ps.webskitterassignment.activity.BaseActivity;
import com.ps.webskitterassignment.adapter.UserListAdapter;
import com.ps.webskitterassignment.databinding.FragmentListBinding;
import com.ps.webskitterassignment.network.APIClient;
import com.ps.webskitterassignment.network.APIInterface;
import com.ps.webskitterassignment.response.user_list_pojo.UserItem;
import com.ps.webskitterassignment.response.user_list_pojo.UserListResponse;
import com.ps.webskitterassignment.utils.AppUtilities;
import com.ps.webskitterassignment.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListFragment extends Fragment {
    Boolean isLoading=false;
    private int start_index=1;
    FragmentListBinding binding;
    List<UserItem> userList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false);
        binding.rvList.setVisibility(View.GONE);
        binding.rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && !isLoading) {
                    if (binding.rvList.getLayoutManager() != null && ((LinearLayoutManager) binding.rvList.getLayoutManager()).findLastCompletelyVisibleItemPosition() == userList.size() - 1) {
                        //bottom of list!
                        isLoading = true;
                        onLoadNextPageStock();
                    }
                }
            }
        });
        if (AppUtilities.isOnline(getActivity())) {
            callUserList(false);
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.txtNetworkNotAvailable), Toast.LENGTH_SHORT).show();
        }
        return binding.getRoot();
    }

    private void onLoadNextPageStock() {
        start_index += 1;
        if (AppUtilities.isOnline(getActivity())) {
            callUserList(true);
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.txtNetworkNotAvailable), Toast.LENGTH_SHORT).show();
        }
    }

    private void callUserList(Boolean isLoadMore) {
        ((BaseActivity) getActivity()).showProgressDialog(getActivity(), getResources().getString(R.string.txtLoading));

        Call<UserListResponse> response1 = APIClient.getClient().create(APIInterface.class).userList();
        response1.enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                ((BaseActivity) getActivity()).hideProgressDialog();
                if (response.code() == 200) {
                    try {
                            //Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            userList.addAll(response.body().getData());
                            if (userList.size() > 0) {
                                if (isLoadMore) {
                                    binding.rvList.getAdapter().notifyItemChanged(userList.size());
                                    isLoading = false;
                                } else {
                                    setupRecycler();
                                }
                                binding.tvNoValueList.setVisibility(View.GONE);
                                binding.rvList.setVisibility(View.VISIBLE);

                            } else {
                                binding.tvNoValueList.setVisibility(View.VISIBLE);
                                binding.rvList.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                Log.e("callSearchStock", "onFailure" + t.getLocalizedMessage());
            }
        });
    }

    private void setupRecycler() {
        binding.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvList.setNestedScrollingEnabled(false);
        binding.rvList.setAdapter(new UserListAdapter(getActivity(), userList, new UserListAdapter.UserListClickListener() {
            @Override
            public void itemListClick(int position) {
                openDetailDialog(userList.get(position));

            }
        }));
        binding.rvList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        isLoading = false;
    }

    private void openDetailDialog(UserItem userItem) {
        final Dialog dialogEdit = new Dialog(getActivity());
        dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogEdit.setCancelable(false);
        dialogEdit.setContentView(R.layout.dialog_user_detail);
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.4);
        dialogEdit.getWindow().setLayout(width, height);
        dialogEdit.show();

        LinearLayout llDialogClose = dialogEdit.findViewById(R.id.llDialogClose);
        TextView tvName = dialogEdit.findViewById(R.id.tvName);
        TextView tvEmail = dialogEdit.findViewById(R.id.tvEmail);
        CircleImageView ivPicUser = dialogEdit.findViewById(R.id.ivPicUser);

        tvName.setText(userItem.getFirstName()+" "+userItem.getLastName());
        tvEmail.setText(userItem.getEmail());
        Glide.with(getActivity()).load(userItem.getAvatar())
                .placeholder(R.drawable.dummy_circle_profile).into(ivPicUser);

        llDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEdit.dismiss();
            }
        });
    }

}
