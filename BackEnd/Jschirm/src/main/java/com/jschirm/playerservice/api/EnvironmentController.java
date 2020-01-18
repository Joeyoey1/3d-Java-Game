package com.jschirm.playerservice.api;

import com.jschirm.playerservice.database.MysqlEnvironmentDatabase;
import com.jschirm.playerservice.model.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EnvironmentController {

    @Autowired
    MysqlEnvironmentDatabase mdb;

    @PutMapping("/environment/color")
    public void newColor(@Valid int color) {
        if (mdb.findAll().isEmpty()) { //  This checks to see if there are any "Environments" in existence yet.
            mdb.save(new Environment());
        }
        mdb.findAll().get(0).setColor(color);
    }

    @GetMapping("/environment/color")
    public int getColor() {
        if (mdb.findAll().isEmpty()) { //  This checks to see if there are any "Environments" in existence yet.
            mdb.save(new Environment());
        }
        return (mdb.findAll().get(0).getColor() > 5 || mdb.findAll().get(0).getColor() < 0) ? mdb.findAll().get(0).getColor() : 0;
    }


}
