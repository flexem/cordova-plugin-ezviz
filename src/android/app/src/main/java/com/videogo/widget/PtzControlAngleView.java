package com.videogo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import com.videogo.util.LogUtil;
import com.videogo.util.Utils;

import __PACKAGE_NAME__.R;

public class PtzControlAngleView extends View {
    private final Paint linePaint;
    private final Paint ballPaint;
    private final float lineLength;
    private final float ballRadius;
    private int start;
    private int end;
    private int current;
    private int style;
    private LinearGradient horizontalGradient;
    private Path path;
    private boolean horizontalInfinityMode;

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float angle = 180.0F;
        int step;
        float offset;
        int i;
        byte var6;
        float x;
        if (this.style != 1 && this.style != 2) {
            if (this.horizontalGradient == null) {
                this.horizontalGradient = new LinearGradient(0.0F, (float)this.getMeasuredHeight() - 30.0F, (float)this.getMeasuredWidth(), (float)this.getMeasuredHeight() - 30.0F, new int[]{Color.parseColor("#00FFFFFF"), Color.parseColor("#FFFFFFFF"), Color.parseColor("#FFFFFFFF"), Color.parseColor("#00FFFFFF")}, new float[]{0.0F, 0.1F, 0.9F, 1.0F}, TileMode.CLAMP);
            }

            this.linePaint.setShader((Shader)this.horizontalGradient);
            if (this.end != this.start) {
                angle = (float)(this.current - this.start) / (float)(this.end - this.start) * (float)360;
            }

            if (this.horizontalInfinityMode) {
                canvas.drawLine(0.0F, (float)this.getMeasuredHeight() - 30.0F, (float)this.getMeasuredWidth(), (float)this.getMeasuredHeight() - 30.0F, this.linePaint);
                step = this.getMeasuredWidth() / 10;
                offset = angle % (float)90 / 90.0F * (float)step * (float)2;
                i = 0;

                for(var6 = 11; i <= var6; ++i) {
                    x = (float)(i * step) - offset;
                    if (i % 2 == 0) {
                        canvas.drawLine(x, (float)this.getMeasuredHeight() - 50.0F, x, (float)this.getMeasuredHeight() - 30.0F, this.linePaint);
                    } else {
                        canvas.drawLine(x, (float)this.getMeasuredHeight() - 40.0F, x, (float)this.getMeasuredHeight() - 30.0F, this.linePaint);
                    }
                }

                this.path.reset();
                this.path.moveTo((float)this.getMeasuredWidth() / 2.0F, (float)this.getMeasuredHeight() - 60.0F);
                this.path.lineTo((float)this.getMeasuredWidth() / 2.0F - (float)20, (float)this.getMeasuredHeight() - 94.0F);
                this.path.lineTo((float)this.getMeasuredWidth() / 2.0F + (float)20, (float)this.getMeasuredHeight() - 94.0F);
                this.path.close();
                canvas.drawPath(this.path, this.ballPaint);
            } else {
                step = this.getMeasuredWidth() / 10;
                canvas.drawLine((float)step, (float)this.getMeasuredHeight() - 30.0F, (float)(this.getMeasuredWidth() - step), (float)this.getMeasuredHeight() - 30.0F, this.linePaint);
                offset = angle * (float)step * (float)2 / 90.0F;
                i = 0;

                for(var6 = 8; i <= var6; ++i) {
                    x = (float)(i + 1) * (float)step;
                    if (i % 2 == 0) {
                        canvas.drawLine(x, (float)this.getMeasuredHeight() - 50.0F, x, (float)this.getMeasuredHeight() - 30.0F, this.linePaint);
                    } else {
                        canvas.drawLine(x, (float)this.getMeasuredHeight() - 40.0F, x, (float)this.getMeasuredHeight() - 30.0F, this.linePaint);
                    }
                }

                this.path.reset();
                this.path.moveTo(offset + (float)step, (float)this.getMeasuredHeight() - 60.0F);
                this.path.lineTo(offset + (float)step - (float)20, (float)this.getMeasuredHeight() - 94.0F);
                this.path.lineTo(offset + (float)step + (float)20, (float)this.getMeasuredHeight() - 94.0F);
                this.path.close();
                canvas.drawPath(this.path, this.ballPaint);
            }
        } else if (this.style == 2) {
            if (this.end != this.start) {
                angle = (float)(this.end - this.current) / (float)(this.end - this.start) * (float)360;
            }

            step = this.getMeasuredHeight() / 10;
            canvas.drawLine((float)this.getMeasuredWidth() - 30.0F, (float)step, (float)this.getMeasuredWidth() - 30.0F, (float)(this.getMeasuredHeight() - step), this.linePaint);
            offset = angle * (float)step * (float)2 / 90.0F;
            i = 0;

            for(var6 = 8; i <= var6; ++i) {
                x = (float)(i + 1) * (float)step;
                if (i % 2 == 0) {
                    canvas.drawLine((float)this.getMeasuredWidth() - 50.0F, x, (float)this.getMeasuredWidth() - 30.0F, x, this.linePaint);
                } else {
                    canvas.drawLine((float)this.getMeasuredWidth() - 40.0F, x, (float)this.getMeasuredWidth() - 30.0F, x, this.linePaint);
                }
            }

            this.path.reset();
            this.path.moveTo((float)this.getMeasuredWidth() - 60.0F, offset + (float)step);
            this.path.lineTo((float)this.getMeasuredWidth() - 94.0F, offset + (float)step - (float)20);
            this.path.lineTo((float)this.getMeasuredWidth() - 94.0F, offset + (float)step + (float)20);
            this.path.close();
            canvas.drawPath(this.path, this.ballPaint);
        } else {
            if (this.end != this.start) {
                angle = (float)(this.end - this.current) / (float)(this.end - this.start) * (float)360;
            }

            step = this.getMeasuredHeight() / 10;
            canvas.drawLine(30.0F, (float)step, 30.0F, (float)(this.getMeasuredHeight() - step), this.linePaint);
            offset = angle * (float)step * (float)2 / 90.0F;
            i = 0;

            for(var6 = 8; i <= var6; ++i) {
                x = (float)(i + 1) * (float)step;
                if (i % 2 == 0) {
                    canvas.drawLine(30.0F, x, 50.0F, x, this.linePaint);
                } else {
                    canvas.drawLine(30.0F, x, 40.0F, x, this.linePaint);
                }
            }

            this.path.reset();
            this.path.moveTo(60.0F, offset + (float)step);
            this.path.lineTo(94.0F, offset + (float)step - (float)20);
            this.path.lineTo(94.0F, offset + (float)step + (float)20);
            this.path.close();
            canvas.drawPath(this.path, this.ballPaint);
        }

    }

    public final void setAngle(int start, int end, int current) {
        this.start = start;
        this.end = end;
        this.current = current;
        LogUtil.d("PtzControlAngleView", "style = " + this.style + " , setAngle start = " + start + ", end = " + end + ", current = " + current);
        this.invalidate();
    }

    public final void setAngleMode(boolean infinity) {
        this.horizontalInfinityMode = infinity;
    }

    public PtzControlAngleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.linePaint = new Paint();
        this.ballPaint = new Paint();
        this.path = new Path();
        this.linePaint.setColor(-1);
        this.linePaint.setAntiAlias(true);
        this.linePaint.setStrokeWidth((float)Utils.dip2px(context, 1.0F));
        this.linePaint.setStyle(Style.STROKE);
        this.linePaint.setAntiAlias(true);
        this.ballPaint.setColor(ContextCompat.getColor(context, R.color.color_648FFC));
        this.ballPaint.setStyle(Style.FILL);
        this.lineLength = (float)Utils.dip2px(context, 3.0F);
        this.ballRadius = (float)Utils.dip2px(context, 3.0F);
        TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.LiveplayPtzControlAngleView);
        this.style = a.getInt(R.styleable.LiveplayPtzControlAngleView_liveplay_angle_style, 4);
        a.recycle();
    }
}
