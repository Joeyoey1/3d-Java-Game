package coms309.jr4.simpleclient;

import coms309.jr4.simpleclient.screens.listUsers;
import coms309.jr4.simpleclient.service.restService;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.RestTemplate;
import javafx.application.Application;
import javafx.scene.*;
import coms309.jr4.simpleclient.screens.login;

//@SpringBootApplication
public class SimpleclientApplication extends Application {
    private ConfigurableApplicationContext springContext;
    private FXMLLoader loader;
    long old_time;
    TabPane tpane = new TabPane();
    Scene scene = new Scene(tpane, 500, 500);

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void init() throws Exception{
/*        springContext = SpringApplication.run(SimpleclientApplication.class);
        loader = new FXMLLoader();
        loader.setControllerFactory(springContext::getBean);*/
    }

    @Override
    public void start(Stage primary) throws Exception{
        tpane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        login l = new login();
        l.createLogin(tpane);
        listUsers lu = new listUsers();
        lu.createUserList(tpane);

        primary.setScene(scene);
        primary.show();
    }

    @Override
    public void stop(){
  //     springContext.stop();
    }

}
