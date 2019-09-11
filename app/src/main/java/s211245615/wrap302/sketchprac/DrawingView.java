package s211245615.wrap302.sketchprac;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DrawingView extends View implements GestureDetector.OnGestureListener {

    int iColor;
    boolean isFilled = true;
    int iShapeChoice; //Shape chosen 0 = ellipse, 1 = rectangle, 3 = line
    float StartX, StartY, EndX, EndY; //Co-ordinates for touch gestures
    Paint defaultPaint;

    private ArrayList<DrawShape> drawShapes;
    boolean isDrawing;

    public void setFilled(boolean filled) {
        isFilled = filled;
    }


    public void setShapeChoice(int iShapeChoice) {
        this.iShapeChoice = iShapeChoice;
    }

    public ArrayList<DrawShape> getDrawShapes() {
        return drawShapes;
    }

    public void setDrawShapes(ArrayList<DrawShape> drawShapes) {
        this.drawShapes = drawShapes;
    }


    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        iShapeChoice = 0;
        iColor = Color.BLACK;
        drawShapes = new ArrayList<>();
        isDrawing = false;
        defaultPaint = new Paint();
        defaultPaint.setColor(Color.BLACK);
        defaultPaint.setStyle(Paint.Style.STROKE);

    }

    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:


                StartX = event.getX() - this.getLeft();
                StartY = event.getY() - this.getTop();
                Log.d("Event down", StartX + " " + StartY);
                isDrawing = true;
                return true;
            case MotionEvent.ACTION_UP:
                EndX = event.getX() - this.getLeft();
                EndY = event.getY() - this.getTop();
                Log.d("Event up", EndX + " " + EndY);
                Paint paint = new Paint();
                paint.setColor(iColor);
                if (iShapeChoice == 3) paint.setStrokeWidth(10);
                addShape(iShapeChoice, paint);
                isDrawing = false;
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                EndX = event.getX();
                EndY = event.getY();
                invalidate();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int min;
        if (w == 0)
            min = h;
        else if (h == 0)
            min = w;
        else
            min = Math.min(w, h);
        if (min != w) {
            this.getLayoutParams().height = min;
            this.getLayoutParams().width = min;
        }
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    private void addShape(int iShapeChoice, Paint paint) {
        drawShapes.add(new DrawShape(StartX, StartY, EndX, EndY, iShapeChoice, paint, isFilled));
    }

    public void setColor(int color) {
        iColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isDrawing)
            drawpreview(iShapeChoice, canvas);
        for (DrawShape drawShape : drawShapes) {
            switch (drawShape.getShapeType()) {
                case 0:
                    canvas.drawOval(drawShape.getStartX(), drawShape.getStartY(), drawShape.getEndX(), drawShape.getEndY(), drawShape.getPaint());
                    break;
                case 1:
                    canvas.drawRect(drawShape.getStartX(), drawShape.getStartY(), drawShape.getEndX(), drawShape.getEndY(), drawShape.getPaint());
                    break;
                case 2:
                    canvas.drawLine(drawShape.getStartX(), drawShape.getStartY(), drawShape.getEndX(), drawShape.getEndY(), drawShape.getPaint());
                    break;
            }
        }
    }

    private void drawpreview(int i, Canvas canvas) {
        switch (i) {
            case 0:
                canvas.drawOval(StartX, StartY, EndX, EndY, defaultPaint);
                break;
            case 1:
                canvas.drawRect(StartX, StartY, EndX, EndY, defaultPaint);
                break;
            case 2:
                canvas.drawLine(StartX, StartY, EndX, EndY, defaultPaint);
                break;
        }
    }

    public void deleteLastShape() {

        if (drawShapes.size() > 0) {
            drawShapes.remove(drawShapes.size() - 1);
            invalidate();
        }
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    public void clear() {
        drawShapes = new ArrayList<>();
        invalidate();
    }


}
