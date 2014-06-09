/**
 * Created by azman on 09/06/2014.
 */
/* the medium class which simulates the medium(-bus) */
class Medium {


    int myBusLength        = 100;         // in meters

    public static final int MAX_SIGNALS = 10;


    Signal[] mySignals;

    boolean isInInterval(int value, int start, int end)
    {
        if ( (value>=Math.min(start,end))&&(value<=Math.max(start,end)) )
            return true;
        else
            return false;
    }

    public void setMediumIdle()
    {
        for(int j=0;j<10;j++)
            if(mySignals[j]!=null)
                mySignals[j].setIdle();

    }
    public Medium()
    {
        mySignals = new Signal[MAX_SIGNALS];
    }

    public int getBusLength()
    {
        return myBusLength;
    }

    Signal[] getSignals()
    {
        return mySignals;
    }


    public boolean isCollision(int station, int position)
    {
        int index;
        for ( index=0; index<MAX_SIGNALS; index++ )
        {
            if ( (mySignals[index] != null) && (mySignals[index].getStationId()!=station) )
            {
                if ( isInInterval( position, mySignals[index].getLeftPart().getStart(),
                        mySignals[index].getLeftPart().getEnd() ) )
                {
                    if ( mySignals[index].getLeftPart().getCollisionFlag() == true )
                    {
                        return true;
                    }
                }
                if ( isInInterval( position, mySignals[index].getRightPart().getStart(),
                        mySignals[index].getRightPart().getEnd() ) ) {
                    if ( mySignals[index].getRightPart().getCollisionFlag() == true ) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isFree(int position) {

        int index;

        for ( index=0; index<MAX_SIGNALS; index++ ) {
            if ( mySignals[index] != null ) {
                if ( isInInterval( position, mySignals[index].getLeftPart().getStart(),
                        mySignals[index].getLeftPart().getEnd() ) ) {
                    return false;
                }
                if ( isInInterval( position, mySignals[index].getRightPart().getStart(),
                        mySignals[index].getRightPart().getEnd() ) ) {
                    return false;
                }

                return true;
            }
        }

        return true;
    }

    void sleep(int time) {
        try {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {

        }
    }

    public boolean send( int station, int position, int frameSize) {

        int index;

        for ( index=0; index<MAX_SIGNALS; index++ ) {
            if ( mySignals[index] == null ) {

                mySignals[index] = new Signal( station, position, frameSize, this);

                while(true) {
                    if ( mySignals[index].isSent() ) {
                        return true;
                    }
                    if ( isCollision(station, position) ) {
                        return false;
                    }
                    sleep(50);
                }
            }
        }

        return false;
    }

    public void update() {

        int i,j;
        int start,end, id;


        // update signals
        for ( i=0; i<MAX_SIGNALS; i++ ) {
            if ( mySignals[i] != null ) {
                mySignals[i].update();
                if ( mySignals[i].isDone() ) {
                    mySignals[i] = null;
                }
            }
        }

        // find collisions
        for ( i=0; i<MAX_SIGNALS; i++ ) {
            if ( mySignals[i] != null ) {
                id    = mySignals[i].getStationId();

                start = mySignals[i].getLeftPart().getStart();
                end   = mySignals[i].getLeftPart().getEnd();

                for ( j=0; j<MAX_SIGNALS; j++ ) {
                    if ( (mySignals[j] != null) && (mySignals[j].getStationId() != id)) {

                        if ( isInInterval( mySignals[j].getLeftPart().getStart(), start, end ) ) {
                            mySignals[i].getLeftPart().setCollisionFlag(true);
                            if (!mySignals[i].getRightPart().isSent())
                            {
                                mySignals[i].getRightPart().setCollisionFlag(true);
                                mySignals[j].getLeftPart().setCollisionFlag(true);
                            }
                            break;
                        }
                        if ( isInInterval( mySignals[j].getLeftPart().getEnd(), start, end ) ) {
                            mySignals[i].getLeftPart().setCollisionFlag(true);
                            if (!mySignals[i].getRightPart().isSent())
                            {
                                mySignals[i].getRightPart().setCollisionFlag(true);
                                mySignals[j].getLeftPart().setCollisionFlag(true);
                            }
                            break;
                        }
                        if ( isInInterval( mySignals[j].getRightPart().getStart(), start, end ) ) {
                            mySignals[i].getLeftPart().setCollisionFlag(true);
                            if (!mySignals[i].getRightPart().isSent())
                            {
                                mySignals[i].getRightPart().setCollisionFlag(true);
                                mySignals[j].getLeftPart().setCollisionFlag(true);
                            }
                            break;
                        }
                        if ( isInInterval( mySignals[j].getRightPart().getEnd(), start, end ) ) {
                            mySignals[i].getLeftPart().setCollisionFlag(true);
                            if (!mySignals[i].getRightPart().isSent())
                            {
                                mySignals[i].getRightPart().setCollisionFlag(true);
                                mySignals[j].getLeftPart().setCollisionFlag(true);
                            }
                            break;
                        }
                    }
                }

                start = mySignals[i].getRightPart().getStart();
                end   = mySignals[i].getRightPart().getEnd();

                for ( j=0; j<MAX_SIGNALS; j++ ) {
                    if ( (mySignals[j] != null) && (mySignals[j].getStationId() != id)) {

                        if ( isInInterval( mySignals[j].getLeftPart().getStart(), start, end ) ) {
                            mySignals[i].getRightPart().setCollisionFlag(true);
                            if (!mySignals[i].getLeftPart().isSent())
                            {
                                mySignals[i].getLeftPart().setCollisionFlag(true);

                                mySignals[j].getRightPart().setCollisionFlag(true);
                            }
                            break;
                        }
                        if ( isInInterval( mySignals[j].getLeftPart().getEnd(), start, end ) ) {
                            mySignals[i].getRightPart().setCollisionFlag(true);
                            if (!mySignals[i].getLeftPart().isSent())
                            {
                                mySignals[i].getLeftPart().setCollisionFlag(true);
                                mySignals[j].getRightPart().setCollisionFlag(true);
                            }                   break;
                        }
                        if ( isInInterval( mySignals[j].getRightPart().getStart(), start, end ) ) {
                            mySignals[i].getRightPart().setCollisionFlag(true);
                            if (!mySignals[i].getLeftPart().isSent())
                            {
                                mySignals[i].getLeftPart().setCollisionFlag(true);
                                mySignals[j].getRightPart().setCollisionFlag(true);
                            }
                            break;
                        }
                        if ( isInInterval( mySignals[j].getRightPart().getEnd(), start, end ) ) {
                            mySignals[i].getRightPart().setCollisionFlag(true);
                            if (!mySignals[i].getLeftPart().isSent())
                            {
                                mySignals[i].getLeftPart().setCollisionFlag(true);
                                mySignals[j].getRightPart().setCollisionFlag(true);
                            } break;
                        }
                    }
                }

            }
        }


    }

}

/*
 *
 */
