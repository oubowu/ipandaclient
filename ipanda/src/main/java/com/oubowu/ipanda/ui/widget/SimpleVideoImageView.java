package com.oubowu.ipanda.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.util.CommonUtil;
import com.oubowu.ipanda.util.MeasureUtil;

/**
 * Created by Oubowu on 2018/1/25 14:53.
 */
public class SimpleVideoImageView extends android.support.v7.widget.AppCompatImageView {

    private float mRatio;
    private int mScreenWidth;
    private int mExpectWidth;
    private int mExpectHeight;
    private int mTextPadding;
    private TextPaint mTextPaint;
    private Paint.FontMetrics mFontMetrics;
    private Paint mTagPaint;

    private String mVideoLength;
    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private RectF mRectF;
    private Path mTrianglePath;
    private int mTriangleHalfHeight;

    public SimpleVideoImageView(Context context) {
        this(context, null);
    }

    public SimpleVideoImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleVideoImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        setScaleType(ScaleType.FIT_XY);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SimpleVideoImageView);
            mRatio = ta.getFraction(R.styleable.SimpleVideoImageView_ratio, 1, 1, 0.4f);
            ta.recycle();
        }

        Log.e("SimpleVideoImageView", "66行-init(): " + mRatio);

        mScreenWidth = MeasureUtil.getScreenSize(context).x;
        mExpectWidth = (int) (mScreenWidth * mRatio);

        mTextPadding = MeasureUtil.dip2px(getContext(), 4);

        mTriangleHalfHeight = MeasureUtil.dip2px(getContext(), 5);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(MeasureUtil.dip2px(context, 14));
        mFontMetrics = mTextPaint.getFontMetrics();

        mTagPaint = new Paint();
        mTagPaint.setColor(Color.RED);
        mTagPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            int dWidth = drawable.getIntrinsicWidth();
            int dHeight = drawable.getIntrinsicHeight();

            float scale = mExpectWidth * 1.0f / dWidth;
            mExpectHeight = (int) (scale * dHeight);

            setMeasuredDimension(MeasureSpec.makeMeasureSpec(mExpectWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(mExpectHeight, MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (CommonUtil.isNotEmpty(mVideoLength)) {

            mTextPaint.setColor(Color.WHITE);
            mTextPaint.setTextSize(MeasureUtil.dip2px(getContext(), 14));
            float length = mTextPaint.measureText(mVideoLength);
            // 绘制渐变阴影
            if (mLinearGradient == null) {
                mPaint = new Paint();
                mRectF = new RectF();
                int[] colors = {Color.parseColor("#ff000000"), Color.parseColor("#22111111")};
                // 我设置着色器开始的位置为（0，0），结束位置为（getWidth(), 0）表示我的着色器要给整个View在水平方向上渲染
                mLinearGradient = new LinearGradient(0, 0, length + mTextPadding * 2 + mTriangleHalfHeight * 2 + mTextPadding, 0, colors, null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
            }
            mRectF.set(0, mExpectHeight - (mFontMetrics.bottom - mFontMetrics.top) - mTextPadding * 2, length + mTextPadding * 2 + mTriangleHalfHeight * 2 + mTextPadding,
                    mExpectHeight);
            canvas.drawRect(mRectF, mPaint);
            canvas.drawText(mVideoLength, mTextPadding + mTriangleHalfHeight * 2 + mTextPadding,
                    mExpectHeight - mTextPadding + (mFontMetrics.descent + mFontMetrics.ascent) / 2, mTextPaint);

            int circleCenterX = mTextPadding + mTriangleHalfHeight;
            float circleCenterY = mExpectHeight - ((mFontMetrics.bottom - mFontMetrics.top) + mTextPadding * 2) / 2;

            mTagPaint.setColor(ContextCompat.getColor(getContext(), R.color.toolbarNormalTextColor));
            if (mTrianglePath == null) {
                mTrianglePath = new Path();
            }
            mTrianglePath.reset();
            mTrianglePath.moveTo(mTextPadding, circleCenterY + mTriangleHalfHeight);
            mTrianglePath.lineTo(mTextPadding, circleCenterY - mTriangleHalfHeight);
            mTrianglePath.lineTo(mTextPadding + 2 * mTriangleHalfHeight, circleCenterY);
            mTrianglePath.lineTo(mTextPadding, circleCenterY + mTriangleHalfHeight);
            canvas.drawPath(mTrianglePath, mTagPaint);

        }

    }

    public void setVideoLength(String videoLength) {
        mVideoLength = videoLength;
    }
}
