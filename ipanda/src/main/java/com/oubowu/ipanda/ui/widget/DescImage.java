package com.oubowu.ipanda.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.oubowu.ipanda.util.MeasureUtil;

/**
 * Created by Oubowu on 2018/1/14 19:13.
 */
public class DescImage extends android.support.v7.widget.AppCompatImageView {

    private int mTextPadding;

    private TextPaint mTextPaint;
    private Paint.FontMetrics mFontMetrics;

    private LinearGradient mLinearGradient;
    private RectF mRectF;
    private Paint mPaint;

    public void setDesc(String desc) {
        mDesc = desc;
        postInvalidate();
    }

    private String mDesc;

    public DescImage(Context context) {
        this(context, null);
    }

    public DescImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DescImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        mTextPadding = MeasureUtil.dip2px(getContext(), 8);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(MeasureUtil.sp2px(context, 14));
        mFontMetrics = mTextPaint.getFontMetrics();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制渐变阴影
        if (mLinearGradient == null) {
            mPaint = new Paint();
            mRectF = new RectF();
            int[] colors = {Color.parseColor("#ff000000"),Color.parseColor("#22111111")};
            // 我设置着色器开始的位置为（0，0），结束位置为（getWidth(), 0）表示我的着色器要给整个View在水平方向上渲染
            mLinearGradient = new LinearGradient(0, getMeasuredHeight(), 0, getMeasuredHeight() - mTextPadding * 2 - (mFontMetrics.bottom - mFontMetrics.top), colors, null, Shader.TileMode.REPEAT);
            mPaint.setShader(mLinearGradient);
            mRectF.set(0, getHeight() - mTextPadding * 2 - (mFontMetrics.bottom - mFontMetrics.top), getWidth(), getHeight());
        }
        canvas.drawRect(mRectF, mPaint);

        canvas.drawText(mDesc, mTextPadding, getHeight() - mTextPadding + (mFontMetrics.descent + mFontMetrics.ascent) / 2, mTextPaint);

    }



}
