package com.example.protractor;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.ColorInt;

public class ProtractorView extends View {

    private Paint mLinePaint;
    private Paint mAngleTextPaint;
    private int mLineLength = 300; // Chiều dài đường thẳng

    private int mLine1Angle = 0;
    private int mLine2Angle = 90; // Đường thẳng thứ hai ban đầu vuông góc với đường thẳng thứ nhất

    public ProtractorView(Context context) {
        super(context);
        init(null);
    }

    public ProtractorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProtractorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        mLinePaint = new Paint();
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStrokeWidth(5);
        mLinePaint.setAntiAlias(true);

        mAngleTextPaint = new Paint();
        mAngleTextPaint.setColor(Color.BLACK);
        mAngleTextPaint.setTextSize(50);
        mAngleTextPaint.setAntiAlias(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                updateLines(event.getX(), event.getY());
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void updateLines(float x, float y) {
        // Tính toán góc của đường thẳng 1
        mLine1Angle = calculateAngle(getWidth() / 2, getHeight() / 2, x, y);

        // Giới hạn góc từ 0 đến 180 độ
        if (mLine1Angle < 0) {
            mLine1Angle = 0;
        } else if (mLine1Angle > 180) {
            mLine1Angle = 180;
        }

        // Tính toán góc của đường thẳng 2
        mLine2Angle = mLine1Angle + 180;

        // Giới hạn góc của đường thẳng 2 từ 0 đến 180 độ
        if (mLine2Angle > 180) {
            mLine2Angle -= 180;
        }

        // Cập nhật giao diện
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Vẽ đường thẳng 1
        float startX1 = getWidth() / 2;
        float startY1 = getHeight() / 2;
        float endX1 = startX1 + mLineLength * (float) Math.cos(Math.toRadians(mLine1Angle));
        float endY1 = startY1 + mLineLength * (float) Math.sin(Math.toRadians(mLine1Angle));
        canvas.drawLine(startX1, startY1, endX1, endY1, mLinePaint);

        // Vẽ đường thẳng 2
        float startX2 = getWidth() / 2;
        float startY2 = getHeight() / 2;
        float endX2 = startX2 + mLineLength * (float) Math.cos(Math.toRadians(mLine2Angle));
        float endY2 = startY2 + mLineLength * (float) Math.sin(Math.toRadians(mLine2Angle));
        canvas.drawLine(startX2, startY2, endX2, endY2, mLinePaint);

        // Tính và vẽ góc giữa hai đường thẳng
        double angleRadians1 = Math.atan2(endY1 - startY1, endX1 - startX1);
        double angleRadians2 = Math.atan2(endY2 - startY2, endX2 - startX2);
        float angleDegrees1 = (float) Math.toDegrees(angleRadians1);
        float angleDegrees2 = (float) Math.toDegrees(angleRadians2);
        float angleBetween = angleDegrees2 - angleDegrees1;
        if (angleBetween < 0) {
            angleBetween += 360;
        }

        String angleText = String.format("%.1f°", angleBetween);
        canvas.drawText(angleText, getWidth() / 2, getHeight() / 2, mAngleTextPaint);
    }

    private int calculateAngle(float startX, float startY, float endX, float endY) {
        double angleRadians = Math.atan2(endY - startY, endX - startX);
        float angleDegrees = (float) Math.toDegrees(angleRadians);
        if (angleDegrees < 0) {
            angleDegrees += 360;
        }
        return (int) angleDegrees;
    }

}
