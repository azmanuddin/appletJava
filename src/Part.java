/**
 * Created by azman on 09/06/2014.
 */
import java.util.Random;
import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.lang.Math;
import java.util.Random;
class Part {

    Medium myMedium;

    int myMaxFrameSize;
    int myLength;

    int myOffset;
    int myStart;
    int myEnd;

    public boolean myDoneFlag;

    boolean myCollisionFlag;

    public Part( int start, int end, int offset, int frameSize, Medium medium )
    {
        myStart = start;
        myEnd = end;
        myOffset = offset;
        myCollisionFlag = false;
        myMaxFrameSize = frameSize;
        myMedium = medium;
        myLength = 0;
        myDoneFlag = false;
    }

    void setCollisionFlag( boolean flag )
    {
        myCollisionFlag = flag;
    }

    boolean getCollisionFlag()
    {
        return myCollisionFlag;
    }

    boolean getDoneFlag()
    {
        return myDoneFlag;
    }

    int getStart()
    {
        return myStart;
    }

    int getEnd()
    {
        return myEnd;
    }

    boolean isSent()
    {
        return (myLength > myMaxFrameSize);
    }

    public void update()
    {

        if (myLength>0 && myStart==myEnd)
        {
            myDoneFlag=true;
            return;
        }
        myLength = myLength + Math.abs(myOffset);
        myStart = myStart + myOffset;
        if ( myLength > myMaxFrameSize )
            myEnd = myEnd + myOffset;

        if (myStart<0)
            myStart = 0;

        if (myStart>myMedium.getBusLength() )//ACTULLY buslength=100
            myStart = myMedium.getBusLength();

        if (myEnd<0)
            myEnd = 0;

        if (myEnd>myMedium.getBusLength() )
            myEnd = myMedium.getBusLength();

    }

}

