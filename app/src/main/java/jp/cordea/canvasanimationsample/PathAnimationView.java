package jp.cordea.canvasanimationsample;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by cordea on 2015/08/31.
 */
public class PathAnimationView extends View {

    private Path mPath;
    private PathMeasure mMeasure;
    private float mProgress = 0.0f;

    public PathAnimationView(Context context) {
        super(context);
        init();
    }

    public PathAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        Path path = new Path();
        path.moveTo(50, 50);
        path.lineTo(50, 100);
        path.lineTo(100, 100);
        path.lineTo(100, 50);
        path.lineTo(50, 50);

        PathMeasure measure = new PathMeasure();
        measure.setPath(path, false);

        mPath = path;
        mMeasure = measure;

//        mMeasure.getSegment(mMeasure.getLength(), mMeasure.getLength() * 0.0f, mPath, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);

        canvas.drawPath(mPath, paint);
    }

    private void setProgress(float progress) {
        mPath.reset();
        mMeasure.getSegment(mMeasure.getLength(), mMeasure.getLength() * progress, mPath, true);
        invalidate();
    }

    public void startAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0.0f, 1.0f);
        animator.setDuration(5000);
        animator.start();
    }
}
