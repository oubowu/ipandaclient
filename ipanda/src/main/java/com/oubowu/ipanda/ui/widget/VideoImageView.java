package com.oubowu.ipanda.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.oubowu.ipanda.R;
import com.oubowu.ipanda.util.CommonUtil;
import com.oubowu.ipanda.util.MeasureUtil;

/**
 * Created by Oubowu on 2018/1/22 14:09.
 */
public class VideoImageView extends android.support.v7.widget.AppCompatImageView {

    private TextPaint mTextPaint;
    private Paint.FontMetrics mFontMetrics;
    private String mTypeDesc;
    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private RectF mRectF;
    private int mTextPadding;
    private Paint mTagPaint;

    private int mCircleRadius;

    private StaticLayout mStaticLayout;

    private String mContent;
    private String mDaytime;

    private Paint mLinePaint;

    private Path mTrianglePath;

    public VideoImageView(Context context) {
        this(context, null);
    }

    public VideoImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mTextPadding = MeasureUtil.dip2px(getContext(), 4);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(MeasureUtil.dip2px(context, 14));
        mFontMetrics = mTextPaint.getFontMetrics();

        mTagPaint = new Paint();
        mTagPaint.setColor(Color.RED);
        mTagPaint.setStyle(Paint.Style.FILL);

        mCircleRadius = MeasureUtil.dip2px(getContext(), 5);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mLinePaint.setColor(ContextCompat.getColor(getContext(), R.color.toolbarNormalTextColor));                    //设置画笔颜色
        mLinePaint.setStrokeWidth(1.5f);              //线宽

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        // 忽略padding
        float paddingLeft = /*getPaddingLeft() */ 0;
        float paddingTop = /*getPaddingTop() */ 0;

        if (drawable == null) {
            return;
        }

        int drawableWidth = drawable.getIntrinsicWidth();

        int drawableHeight = drawable.getIntrinsicHeight();

        if (drawableWidth == 0 || drawableHeight == 0) {
            return;
        }

        // 图片期望绘制到画布的宽度
        int expectDrawWidth = getWidth();
        float scale = expectDrawWidth * 1.0f / 59;
        // 图片期望绘制到画布的高度
        int expectDrawHeight = (int) (33 * scale);

        Matrix drawMatrix = getImageMatrix();

        if (drawMatrix == null && paddingTop == 0 && paddingLeft == 0) {
            drawable.draw(canvas);
        } else {
            final int saveCount = canvas.getSaveCount();
            canvas.save();

            canvas.translate(paddingLeft, paddingTop);

            if (drawMatrix != null) {

                drawMatrix.reset();

                // 图片期望绘制到画布的宽度是定死的
                float scale1 = expectDrawWidth * 1.0f / drawableWidth;
                // 算出实际的绘制到画布的高度
                int measureExpectDrawHeight = (int) (scale1 * drawableHeight);

                if (measureExpectDrawHeight > expectDrawHeight) {
                    // 算出的高度大于期望的高度，上移画布
                    drawable.setBounds(0, 0, expectDrawWidth, measureExpectDrawHeight);
                    drawMatrix.setTranslate(0, expectDrawHeight - measureExpectDrawHeight);
                } else if (measureExpectDrawHeight < expectDrawHeight) {
                    // 算出的高度小于期望的高度，缩放画布
                    drawable.setBounds(0, 0, expectDrawWidth, measureExpectDrawHeight);
                    float scale2 = expectDrawHeight * 1.0f / measureExpectDrawHeight;
                    drawMatrix.setScale(scale2, scale2);
                    int measureExpectDrawWidth = (int) (expectDrawWidth / scale2);
                    drawMatrix.postTranslate((measureExpectDrawWidth - expectDrawWidth) / 2, 0);
                } else {
                    // 算出的高度等于期望的高度，不做处理
                    drawable.setBounds(0, 0, expectDrawWidth, measureExpectDrawHeight);
                }

                canvas.concat(drawMatrix);

            }
            drawable.draw(canvas);
            canvas.restoreToCount(saveCount);
        }


        if (CommonUtil.isNotEmpty(mContent) && CommonUtil.isNotEmpty(mTypeDesc)) {

            mTextPaint.setColor(Color.WHITE);
            mTextPaint.setTextSize(MeasureUtil.dip2px(getContext(), 14));
            mFontMetrics = mTextPaint.getFontMetrics();

            float length = mTextPaint.measureText(mTypeDesc);
            // 绘制渐变阴影
            if (mLinearGradient == null) {
                mPaint = new Paint();
                mRectF = new RectF();
                int[] colors = {Color.parseColor("#ff000000"), Color.parseColor("#22111111")};
                // 我设置着色器开始的位置为（0，0），结束位置为（getWidth(), 0）表示我的着色器要给整个View在水平方向上渲染
                mLinearGradient = new LinearGradient(0, 0, length + mTextPadding * 2 + mCircleRadius * 2 + mTextPadding, 0, colors, null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
            }
            mRectF.set(0, expectDrawHeight - (mFontMetrics.bottom - mFontMetrics.top) - mTextPadding * 2, length + mTextPadding * 2 + mCircleRadius * 2 + mTextPadding,
                    expectDrawHeight);
            canvas.drawRect(mRectF, mPaint);
            canvas.drawText(mTypeDesc, mTextPadding + mCircleRadius * 2 + mTextPadding,
                    expectDrawHeight - mTextPadding + (mFontMetrics.descent + mFontMetrics.ascent) / 2, mTextPaint);

            int circleCenterX = mTextPadding + mCircleRadius;
            float circleCenterY = expectDrawHeight - ((mFontMetrics.bottom - mFontMetrics.top) + mTextPadding * 2) / 2;

            boolean isLiveType = CommonUtil.isEmpty(mDaytime);

            if (isLiveType) {
                mTagPaint.setColor(Color.RED);
                canvas.drawCircle(circleCenterX, circleCenterY, mCircleRadius, mTagPaint);
            } else {
                mTagPaint.setColor(ContextCompat.getColor(getContext(), R.color.toolbarNormalTextColor));
                if (mTrianglePath == null) {
                    mTrianglePath = new Path();
                }
                mTrianglePath.reset();
                mTrianglePath.moveTo(mTextPadding, circleCenterY + mCircleRadius);
                mTrianglePath.lineTo(mTextPadding, circleCenterY - mCircleRadius);
                mTrianglePath.lineTo(mTextPadding + 2 * mCircleRadius, circleCenterY);
                mTrianglePath.lineTo(mTextPadding, circleCenterY + mCircleRadius);
                canvas.drawPath(mTrianglePath, mTagPaint);
            }

            canvas.save();
            mTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.toolbarSelectTextColor));
            mTextPaint.setTextSize(MeasureUtil.dip2px(getContext(), 16));
            mFontMetrics = mTextPaint.getFontMetrics();
            if (mStaticLayout == null) {
                mStaticLayout = new StaticLayout(mContent, mTextPaint, getWidth() - mTextPadding * 2, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
            }
            if (isLiveType) {
                canvas.translate(mTextPadding, expectDrawHeight + (getHeight() - expectDrawHeight - mStaticLayout.getHeight()) / 2);
            } else {
                canvas.translate(mTextPadding, expectDrawHeight + mTextPadding/*+ (getHeight() - expectDrawHeight - mStaticLayout.getHeight()) / 2*/);
            }

            mStaticLayout.draw(canvas);
            canvas.restore();

            if (!isLiveType) {
                mTextPaint.setColor(ContextCompat.getColor(getContext(), R.color.toolbarNormalTextColor));
                mTextPaint.setTextSize(MeasureUtil.dip2px(getContext(), 12));
                mFontMetrics = mTextPaint.getFontMetrics();
                canvas.drawText(mDaytime, mTextPadding, getHeight() - mTextPadding + (mFontMetrics.descent + mFontMetrics.ascent) / 2, mTextPaint);
            }

            float[] pts = {0, 0, getWidth(), 0,//
                    0, 0, 0, getHeight(),//
                    getWidth(), 0, getWidth(), getHeight(),//
                    0, getHeight(), getWidth(), getHeight()};//
            canvas.drawLines(pts, mLinePaint);

        }


    }

    public void setInfo(String typeDesc, String content, String daytime) {
        mTypeDesc = typeDesc;
        mContent = content;
        mDaytime = daytime;
        postInvalidate();
    }

}
