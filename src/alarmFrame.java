import java.awt.*;

/**
 * Created by azman on 09/06/2014.
 */
class alarmFrame extends Frame
{
    public alarmFrame(String msg,int x1,int y1)
    {
        setSize(170,140);
        setLocation(x1-200,y1-350);
        setLayout(new GridLayout(2,1));
        Label m = new Label(msg);
        Button ok = new Button("OK");
        add(m);
        add(ok);


    }

    public boolean action (Event ev1, Object arg)
    {
        if (ev1.target instanceof Button)
        {
            String label = (String)arg;

            if (label.equals("OK"))
            {
                setVisible(false);
            }
        }
        return true;
    }
}
