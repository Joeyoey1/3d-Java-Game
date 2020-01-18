package coms309.jr4.simpleclient.screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import coms309.jr4.simpleclient.service.restService;

public class login {
    long old_time;

    public void createLogin(TabPane tpane){
        //primary.setTitle("Login");
        Pane pane = new Pane();
        Tab login = new Tab("Login");
        Text message = new Text("no events yet");
        message.setLayoutX(50);
        message.setLayoutY(400);
        TextField username = new TextField("username");
        username.setLayoutX(50);
        username.setLayoutY(100);
        TextField password = new TextField("password");
        password.setLayoutX(50);
        password.setLayoutY(200);
        Button submit = new Button("submit");
        submit.setLayoutX(50);
        submit.setLayoutY(300);
        submit.setOnAction(event -> {
            String name = username.getCharacters().toString();
            String pass = password.getCharacters().toString();
            if(!name.contentEquals("") && !pass.contentEquals("")){
                restService rs = new restService();
                old_time = System.currentTimeMillis();
                int err = rs.sendLogin(name, pass);
                if(err == 0) message.setText(("Successful in: " + (System.currentTimeMillis() - old_time)));
                else message.setText("not successful");
            }
            else message.setText("invalid login");
        });
        pane.getChildren().addAll(message, username, password, submit);
        login.setContent(pane);
        tpane.getTabs().add(login);

    }
}
