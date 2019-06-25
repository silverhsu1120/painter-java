package com.silver.painter.view;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.silver.painter.model.Painter;
import com.silver.painter.viewmodel.PaintViewModel;

public class PaintView extends View {

    private Canvas canvas;
    private Bitmap bitmap;
    private Paint paint;
    private Path path;
    private float x, y;

    private PaintViewModel viewModel;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        canvas = new Canvas();
        paint = new Paint();
        path = new Path();

        viewModel = ViewModelProviders.of((FragmentActivity) context).get(PaintViewModel.class);
        viewModel.getPainter().observe((LifecycleOwner) context, new Observer<Painter>() {
            @Override
            public void onChanged(@Nullable Painter painter) {
                if (painter == null) return;
                paint.setXfermode(painter.mode == Painter.Mode.ERASER
                        ? new PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                        : null);
                switch (painter.mode) {
                    case PENCIL:
                    case ERASER:
                        paint.setStyle(Paint.Style.STROKE);
                        paint.setStrokeWidth(painter.width);
                        paint.setColor(painter.color);
                        break;
                }
            }
        });

        post(new Runnable() {
            @Override
            public void run() {
                bitmap = Bitmap.createBitmap(
                        getWidth(), getHeight(),
                        Bitmap.Config.ARGB_8888);
                canvas.setBitmap(bitmap);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                this.x = x;
                this.y = y;
                break;
            case MotionEvent.ACTION_MOVE:
                path.quadTo(this.x, this.y, (this.x + x) / 2, (this.y + y) / 2);
                this.x = x;
                this.y = y;
                canvas.drawPath(path, paint);
                break;
            case MotionEvent.ACTION_UP:
                path.reset();
                break;
        }
        invalidate();
        return true;
    }
}
