package com.example.administrator.my80.mvp.ui.main.child;

import android.Manifest;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.administrator.my80.R;
import com.example.administrator.my80.fragment.BaseLazyFragment;
import com.example.administrator.my80.mvp.m.entity.UserInfo;
import com.example.administrator.my80.mvp.ui.publish.PublishActivity;
import com.example.art.utils.UiUtils;
import com.luoxx.xxlib.weidet.BaseQuickAdapter;
import com.luoxx.xxlib.weidet.BaseViewHolder;
import com.luoxx.xxlib.weidet.CoreRecyclerView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.lemon.multi.MultiView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/8/11 0011.
 */

public class FragmentShop extends BaseLazyFragment implements View.OnClickListener {

    private List<String> mData = new ArrayList<>();

    @BindView(R.id.recyclerView)
    CoreRecyclerView coreRecyclerView;
    @BindView(R.id.publish)
    Button publish;


    @Override
    protected void onFragmentVisibleChange(boolean b) {

    }

    @Override
    protected void onFragmentFirstVisible() {


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

                            List<UserInfo> userInfos = new ArrayList<UserInfo>();
                            userInfos.add(new UserInfo());
                            userInfos.add(new UserInfo());
                            userInfos.add(new UserInfo());
                            userInfos.add(new UserInfo());
                            userInfos.add(new UserInfo());
                            userInfos.add(new UserInfo());
                            coreRecyclerView.getAdapter().addData(userInfos);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        UiUtils.snackbarText(throwable.getMessage());
                    }
                });


        coreRecyclerView.init(new BaseQuickAdapter<UserInfo, BaseViewHolder>(R.layout.item_mult_view) {
            @Override
            protected void convert(BaseViewHolder helper, UserInfo item) {
                MultiView multiView = (MultiView) helper.getView(R.id.multi_view);
                multiView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                List<String> data = new ArrayList<String>();
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");
                data.add("http://i02.pictn.sogoucdn.com/73a90748d5e19769");

                multiView.setImages(data);


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

    @OnClick(R.id.publish)
    @Override
    public void onClick(View v) {

        UiUtils.snackbarText("--publish--");

        PublishActivity.start(mActivity);

    }
}
