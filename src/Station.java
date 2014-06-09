import java.util.Random;
import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.lang.Math;
import java.util.Random;

/**
 * Created by azman on 09/06/2014.
 */

class Station extends Thread {

    public static final int IDLE = 0;
    public static final int CARRIER_SENSE = 1;
    public static final int TRANSMIT = 2;
    public static final int BACKOFF = 3;


    Counter myCounter;

    Medium myMedium;
    Network myNetwork;

    int myBackoffCount;

    int myPosition;
    int myState;
    int myId;

    Random myRandom;


    boolean mySendRequest;

    public Station(int id, int position, Network network, Medium medium) {
        myPosition = position;
        myMedium = medium;
        myNetwork = network;
        myId = id;
        myState = IDLE;
        mySendRequest = false;
        myRandom = new Random(this.hashCode());
        this.start();
    }

    public void setSendRequest( boolean flag ) {
        mySendRequest = flag;
    }
    public int getState() {
        return myState;
    }


    public void setState(int x)
    {
        myState=x;
    }

    public int getBackoffCount() {
        return myBackoffCount;
    }

    public int getBackoffRemain() {
        return myCounter.getCount();
    }

    public int getPosition() {
        return myPosition;
    }

    void sleep(int time) {
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
            ;
        }
    }

    public void backoff(int count) {

        int slotTime = 200;

        myCounter = new Counter(
                (int)((myRandom.nextDouble()*(double)Math.pow(2,count))*(double)slotTime)
        );

        while(myCounter.getCount()>0) {
            sleep(50);
        }

    }

    public void update() {
        if (myState==BACKOFF) {
            myCounter.decrement();
        }
        return;
    }

    public boolean send() {

        myState = CARRIER_SENSE;
        while(!myMedium.isFree(myPosition)) {
            sleep(100);
        }

        myState = TRANSMIT;
        if (myMedium.send(myId, myPosition, myNetwork.getFrameSize())==false) {
            myState = BACKOFF;
            return false;
        }
        else {
            myState = IDLE;
            return true;
        }

    }

    public void run() {

        while (true) {
            if (mySendRequest==true) {
                mySendRequest=false;

                myBackoffCount = 0;
                while (send()==false) {
                    backoff(myBackoffCount);
                    myBackoffCount++;
                }
            }
            sleep(100);
        }
    }

}