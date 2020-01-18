package com.barbarian.game.components;

import com.badlogic.ashley.core.Component;
import com.barbarian.game.Game;
import com.barbarian.game.models.Chat;

import java.util.Random;

public class ChatComponent implements Component {

    private Integer id;
    private Chat chat;

    public ChatComponent(){
        Random r = new Random();
        this.id = Game.thisID;//r.nextInt(500);
        chat = new Chat(id, "");
    }

    public Chat getChat() {
        return chat;
    }
    public void setChatMessage(String str) {chat.setMessage(str);}
    public String getChatMessage(){return chat.getMessage();}

    public void clearMessage(){chat.setMessage("");}
    public boolean messageToSend(){
        if(chat.getMessage().contentEquals("")){
            return false;
        }
        return true;
    }

    public int getID() {
        return this.id;
    }
}
