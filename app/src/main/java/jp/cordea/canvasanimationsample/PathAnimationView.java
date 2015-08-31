/*
 * Copyright 2015 Yoshihiro Tanaka
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.cordea.canvasanimationsample;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cordea on 2015/08/31.
 */
public class PathAnimationView extends View {

    private Path mPath;
    private PathMeasure mMeasure;

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

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mPath = path;
        mMeasure = measure;
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
        mMeasure.getSegment(0.0f, mMeasure.getLength() * progress, mPath, true);
        invalidate();
    }

    public void startAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0.0f, 1.0f);
        animator.setDuration(5000);
        animator.start();
    }
}
