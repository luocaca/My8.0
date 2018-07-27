package com.example.administrator.my80.mvp.ui.main.child;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.aloglibrary.ALog;
import com.example.administrator.my80.R;
import com.example.administrator.my80.fragment.BaseLazyFragment;
import com.example.administrator.my80.mvp.m.entity.outdoor.Outdoor;
import com.example.administrator.my80.mvp.ui.web.OutdoorDetailActivity;
import com.example.administrator.my80.util.GlideImageLoader;
import com.example.art.utils.UiUtils;
import com.luoxx.xxlib.weidet.BaseQuickAdapter;
import com.luoxx.xxlib.weidet.BaseViewHolder;
import com.luoxx.xxlib.weidet.CoreRecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 户外知识
 */

public class FragmentTips extends BaseLazyFragment {

    @BindView(R.id.web)
    WebView web;

    @BindView(R.id.coreRecyclerView)
    CoreRecyclerView mCoreRecyclerView;

    private static String url = "http://weixin.sogou.com/weixin?type=2&s_from=input&query=%E6%88%B7%E5%A4%96%E7%9F%A5%E8%AF%86&ie=utf8&_sug_=y&_sug_type_=&w=01015002&oq=%E6%88%B7%E5%A4%96&ri=0&sourceid=sugg&stj=0%3B0%3B0%3B0&stj2=0&stj0=0&stj1=0&hp=36&hp1=&sut=2335&sst0=1532414662949&lkt=2%2C1532414661019%2C1532414662843";

    @Override
    protected void onFragmentVisibleChange(boolean b) {


    }


    @Override
    protected void onFragmentFirstVisible() {


        mCoreRecyclerView.init(new BaseQuickAdapter<Outdoor, BaseViewHolder>(R.layout.item_outdoor) {
            @Override
            protected void convert(BaseViewHolder helper, Outdoor item) {


                helper
                        .setText(R.id.title, item.title)
                        .setText(R.id.content, item.content)
                        .setText(R.id.from, item.from)
                        .setText(R.id.time, item.time)


                ;

                helper.getConvertView().setOnClickListener(v -> {
                    OutdoorDetailActivity.url = item.href;
                    OutdoorDetailActivity.start(mActivity);
                });


                new GlideImageLoader().displayImage(mContext, item.banner, helper.getView(R.id.banner));


//                HttpRequestImage.getInstance().requestImage(url, new ImageCallback() {
//                    @Override
//                    public void callback(Bitmap bitmap) {
//                        helper.setImageBitmap(R.id.banner,bitmap);
//                    }
//
//                    @Override
//                    public void failure() {
//
//                    }
//                });


                ALog.i("------------------" + item.title);

            }

        })
                .openRefresh()
                .openLoadMore(10, page -> {
                    ALog.i("jsoupAnsyGet ----->  start");
                    getOutdoorsByAsny(page)
                            .observeOn(AndroidSchedulers.mainThread())
                            .doFinally(() -> {
                                mCoreRecyclerView.selfRefresh(false);
                            })
                            .subscribe(outdoors -> {

                                mCoreRecyclerView.getAdapter().addData(outdoors);
//                                UiUtils.snackbarText("onNext");

                            }, throwable -> {
                                UiUtils.snackbarText(throwable.getMessage());
                                ALog.w(throwable.getMessage());
                            }, () -> {
//                                UiUtils.snackbarText("complete");

                            })

                    ;
                    ALog.i("jsoupAnsyGet ----->  end");
                })
        ;

        mCoreRecyclerView.onRefresh();
        WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);

//        web.loadUrl("http://www.baidu.com");


        //WebView加载web资源
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

//http://weixin.sogou.com/weixin?type=2&s_from=input&query=%E6%88%B7%E5%A4%96%E7%9F%A5%E8%AF%86&ie=utf8&_sug_=y&_sug_type_=&w=01015002&oq=%E6%88%B7%E5%A4%96&ri=0&sourceid=sugg&stj=0%3B0%3B0%3B0&stj2=0&stj0=0&stj1=0&hp=36&hp1=&sut=2335&sst0=1532414662949&lkt=2%2C1532414661019%2C1532414662843
        web.loadUrl(url);
//        web.loadUrl("https://mp.weixin.qq.com/s/dAWmyaBEzym8sy3X7AJL9w");


    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected int bindLayoutID() {
        return R.layout.tips;
    }


    /**
     * 异步请求。通过jsoup 解析后 传回
     *
     * @return
     */
    public String getDataByPage(int page) {


        return String.format("http://weixin.sogou.com/weixin?hp=36&query=户外知识&s_from=input&type=2&page=%s", page + "");


    }


    public Observable<List<Outdoor>> getOutdoorsByAsny(int page) {

        return Observable.just(getDataByPage(page))
                .subscribeOn(Schedulers.io())
                .map(new Function<String, List<Outdoor>>() {
                    @Override
                    public List<Outdoor> apply(@NonNull String url) throws Exception {
                        ALog.i("jsoupAnsyGet");
                        return jsoupAnsyGet(url);
                    }
                });


    }

    private List<Outdoor> jsoupAnsyGet(String url) throws IOException {

        List<Outdoor> outdoors = new ArrayList<>();

        Document document = Jsoup.connect(url).get();


        Elements lis = document.getElementsByClass("news-list").get(0).getElementsByTag("li");


        //img-box
        //txt-box
        /**
         *    ║ <div class="img-box">
         ║  <a data-z="art" target="_blank" id="sogou_vr_11002601_img_0"
         href="http://mp.weixin.qq.com/s?src=11&amp;timestamp=1532418355&amp;ver=1017&amp;signature=YPE4DUSOK
         *na75Bab4ffJfanEfO6mE5q44c2mOBbamWFwiyDLUwgkUSnkrpO6mdb1sSEI1J5N*wpqmr*A32AOxtUePB6oPpGpzr8z2E5S6F5lcZ9
         * j2AOKaCuiFlVj25A&amp;new=1" uigs="article_image_0">
         * <img src="//img01.sogoucdn.com/net/a/04/link?appid=100520033&amp
         * ;url=http://mmbiz.qpic.cn/mmbiz_jpg/jsvaLkH1EJvJBRHeicBINicv1NRJI5weunNY6XduJ53lOxCWkyiab0zJnJ70OPQPsccuRE7iaWnUTic9VOQ9WKuSNiag
         * /0?wx_fmt=jpeg" onload="resizeImage(this,140,105)" onerror="errorImage(this)">
         * </a>
         ║ </div>
         */
        for (Element element : lis) {

            Outdoor outdoor = new Outdoor();


            Element imgBox = null;
            try {
                imgBox = element.getElementsByClass("img-box").get(0);

                Element imgeElement = imgBox.getElementsByTag("a").get(0).getElementsByTag("img").get(0);

                ALog.i("img url is  -----> \n" + imgeElement.attr("abs:src"));

                outdoor.banner = imgeElement.attr("abs:src");

            } catch (Exception e) {
                outdoor.banner = "";
//                e.printStackTrace();
            }


            String content = element.getElementsByClass("txt-info").get(0).getElementsByTag("p").get(0).text();

            String from = null;
            try {
                from = element.getElementsByClass("s-p").get(0).getElementsByClass("account").get(0).text();
            } catch (Exception e) {
                from = "-";
            }


            String time = null;
            try {


                Element child = element.getElementsByClass("s-p").get(0).getElementsByClass("s2").get(0);
                time = element.getElementsByClass("s-p").get(0).getElementsByClass("s2").get(0).text();

                Element resu = element.getElementsByClass("s-p").get(0).getElementsByClass("s2").get(0);


                ALog.i("=========" + resu);


                String note = resu + "";


                ALog.i("=======substring==" + note.substring(note.indexOf("('") + 2, note.indexOf("')")));
                String timetamp = note.substring(note.indexOf("('") + 2, note.indexOf("')"));


                long ti = Long.getLong(timetamp);

                Date date = new Date(ti);


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
                time = simpleDateFormat.format(date);

                ALog.i("=========" + resu.textNodes());

            } catch (Exception e) {
                time = "-";
            }
            Element contentBox = element.getElementsByClass("txt-box").get(0);
            Element contentElement = contentBox.getElementsByTag("h3").get(0).getElementsByTag("a").get(0);
            outdoor.title = contentElement.text();
            outdoor.content = content;//txt-info
            outdoor.from = from;
            outdoor.time = time;
            outdoor.href = contentElement.attr("href");

//            ALog.i("img content is -----> \n" + contentElement.text());
//            ALog.i("content content is -----> \n" + contentElement.text());
            ALog.i("outdoor.toString-----> \n" + outdoor.toString());

            outdoors.add(outdoor);

        }


        return outdoors;
    }

    /**
     * <a target="_blank"
     * href="http://mp.weixin.qq.com/s?src=3&amp;timestamp=1532415259&amp;ver=1&amp;signature=OFQgUDWJxRBG4tDPMRtDeW2w0LYgAVDzkdW2VAy6Hu5Lapt8Jv0-nk2LXbq8zQlYu*G4-QadqW6oWaqf03ijrTxypqyFuo2duvDjlrye1RhpQlFcx37oyVFAHn*t8PZce4Jejb1Xq*mrFMOIJH5e5Q=="
     * id="sogou_vr_11002601_title_0" uigs="article_title_0"
     * data-share="http://weixin.sogou.com/api/share?timestamp=1532415259&amp;signature=qIbwY*nI6KU9tBso4VCd8lYSesxOYgLcHX5tlbqlMR-qu0IkVTYmeqBZlKdhwXATGMLt8OAGPDt7J1UWBM8apSVwqhhrtrVbrcF2fb-ZnVitJeDmk-orosDjId*gqU5M0*VlyscZDtLLnyq8egOCnZfqLf8PlwhDn6ujV5PGMUR2703a98Qwmf9VlF43TmyVrglVsll0TQDqBVR3pEPfwIiu3jRXdrYTtLK7nBCUE6uyCfFWSAhMbRDLgCT1BBA2Yvaorh2LO8Ttg6kUSajlushk4JHOx6MKxksw*dkZ3NRPJlzXkB-G75ePJNHkYdsP">
     * <em><!--red_beg-->户外知识<!--red_end--></em>
     * 科普:户外失温,容易被人忽略的危险
     * </a>
     *
     * @return
     */
    public String getTitle(Document document) {

        return "hello - world ";

    }


    /**  <li id="sogou_vr_11002601_box_0" d="ab735a258a90e8e1-6bee54fcbd896b2a-11b112572531a7cb642cf2585dbee3e0">
     <div class="img-box">
     <a data-z="art" target="_blank" id="sogou_vr_11002601_img_0" href="http://mp.weixin.qq.com/s?src=3&amp;timestamp=1532415259&amp;ver=1&amp;signature=OFQgUDWJxRBG4tDPMRtDeW2w0LYgAVDzkdW2VAy6Hu5Lapt8Jv0-nk2LXbq8zQlYu*G4-QadqW6oWaqf03ijrTxypqyFuo2duvDjlrye1RhpQlFcx37oyVFAHn*t8PZce4Jejb1Xq*mrFMOIJH5e5Q==" uigs="article_image_0"><img src="//img01.sogoucdn.com/net/a/04/link?appid=100520033&amp;url=http://mmbiz.qpic.cn/mmbiz/GEOOrU4NjgD46mveicqR4kL1ZPicRZEgv7Nh5Mrwfbdtu6ZDclJArriahbNy7BfpE9f66cqSSVtO8MzAruOkP5BMQ/0?wx_fmt=jpeg" onload="resizeImage(this,140,105)" onerror="errorImage(this)" style="width: 140px; height: auto; margin-top: 0px;"></a>
     </div>
     <div class="txt-box">
     <h3>

     <a target="_blank"
     href="http://mp.weixin.qq.com/s?src=3&amp;timestamp=1532415259&amp;ver=1&amp;signature=OFQgUDWJxRBG4tDPMRtDeW2w0LYgAVDzkdW2VAy6Hu5Lapt8Jv0-nk2LXbq8zQlYu*G4-QadqW6oWaqf03ijrTxypqyFuo2duvDjlrye1RhpQlFcx37oyVFAHn*t8PZce4Jejb1Xq*mrFMOIJH5e5Q=="
     id="sogou_vr_11002601_title_0" uigs="article_title_0"
     data-share="http://weixin.sogou.com/api/share?timestamp=1532415259&amp;signature=qIbwY*nI6KU9tBso4VCd8lYSesxOYgLcHX5tlbqlMR-qu0IkVTYmeqBZlKdhwXATGMLt8OAGPDt7J1UWBM8apSVwqhhrtrVbrcF2fb-ZnVitJeDmk-orosDjId*gqU5M0*VlyscZDtLLnyq8egOCnZfqLf8PlwhDn6ujV5PGMUR2703a98Qwmf9VlF43TmyVrglVsll0TQDqBVR3pEPfwIiu3jRXdrYTtLK7nBCUE6uyCfFWSAhMbRDLgCT1BBA2Yvaorh2LO8Ttg6kUSajlushk4JHOx6MKxksw*dkZ3NRPJlzXkB-G75ePJNHkYdsP">
     <em><!--red_beg-->户外知识<!--red_end--></em>
     科普:户外失温,容易被人忽略的危险
     </a>

     </h3>
     <p class="txt-info" id="sogou_vr_11002601_summary_0">此前的<em><!--red_beg-->户外<!--red_end--></em>经历中还从来没有过类似的体验,我分析来分析去,觉着还是可能消耗太大,也许是低血糖,所以掏出士力架吃了一根,又...</p>
     <div class="s-p" t="1444298116">
     <a class="account" target="_blank" id="sogou_vr_11002601_account_0" i="oIWsFt8oavh3liS80TxtSyXW4i_k" href="http://mp.weixin.qq.com/profile?src=3&amp;timestamp=1532415259&amp;ver=1&amp;signature=wBAtabhSKjiQ3aTg-Ransd0pn36aEGyXXhLwe2chyBcmq8FOprmasuFZkeTwh4Kn5DkbpwCyctLg9FERLBfkCg==" data-headimage="http://wx.qlogo.cn/mmhead/Q3auHgzwzM7O1ExDIRVf9qf75dGVuQZzlMicDRAwicV0vWQ8mqgGGk0g/0" data-isv="0" uigs="article_account_0">战甲军品资料网</a><span class="s2"><script>document.write(timeConvert('1444298116'))</script>2015-10-8</span>
     <div class="moe-box">
     <span style="display: none;" class="sc"><a data-except="1" class="sc-a" href="javascript:void(0)" uigs="share_0"></a></span><span style="display:none;" class="fx"><a data-except="1" class="fx-a" href="javascript:void(0)" uigs="like_0"></a></span>
     </div>
     </div>
     </div>
     </li>
     */


}
