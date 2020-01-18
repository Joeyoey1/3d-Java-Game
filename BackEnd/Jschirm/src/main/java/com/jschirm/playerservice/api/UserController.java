package com.jschirm.playerservice.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.jschirm.playerservice.database.MysqlUserDatabase;
import com.jschirm.playerservice.model.Location;
import com.jschirm.playerservice.model.Player;
import com.jschirm.playerservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    private MysqlUserDatabase mdb;


    /**
     * @param username
     * @param password
     * @return player that is linked to the user logging in
     */
    @GetMapping("/Users/{username}/{password}")
    public String getPlayerLogin(@PathVariable("username") String username, @PathVariable("password") String password) {

        for (User p : mdb.findAll()) {
            System.out.println("Username: " + p.getUsername() + " Password: " + p.getPassword());
            if (p.getUsername() != null && p.getPassword() != null) {
                if (p.getUsername().equals(username)) {
                    if (p.getPassword().equals(password)) {
                        PlayerController.activePlayers.put(p.getId(), p.getPlayer());
                        System.out.println("Success");
                        return p.getId() + "";
                    }
                }
            }
        }

        return "Failed";
    }


    /**
     *
     * @return creates a new user and player linked to user
     */
    @GetMapping("/Users/Create/{username}/{password}")
    public boolean createNewUser(@PathVariable("username") String username, @PathVariable("password") String password) {
        try {
            for (User p : mdb.findAll()) {
                System.out.println("Username: " + p.getUsername() + " Password: " + p.getPassword());
                if (p.getUsername().equals(username)) {
                    return false;
                }
            }

            Player player = new Player();
            User user = new User();
            user.setName(username);
            user.setPassword(password);
            user.setPlayer(player);
            mdb.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * @param user
     * @param admin
     * @return true
     * removes user from database
     */
    @PostMapping("/Users/Delete")
    public boolean removeUserFromDatabase(@RequestBody @Valid User user, @RequestBody @Valid User admin) {
            mdb.delete(user);
            return true;
    }


    /**
     * @param user
     * @return true
     * adds new player object linked to user param
     */
    @PostMapping("/Users/NewPlayer")
    public boolean newPlayer(@RequestBody @Valid User user) {
        Player player = new Player();
        user.setPlayer(player);
        mdb.save(user);
        return true;
    }
}
