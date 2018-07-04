package kz.rapidminerjava.bean;
import kz.rapidminerjava.constant.Constant;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;


@ManagedBean
@ApplicationScoped
public class LoginRedirect {

    public void doRedirect() throws IOException{

        FacesContext.getCurrentInstance().getExternalContext().redirect(Constant.PATH_PAGE_LOGIN);//redirect to login page
    }

}
