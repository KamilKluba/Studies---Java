package bean;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyEditorSupport;

public class JumpPeriodEditorPanel extends JPanel {

    private PropertyEditorSupport editor;

    private JSlider[] sliders = new JSlider[]{new JSlider(JSlider.HORIZONTAL, 1, 10, 1),
                                              new JSlider(JSlider.HORIZONTAL, 1, 10, 1),
                                              new JSlider(JSlider.HORIZONTAL, 1, 10, 1)};
    private Integer[] slidersValues;

    private JButton buttonOk = new JButton("OK");

    public JumpPeriodEditorPanel(PropertyEditorSupport ed){
        editor = ed;
        slidersValues = ((Integer[])ed.getValue());
        sliders[0].setValue(slidersValues[0]);
        sliders[1].setValue(slidersValues[1]);
        sliders[2].setValue(slidersValues[2]);

        setLayout(new FlowLayout());
        add(sliders[0]);
        add(sliders[1]);
        add(sliders[2]);
        add(buttonOk);

        buttonOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.setValue(new Integer[]{sliders[0].getValue(), sliders[1].getValue(), sliders[2].getValue()});
            }
        });
    }
}
