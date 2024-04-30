package com.haibin.calendarviewproject.myrange;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.RangeMonthView;

import java.lang.reflect.Field;

/**
 * 范围选择月视图
 * Created by huanghaibin on 2018/9/13.
 */

public class CustomMyRangeMonthView extends RangeMonthView {

    private float mRadius;
    private Paint mSelectedMiddlePaint;

    public CustomMyRangeMonthView(Context context) {
        super(context);
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.max(mItemWidth, mItemHeight) / 5 * 2f;
        mSchemePaint.setStyle(Paint.Style.STROKE);
        mSelectedMiddlePaint = new Paint();
        mSelectedMiddlePaint.setAntiAlias(true);
        mSelectedMiddlePaint.setStyle(Paint.Style.FILL);
        mSelectedMiddlePaint.setStrokeWidth(2);
        mSelectedMiddlePaint.setColor(Color.RED);
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme,
                                     boolean isSelectedPre, boolean isSelectedNext, boolean isSameSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        String dateSelectedFlag = "开始";
        if (isSelectedPre & isSelectedNext) {
            dateSelectedFlag = "";
            canvas.drawRect(x, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedMiddlePaint);
        } else {
            if (isSelectedPre) {
                dateSelectedFlag = "结束";
                canvas.drawRect(x, cy - mRadius, cx, cy + mRadius, mSelectedPaint);
            } else if (isSelectedNext) {
                dateSelectedFlag = "开始";
                canvas.drawRect(cx, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
            } else if (isSameSelected) {
                dateSelectedFlag = "开始/结束";
            }
            canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        }
        canvas.drawText(dateSelectedFlag, cx, mTextBaseLine + y + mItemHeight / 8, mSelectedLunarTextPaint);

        return false;
    }


    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y - mItemHeight / 10;
        int cx = x + mItemWidth / 2;

        boolean isInRange = isInRange(calendar);
        boolean isEnable = !onCalendarIntercept(calendar);

        if (isSelected) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        } else if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() && isInRange && isEnable ? mSchemeTextPaint : mOtherMonthTextPaint);

        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() && isInRange && isEnable ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }

}
