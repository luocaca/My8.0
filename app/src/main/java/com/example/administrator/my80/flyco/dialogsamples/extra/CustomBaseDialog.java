package com.example.administrator.my80.flyco.dialogsamples.extra;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.my80.R;
import com.example.administrator.my80.flyco.animation.Attention.Swing;
import com.example.administrator.my80.flyco.dialog.utils.CornerUtils;
import com.example.administrator.my80.flyco.dialog.widget.base.BaseDialog;
import com.example.administrator.my80.flyco.dialogsamples.utils.ViewFindUtils;


public class CustomBaseDialog extends BaseDialog<CustomBaseDialog> {
    private TextView mTvCancel;
    private TextView mTvExit;

    public CustomBaseDialog(Context context,boolean b) {
        super(context,b);
    }

    public CustomBaseDialog(Context context) {
        super(context);
    }




    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new Swing());

        // dismissAnim(this, new ZoomOutExit());
        View inflate = View.inflate(mContext, R.layout.dialog_custom_base, null);
        mTvCancel = ViewFindUtils.find(inflate, R.id.tv_cancel);
        mTvExit = ViewFindUtils.find(inflate, R.id.tv_exit);
        inflate.setBackgroundDrawable(
                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));

        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mTvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
