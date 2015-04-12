package com.example.mdeshpande.bouncingcircles;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements View.OnTouchListener {

    private  float x;
    private  float y;

    private ArrayList<Circles> mCircles = new ArrayList<Circles>();
    private Circles c;
    private GestureDetector mDetector;
    public float xPosition=100, xAcceleration,xVelocity = 0.0f;
    public float yPosition=100, yAcceleration,yVelocity = 0.0f;

    private static SensorManager sensorManager;
    public float frameTime = 0.666f;
    public float xmax,ymax;
    private MyCustomPanel ballView;
    int r =0;

    private Sensor sensor;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ballView = new MyCustomPanel(this);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
        addContentView(ballView, params);
        ballView.setOnTouchListener(this);

        mDetector =  new GestureDetector(this, new GestureListener());

        /*
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (sensor != null) {
            sensorManager.registerListener(mySensorEventListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");
        } else {
            Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
            Toast.makeText(this, "ORIENTATION Sensor not found",
                    Toast.LENGTH_LONG).show();
            finish();
        }
*/
        Display display = getWindowManager().getDefaultDisplay();
        xmax = (float)display.getWidth() - 50;
        ymax = (float)display.getHeight() - 50;

    }


    private SensorEventListener mySensorEventListener = new SensorEventListener() {

        // This method will update the UI on new sensor events
        @Override
        public void onSensorChanged(SensorEvent sensorEvent)
        {


                    xAcceleration = sensorEvent.values[0];
                    yAcceleration = sensorEvent.values[1];



            //System.out.println("ACC X "+xAcceleration+"  "+yAcceleration);
                  //  updateBall();
            //ballView.setXYPosition(xAcceleration,yAcceleration);


        }



        // I've chosen to not implement this method
        @Override
        public void onAccuracyChanged(Sensor arg0, int arg1)
        {
            // TODO Auto-generated method stub
        }
    };


    public void  updateBall()
    {
       // VelocityTracker velocity = VelocityTracker.obtain();
       // velocity.addMovement(movingE);

        //Calculate new speed

        xVelocity += (xAcceleration * frameTime);
       yVelocity += (yAcceleration * frameTime);


        //Calc distance travelled in that time
        float xS = (xVelocity/2)*frameTime;
        float yS = (yVelocity/2)*frameTime;

        System.out.println(" ACCE : "+xS+" ACCY: "+yS);
        //Add to position negative due to sensor
        //readings being opposite to what we want!
        xPosition += xS;
        yPosition += yS;

        System.out.println(" Before X POSITION :"+xPosition+"  Y: "+yPosition);

        if (xPosition >= xmax)
        {
            xPosition = xmax;
            xVelocity *= -1.0;
        }
        else if (xPosition < 0.0) {
            xPosition = 0;
            xVelocity *= -1.0;
        }


        if (yPosition >= ymax)
        {
            yPosition = ymax;
            yVelocity *= -1.0;
        }
        else if (yPosition < 0.0) {
            yPosition = 0;
            yVelocity *= -1.0;
        }






    }


    @Override
    protected void onStop()
    {
        // Unregister the listener
        sensorManager.unregisterListener(mySensorEventListener);
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class MyCustomPanel extends View {


        public MyCustomPanel(Context context) {
            super(context);

        }
        @Override
        public void draw(Canvas canvas) {

            Paint paint = new Paint();
            paint.setStrokeWidth(6);
            Paint mCirclePaint;
            mCirclePaint = new Paint();
            mCirclePaint.setColor(0xCCCCCC);

            mCirclePaint.setStrokeWidth(10);
            mCirclePaint.setStyle(Paint.Style.STROKE);

            System.out.println("IN side draw");

            for(Circles c: mCircles)
            {
                System.out.println(" circle  "+c.getOrigin().x+" "+c.getCurrent().y+"  "+ c.getRadius());
                canvas.drawCircle(c.getOrigin().x, c.getCurrent().y, r, paint);

            }

            //invalidate();

            canvas.drawCircle(xPosition,yPosition,50,paint);

            try {
                Thread.sleep(60);
            } catch (InterruptedException e) { }

            updateBall();
            invalidate();  // Force a re-draw



    }


    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,float velocityX, float velocityY) {

            xPosition = event1.getX();
            yPosition = event1.getY();

            xAcceleration = velocityX/100;
            yAcceleration = velocityY/100;



            System.out.println("Position of X at Fling : " + xPosition+"  Position of Y at Fling Y "+yPosition);



            updateBall();

            return true;
        }



    }




    @Override
    public boolean onTouch(View v, MotionEvent event) {


        mDetector.onTouchEvent(event);

        PointF curr = new PointF(event.getX(),event.getY());
        boolean touched = false;

        int eventaction = event.getAction();
        switch (eventaction ) {
            case MotionEvent.ACTION_DOWN: {


                break;

            }
            case MotionEvent.ACTION_MOVE:  break;// a pointer was moved break;

            case MotionEvent.ACTION_UP: { c = new Circles(curr, r);
                mCircles.add(c);
                System.out.println("THHHH ");
                ballView.invalidate();
                break;

            }

        }

        System.out.println("touched = "+touched);


        return true;
    }

}
