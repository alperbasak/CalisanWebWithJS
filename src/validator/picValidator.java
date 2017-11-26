package validator;

import util.Util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import java.awt.*;

public class picValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
       if (o!=null){
        Part img=(Part)o;
        if(!(img.getContentType().equals("image/png") || img.getContentType().equals("image/jpeg"))){
            throw new ValidatorException(new FacesMessage("Hatalı dosya tipi"));
        }
        else if(img.getSize()> 900*1024){
            throw new ValidatorException(new FacesMessage("Dosya boyutu 900 kByi aşmamalıdır"));
        }
       }
       else throw new ValidatorException(new FacesMessage("Bir resim seçiniz"));
    }
}
