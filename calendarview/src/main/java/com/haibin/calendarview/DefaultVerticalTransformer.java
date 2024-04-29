package com.haibin.calendarview;


import android.view.View;

import androidx.annotation.NonNull;

public class DefaultVerticalTransformer extends BaseVerticalTransformer {

    @Override
    public void transformPage(@NonNull View page, float position) {
        super.transformPage(page, position);
        if (0 <= position && position <= 1) {
            float alpha = 1 - position;
            page.setAlpha(alpha);
        } else if (-1 < position && position < 0) {
            float alpha = position + 1;
            page.setAlpha(alpha);
        }
    }
}