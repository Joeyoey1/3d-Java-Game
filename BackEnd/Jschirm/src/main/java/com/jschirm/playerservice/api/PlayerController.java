package com.jschirm.playerservice.api;

import com.jschirm.playerservice.database.mysqlDatabase;
import com.jschirm.playerservice.model.Location;
import com.jschirm.playerservice.model.Player;
//import com.jschirm.playerservice.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

//@RequestMapping("api/players")
@RestController
public class PlayerController {

    @Autowired
    mysqlDatabase mdb;

    public static Map<Integer, Player> activePlayers = new HashMap<>();

//    private final PlayerService playerService;

//    @Autowired
//    public PlayerController(PlayerService playerService) {
//        this.playerService = playerService;
//    }

    /**
     * @param player
     * adds player object to database
     */
    @PostMapping("/players")
    public void addPlayer(@RequestBody @Valid Player player) {
        mdb.save(player);
        activePlayers.put(player.getId(), player);
    }

    /**
     * @return list of payer object from database
     */
    @RequestMapping("/players/database")
    public List<Player> getAllPlayersDatabase() {
        return mdb.findAll();
    }

    /**
     * @return list of active players from database
     */
    @RequestMapping("/players/active")
    public List<Player> getAllPlayersActive() {
        return new ArrayList<>(activePlayers.values());
    }

    /**
     * @param id
     * @return player object specified by id
     */
    @GetMapping("/players/{id}")
    public Player getPlayerById(@PathVariable Integer id) {
        return activePlayers.get(id);
    }

    /**
     * @param id
     * @return player onbject by id or null player object if none
     */
    @GetMapping("/players/database/{id}")
    public Player getPlayerByIdDatabase(@PathVariable Integer id) {
        return mdb.findById(id).orElse(new Player());
    }

    /**
     * @param id
     * @return player deleted from database
     */
    @DeleteMapping("/players/{id}")
    public String deletePlayerById(@PathVariable("id") Integer id) {
        mdb.deleteById(id);
        return "deleted " + id;
    }

    /**
     * @param id
     * @param player
     * updates player object specified by id
     */
    @PutMapping("/players/{id}")
    public void updatePlayerById(@PathVariable Integer id, @RequestBody Player player) {
        Player old = activePlayers.get(id);
        //old.setLocation(player.location);
        old.setHealth(player.getHealth());
        mdb.save(old);
        activePlayers.put(id, old);
    }


/*

    @GetMapping("/players/{id}/location")
    public Location getPlayerLocationById(@PathVariable("id") UUID uuid) {
        return mdb.(uuid);
    }

    @GetMapping("/players/{id}/health")
    public double getPlayerHealthById(@PathVariable("id") UUID uuid) {
        return mdb.getPlayerHealth(uuid);
    }
*/

}
