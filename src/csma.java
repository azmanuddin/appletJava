import java.applet.Applet;
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
/* the csma class */

public class csma extends Applet {


    private Image myOffScreenImage = null;
    private Graphics  myOffScreenGraphics = null;
    private boolean   myResized = true;

    Network myNetwork;
    public static Timer myTimer;
    int clicked=0;
    Button mySetSizeButton;
    Button stopButton;
    Button addButton;
    Button clearButton;

    TextField mySizeTextField;
    TextField posTextField;
    int start;
    int posArray[] = new int[10];
    int count=0;

    public void init()
    {
        myNetwork = new Network();

        // the network is initialized
        myNetwork.init();

        // the clock is started
        myTimer = new Timer(myNetwork,this,50);
        myTimer.start();


        setBackground(Color.yellow);

        mySetSizeButton = new Button("FrameSize:");
        stopButton = new Button("Stop");
        addButton = new Button("AddStation");
        clearButton = new Button("Clear");

        mySizeTextField = new TextField("150");
        posTextField = new TextField(3);

        setLayout (new BorderLayout());

        Panel p = new Panel();
        p.setLayout( new GridLayout(2,1));

        Panel p1 = new Panel();

        p1.setLayout(new FlowLayout());
        p1.add(addButton);
        p1.add(posTextField);
        p.add(p1);

        Panel panel = new Panel();
        panel.setLayout(new FlowLayout());


        panel.add(stopButton);
        panel.add(clearButton);
        panel.add(mySetSizeButton);
        panel.add(mySizeTextField);
        p.add(panel);

        add ("South", p );
        setSize(600,470);


    }

    public void destroy() {
    }

    public void update( Graphics g ) {

        if ( null == myOffScreenImage || myResized ) {
            myOffScreenImage = createImage( size().width,size().height );
            myOffScreenGraphics = myOffScreenImage.getGraphics();
            myResized = false;
        }

        myOffScreenGraphics.setColor( getBackground() );
        myOffScreenGraphics.fillRect( 0, 0, size().width,size().height );
        myOffScreenGraphics.setColor( g.getColor() );
        paint( myOffScreenGraphics );

        g.drawImage( myOffScreenImage, 0, 0, this );
    }

    public void paint( Graphics g ) {


        Integer time = new Integer(myTimer.myTime);

        // draw time

        g.setColor(Color.black);
//    g.drawString("Time:   "+time.toString(),80,10);


        // draw network
        myNetwork.paint(g);


    }

    public boolean handleEvent(Event e) {

        if ( myNetwork.handleEvent(e) ) {
            repaint();
            return true;
        }


        return super.handleEvent(e);

    }

    public boolean action (Event ev1, Object arg) {
        if (ev1.target instanceof Button)
        {
            String label = (String)arg;

            if (label.equals("FrameSize:"))
            {
                myNetwork.setFrameSize(Integer.parseInt(mySizeTextField.getText()));
                return true;
            }
            if(label.equals("Stop"))
            {
                System.out.println("pressed INIT!!");
                //init();

                myNetwork = new Network();

                // the network is initialized
                myNetwork.init();

                // the clock is started
                myTimer = new Timer(myNetwork,this,50);
                myTimer.start();


                setBackground(Color.yellow);



                for(int k=0;k<count;k++)
                    myNetwork.addStation(posArray[k]);
                //Network.stop();
                System.out.println("returned from Init!!");

                posTextField.setText("0");
            }


            if(label.equals("Clear"))
            {
                count=0;
                myNetwork = new Network();

                // the network is initialized
                myNetwork.init();

                // the clock is started
                myTimer = new Timer(myNetwork,this,50);
                myTimer.start();


                setBackground(Color.yellow);

                for(int k=0;k<count;k++)
                    myNetwork.addStation(posArray[k]);
                //Network.stop();
                System.out.println("returned from Init!!");

                posTextField.setText("0");


            }
            if(label.equals("AddStation"))
            {

                int position = Integer.parseInt(posTextField.getText());

                if((posTextField.getText()==null) || (posTextField.getText().equals("")))
                {
                    alarmFrame a = new alarmFrame("Enter some value!",400,410);
                    a.show();

                }
                else
                {
                    if(!(position>0 && position < 100))
                    {   alarmFrame a = new alarmFrame("Give Value between 0 and 100",400,410);
                        a.show();
                    }
                    else
                    {
                        int n=0;
                        for(int k=0;k<count;k++)
                            if(Math.abs(position-posArray[k])<15)
                            {
                                alarmFrame b = new alarmFrame("min. 15 distance",400,410);
                                b.show();
                                n=1;
                            }
                        if(n!=1)
                        {
                            posArray[count++]=position;
                            System.out.println("pressed the button!!");
                            System.out.println(position);
                            if(position!=0)
                                myNetwork.addStation(Integer.parseInt(posTextField.getText()));
                        }
                    }
                }
            }
            else
                return false;
        }
        return false;

    }


}