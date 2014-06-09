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
/* the network class which simulates the network */
class Network {

    public static final int NUM_STATIONS = 4;

    Medium myMedium;
    Station[] myStations;

    int   myFrameSize = 150;
    int i=0;


    public Network() {
    /* initializes the medium and also indicates the number of stations */
        myMedium = new Medium();
        myStations = new Station[NUM_STATIONS];

    }
    public void stop()
    {
        for(int j=0;j<i;j++)
            myStations[j].setState(0); //setting the state of stations to idle

        update();
    }
    public void addStation(int x)
    {
        myStations[i] = new Station(i++,x, this, myMedium);

    }

    public void init() {

        i=0;
    /* the stations are initialized */

        // myStations[0] = new Station(0, 10, this, myMedium);

        // myStations[1] = new Station(1, 50, this, myMedium);
        // myStations[2] = new Station(2, 90, this, myMedium);
    }

    public int convertPositionToPixel(int position) {
        return (int)Math.round((double)(position*400)/(double)myMedium.getBusLength());
    }


    public int getFrameSize() {
        return myFrameSize;
    }

    public void setFrameSize(int size) {
        myFrameSize = size;
    }

    public void update() {

        int index;
        if(i>1)
            myMedium.update();

        for (index = 0; index</*NUM_STATIONS*/i; index++) {
            myStations[index].update();
        }
    }

    public void paint(Graphics g) {

        int index;
        int pixel;

        Integer busLength = new Integer(myMedium.getBusLength());
        Integer stationNumber;
        Integer backoffCount;
        Integer backoffRemain;

        //draw states
        g.setColor(Color.white);
        g.fillRect(120,350,20,20);
        g.setColor(Color.black);
        g.drawString("IDLE",150,360);

        g.setColor(Color.green);
        g.fillRect(240,350,20,20);
        g.setColor(Color.black);
        g.drawString("TRANSMIT",270,360);

        g.setColor(Color.blue);
        g.fillRect(360,350,20,20);
        g.setColor(Color.black);
        g.drawString("CARRIER-SENSE",380,360);


        g.setColor(Color.red);
        g.fillRect(480,350,20,20);
        g.setColor(Color.black);
        g.drawString("BACK-OFF",510,360);


        // draw bus

        g.setColor(Color.black);
        g.fillRect(100,200,400,10);

        g.drawString("0",90,225);
        g.drawString(busLength.toString(),500,225);
        g.drawLine(100,210,100,220);
        g.drawLine(498,210,498,220);

        // draw stations and indicate their states

        for (index = 0; index</*NUM_STATIONS*/i; index++) {
            pixel = convertPositionToPixel(myStations[index].getPosition());
            stationNumber = new Integer(index+1);
            switch (myStations[index].getState()) {
                case Station.IDLE:
                    g.setColor(Color.white);
                    break;
                case Station.CARRIER_SENSE:
                    g.setColor(Color.blue);
                    break;
                case Station.TRANSMIT:
                    g.setColor(Color.green);
                    break;
                case Station.BACKOFF:
                    g.setColor(Color.red);
                    break;
                default:
                    g.setColor(Color.pink);
            }


            g.fillRect(88+pixel,230,25,25);
            g.setColor(Color.black);
            g.drawRect(88+pixel,230,25,25);
            g.drawString("Station "+stationNumber.toString(),85+pixel,270);
            g.drawLine(100+pixel,200, 100+pixel,230);
            g.drawString("Station "+stationNumber.toString(),50,30+index*40+15);

            if (myStations[index].getState() == Station.BACKOFF) {
                backoffCount = new Integer(myStations[index].getBackoffCount());
                backoffRemain = new Integer(myStations[index].getBackoffRemain());

                g.drawString("k = " + backoffCount.toString(),90+pixel-3,300);
                g.drawString("t = " + backoffRemain.toString(),90+pixel-3,320);

            }

        }

        // draw frames

        int start, end, id;
        Signal[] signals = myMedium.getSignals();

        for ( index=0; index<myMedium.MAX_SIGNALS; index++ ) {
            if ( signals[index] != null ) {

                id    = signals[index].getStationId();

                start = convertPositionToPixel(signals[index].getLeftPart().getStart());
                end   = convertPositionToPixel(signals[index].getLeftPart().getEnd());
                if (signals[index].getLeftPart().getCollisionFlag() )
                {
                    g.setColor(Color.red);

                }
                else
                    g.setColor(Color.black);

                g.fillRect(100+start,(id+1)*40,Math.abs(end-start),8);

                start = convertPositionToPixel(signals[index].getRightPart().getStart());
                end   = convertPositionToPixel(signals[index].getRightPart().getEnd());
                if (signals[index].getRightPart().getCollisionFlag() )
                {
                    g.setColor(Color.red);

                }
                else
                    g.setColor(Color.black);
                g.fillRect(100+end,(id+1)*40,Math.abs(start-end),8);

            }
        }



    }


    public boolean handleEvent(Event e) {

        if (e.id == Event.MOUSE_DOWN) {
            if(i==1)
            {
                Frame alarm = new alarmFrame("Please add another station..min 2 stations",400,410);
                alarm.show();
                System.out.println("Please add another station..min 2 stations");
            }
            else
            {
                int index;
                int pixel;

                for (index = 0; index<NUM_STATIONS; index++) {
                    pixel = convertPositionToPixel(myStations[index].getPosition());
                    if ((e.x>=88+pixel)&&(e.x<=113+pixel)&&(e.y>=230)&&(e.y<=255)) {
                        myStations[index].setSendRequest(true);




                        return true;
                    }
                }

            }}

        return false;

    }


}
