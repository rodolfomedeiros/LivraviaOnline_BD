package livraria.util;

import java.text.DecimalFormat;

public class FerramentaUtil {
    public String floatToStringFormatter(float f){
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(f);
    }
}
