import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.lang.Math.*;
import java.awt.event.*;


public class ColorSampler extends JFrame
{
    protected JButton buttonSave;
    protected JButton buttonReset;
    protected JButton RedUpBtn;
    protected JButton RedDownBtn;
    protected JButton GreenUpBtn;
    protected JButton GreenDownBtn;
    protected JButton BlueUpBtn;
    protected JButton BlueDownBtn;
    protected JPanel buttonPanel;
    protected JPanel selectPanel;
    protected JPanel leftPanel;

    //this is for choosing the color we have 3 field for rgb
    //which is green, blue and red
    protected JTextField green;
    protected JTextField blue;
    protected JTextField red;

    // list for the colors and window for showing the colors
    protected JList<String> listColors;
    protected DrawColor draw;
    protected ColorType currentColor;
    protected ArrayList<ColorType> colorSet;
    protected String colorNames[];
    protected int listSize;
    protected int IndexForColor;


    static ColorSampler program;

    //main methid whcih would try to run the program
    public static void main (String[] args)
    {
        try
        {
            program = new ColorSampler("Color Sampler");
        }
        catch (IOException e)
        {
            System.out.println("File not found");
            System.exit(0);
        }
    }

    public ColorSampler(String title) throws IOException
    {
        super(title);
        setBounds(100, 100, 450, 380);
        addWindowListener(new WindowDestroyer());

        listColors = new JList<String>();
        listColors.addListSelectionListener( new ListHandler());

        //we just need one set of save and rest
        buttonSave = new JButton("Save");
        buttonReset = new JButton("Reset");

        /*
        for thos secion we will beed + and - btn for all three variable
        */

        //red
        RedUpBtn = new JButton("+");
        RedDownBtn = new JButton("-");

        //green
        GreenUpBtn = new JButton("+");
        GreenDownBtn = new JButton("-");

        //blue
        BlueUpBtn = new JButton("+");
        BlueDownBtn = new JButton("-");

        //save , reset
        buttonSave.addActionListener( new ActionHandler() );
        buttonReset.addActionListener( new ActionHandler() );

        //red
        RedUpBtn.addActionListener( new ActionHandler() );
        RedDownBtn.addActionListener( new ActionHandler() );

        //green
        GreenUpBtn.addActionListener( new ActionHandler() );
        GreenDownBtn.addActionListener( new ActionHandler() );

        //blue
        BlueUpBtn.addActionListener( new ActionHandler() );
        BlueDownBtn.addActionListener( new ActionHandler() );


        draw = new DrawColor();

        //this is the best bounds for a 1080p display
        //we wil probably have to change that for lower or higher
        //resolution displays
        draw.setBounds(10, 10, 200, 200);

        //our 3 panels, one for color window, one for list and one for save and rest at the btns
        buttonPanel = new JPanel();
        leftPanel = new JPanel();
        selectPanel = new JPanel();

        //load the original colors
        File infile = new File("colors.txt");
        if( !infile.exists() )
        {
            throw new IOException("File not found");
        }
        FileInputStream stream = new FileInputStream(infile);
        InputStreamReader reader = new InputStreamReader(stream);
        StreamTokenizer tokens = new StreamTokenizer(reader);

        colorSet = new ArrayList<ColorType>();

        while( tokens.nextToken() != tokens.TT_EOF)
        {
            currentColor = new ColorType();

            currentColor.name = (String)tokens.sval;
            tokens.nextToken();
            currentColor.r = (int)tokens.nval;
            tokens.nextToken();
            currentColor.g = (int)tokens.nval;
            tokens.nextToken();
            currentColor.b = (int)tokens.nval;
            colorSet.add(currentColor);
            IndexForColor++;
        }

        stream.close();

        IndexForColor--;
        listSize = IndexForColor + 1;
        IndexForColor = 0;
        currentColor = new ColorType(colorSet.get(IndexForColor));
        colorNames = new String[listSize];
        for( int i = 0; i < listSize; i++ )
        {
            colorNames[i] = (colorSet.get(i)).name;
        }

        listColors.setListData(colorNames);

        selectPanel.setLayout( new GridLayout(3, 4, 5, 5));

        //save and rest
        buttonPanel.add(buttonSave);
        buttonPanel.add(buttonReset);

        //red
        red = new JTextField(String.valueOf(currentColor.r));
        selectPanel.add(new JLabel("Red:"));
        selectPanel.add(red);
        selectPanel.add(RedDownBtn);
        selectPanel.add(RedUpBtn);

        //green
        green = new JTextField(String.valueOf(currentColor.g));
        selectPanel.add(new JLabel("Green:"));
        selectPanel.add(green);
        selectPanel.add(GreenDownBtn);
        selectPanel.add(GreenUpBtn);

        //blue
        blue = new JTextField(String.valueOf(currentColor.b));
        selectPanel.add(new JLabel("Blue:"));
        selectPanel.add(blue);
        selectPanel.add(BlueDownBtn);
        selectPanel.add(BlueUpBtn);


        leftPanel.setLayout(null);
        leftPanel.add(draw);
        leftPanel.add(selectPanel);
        leftPanel.add(buttonPanel);

        draw.setBounds(10, 10, 250, 150);
        selectPanel.setBounds(10, 170, 250, 100);
        buttonPanel.setBounds(50, 290, 200, 50 );

        getContentPane().setLayout(null);

        getContentPane().add(leftPanel);
        getContentPane().add(listColors);

        leftPanel.setBounds(10, 10, 270, 400);
        listColors.setBounds(300, 20, 130, 310);

        setVisible(true);
        IndexForColor = 0;
    }

    private class WindowDestroyer extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            try
            {
                output();
            }
            catch (IOException ex)
            {
                System.out.println("File not found");
            }
            System.exit(0);
        }

        public void output() throws IOException
        {
            File ofile = new File("colors.txt");
            if( !ofile.exists() )
            {
                throw new IOException("File not found");
            }
            FileOutputStream ostream = new FileOutputStream(ofile);
            PrintWriter writer = new PrintWriter(ostream, true);
            for( int i = 0; i < listSize; i++ )
            {
                writer.println((colorSet.get(i)).toString());
            }
            ostream.close();
        }
    }

    private class ListHandler implements ListSelectionListener
    {
        public void valueChanged( ListSelectionEvent e )
        {
            if( e.getSource() == listColors )
                if( !e.getValueIsAdjusting() )
                {
                    IndexForColor = listColors.getSelectedIndex();
                    currentColor = new ColorType(colorSet.get(IndexForColor));
                    red.setText(String.valueOf(currentColor.r));
                    green.setText(String.valueOf(currentColor.g));
                    blue.setText(String.valueOf(currentColor.b));
                    draw.repaint();
                    program.setTitle("Color Sampler");
                }
        }
    }

    private class ActionHandler implements ActionListener
    {
        public void actionPerformed( ActionEvent e )
        {
            if( e.getSource() == buttonSave )
            {
                colorSet.set(IndexForColor, new ColorType(currentColor));
                program.setTitle("Color Sampler");
            }
            else if( e.getSource() == buttonReset )
            {
                currentColor = new ColorType(colorSet.get(IndexForColor));
                program.setTitle("Color Sampler");
            }
            else if( e.getSource() == RedUpBtn )
            {
                currentColor.r = Math.min( 255, currentColor.r+5 );
                program.setTitle("Color Sampler*");
            }
            else if( e.getSource() == RedDownBtn )
            {
                currentColor.r = Math.max( 0, currentColor.r-5 );
                program.setTitle("Color Sampler*");
            }
            else if( e.getSource() == GreenUpBtn )
            {
                currentColor.g = Math.min( 255, currentColor.g+5 );
                program.setTitle("Color Sampler*");
            }
            else if( e.getSource() == GreenDownBtn )
            {
                currentColor.g = Math.max( 0, currentColor.g-5 );
                program.setTitle("Color Sampler*");
            }
            else if( e.getSource() == BlueUpBtn )
            {
                currentColor.b = Math.min( 255, currentColor.b+5 );
                program.setTitle("Color Sampler*");
            }
            else if( e.getSource() == BlueDownBtn )
            {
                currentColor.b = Math.max( 0, currentColor.b-5 );
                program.setTitle("Color Sampler*");
            }

            red.setText(String.valueOf(currentColor.r));
            green.setText(String.valueOf(currentColor.g));
            blue.setText(String.valueOf(currentColor.b));
            draw.repaint();
        }
    }

    private class ColorType
    {
        public int r;
        public int g;
        public int b;

        public String name;

        ColorType()
        {
            r = 0;
            g = 0;
            b = 0;
            name = "";
        }
        public ColorType( ColorType Other )
        {
            this.r = Other.r;
            this.g = Other.g;
            this.b = Other.b;
            this.name = Other.name;
        }
        public String toString()
        {
            return name + "\t\t" + r + "\t" + g + "\t" + b;
        }
    }

    private class DrawColor extends JComponent
    {
        public void paint(Graphics graph)
        {
            Dimension d = getSize();

            graph.setColor( new Color(currentColor.r, currentColor.g, currentColor.b));
            graph.fillRect(1, 1, d.width-2, d.height-2);
        }
    }
}
