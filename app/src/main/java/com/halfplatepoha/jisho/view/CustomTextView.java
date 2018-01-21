package com.halfplatepoha.jisho.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.TextView;

import com.halfplatepoha.jisho.R;

public class CustomTextView extends TextView {


    public CustomTextView(Context context) {
        super(context);
        setupCompatResources(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupCompatResources(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupCompatResources(context, attrs);
    }

    private void setupCompatResources(Context context, AttributeSet attrs) {
        if(attrs != null) {
            TypedArray attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.CustomView);

            Drawable drawableLeft = null;
            Drawable drawableRight = null;
            Drawable drawableBottom = null;
            Drawable drawableTop = null;

            Drawable drawableBackground = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawableLeft = attributeArray.getDrawable(R.styleable.CustomView_drawableLeftCompat);
                drawableRight = attributeArray.getDrawable(R.styleable.CustomView_drawableRightCompat);
                drawableBottom = attributeArray.getDrawable(R.styleable.CustomView_drawableBottomCompat);
                drawableTop = attributeArray.getDrawable(R.styleable.CustomView_drawableTopCompat);

                drawableBackground = attributeArray.getDrawable(R.styleable.CustomView_backgroundCompat);
            } else {
                final int drawableLeftId = attributeArray.getResourceId(R.styleable.CustomView_drawableLeftCompat, -1);
                final int drawableRightId = attributeArray.getResourceId(R.styleable.CustomView_drawableRightCompat, -1);
                final int drawableBottomId = attributeArray.getResourceId(R.styleable.CustomView_drawableBottomCompat, -1);
                final int drawableTopId = attributeArray.getResourceId(R.styleable.CustomView_drawableTopCompat, -1);

                final int drawableBackGroundId = attributeArray.getResourceId(R.styleable.CustomView_backgroundCompat, -1);

                if (drawableLeftId != -1)
                    drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId);
                if (drawableRightId != -1)
                    drawableRight = AppCompatResources.getDrawable(context, drawableRightId);
                if (drawableBottomId != -1)
                    drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId);
                if (drawableTopId != -1)
                    drawableTop = AppCompatResources.getDrawable(context, drawableTopId);
                if(drawableBackGroundId != -1)
                    drawableBackground = AppCompatResources.getDrawable(context, drawableBackGroundId);
            }
            setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);

            if(drawableBackground != null) {
                setBackground(drawableBackground);
            }
            attributeArray.recycle();
        }
    }

    public void setCompoundDrawablesWithIntrinsicBoundsCompat(int drawableLeftId, int drawableTopId, int drawableRightId, int drawableBottomId) {

        Context context = getContext();

        Drawable drawableLeft = null;
        Drawable drawableRight = null;
        Drawable drawableBottom = null;
        Drawable drawableTop = null;

        if (drawableLeftId != -1)
            drawableLeft = AppCompatResources.getDrawable(context, drawableLeftId);
        if (drawableRightId != -1)
            drawableRight = AppCompatResources.getDrawable(context, drawableRightId);
        if (drawableBottomId != -1)
            drawableBottom = AppCompatResources.getDrawable(context, drawableBottomId);
        if (drawableTopId != -1)
            drawableTop = AppCompatResources.getDrawable(context, drawableTopId);

        setCompoundDrawablesWithIntrinsicBounds(drawableLeft, drawableTop, drawableRight, drawableBottom);

    }

}