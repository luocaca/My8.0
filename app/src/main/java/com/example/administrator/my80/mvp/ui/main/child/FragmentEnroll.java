package com.example.administrator.my80.mvp.ui.main.child;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.example.administrator.my80.R;
import com.example.administrator.my80.base.fragment.BaseLazyFragment;
import com.example.art.mvp.IPresenter;

import butterknife.BindView;

/**
 * 报名须知
 */

public class FragmentEnroll extends BaseLazyFragment {


    @BindView(R.id.tcp)
    TextView tcp;



    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        mImmersionBar.statusBarColorTransformEnable(false)
                .navigationBarColor(R.color.white)
                .statusBarDarkFont(true)
                .statusBarColor(R.color.white)
                .init();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.enroll;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void initData(Bundle saveInstanceState) {

        tcp.setText(Html.fromHtml(html));

    }

    @Override
    public IPresenter obtainPresenter() {
        return null;
    }

    @Override
    public void setData(Object data) {

    }


    String html = "<div id=\"JoinPay\">\n" +
            "            <h3 class=\"hd\">报名/支付</h3>\n" +
            "            <div class=\"content\"><p>1、建议汇款的时候加几分零头。如：100元汇100.03元，方便核对您的汇款。</p><p>2、汇款后务必发消息给活动负责人，格式为：姓名，活动名称日期，汇款银行，汇款金额；以便及时确认您的报名！因汇款后未通知俱乐部而无法核对的汇款，可能会导致的名额无法保留。</p><p><br></p><p>网银及支付宝在线支付&amp;柜员机转账方式：<br><label class=\"title\">建设银行</label>：6217 0019 3001 6417 462<span class=\"filed-gap\"></span><label class=\"title\">开户名</label>：魏继忠<span class=\"filed-gap\"></span><label class=\"title\">开户行</label>：厦门市建设银行海沧支行<br><label class=\"title\">微信支付</label>：15980904813<span class=\"filed-gap\"></span><label class=\"title\">开户名</label>：魏继忠<br><label class=\"title\">支付宝</label>：15980904813<span class=\"filed-gap\"></span><label class=\"title\">开户名</label>：魏继忠<br><label class=\"title\">兴业银行</label>（对公）：1294 7010 0100 1984 13<span class=\"filed-gap\"></span><label class=\"title\">开户名</label>：莲花支行<span class=\"filed-gap\"></span><label class=\"title\">开户行</label>：厦门市徒步交友户外运动有限公司</p></div>\n" +
            "        </div><div id=\"Notice\">\n" +
            "        <h3 class=\"hd\">报名须知</h3>\n" +
            "        <div class=\"content\"><p>1、厦门徒步交友户外有限公司的活动不同于传统旅游线路，对参加队员的安全、环保和团队协作精神要求较高，要求尊重当地风俗，体现中华传统美德;</p><p>2、为保证活动质量和安全，厦门徒步交友户外有限公司大巴游和自驾游 亲子游 休闲游 拓展游一般情况下不进行混搭，大巴不能超载;</p><p>3、因为厦门徒步交友户外有限公司多为户外、摄影和深度游线路，操作比较复杂，根据外界天气、交通等各种情况的改变，厦门徒步交友户外有限公司领队有权在大部分会员同意，少数服从多数的情况下，更改活动行程;</p><p>4、因交通延阻、罢工、天气、飞机火车故障、航班取消或更改时间等俱乐部不可抗力原因所引致的额外费用由顾客承担，但是俱乐部会尽力协助大家克服困难。活动前因为俱乐部不可抗力而导致活动取消，俱乐部财务负责退还活动款;</p><p>5、在不减少景点的情况下，俱乐部领队有权根据天气、航班、船班等因素调整游览景点的先后顺序;</p><p>6、因游客自身原因造成的伤害，无权向厦门徒步交友户外有限公司要求赔偿，如果造成俱乐部或者第三人受到损害的，应当对此予以赔偿；</p><p>7、由于部分线路路途相对较为艰辛，各项条件比不上本地，希望参加队员有合理的心理预期。代他人报名者，须准确、及时转告；</p><p>8、儿童报价说明，儿童均为不占床、不含门票、不含早餐的价格（如报价另有说明除外）；</p><p>9、如果单人报名要求单独住一个房间的，需致电客服确认是否能安排，单房差自理。男女一起报名，如无法同住者请务必在报名时和工作人员说明；</p><p>10、凡有传染性疾病、心血管疾病、脑血管疾病、呼吸系统疾病、精神病、严重贫血病、大中型手术的恢复期病患者、行动不便者、孕妇等其他不适合出行者请勿报名。</p><p><br></p><p>活动退团（改期）规定：</p><p>【一】所有代办费用按实际损失计算。</p><p>&nbsp; I)大交通（飞机票，火车票，轮船票，含订票费）等按承运人的规定计算实际损失。特别注意，团队机票一旦开票不能退改签。</p><p>&nbsp; II)签证及办理签证的费用等全部不退。</p><p>&nbsp; III)其他额外的订房，订车等，按实际损失计算。</p><p>&nbsp;&nbsp;</p><p>【二】报名付款后，如不能参加或者改期，活动费扣款约定：</p><p>&nbsp; A)出境游</p><p>&nbsp; &nbsp; 1、因为出境游的特殊性，一般确定后不能随意变更，若以下约定不足赔偿实际损失的，按实际损失计算；</p><p>&nbsp; &nbsp; 2、报名确认后要退出活动的，需支付旅游费用总额的30%手续费；</p><p>&nbsp; &nbsp; 3、出发前30日至16日（含30日，下同），支付旅游费用总额的50%；</p><p>&nbsp; &nbsp; 4、出发前15日至8日，支付旅游费用总额70%；</p><p>&nbsp; &nbsp; 5、出发前7日至当日，支付旅游费用总额90%；</p><p>&nbsp; B)国内长途活动：</p><p>&nbsp; &nbsp; 1、报名确认后要退出活动的，需支付旅游费用总额的1%手续费；</p><p>&nbsp; &nbsp; 2、出发前15日至7日，支付旅游费用总额的10%；</p><p>&nbsp; &nbsp; 3、出发前7日至4日，支付旅游费用总额40%；</p><p>&nbsp; &nbsp; 4、出发前3日至1日，支付旅游费用总额60%；</p><p>&nbsp; &nbsp; 5、出发当日，支付旅游费用总额70%。</p><p>&nbsp; C)短途活动：</p><p>&nbsp; &nbsp; 1、出发前3日及以上，支付旅游费用总额1%；</p><p>&nbsp; &nbsp; 2、出发前2日至1日，支付旅游费用总额50%；</p><p>&nbsp; &nbsp; 3、出发当日，支付旅游费用总额70%；</p><p>&nbsp; D)特别说明：</p><p>&nbsp; &nbsp; 如按上述比例支付的业务损失费不足以赔偿旅行社的实际损失，旅游者应当按实际损失对旅行社予以赔偿，但最高额不应当超过旅游费用总额。旅游者未按约定时间到达约定集合地点 ，也未能在出发途中加入旅游团队的，视为旅游者在出发当日解除合同。</p><p><br></p><p>【三】如要退出请发送手机短信“姓名+活动+退出人数+退出原因”给活动领队或者厦门徒步交友户外有限公司工作人员，退出成功以收到回复短信为准。活动退款一般在确认退团后7个工作日退回，请谅解。</p></div>\n" +
            "    </div>";

}