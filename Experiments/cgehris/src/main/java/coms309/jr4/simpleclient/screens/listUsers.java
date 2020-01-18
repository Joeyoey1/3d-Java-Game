package coms309.jr4.simpleclient.screens;

import coms309.jr4.simpleclient.service.restService;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class listUsers {
    long old_time;

    public void createUserList(TabPane tpane){
        Pane pane = new Pane();
        Text text = new Text("");
        ScrollPane spane = new ScrollPane(text);
        spane.setPrefSize(300,300);
        spane.setLayoutX(25);
        spane.setLayoutY(25);
        pane.getChildren().add(spane);
        Tab list = new Tab("User List");
        Text message = new Text("no events yet");
        message.setLayoutX(50);
        message.setLayoutY(450);
        TextField id = new TextField("id");
        id.setLayoutX(50);
        id.setLayoutY(350);
        TextField health = new TextField("health");
        health.setLayoutX(250);
        health.setLayoutY(350);
        Button submit = new Button("submit");
        submit.setLayoutX(400);
        submit.setLayoutY(400);
        submit.setOnAction(event -> {
            String idn = id.getCharacters().toString();
            String heal = health.getCharacters().toString();
            if(!idn.contentEquals("") && idn.matches("[0-9]*") && !heal.contentEquals("") && heal.matches("[0-9]*")){
                restService rs = new restService();
                old_time = System.currentTimeMillis();
                String response = rs.updateList(idn, heal);
                /*if(err == 0) message.setText(("Successful in: " + (System.currentTimeMillis() - old_time)));
                else message.setText("not successful");*/
                text.setText(parseJSON(response));
            }
            else message.setText("invalid update");
        });
        pane.getChildren().addAll(message, id, health, submit);
        list.setContent(pane);
        tpane.getTabs().add(list);
    }
    private String parseJSON(String in){
        char[] input = in.toCharArray();
        for(int i = 0; i < input.length; i++){
            if(input[i] == '{' || input[i] == '[' || input[i] == ']' || input[i] == '"'){
                input[i] = ' ';
            }
            if(input[i] == ',' || input[i] == '}'){
                input[i] = '\n';
            }
        }
        return new String(input);
    }
}
