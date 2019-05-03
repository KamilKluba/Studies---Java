package bean;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Customizer;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

public class JumpingCirclesBeanCustomizer extends JPanel implements Customizer{
    private static final long serialVersionUID = 1L;

    private static final int xSize = 200;
    private static final int ySize = 120;
    private JumpingCirclesBean bean;
   // private PropertyEditor colorEditor;

    private JTextField textFieldTitle;
    private JSlider slider[];
    private JButton buttonSetValues;
    private String title;

    public JumpingCirclesBeanCustomizer(){
        textFieldTitle = new JTextField(20);
       slider = new JSlider[]{new JSlider(JSlider.HORIZONTAL, 1, 10, 1),
                             new JSlider(JSlider.HORIZONTAL, 1, 10, 1),
                             new JSlider(JSlider.HORIZONTAL, 1, 10, 1),
                             new JSlider(JSlider.HORIZONTAL, 1, 400, 1),
                             new JSlider(JSlider.HORIZONTAL, 1, 400, 1),
                             new JSlider(JSlider.HORIZONTAL, 1, 400, 1)};
       buttonSetValues = new JButton("Set values");
       buttonSetValues.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               setTitle(textFieldTitle.getText());
               setValuesX(new Integer[]{slider[3].getValue(), slider[4].getValue(), slider[5].getValue()});
               setJumpPeriod(new Integer[]{slider[0].getValue(), slider[1].getValue(), slider[2].getValue()});
           }
       });
       add(textFieldTitle);
       add(slider[0]);
       add(slider[1]);
       add(slider[2]);
       add(slider[3]);
       add(slider[4]);
       add(slider[5]);
       add(buttonSetValues);
       revalidate();
    }

    @Override
    public void setObject(Object obj) {
        bean = (JumpingCirclesBean) obj;

        Integer[] values = bean.getValuesX();
        Integer[] jumpPeriod = bean.getJumpPeriod();

        slider[0].setValue(jumpPeriod[0]);
        slider[1].setValue(jumpPeriod[1]);
        slider[2].setValue(jumpPeriod[2]);
        slider[3].setValue(values[0]);
        slider[4].setValue(values[1]);
        slider[5].setValue(values[2]);


        textFieldTitle.setText(bean.getTitle());
    }

    public void setValuesX(Integer[] newValues){
        if(bean == null)
            return;
        Integer[] oldValues = bean.getValuesX();
        bean.setValuesX(newValues);
       // firePropertyChange("valuesX", oldValues, newValues);
    }

    public void setJumpPeriod(Integer[] newValues){
        if(bean == null)
            return;
        Integer[] oldValues = bean.getJumpPeriod();
        bean.setJumpPeriod(newValues);
        //firePropertyChange("jumpPeriod", oldValues, newValues);
    }

    public void setTitle(String newTitle){
        if(bean == null)
            return;
        String oldTitle = bean.getTitle();
        bean.setTitle(newTitle);
        firePropertyChange("title", oldTitle, newTitle);
    }

    public Dimension getPreferredSize(){
        return new Dimension(xSize, ySize);
    }
}
