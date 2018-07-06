package com.example.administrator.my80.mvp.ui.publish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.aloglibrary.ALog;
import com.example.administrator.my80.R;
import com.example.administrator.my80.http.UploadRunnable;
import com.example.administrator.my80.i.OnImageUploadListener;
import com.example.administrator.my80.mvp.m.entity.base.BaseJson;
import com.example.administrator.my80.mvp.m.entity.mountaineering.ImagesBean;
import com.example.administrator.my80.mvp.p.MountaineeringPresenter;
import com.example.art.base.App;
import com.example.art.base.BaseActivity;
import com.example.art.mvp.IView;
import com.example.art.mvp.Message;
import com.example.art.utils.UiUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.helper.GsonUtil;

public class PublishActivity extends BaseActivity<MountaineeringPresenter> implements IView, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.top_banner)
    EditText top_banner;

    @BindView(R.id.bottom_banner)
    EditText bottom_banner;

    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.close_date)
    EditText close_date;
    @BindView(R.id.loaction)
    EditText loaction;//上车地点
    @BindView(R.id.lineFeature)
    EditText lineFeature;//特色线路
    @BindView(R.id.star)
    EditText star;//风景指数
    @BindView(R.id.leaderName)
    EditText leaderName;//活动领队
    @BindView(R.id.userJoin)
    EditText userJoin;//包名列表
    @BindView(R.id.specialOffers)
    EditText specialOffers;// 优惠列表
    @BindView(R.id.desc)
    EditText desc;// 一段描述


    private List<LocalMedia> selectList = new ArrayList<>();
    private ExecutorService executorService;


    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_publish;
    }

    @Override
    public MountaineeringPresenter obtainPresenter() {
        return new MountaineeringPresenter(((App) getApplication()).getAppComponent());
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.snackbarText("snack");
            }
        });


        executorService = Executors.newFixedThreadPool(10);

    }

    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, PublishActivity.class));
    }

    public int select_type = 0; // 00 top   1  bottom

    @OnClick({R.id.select_top, R.id.select_bottom, R.id.publish, R.id.select_date_picker})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_top:
                select_type = 0;
                select1();
                break;
            case R.id.select_bottom:
                select_type = 1;
                select1();
                break;
            case R.id.publish:
                publish();
                break;
            case R.id.select_date_picker:
//                View custView = LayoutInflater.from(this).inflate(R.layout.dialog_datepicker, null);

                new MaterialDialog.Builder(this)
                        .title(R.string.app_name)
                        .customView(R.layout.dialog_datepicker, false)
                        .positiveText(android.R.string.ok)
                        .negativeText(android.R.string.cancel)
                        .callback(new MaterialDialog.ButtonCallback() {
                            @Override
                            public void onPositive(MaterialDialog dialog) {
                                super.onPositive(dialog);
                                DatePicker datepicker1 = (DatePicker) dialog.findViewById(R.id.datePicker);
                                int y = datepicker1.getYear();
                                int m = datepicker1.getMonth() + 1;
                                int d = datepicker1.getDayOfMonth();
                                close_date.setText(String.format("%s-%s-%s", y + "", m + "", d + ""));
//                                System.out.println("y:" + y + " m:" + m + " d:" + d);
                            }


                        })
                        .show();
                break;
        }

    }

    private void publish() {
        UiUtils.snackbarText("发布");

        getInputText(title);
        getInputText(top_banner);
        getInputText(loaction);
        getInputText(lineFeature);
        getInputText(star);
        getInputText(leaderName);
        getInputText(userJoin);
        getInputText(specialOffers);
        getInputText(desc);
        getInputText(close_date);
        getInputText(bottom_banner);
//http://www.luocaca.cn/hello-ssm/mountaineering/publish
        String[] strings = new String[]{
                "http://www.luocaca.cn/hello-ssm/mountaineering/add",
                getInputText(title),
                getInputText(top_banner),
                getInputText(loaction),
                getInputText(lineFeature),
                getInputText(star),
                getInputText(leaderName),
                getInputText(userJoin),
                getInputText(specialOffers),
                getInputText(desc),
                getInputText(close_date),
                getInputText(bottom_banner),
        };
//        Message message = new Message(this);
//        message.obj = strings;
        mPresenter
                .commitMsgs(Message.obtain(this, new Object[]{strings}));


    }

    private void select1() {
        // 进入相册 以下是例子：用不到的api可以不写
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                .maxSelectNum(20)// 最大图片选择数量 int
                .minSelectNum(1)// 最小选择数量 int
                .imageSpanCount(4)// 每行显示个数 int
                .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                .previewImage(true)// 是否可预览图片 true or false
                .previewVideo(true)// 是否可预览视频 true or false
                .enablePreviewAudio(true) // 是否可播放音频 true or false
                .isCamera(true)// 是否显示拍照按钮 true or false
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                .enableCrop(false)// 是否裁剪 true or false
                .compress(true)// 是否压缩 true or false
                .glideOverride(200, 200)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                .withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                .isGif(true)// 是否显示gif图片 true or false
//                .compressSavePath(getPath())//压缩图片保存地址
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                .showCropFrame(false)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                .openClickSound(true)// 是否开启点击声音 true or false
                .selectionMedia(null)// 是否传入已选图片 List<LocalMedia> list
                .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                .cropCompressQuality(80)// 裁剪压缩质量 默认90 int
                .minimumCompressSize(200)// 小于100kb的图片不压缩
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                .cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                .videoQuality(0)// 视频录制质量 0 or 1 int
                .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                .recordVideoSecond(15)//视频秒数录制 默认60s int
                .isDragFrame(false)// 是否可拖动裁剪框(固定)
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == RESULT_OK) {

            // 图片、视频、音频选择结果回调
            selectList = PictureSelector.obtainMultipleResult(data);
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

            if (selectList != null) {


                for (int i = 0; i < selectList.size(); i++) {
                    String url = selectList.get(i).getPath();
                    String compressPath = selectList.get(i).getCompressPath();
//                    String videoPath = selectList.get(i).getPath();
                    Log.e("--url--", url);
                    Log.e("---compressPath-", url + "");
//                    top_banner.append(url);
                }

                for (LocalMedia localMedia : selectList) {
                    executorService.execute(new UploadRunnable(new OnImageUploadListener() {
                        @Override
                        public void onSucceed(String json) {
                            Log.i("onSucceed", json);

                            BaseJson<String> stringBaseJson = (BaseJson<String>) GsonUtil.toObject(json, BaseJson.class);
                            if (stringBaseJson == null) {
                                UiUtils.snackbarText("--上传失败-");
                                return;
                            }

                            ImagesBean imagesBean = new ImagesBean();
                            imagesBean.url = stringBaseJson.getData();

                            if (select_type == 0) {
                                banners.add(imagesBean);
                            } else {
                                banners_foorer.add(imagesBean);
                            }

                            UiUtils.snackbarText("---" + stringBaseJson.getMsg());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (select_type == 0) {
                                        top_banner.setText(GsonUtil.toJson(banners));

                                    } else {

                                        bottom_banner.setText(GsonUtil.toJson(banners_foorer));

                                    }

                                }
                            });
                        }

                        @Override
                        public void onFailed(String msg) {
                            Log.i("onFailed", msg);
                        }
                    }, new File(localMedia.getCompressPath()), this));

                }


            }
        }
    }


    private List<ImagesBean> banners = new ArrayList<>();
    private List<ImagesBean> banners_foorer = new ArrayList<>();


    public String getInputText(EditText text) {
        ALog.i("-----parama--------> " + text.getText().toString());
        return text.getText().toString();

    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(String message) {

        UiUtils.snackbarText(message);
    }

    @Override
    public void handleMessage(Message message) {

    }
}
