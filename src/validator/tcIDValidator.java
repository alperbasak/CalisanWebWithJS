package validator;

import util.Util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class tcIDValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {

        String str=o.toString();
        if (!Util.isValidTckn(str)){
            throw new ValidatorException(new FacesMessage("TC kimlik noyu kontrol ediniz"));
        }
    }
}
