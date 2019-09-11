package s211245615.wrap302.sketchprac;

import android.graphics.Paint;

class DrawShape{
    private float StartX, StartY, EndX, EndY;
    private Paint paint;
    private int ShapeType;

    float getStartX() {
        return StartX;
    }

    float getStartY() {
        return StartY;
    }

    float getEndX() {
        return EndX;
    }

    float getEndY() {
        return EndY;
    }

    Paint getPaint() {
        return paint;
    }

    int getShapeType() {
        return ShapeType;
    }

    DrawShape(float StartX, float StartY, float EndX, float EndY, int ShapeType, Paint paint, boolean isFilled) {
        this.StartX = StartX;
        this.StartY = StartY;
        this.EndX = EndX;
        this.EndY = EndY;
        this.ShapeType = ShapeType;
        this.paint = paint;
        if (ShapeType == 2)
            this.paint.setStrokeWidth(12);
        if (isFilled)
            this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
        else {
            this.paint.setStrokeWidth(12);
            this.paint.setStyle(Paint.Style.STROKE);
        }
    }
}






