package com.example.kripajha.customviewdemo.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kripajha.customviewdemo.R;

public class CustomView extends View {

    private boolean setHGuideLine = false, setVGuideLine = false;
    private int mRectVSquareTop, mRectVSquareLeft, mRectVSquareBottom, mRectVSquareRight;
    private int mRectHSquareTop, mRectHSquareLeft, mRectHSquareBottom, mRectHSquareRight;
    private Paint paintObj;

    private int mColorObject, mReqLength, mReqBreadth;
    private int mCursorX, mCursorY, mCursorSize = 30;
    private int touchX, touchY, lineWidth = 10;

    private Bitmap bmpCursor, bmpSolidCursor, bmpBackground;
    private int mCursorResourceId, mBackgroundResourceId;

    private int pixel;
    private String parameter=" Hi ";
    private IcolorPalletListener mListener;


    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }




    //initializing all attributes
    public void init(@Nullable AttributeSet set) {
        paintObj = new Paint(Paint.ANTI_ALIAS_FLAG);

        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                bmpCursor = BitmapFactory.decodeResource(getResources(), mCursorResourceId);
                if (bmpCursor != null)
                    bmpCursor = BitmapFactory.decodeResource(getResources(), mCursorResourceId);

                bmpBackground = BitmapFactory.decodeResource(getResources(), mBackgroundResourceId);
                if (bmpBackground != null)
                    bmpBackground = Bitmap.createScaledBitmap(bmpBackground, getWidth(), getHeight(), false);

            }
        });


        if (set != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(set, R.styleable.customview);

            setHGuideLine = typedArray.getBoolean(R.styleable.customview_SetHorizantalLine, false);
            setVGuideLine = typedArray.getBoolean(R.styleable.customview_SetVerticalLine, false);

            mColorObject = typedArray.getColor(R.styleable.customview_rectangle_color, Color.BLACK);
            mReqLength = typedArray.getDimensionPixelSize(R.styleable.customview_rectangle_length, 130);
            mReqBreadth = typedArray.getDimensionPixelSize(R.styleable.customview_rectangle_breadth, 30);
            mCursorSize = typedArray.getDimensionPixelSize(R.styleable.customview_circle_radius, 30);
            mCursorResourceId = typedArray.getResourceId(R.styleable.customview_circle_image, R.drawable.cursor);
            mBackgroundResourceId = typedArray.getResourceId(R.styleable.customview_backGround_image, R.drawable.colorpickerrectangular);

            setBackgroundResource(mBackgroundResourceId);


            paintObj.setColor(mColorObject);
            paintObj.setStyle(Paint.Style.FILL);
            typedArray.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //cursor image
        if (bmpCursor != null && bmpBackground != null
                && touchX > 0 && touchY > 0
                && touchX < bmpBackground.getWidth() && touchY < bmpBackground.getHeight()
                && bmpBackground.getPixel(touchX, touchY) != Color.TRANSPARENT
                ) {

            paintObj.setColor(mColorObject);
            paintObj.setStyle(Paint.Style.FILL);
            //guideline with respect to cursor
            if (setVGuideLine == true)
                canvas.drawRect(mRectVSquareLeft, mRectVSquareTop, mRectVSquareRight, mRectVSquareBottom, paintObj);
            if (setHGuideLine == true)
                canvas.drawRect(mRectHSquareLeft, mRectHSquareTop, mRectHSquareRight, mRectHSquareBottom, paintObj);

            paintObj.setColor(Color.WHITE);
            paintObj.setTextSize(50);


            //
            if (touchX - 150 < getX())
                canvas.drawText(parameter, touchX+150, touchY, paintObj);
            else
                canvas.drawText(parameter, touchX-150, touchY, paintObj);

//            canvas.drawBitmap(bmpCursor, mCursorX, mCursorY, null);

            canvas.drawCircle(touchX, touchY, mCursorSize + lineWidth, paintObj);
//            paintObj.setColor(Color.GRAY);
//            canvas.drawCircle(touchX, touchY, mCursorSize+ lineWidth+1, paintObj);

            paintObj.setColor(getColorSelected());
            canvas.drawCircle(touchX, touchY, mCursorSize, paintObj);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
//        getParent().requestDisallowInterceptTouchEvent(true);
        touchX = (int) event.getX();
        touchY = (int) event.getY();

        //Listen only in view
        if (touchX > getWidth() || touchY > getHeight() || touchX < 0 || touchY < 0) return false;

        if (bmpBackground != null && bmpCursor != null) {


            //setting up rectangle coordinates
            if (setVGuideLine == true)
                setVerticalLinePosition(touchX, touchY);
            if (setHGuideLine == true)
                setHorizantalLinePosition(touchX, touchY);

            //setting up circle center
            settingCursorPosition(touchX, touchY);

            // getColor
            setColorSelected(touchX, touchY);


            //calculatePercentage
            if (mListener != null)
                mListener.onEvent(getColorSelected(), xPercentage(touchX), yPercentage(touchY), event.getAction());


            invalidate();
            return true;
        }
        return false;
    }

    //color selected by point
    private void setColorSelected(float x, float y) {
        if (x > 0 && y > 0 && x < bmpBackground.getWidth() && y < bmpBackground.getHeight())
            pixel = bmpBackground.getPixel((int) x, (int) y);
    }

    //set verticalLine position
    private void setVerticalLinePosition(float x, float y) {
        mRectVSquareLeft = (int) x - mReqBreadth / 2;
        mRectVSquareTop = 0;
        mRectVSquareRight = (int) x + mReqBreadth / 2;
        mRectVSquareBottom = getHeight();
    }

    //set horizantalLine position
    private void setHorizantalLinePosition(float x, float y) {
        mRectHSquareLeft = 0;
        mRectHSquareTop = (int) y - mReqBreadth / 2;
        mRectHSquareRight = getWidth();
        mRectHSquareBottom = (int) y + mReqBreadth / 2;
    }

    //set cursor position
    private void settingCursorPosition(float x, float y) {
        mCursorX = (int) x - bmpCursor.getWidth() / 2;
        mCursorY = (int) y - bmpCursor.getHeight() / 2;
    }

    //get Selected Color
    public int getColorSelected() {
        return pixel;
    }

    // Function to inject object reference
    public void listenToColorPallet(@NonNull IcolorPalletListener obj) {
        mListener = obj;
    }
    public void setProgressText(String text){
        this.parameter=text;
    }

    public int xPercentage(int touchX) {
        return Math.min(100, Math.round((touchX * 100 / getWidth())));
    }

    public int yPercentage(int touchY) {
//        Log.d("Height"+touchY,"check"+getHeight());
        return Math.min(100, Math.round(((touchY * 100) / getHeight())));
    }
}
