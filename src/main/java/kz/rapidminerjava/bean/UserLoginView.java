package kz.rapidminerjava.bean;


import kz.rapidminerjava.constant.Constant;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@ManagedBean
@RequestScoped
public class UserLoginView {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


/**Checks login and password*/
    public void login() throws IOException {

        if (username != null && username.equals(Constant.ADMIN) && password != null && password.equals(Constant.ADMIN)) { //проверка логина пароля
// Берем контекст что бы создать сессию
            FacesContext context = FacesContext.getCurrentInstance();
// Создаем ссесию
            HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
// Кладем имя пользователя в ссесию
            session.setAttribute(Constant.USER_NAME, username); //Set user name in session
// Переводим пользователя на страницу Рапид Майнера
            context.getExternalContext().redirect(Constant.RAPID_MINER_PAGE); //Redirect to rapid miner page
        } else {

            username=Constant.EMPTY_STR;
            password=Constant.EMPTY_STR;

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, Constant.ERROR_AUTHOZ, Constant.INCORRECT_LOGIN_OR_PASS);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
}
