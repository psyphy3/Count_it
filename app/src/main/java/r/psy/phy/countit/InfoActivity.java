package r.psy.phy.countit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        CheckBox touchCheck=findViewById(R.id.touchCheck2);
        CheckBox btnCheck=findViewById(R.id.btnCheck2);
        CheckBox shkCheck=findViewById(R.id.shkCheck2);
        CheckBox negCheck=findViewById(R.id.negCheck);
        CheckBox screenCheck=findViewById(R.id.screenCheck2);
        CheckBox vibCheck = findViewById(R.id.vibCheck);
        SeekBar shakeSense =findViewById(R.id.shakeSense);

        int shk= 15-(int) MainActivity.shakevar;
        shakeSense.setProgress(shk);

        if (shakeSense != null) {
            shakeSense.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    MainActivity.shakevar= 15-progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Write code to perform some action when touch is started.
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // Write code to perform some action when touch is stopped.

                }
            });
        }




        if (!MainActivity.touchToggle){
            touchCheck.setChecked(true);
            touchCheck.setTextColor(Color.WHITE);
        }else{
            touchCheck.setChecked(false);
            touchCheck.setTextColor(Color.GRAY);
        }

        if (MainActivity.btnToggle){
            btnCheck.setChecked(true);
            btnCheck.setTextColor(Color.WHITE);
        }else{
            btnCheck.setChecked(false);
            btnCheck.setTextColor(Color.GRAY);
        }

        if (MainActivity.shkToggle){
            shkCheck.setChecked(true);
            shkCheck.setTextColor(Color.WHITE);
        }else{
            shkCheck.setChecked(false);
            shkCheck.setTextColor(Color.GRAY);
        }

        if (MainActivity.negToggle){
            negCheck.setChecked(true);
            negCheck.setTextColor(Color.WHITE);
        }else{
            negCheck.setChecked(false);
            negCheck.setTextColor(Color.GRAY);
        }

        if (MainActivity.screenToggle){
            screenCheck.setChecked(true);
            screenCheck.setTextColor(Color.WHITE);
        }else{
            screenCheck.setChecked(false);
            screenCheck.setTextColor(Color.GRAY);
        }

        if (MainActivity.vibToggle){
            vibCheck.setChecked(true);
            vibCheck.setTextColor(Color.WHITE);
        }else{
            vibCheck.setChecked(false);
            vibCheck.setTextColor(Color.GRAY);
        }

        touchCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MainActivity.touchToggle=false;
                    touchCheck.setTextColor(Color.WHITE);
                }
                else{
                    MainActivity.touchToggle=true;
                    touchCheck.setTextColor(Color.GRAY);
                }
            }
        });

        btnCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MainActivity.btnToggle=true;
                    btnCheck.setTextColor(Color.WHITE);
                }
                else{
                    MainActivity.btnToggle=false;
                    btnCheck.setTextColor(Color.GRAY);
                }
            }
        });

        shkCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MainActivity.shkToggle=true;
                    shkCheck.setTextColor(Color.WHITE);
                }
                else{
                    MainActivity.shkToggle=false;
                    shkCheck.setTextColor(Color.GRAY);
                }
            }
        });

        negCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MainActivity.negToggle=true;
                    negCheck.setTextColor(Color.WHITE);
                }
                else{
                    MainActivity.negToggle=false;
                    negCheck.setTextColor(Color.GRAY);
                }
            }
        });

        screenCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MainActivity.screenToggle=true;
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    screenCheck.setTextColor(Color.WHITE);
                }
                else{
                    MainActivity.screenToggle=false;
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    screenCheck.setTextColor(Color.GRAY);
                }
            }
        });


        vibCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MainActivity.vibToggle=true;
                    vibCheck.setTextColor(Color.WHITE);
                }
                else{
                    MainActivity.vibToggle=false;
                    vibCheck.setTextColor(Color.GRAY);
                }
            }
        });

        TextView infoBtn =findViewById(R.id.infoBtn2);
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}