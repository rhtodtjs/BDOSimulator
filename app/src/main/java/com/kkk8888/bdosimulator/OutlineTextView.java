package com.kkk8888.bdosimulator;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

public class OutlineTextView extends AppCompatTextView {



    private boolean hasStroke = false;

    private float mStrokeWidth = 0.0f;

    private int mStrokeColor;



    public OutlineTextView(Context context) {

        super(context);

    }



    public OutlineTextView(Context context, AttributeSet attrs) {

        super(context, attrs);

        initView(context, attrs);

    }



    public OutlineTextView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);

        initView(context, attrs);

    }



    @Override

    protected void onDraw(Canvas canvas) {

        if (hasStroke) {

            ColorStateList states = getTextColors();

            getPaint().setStyle(Paint.Style.STROKE);

            getPaint().setStrokeWidth(mStrokeWidth);

            setTextColor(mStrokeColor);

            super.onDraw(canvas);



            getPaint().setStyle(Paint.Style.FILL);

            setTextColor(states);

        }

        super.onDraw(canvas);

    }



    private void initView(Context context, AttributeSet attrs) {

        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.OutlineTextView);

        hasStroke = typeArray.getBoolean(R.styleable.OutlineTextView_textStroke, false);

        mStrokeWidth = typeArray.getDimension(R.styleable.OutlineTextView_textStrokeWidth, 0.0f);

        mStrokeColor = typeArray.getColor(R.styleable.OutlineTextView_textStrokeColor, 0xffff0000);

    }

}

