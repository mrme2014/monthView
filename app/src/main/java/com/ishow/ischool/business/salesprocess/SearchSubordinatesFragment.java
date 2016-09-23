package com.ishow.ischool.business.salesprocess;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.commonlib.core.BaseFragment4mvp;
import com.commonlib.widget.pull.BaseListAdapter;
import com.commonlib.widget.pull.BaseViewHolder;
import com.commonlib.widget.pull.DividerItemDecoration;
import com.commonlib.widget.pull.PullRecycler;
import com.commonlib.widget.pull.layoutmanager.MyLinearLayoutManager;
import com.ishow.ischool.R;
import com.ishow.ischool.bean.saleprocess.Subordinate;
import com.ishow.ischool.bean.saleprocess.SubordinateObject;
import com.ishow.ischool.widget.custom.AvatarImageView;
import com.ishow.ischool.widget.custom.CircleTransform;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MrS on 2016/9/23.
 */

public class SearchSubordinatesFragment extends BaseFragment4mvp<SalesProcessPresenter,SalesProcessModel> implements SalesProcessContract.View<Subordinate>, PullRecycler.OnRecyclerRefreshListener {

    public static SearchSubordinatesFragment newInstance(int campus_id,int position_id, String keywords){
        SearchSubordinatesFragment fragment = new SearchSubordinatesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("campus_id",campus_id);
        bundle.putString("keywords",keywords);
        bundle.putInt("position_id",position_id);
        fragment.setArguments(bundle);
        return fragment;
    }

    PullRecycler pullRecycler;
    Adapter adapter;
    int campus_id;
    String keywords;
    int position_id;

    ArrayList<SubordinateObject> lists;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        pullRecycler = new PullRecycler(getContext());
        pullRecycler.setId(R.id.recycleview);
        pullRecycler.setOnRefreshListener(this);
        pullRecycler.setLayoutManager(new MyLinearLayoutManager(getContext().getApplicationContext()));
        pullRecycler.addItemDecoration(new DividerItemDecoration(getContext().getApplicationContext(), com.commonlib.R.drawable.widget_list_divider));
        pullRecycler.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT));
        adapter = new Adapter();
        pullRecycler.setAdapter(adapter);

        Bundle arguments = getArguments();
        if (arguments!=null){
            campus_id = arguments.getInt("campus_id");
            keywords = arguments.getString("keywords");
            position_id = arguments.getInt("position_id");
        }
        return pullRecycler;
    }

    @Override
    public void init() {
        pullRecycler.setRefreshing();
        mPresenter.getOptionSubordinateKeyWords("Subordinate",campus_id,position_id ,keywords);
    }
    @Override
    public void getListSuccess(Subordinate subordinate) {
        if (subordinate!=null)
            lists = subordinate.Subordinate;
        pullRecycler.onRefreshCompleted();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getListFail(String msg) {
        pullRecycler.onRefreshCompleted();
        pullRecycler.showEmptyView();
    }

    @Override
    public void onRefresh(int action) {

    }

    private class Adapter extends BaseListAdapter {

        @Override
        protected int getDataCount() {
            return lists==null?0:lists.size();
        }

        @Override
        protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_pick_referrer, parent, false);
            return new ViewHolder(view);
        }
    }

    public class ViewHolder extends BaseViewHolder{
        @BindView(R.id.avatar_text)
        AvatarImageView avatarTv;
        @BindView(R.id.avatar_iv)
        ImageView avatarIv;
        @BindView(R.id.referrer_name)
        TextView referrerName;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onBindViewHolder(final int position) {
            SubordinateObject data = lists.get(position);
             if (data.file_name != null && !TextUtils.isEmpty(data.file_name)) {
                avatarTv.setVisibility(View.GONE);
                avatarIv.setVisibility(View.VISIBLE);
                Glide.with(getContext().getApplicationContext()).load(data.user_name).fitCenter().placeholder(R.mipmap.img_header_default)
                        .transform(new CircleTransform(getContext().getApplicationContext())).into(new ImageViewTarget<GlideDrawable>(avatarIv) {
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        avatarIv.setImageDrawable(resource);
                    }
                    @Override
                    public void setRequest(Request request) {
                        avatarIv.setTag(position);
                        avatarIv.setTag(R.id.glide_tag_id,request);
                    }

                    @Override
                    public Request getRequest() {
                        return (Request) avatarIv.getTag(R.id.glide_tag_id);
                    }
                });
            } else {
                avatarIv.setVisibility(View.GONE);
                avatarTv.setVisibility(View.VISIBLE);
                avatarTv.setText(data.user_name, data.id, data.file_name);
            }
            referrerName.setText(data.user_name);
        }

        @Override
        public void onItemClick(View view, int position) {
            super.onItemClick(view, position);
            Toast.makeText(getContext(),position+"",Toast.LENGTH_SHORT).show();
        }
    }
}
