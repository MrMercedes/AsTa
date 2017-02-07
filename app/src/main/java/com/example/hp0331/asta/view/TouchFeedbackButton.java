package com.example.hp0331.asta.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jerrywyx on 8/18/16.
 */
public class TouchFeedbackButton extends AppCompatButton {

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(final View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setAlpha(0.2f);
            } else {
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setAlpha(1.0f);
                    }
                }, 100);
            }
            return false;
        }
    };

    public TouchFeedbackButton(Context context) {
        super(context);
        setOnTouchListener(onTouchListener);
    }

    public TouchFeedbackButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(onTouchListener);
    }

    public TouchFeedbackButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(onTouchListener);
    }
}
