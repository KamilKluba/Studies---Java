package bean;

import javax.imageio.ImageIO;
import java.awt.*;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.io.IOException;

public class JumpingCirclesBeanBeanInfo extends SimpleBeanInfo {
    public Image getIcon(int iconType){
        String name = "";
        Image image = null;

        if(iconType == BeanInfo.ICON_COLOR_16x16)
            name = "COLOR_16x16";
        else if(iconType == BeanInfo.ICON_COLOR_32x32)
            name = "COLOR_32x32";
        else if(iconType == BeanInfo.ICON_MONO_16x16)
            name = "MONO_16x16";
        else if(iconType == BeanInfo.ICON_MONO_32x32)
            name = "MONO_32x32";
        else
            return null;

        try{
            image = ImageIO.read(JumpingCirclesBeanBeanInfo.class.getClassLoader().getResourceAsStream("JumpingCirclesBean_" + name + ".gif"));
        } catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public PropertyDescriptor[] getPropertyDescriptors(){
        try{
            PropertyDescriptor valuesXDescriptor = new PropertyDescriptor("valuesX", JumpingCirclesBean.class);
            valuesXDescriptor.setPropertyEditorClass(ValuesXEditor.class);

            PropertyDescriptor jumpPeriodDescriptor = new PropertyDescriptor("jumpPeriod", JumpingCirclesBean.class);
             jumpPeriodDescriptor.setPropertyEditorClass(JumpPeriodEditor.class);

            PropertyDescriptor titleDescriptor = new PropertyDescriptor("title", JumpingCirclesBean.class);
           // titleDescriptor.setPropertyEditorClass(TitleEditor.class);
            return new PropertyDescriptor[]{valuesXDescriptor, jumpPeriodDescriptor, titleDescriptor};
        }catch(IntrospectionException e){
            //e.printStackTrace();
            return null;
        }
    }
}
