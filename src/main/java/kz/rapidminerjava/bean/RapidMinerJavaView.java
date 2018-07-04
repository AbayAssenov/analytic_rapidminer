package kz.rapidminerjava.bean;

import com.rapidminer.Process;
import com.rapidminer.RapidMiner;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.tools.XMLException;
import com.rapidminer.tools.plugin.Plugin;
import kz.rapidminerjava.constant.Constant;
import kz.rapidminerjava.util.ClassLoaderUtil;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;


@ManagedBean(name = "rapidMinerJavaView")
@SessionScoped
public class RapidMinerJavaView implements Serializable {

    private Map<String, String> urls;
    public Boolean hideResult = true;


    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);


    public void buttonAction(ActionEvent actionEvent) {
        executeAnalyz();
    }

    public void goToPageResult() throws IOException {

        FacesContext.getCurrentInstance().getExternalContext().redirect("result/index.html");//redirect to login page
    }

    private void executeAnalyz() {

        Plugin.addAdditionalExtensionDir("plugins/"); // Добавляем плагины Рапид Майнера
        Plugin.setInitPlugins(true);

            try {

                hideResult = true; // деактивируем кнопку результат

                RapidMiner.setExecutionMode(RapidMiner.ExecutionMode.COMMAND_LINE);
                RapidMiner.init();

                Process process = null;

                URL url = ClassLoaderUtil.getResource("file/ProccessCreditBank.rmp", RapidMinerJavaView.class); // Указываем где файл находится
                Path path = Paths.get(url.toURI());

                process = new Process(new File(String.valueOf(path))); // Создаем процесс

                process.run();

            } catch (XMLException e) {
                addErrorMessage(Constant.ERROR_ANALYZE); //"Ошибка при загрузки подготовленного процесса \n анализа !"
                return;
            } catch (IOException ex) {
                addErrorMessage(Constant.ERROR_INPUT_OUTPUT); //"Ошибка ввода / вывода !"
                return;
            } catch (OperatorException e) {
                addErrorMessage(Constant.ERROR_OPERATOR);//Ошибка применения заданного оператора при анализе !
                return;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }


            addInfoMessage(Constant.SUCCESS_ANALYZE);//"Анализ выбраных данных выполнен"

            hideResult = false; // активируем кнопку результат

    }

    public void addInfoMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addErrorMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public Boolean getHideResult() {
        return hideResult;
    }

    public void setHideResult(Boolean hideResult) {
        this.hideResult = hideResult;
    }

    public Map<String, String> getUrls() {
        return urls;
    }

    public void setUrls(Map<String, String> urls) {
        this.urls = urls;
    }

    //logout event, invalidate session
    public void logout() throws IOException {
        session.invalidate(); // Мы уничтожаем сессию
        FacesContext.getCurrentInstance().getExternalContext().redirect(Constant.LOGIN_PAGE);//redirect to login page
    }


}
