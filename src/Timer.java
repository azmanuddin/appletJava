import java.awt.*;
import java.util.Random;
import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.lang.Math;
import java.util.Random;
/**
 * Created by azman on 09/06/2014.
 */
class Timer extends Thread {

    public  int myTime = 0;
    public static int myTime1 = 0;
    Network myNetwork;
    Component myComponent;
    int myInterval;

    void setTime(int value)
    {
        myTime = value;
    }

    void sleep(int time)
    {
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e)
        {

        }
    }

    public Timer(Network network, Component component, int interval) {
        myNetwork = network;
        myComponent = component;
        myInterval = interval;

    }

    public void run()
    {
        while (true)
        {
            myNetwork.update();
            myComponent.repaint();
            sleep(myInterval);
            myTime++;
        }
    }
}


