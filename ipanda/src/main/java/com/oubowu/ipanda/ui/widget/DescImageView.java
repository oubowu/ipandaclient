package com.oubowu.ipanda.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.util.MeasureUtil;

/**
 * Created by Oubowu on 2018/1/14 19:13.
 */
public class DescImageView extends android.support.v7.widget.AppCompatImageView {

    private int mTextPadding;

    private TextPaint mTextPaint;
    private Paint.FontMetrics mFontMetrics;

    private LinearGradient mLinearGradient;
    private RectF mRectF;
    private Paint mPaint;

    private StaticLayout mStaticLayout;

    private float mTextRatio;

    public void setDesc(String desc) {
        mDesc = desc;
        postInvalidate();
    }

    private String mDesc;

    public DescImageView(Context context) {
        this(context, null);
    }

    public DescImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DescImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DescImageView);
            mTextRatio = ta.getFraction(R.styleable.DescImageView_textRatio, 1, 1, 0.7f);
            ta.recycle();
        }

        mTextPadding = MeasureUtil.dip2px(getContext(), 8);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(MeasureUtil.dip2px(context, 14));
        mFontMetrics = mTextPaint.getFontMetrics();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!TextUtils.isEmpty(mDesc)) {
            // 绘制渐变阴影
            if (mLinearGradient == null) {
                mPaint = new Paint();
                mRectF = new RectF();
                int[] colors = {Color.parseColor("#ff000000"), Color.parseColor("#22111111")};
                // 我设置着色器开始的位置为（0，0），结束位置为（getWidth(), 0）表示我的着色器要给整个View在水平方向上渲染
                mLinearGradient = new LinearGradient(0, getHeight(), 0, getHeight() - mTextPadding * 2 - (mFontMetrics.bottom - mFontMetrics.top), colors, null,
                        Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
            }
            mRectF.set(0, getHeight() - mTextPadding * 2 - (mFontMetrics.bottom - mFontMetrics.top), getWidth(), getHeight());
            canvas.drawRect(mRectF, mPaint);

            canvas.save();
            if (mStaticLayout == null) {
                float outerWidth = (int) (getWidth() * mTextRatio);

                float w = mTextPaint.measureText(mDesc);
                if (w > outerWidth) {
                    float ellipsizedWidth;
                    int bufEnd = mDesc.length();
                    while (outerWidth <= mTextPaint.measureText(mDesc.substring(0, --bufEnd))) {
                        // 直到小于等于最大宽度
                    }
                    outerWidth = mTextPaint.measureText(mDesc.substring(0, bufEnd));
                    ellipsizedWidth = mTextPaint.measureText(mDesc.substring(0, bufEnd - 1));

                    // 显示0~22位，由于设定ellipsizedWidth，因此后两位用省略号显示
                    mStaticLayout = new StaticLayout(mDesc, 0, bufEnd, mTextPaint, (int) outerWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true,
                            TextUtils.TruncateAt.END, (int) ellipsizedWidth);
                } else {
                    mStaticLayout = new StaticLayout(mDesc, mTextPaint, (int) outerWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
                }

            }
            canvas.translate(mTextPadding, getHeight() - mTextPadding - (mFontMetrics.bottom - mFontMetrics.top));

            mStaticLayout.draw(canvas);

            canvas.restore();

        }

    }


}
