/**
 * Created by azman on 09/06/2014.
 */
import java.util.Random;
import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.lang.Math;
import java.util.Random;
class Counter {

    int myCount;

    public Counter( int start )
    {
        myCount = start;
    }

    public void decrement()
    {
        myCount--;
        return;
    }

    public void increment()
    {
        myCount++;
        return;
    }

    public int getCount()
    {
        return myCount;
    }

    public void setCount(int count)
    {
        myCount = count;
    }

}

/* the left and right parts of a signal are simualated */

