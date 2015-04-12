import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import com.example.mdeshpande.bouncingcircles.Circles;


import java.util.ArrayList;

public class DrawingView extends View {

    ArrayList<Circles> mCircles = new ArrayList<Circles>();

    public DrawingView(Context context) {
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
            canvas.drawCircle(c.getOrigin().x, c.getCurrent().y, c.getRadius(), paint);

        }

    }

    public void updateCircles(Circles c)
    {
        mCircles.add(c);
    }
}