/**
 * Created by azman on 09/06/2014.
 */
import java.util.Random;
import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.lang.Math;
import java.util.Random;
/* the various kinds of (messages for transmission) signals are simulated here */
class Signal
{

    Part myLeftPart;
    Part myRightPart;

    int myStationId;

    int getStationId()
    {
        return myStationId;
    }
    public void setIdle()
    {
        myLeftPart.myDoneFlag=true;
        myRightPart.myDoneFlag=true;
    }
    Part getLeftPart()
    {
        return myLeftPart;
    }

    Part getRightPart()
    {
        return myRightPart;
    }

    public Signal(int stationId, int position, int frameSize, Medium medium)
    {
        myStationId = stationId;
        myLeftPart = new Part(position,position,-1,frameSize,medium);
        myRightPart = new Part(position,position, 1,frameSize,medium);
    }

    public void update()
    {
        myLeftPart.update();
        myRightPart.update();
    }

    public boolean isDone()
    {
        return myLeftPart.getDoneFlag() && myRightPart.getDoneFlag();
    }

    public boolean isCollision()
    {
        return myLeftPart.getCollisionFlag() || myRightPart.getCollisionFlag();
    }

    boolean isSent()
    {
        return myLeftPart.isSent() && myRightPart.isSent();
    }


}
