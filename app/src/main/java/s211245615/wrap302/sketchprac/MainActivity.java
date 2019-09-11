package s211245615.wrap302.sketchprac;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import top.defaults.colorpicker.ColorPickerPopup;

import static java.lang.Math.min;

public class MainActivity extends AppCompatActivity {
    DrawingView drawingView;
    ImageButton imageButton;
    RadioGroup radioGroup;
    int shapeType;
    int index1id;
    int index2id;
    int index3id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int x = size.x;
        int y = size.y;
        int minSize = min(x,y);

        drawingView.getLayoutParams().width = minSize-16;
        drawingView.getLayoutParams().height = minSize-16;

    }

    private void init() {
        drawingView = findViewById(R.id.drawingView);

        imageButton = findViewById(R.id.shapeButton);
        imageButton.setBackgroundColor(Color.BLACK);

        radioGroup = findViewById(R.id.rgpSelect);

        index1id = R.id.radioButton1;
        index2id = R.id.radioButton2;
        index3id = R.id.radioButton3;
        radioGroup.check(index1id);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == index1id) {
                    shapeType = 0;
                } else if (i == index2id) {
                    shapeType = 1;
                } else {
                    shapeType = 2;
                }
                drawingView.setShapeChoice(shapeType);
            }

        });
    }


    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    public void undoPress(View view) {
        drawingView.deleteLastShape();
    }

    public void onPickColour(View view) {
        new ColorPickerPopup.Builder(this)
                .initialColor(Color.RED) // Set initial color
                .enableBrightness(true) // Enable brightness slider or not
                .enableAlpha(true) // Enable alpha slider or not
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(drawingView, new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {
                        drawingView.setColor(color);
                        imageButton.setBackgroundColor(color);
                    }
                });
//        Intent intent = new Intent(this, ShapePickerActivity.class);
//        intent.putExtra("colorInfo", propertiesArray);
//        startActivityForResult(intent, 1);
    }

    public void onClearClick(View view) {
        drawingView.clear();

    }

    public void onFillClicked(View view) {
        drawingView.setFilled(((CheckBox) view).isChecked());
    }
}


