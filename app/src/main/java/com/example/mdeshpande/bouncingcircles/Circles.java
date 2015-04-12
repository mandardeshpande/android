package com.example.mdeshpande.bouncingcircles;

import android.graphics.PointF;

/**
 * Created by mdeshpande on 4/5/15.
 */
public class Circles
{
    private PointF mOrigin;
    private PointF mCurrent;
    private float radius;

    public Circles(PointF origin, float circleRadius)
    {
        mOrigin = mCurrent = origin;
        radius = circleRadius;
    }

    public PointF getCurrent()
    {
        return mCurrent;
    }

    public void setCurrent(PointF current)
    {
        mCurrent = current;
    }

    public PointF getOrigin()
    {
        return mOrigin;
    }

    public float getRadius()
    {
        return radius;
    }
}
