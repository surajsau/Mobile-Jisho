//package com.halfplatepoha.jisho.view;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.ValueAnimator;
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.DashPathEffect;
//import android.graphics.Matrix;
//import android.graphics.Paint;
//import android.graphics.Path;
//import android.graphics.PathMeasure;
//import android.graphics.PointF;
//import android.graphics.RectF;
//import android.support.annotation.Nullable;
//import android.util.AttributeSet;
//import android.util.TypedValue;
//import android.view.View;
//
//import com.halfplatepoha.jisho.jdb.Kanji;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.JishoList;
//import java.util.TreeSet;
//
///**
// * Created by surjo on 20/12/17.
// */
//
//public class KanjiView extends View {
//
//    public KanjiView(Context context) {
//        super(context);
//        init(context, null);
//    }
//
//    public KanjiView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init(context, attrs);
//    }
//
//    public KanjiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context, attrs);
//    }
//
//    private ArrayList<Path> rawPathList = new ArrayList<>();
//    private ArrayList<Float> rawPathLengthList = new ArrayList<>();
//
//    private ArrayList<Path> pathList = new ArrayList<>();
//    private ArrayList<PathMeasure> pathMeasureList = new ArrayList<>();
//
//    private Matrix transformMatrix = new Matrix();
//
//    private ValueAnimator drawAnimation;
//    private DashPathEffect dashPathEffect;
//    private int strokeIndex;
//    private PointF fingerPosition;
//    private float[] pos = new float[2];
//
//    private Paint paint;
//
//    private Paint animPaint;
//    private Paint fingerPaint;
//    private Paint lightPaint;
//
//    private TreeSet<Integer> highlighStrokeList = new TreeSet<>();
//
//    private boolean drawLight = true;
//    private boolean autoRun;
//    private long autoRunDelay = 500L;
//    private boolean animate = true;
//    private int strokeColor = Color.BLACK;
//    private int highlightColor = Color.RED;
//
//    private void init(Context context, AttributeSet attrs) {
//        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeCap(Paint.Cap.ROUND);
//
//        TypedValue typedValue = new TypedValue();
//        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary, R.attr.colorAccent});
//
//        int colorPrimary = a.getColor(0, Color.BLACK);
//        int colorAccent = a.getColor(1, Color.BLACK);
//
//        a.recycle();
//
//        animPaint = new Paint(paint);
//        fingerPaint = new Paint(paint);
//        lightPaint = new Paint(paint);
//
//        fingerPaint.setStyle(Paint.Style.FILL);
//
//        if(attrs != null) {
//            autoRun = attrs.getAttributeBooleanValue(R.styleable.KanjiStrokeView_svAutoRun, false);
//            animate = attrs.getAttributeBooleanValue(R.styleable.KanjiStrokeView_svAnimate, true);
//            strokeColor = attrs.getAttributeIntValue(R.styleable.KanjiStrokeView_svStrokeColor, Color.BLACK);
//            fingerPaint.setColor(attrs.getAttributeIntValue(R.styleable.KanjiStrokeView_svFingerColor, colorPrimary));
//            highlightColor = attrs.getAttributeIntValue(R.styleable.KanjiStrokeView_svStrokeHighlightColor, colorAccent);
//
//            paint.setStrokeWidth(context.getResources().getDimension(R.dimen.sv_default_stroke_width));
//            lightPaint.setStrokeWidth(context.getResources().getDimension(R.dimen.sv_default_stroke_width));
//            animPaint.setStrokeWidth(context.getResources().getDimension(R.dimen.sv_default_stroke_width));
//
//            fingerPaint.setStrokeWidth(context.getResources().getDimension(R.dimen.sv_default_finger_radius));
//        }
//
//        animPaint.setStrokeWidth(paint.getStrokeWidth());
//
//        if(isInEditMode()) {
//            loadPathData(PREVIEW_STROKE_DATA);
//        }
//    }
//
//    public void loadPathData(JishoList<String> pathDataList) {
//        buildPathList(pathDataList);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        if(pathList.size() == 0)
//            return;
//
//        if(!animate) {
//            for (int i = 0; i < pathList.size(); i++) {
//                paint.setColor(highlighStrokeList.contains(i) ? highlightColor : strokeColor);
//            }
//
//            return;
//        }
//
//        if(strokeIndex != pathList.size() && !drawAnimation.isStarted() && autoRun)
//            startStrokeAnimation(autoRunDelay);
//
//        if(drawLight) {
//            for (int i=strokeIndex; i<pathList.size(); i++) {
//                canvas.drawPath(pathList.get(i), lightPaint);
//            }
//        }
//
//        for(int i=0; i<strokeIndex; i++) {
//            paint.setColor(highlighStrokeList.contains(i) ? highlightColor : strokeColor);
//            canvas.drawPath(pathList.get(i), paint);
//        }
//
//        if(dashPathEffect != null && (drawAnimation.isRunning() || isInEditMode())) {
//            animPaint.setPathEffect(dashPathEffect);
//            animPaint.setColor(highlighStrokeList.contains(strokeIndex + 1) ? highlightColor : strokeColor);
//            canvas.drawPath(pathList.get(strokeIndex), animPaint);
//            canvas.drawCircle(fingerPosition.x, fingerPosition.y, fingerPaint.getStrokeWidth(), fingerPaint);
//        }
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        transformMatrix.setRectToRect(INPUT_RECT, new RectF(0f, 0f, (float)w, (float)h), Matrix.ScaleToFit.FILL);
//        applyTransformMatrix();
//        if(isInEditMode()) {
//            float len = pathMeasureList.get(strokeIndex).getLength();
//            dashPathEffect = new DashPathEffect(new float[]{len, len}, len *0.6f);
//        }
//    }
//
//    public void setPathData(KanjiView kanjiView) {
//        clear();
//
//        rawPathList.addAll(kanjiView.rawPathList);
//        rawPathLengthList.addAll(kanjiView.rawPathLengthList);
//
//        applyTransformMatrix();
//        invalidate();
//    }
//
//    public void startDrawAnimation() {
//        startStrokeAnimation(0L);
//    }
//
//    public void startDrawAnimation(long delay) {
//        if(drawAnimation.isStarted()) {
//            drawAnimation.cancel();
//            drawAnimation = null;
//        }
//        strokeIndex = 0;
//        if(strokeIndex > 0) {
//            startStrokeAnimation(delay);
//        }
//    }
//
//    private void startStrokeAnimation(long delay) {
//        final float length = pathMeasureList.get(strokeIndex).getLength();
//        drawAnimation = ValueAnimator.ofFloat(0f, 1f);
//
//        drawAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                if(strokeIndex >= pathList.size()) {
//                    drawAnimation.cancel();
//                    return;
//                }
//
//                dashPathEffect = new DashPathEffect(new float[]{length, length}, length * (1f - animation.getAnimatedFraction()));
//
//                pathMeasureList.get(strokeIndex).getPosTan(length * animation.getAnimatedFraction(), pos, null);
//
//                fingerPosition.set(pos[0], pos[1]);
//                invalidate();
//            }
//        });
//
//        drawAnimation.addListener(new AnimatorListenerAdapter() {
//
//            private boolean cancelled = false;
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                cancelled = true;
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if(cancelled)
//                    return;
//
//                if(strokeIndex++ < pathList.size())
//                    startStrokeAnimation(0);
//                else {
//                    drawAnimation = null;
//                    dashPathEffect = null;
//                }
//            }
//
//        });
//
//        // Panoramix' formula
//        drawAnimation.setDuration((long)(rawPathLengthList.get(strokeIndex) * 10));
//
//        drawAnimation.setStartDelay(delay);
//
//        drawAnimation.start();
//
//    }
//
//    private void applyTransformMatrix() {
//        if (pathList.isEmpty())
//            pathList.addAll(new ArrayList<Path>(rawPathList.size()));
//
//        for(int i=0; i<rawPathList.size(); i++) {
//            rawPathList.get(i).transform(transformMatrix, pathList.get(i));
//        }
//
//        pathMeasureList.clear();
//        for(Path path : rawPathList) {
//            pathMeasureList.add(new PathMeasure(path, false));
//        }
//
//    }
//
//    private void buildPathList(JishoList<String> strokePathData) {
//        clear();
//        if(strokePathData != null && !strokePathData.isEmpty()) {
//            for(String strokePath : strokePathData)
//                StrokeView.buildPath(strokePath);
//
//            for(Path path : rawPathList) {
//                rawPathLengthList.add(new PathMeasure(path, false).getLength());
//            }
//
//            applyTransformMatrix();
//        }
//
//        invalidate();
//    }
//
//    private void clear() {
//        rawPathList.clear();
//        rawPathLengthList.clear();
//        highlighStrokeList.clear();
//        pathList.clear();
//        pathMeasureList.clear();
//        strokeIndex = 0;
//
//        if(drawAnimation != null)
//            drawAnimation.cancel();
//    }
//
//    private static final String TAG = "KanjiStrokeView";
//
//    // Constant from KanjiVG, used to compute the transform matrix
//    private static final RectF INPUT_RECT = new RectF(0f, 0f, 109f, 109f);
//
//    // Kanji 月 for preview
//    private static final JishoList<String> PREVIEW_STROKE_DATA = Arrays.asList(new String[]{"M34.25,16.25c1,1,1.48,2.38,1.5,4c0.38,33.62,2.38,59.38-11,73.25",
//            "M36.25,19c4.12-0.62,31.49-4.78,33.25-5c4-0.5,5.5,1.12,5.5,4.75c0,2.76-0.5,49.25-0.5,69.5c0,13-6.25,4-8.75,1.75",
//            "M37.25,38c10.25-1.5,27.25-3.75,36.25-4.5",
//            "M37,58.25c8.75-1.12,27-3.5,36.25-4"});
//
//}
