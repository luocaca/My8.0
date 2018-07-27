package com.example.administrator.my80.mvp.ui.main.child;

import android.Manifest;
import android.support.design.widget.FloatingActionButton;
import android.view.MenuItem;
import android.view.View;

import com.blankj.aloglibrary.ALog;
import com.example.administrator.my80.R;
import com.example.administrator.my80.fragment.BaseLazyFragment;
import com.example.administrator.my80.mvp.m.api.Api;
import com.example.administrator.my80.mvp.m.entity.base.BaseJson;
import com.example.administrator.my80.mvp.m.entity.mountaineering.Mountaineering;
import com.example.administrator.my80.mvp.ui.publish.PublishActivity;
import com.example.art.utils.UiUtils;
import com.github.rubensousa.floatingtoolbar.FloatingToolbar;
import com.luoxx.xxlib.weidet.BaseQuickAdapter;
import com.luoxx.xxlib.weidet.BaseViewHolder;
import com.luoxx.xxlib.weidet.CoreRecyclerView;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;

import static com.example.administrator.my80.mvp.ui.main.child.FragmentHome.getClient;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class FragmentShop extends BaseLazyFragment implements View.OnClickListener {

    private List<String> mData = new ArrayList<>();

    @BindView(R.id.recyclerView)
    CoreRecyclerView coreRecyclerView;
//    @BindView(R.id.publish)
//    Button publish;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.floatingToolbar)
    FloatingToolbar mFloatingToolbar;


    @Override
    protected void onFragmentVisibleChange(boolean b) {

    }

    @Override
    protected void onFragmentFirstVisible() {

        mFloatingToolbar.attachFab(fab);
        mFloatingToolbar.setClickListener(new FloatingToolbar.ItemClickListener() {
            @Override
            public void onItemClick(MenuItem item) {

                if (item.getItemId() == R.id.menu_1) {
                    PublishActivity.start(mActivity);
                } else if (item.getItemId() == R.id.menu_2) {

                    PublishActivity.start(mActivity);
                } else if (item.getItemId() == R.id.menu_3) {

                    PublishActivity.start(mActivity);
                }


            }

            @Override
            public void onItemLongClick(MenuItem item) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFloatingToolbar.show();
//                mFloatingToolbar.hide();
            }
        });

        mFloatingToolbar.attachRecyclerView(coreRecyclerView.getRecyclerView());

        RxPermissions rxPermissions = new RxPermissions(mActivity);
//        coreRecyclerView = getView(R.id.recyclerView);

//                rxPermissions.ensure().apply(new );
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {

//                            List<UserInfo> userInfos = new ArrayList<UserInfo>();
//                            userInfos.add(new UserInfo());
//                            userInfos.add(new UserInfo());
//                            userInfos.add(new UserInfo());
//                            userInfos.add(new UserInfo());
//                            userInfos.add(new UserInfo());
//                            userInfos.add(new UserInfo());
//                            coreRecyclerView.getAdapter().addData(userInfos);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        UiUtils.snackbarText(throwable.getMessage());
                    }
                });


        coreRecyclerView.init(new BaseQuickAdapter<Mountaineering.DataBean, BaseViewHolder>(R.layout.item_mult_view) {
            @Override
            protected void convert(BaseViewHolder helper, Mountaineering.DataBean item) {

                helper.setText(R.id.title, item.title)
                        .setText(R.id.create, item.createDate);

//                MultiView multiView = (MultiView) helper.getView(R.id.multi_view);
                NineGridView multiView = (NineGridView) helper.getView(R.id.multi_view);

                ArrayList<ImageInfo> imageInfo = new ArrayList<>();
                if (item.allUrls() != null) {
                    for (String imageDetail : item.allUrls()) {
                        ImageInfo info = new ImageInfo();
                        info.setThumbnailUrl(imageDetail);
                        info.setBigImageUrl(imageDetail);
                        imageInfo.add(info);
                    }
                }
                multiView.setAdapter(new NineGridViewClickAdapter(mActivity, imageInfo));

//                multiView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
//                multiView.clear();
//                try {
//                    multiView.setImages(item.allUrls());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }).openRefresh()
                .openLoadMore(999, page -> {
                    pullOnlineData();
                });

        coreRecyclerView.onRefresh();

    }

    public void pullOnlineData() {
//        ApiInterface apiService = getClient(mActivity, "http://192.168.1.142/hello-ssm/").create(ApiInterface.class);
        FragmentHome.ApiInterface apiService = getClient(mActivity, Api.host).create(FragmentHome.ApiInterface.class);

        //"94361"
        apiService.list().enqueue(new Callback<BaseJson<List<Mountaineering.DataBean>>>() {
            @Override
            public void onResponse(Call<BaseJson<List<Mountaineering.DataBean>>> call, retrofit2.Response<BaseJson<List<Mountaineering.DataBean>>> response) {

                try {
                    ALog.e("hello-world");


//                List<Mountaineering> mountaineering = response.body();

                    BaseJson<List<Mountaineering.DataBean>> data = response.body();

                    ALog.e("end-end" + data.getMsg());


                    Collections.reverse(data.getData());
                    coreRecyclerView.getAdapter().addData(data.getData());


//                if ((mountaineering != null ? mountaineering.data : null) != null) {
//
//                }


                } catch (Exception e) {
                    UiUtils.snackbarText("出错了--->" + e.getMessage());

                }
                coreRecyclerView.selfRefresh(false);

            }

            @Override
            public void onFailure(Call<BaseJson<List<Mountaineering.DataBean>>> call, Throwable t) {
                coreRecyclerView.selfRefresh(false);

            }


        });


    }


    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected int bindLayoutID() {
        return R.layout.shop;
    }

    //    @OnClick(R.id.publish)
    @Override
    public void onClick(View v) {

//        UiUtils.snackbarText("--publish--");

        PublishActivity.start(mActivity);

    }
}
