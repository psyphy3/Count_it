package r.psy.phy.countit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public double countvar;
    public static boolean touchToggle=false;
    public static boolean btnToggle=false;
    public static boolean shkToggle=false;
    public static boolean negToggle=false;
    public static boolean screenToggle=false;
    public static boolean vibToggle=false;
    public static double inc=1;
    public static double dec=1;
    public static float shakevar=12;

    //////////////////////////




    /////////////////
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            if (mAccel > shakevar && shkToggle) {
                //Toast.makeText(getApplicationContext(), "Shake event detected", Toast.LENGTH_SHORT).show();
                countUp();
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    @Override
    protected void onResume() {
        TextView count=findViewById(R.id.counttext);
        TextView minus=findViewById(R.id.minusBtn);
        TextView reset=findViewById(R.id.resetBtn);
        CheckBox screenCheck=findViewById(R.id.screenCheck);
        CheckBox touchCheck=findViewById(R.id.touchCheck);

        if (!touchToggle){
            count.setClickable(true);
            minus.setClickable(true);
            reset.setClickable(true);
            touchCheck.setChecked(false);
        }else{
            count.setClickable(false);
            minus.setClickable(false);
            reset.setClickable(false);
            touchCheck.setChecked(true);
        }


        if (screenToggle){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            screenCheck.setChecked(true);

        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            screenCheck.setChecked(false);
        }


        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    protected void onPause() {

        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);

// Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

// Storing the key and its value as the data fetched from edittext
        myEdit.putString("count", new DecimalFormat("#.##").format(countvar));

// Once the changes have been made,
// we need to commit to apply those changes made,
// otherwise, it will throw an error
        myEdit.commit();

        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    @Override
    protected void onStop() {
        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("count", new DecimalFormat("#.##").format(countvar));
        myEdit.commit();

        super.onStop();
    }


    ////////////////////

    public void countUp(){

        countvar=countvar+inc;
        TextView count=findViewById(R.id.counttext);
        count.setText(new DecimalFormat("#.##").format(countvar));
    }

    @Override    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_HEADSETHOOK){

            if (btnToggle){
                countUp();
            }


            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        //////////////
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        ////////////////

        TextView count=findViewById(R.id.counttext);
        TextView minus=findViewById(R.id.minusBtn);
        TextView reset=findViewById(R.id.resetBtn);
        TextView info=findViewById(R.id.infoBtn);
        CheckBox screenCheck=findViewById(R.id.screenCheck);
        CheckBox touchCheck=findViewById(R.id.touchCheck);
        TextView incText =findViewById(R.id.incText);
        TextView decText =findViewById(R.id.decText);
        incText.setText(Double.toString(inc));
        decText.setText(Double.toString(dec));

        touchCheck.setText("\uD83D\uDD12");
        screenCheck.setText("\uD83D\uDCF1");


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        try{
            countvar= Double.parseDouble(sh.getString("count",""));
        }catch(Exception e){
            countvar=0;
        }

        count.setText(new DecimalFormat("#.##").format(countvar));

        if (!touchToggle){
            count.setClickable(true);
            minus.setClickable(true);
            reset.setClickable(true);
            touchCheck.setChecked(false);
        }else{
            count.setClickable(false);
            minus.setClickable(false);
            reset.setClickable(false);
            touchCheck.setChecked(true);
        }


        if (screenToggle){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            screenCheck.setChecked(true);

        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            screenCheck.setChecked(false);
        }



        final Vibrator vibe = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);


        screenCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    screenToggle=true;
                }
                else{
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                    screenToggle=false;
                }
            }
        });

        touchCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    touchToggle=true;
                    count.setClickable(false);
                    minus.setClickable(false);
                    reset.setClickable(false);
                }
                else{
                    touchToggle=false;
                    count.setClickable(true);
                    minus.setClickable(true);
                    reset.setClickable(true);
                }
            }
        });

        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!touchToggle){
                    if(vibToggle){
                        vibe.vibrate(70);
                    }

                    countUp();
                }

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!touchToggle){
                    if ((countvar-dec>=0) || (countvar-dec<0 && negToggle)){

                        if (vibToggle){
                            vibe.vibrate(40);
                        }

                        countvar=countvar-dec;
                        count.setText(new DecimalFormat("#.##").format(countvar));
                    }
                }

            }
        });

       reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!touchToggle){

                    if (vibToggle){
                        vibe.vibrate(150);
                    }
                    countvar=0;
                    count.setText(new DecimalFormat("#.##").format(countvar));
                }


            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,InfoActivity.class);
                startActivity(intent);

            }
        });

        incText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        try{
                                            inc=Double.parseDouble(userInput.getText().toString());
                                            incText.setText(userInput.getText());
                                        } catch (Exception e) {
                                            Toast myToast = Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_SHORT);
                                            myToast.show();
                                        }

                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });


        decText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get prompts.xml view
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View promptsView = li.inflate(R.layout.prompts, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText userInput = (EditText) promptsView
                        .findViewById(R.id.editTextDialogUserInput);

                // set dialog message
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        try{
                                            dec=Double.parseDouble(userInput.getText().toString());
                                            decText.setText(userInput.getText());
                                        } catch (Exception e) {
                                            Toast myToast = Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_SHORT);
                                            myToast.show();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });

        




    }
}