package bean;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.beans.*;

public class JumpingCirclesBean extends JPanel implements PropertyChangeListener, VetoableChangeListener {
    private static final long serialVersionUID = 1L;

    private final int xSize = 600;
    private final int ySize = 300;
    private Integer[] valuesX = {xSize / 4, 2 * xSize / 4, 3 * xSize / 4};
    private Integer[] valuesY = {50, 100, 150};
    private Boolean[] riseY = {true, true, true};
    private Integer[] jumpPeriod = {1, 10, 10};
    private Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};
    private String title = "Skaczące kółka";
    private JButton bLaunchButton = new JButton("Zmien tytul");
    private JTextField textFieldValuesX = new JTextField(12);
    private JTextField textFieldTitle = new JTextField(12);
    private JButton buttonChangeValuesX = new JButton("Change value");

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private VetoableChangeSupport vetoableChangeSupport = new VetoableChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        propertyChangeSupport.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        propertyChangeSupport.removePropertyChangeListener(pcl);
    }

    public void addVetoableChangeListener(VetoableChangeListener vcl){
        vetoableChangeSupport.addVetoableChangeListener(vcl);
    }

    public void removeVetoableChangeListener(VetoableChangeListener vcl){
        vetoableChangeSupport.removeVetoableChangeListener(vcl);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println(evt.getPropertyName() + " prop");
       if(evt.getPropertyName().equals("title"))
           title = evt.getNewValue().toString();
    }

    @Override
    public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException{
        System.out.println(evt.getPropertyName() + " veto");
        if(evt.getPropertyName().equals("valuesX")){
            Integer[] values = (Integer[])evt.getNewValue();
            if(values[0] < 100) {
                System.out.println("Pierwsza wartosc musi byc wieksza od 99! (to nie jest silnia, tylko wykrzyknik)");
                throw new PropertyVetoException("", evt);
            }
            valuesX = values;
        }
    }

    public JumpingCirclesBean() {
        this.setPreferredSize(new Dimension(xSize, ySize));
        bLaunchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle(textFieldTitle.getText());
            }
        });
        this.add(bLaunchButton);

        this.add(textFieldTitle);
        this.add(textFieldValuesX);

        buttonChangeValuesX.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] parts = textFieldValuesX.getText().split(",");
                Integer[] values = new Integer[]{Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])};
                setValuesX(values);
            }
        });
        this.add(buttonChangeValuesX);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                move();
            }
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, xSize, ySize);
        g.setColor(colors[0]);
        g.fillOval(valuesX[0], valuesY[0], 20, 20);
        g.setColor(colors[1]);
        g.fillOval(valuesX[1], valuesY[1], 20, 20);
        g.setColor(colors[2]);
        g.fillOval(valuesX[2], valuesY[2], 20, 20);

        Graphics2D g2 = (Graphics2D) g;
        Font titleFont = new Font("Arial", Font.BOLD, 20);
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D titleBounds = titleFont.getStringBounds(title, context);
        g.setFont(titleFont);
        g.drawString(title, xSize / 2 - (int) titleBounds.getWidth() / 2, 50);
    }

    private void move() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {

                    if (riseY[0])
                        valuesY[0] += jumpPeriod[0];
                    else
                        valuesY[0] -= jumpPeriod[0];
                    if (valuesY[0] > 219 || valuesY[0] < 21)
                        riseY[0] = !riseY[0];


                    if (riseY[1])
                        valuesY[1] += jumpPeriod[1];
                    else
                        valuesY[1] -= jumpPeriod[1];

                    if (valuesY[1] > 219 || valuesY[1] < 21)
                        riseY[1] = !riseY[1];


                    if (riseY[2])
                        valuesY[2] += jumpPeriod[2];
                    else
                        valuesY[2] -= jumpPeriod[2];
                    if (valuesY[2] > 219 || valuesY[2] < 21)
                        riseY[2] = !riseY[2];

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    repaint();
                }
            }
        }).start();
    }

    public Integer[] getValuesX() {
        return valuesX;
    }

    public void setValuesX(Integer[] valuesX){
        try {
            this.valuesX = new Integer[valuesX.length];
            for (int i = 0; i < valuesX.length; i++)
                this.valuesX[i] = valuesX[i];
            vetoableChangeSupport.fireVetoableChange("valuesX", this.valuesX, valuesX);
        } catch(Exception e){e.printStackTrace();}
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        propertyChangeSupport.firePropertyChange("title", this.title, title);
        this.title = title;
    }

    public Integer[] getValuesY() {
        return valuesY;
    }

    public void setValuesY(Integer[] valuesY) {
        this.valuesY = valuesY;
    }

    public Boolean[] getRiseY() {
        return riseY;
    }

    public void setRiseY(Boolean[] riseY) {
        this.riseY = riseY;
    }

    public Color[] getColors() {
        return colors;
    }

    public void setColors(Color[] colors) {
        this.colors = colors;
    }

    public Integer[] getJumpPeriod() {
        return jumpPeriod;
    }

    public void setJumpPeriod(Integer[] jumpPeriod) {
        this.jumpPeriod = new Integer[jumpPeriod.length];
        for (int i = 0; i < jumpPeriod.length; i++)
            this.jumpPeriod[i] = jumpPeriod[i];
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(600, 300));
        frame.setContentPane(new JumpingCirclesBean());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }
}
