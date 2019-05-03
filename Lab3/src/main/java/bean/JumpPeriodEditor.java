package bean;

import java.awt.Component;
import java.beans.PropertyEditorSupport;
import java.util.StringTokenizer;

public class JumpPeriodEditor extends PropertyEditorSupport {
    public Component getCustomEditor(){
        return new JumpPeriodEditorPanel(this);
    }

    public boolean supportsCustomEditor(){
        return true;
    }

    public boolean isPaintable(){
        return false;
    }

    public String getJavaInitializationString() {
        Integer[] jumpPeriod = (Integer[]) getValue();

        StringBuffer s = new StringBuffer();
        s.append("new Integer[]{");
        for (int i = 0; i < jumpPeriod.length - 1; i++) {
            s.append(jumpPeriod[i]);
            s.append(",");
        }
        s.append(jumpPeriod[jumpPeriod.length - 1]);
        s.append("}");
        return s.toString();
    }

    public String getAsText(){
        Integer[] jumpPeriod = (Integer[]) getValue();
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < jumpPeriod.length - 1; i++) {
            s.append(jumpPeriod[i]);
            s.append(",");
        }
        s.append(jumpPeriod[jumpPeriod.length - 1]);
        return s.toString();
    }

    public void setAsText(String s){
        Integer[] values = new Integer[3];
        String[] parts = s.split(",");
        for(int i = 0; i < 3; i++)
            values[i] = Integer.parseInt(parts[i]);
        setValue(values);
    }
}
