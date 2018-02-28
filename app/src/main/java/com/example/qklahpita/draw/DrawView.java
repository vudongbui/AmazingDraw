package com.example.qklahpita.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
    private static final String TAG = "DrawView";

    private Canvas canvas;
    private Path path;
    private Paint paint;

    private Bitmap bitmap;

    public DrawView(Context context, Bitmap bitmap) {
        super(context);

        canvas = new Canvas();
        path = new Path();

        paint = new Paint();
        paint.setColor(DrawActivity.currentColor);
        paint.setStrokeWidth(DrawActivity.currentSize);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        this.bitmap = bitmap;
    }

    @Override
    protected void onSizeChanged(int wight, int height, int Wold, int Hold) { //take w h of DrawView
        super.onSizeChanged(wight, height, Wold, Hold);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(wight, height, Bitmap.Config.ARGB_8888);
            bitmap.eraseColor(Color.WHITE);
        }
        canvas = new Canvas(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap, 10, 10, paint);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                paint.setColor(DrawActivity.currentColor);
                paint.setStrokeWidth(DrawActivity.currentSize);
                path.moveTo(event.getX(), event.getY());
                break;
            }
            
            case MotionEvent.ACTION_MOVE: {
                path.lineTo(event.getX(), event.getY());
                break;
            }
            
            case MotionEvent.ACTION_UP: {
                canvas.drawPath(path, paint);
                path.reset();
                break;
            }
            
        }
        invalidate();

        return true;
    }
}
