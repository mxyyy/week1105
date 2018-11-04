package view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bwie.week1105.R;


public class WaveView extends View {

    private Paint paint1;
    private Paint paint2;
    private Path path1;
    private Path path2;
    private DrawFilter drawFilter;
    private float φ;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public interface OnWaveChangeListener {
        void onWaveChange(float y);
    }

    private OnWaveChangeListener listener;

    public void setOnWaveChangeListener(OnWaveChangeListener listener) {
        this.listener = listener;
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaveView);

        int color = a.getColor(R.styleable.WaveView_wave_color, Color.rgb(202, 75, 55));

        a.recycle();

        paint1 = new Paint();
        paint1.setColor(color);
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setStrokeWidth(5);

        paint2 = new Paint();
        paint2.setColor(color);
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setStrokeWidth(5);
        paint2.setAlpha(60);

        path1 = new Path();
        path2 = new Path();

        drawFilter = new PaintFlagsDrawFilter(0, Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.setDrawFilter(drawFilter);

        int A = getMeasuredHeight() / 2;

        double ω = 2 * Math.PI / getMeasuredWidth();

        φ -= 0.1f;

        // 重置path
        path1.reset();
        path2.reset();

        // path起始点都在左下
        path1.moveTo(getLeft(), getBottom());
        path2.moveTo(getLeft(), getBottom());

        for (int x = 0; x <= getMeasuredWidth(); x += 20) {
            // float y = A * sin(ω * x + φ) + k;
            float y1 = A * (float) Math.sin(ω * x + φ) + A;
            float y2 = -A * (float) Math.sin(ω * x + φ) + A;

            if (x > getMeasuredWidth() / 2 - 10 && x < getMeasuredWidth() / 2 + 10) {
                listener.onWaveChange(y2);
            }

            path1.lineTo(x, y1);
            path2.lineTo(x, y2);
        }

        // 两条线都画到右下
        path1.lineTo(getWidth(), getBottom());
        path2.lineTo(getWidth(), getBottom());

        canvas.drawPath(path1, paint1);
        canvas.drawPath(path2, paint2);

        postInvalidateDelayed(50);

    }
}
