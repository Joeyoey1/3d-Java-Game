package com.jschirm.playerservice.database;

import com.jschirm.playerservice.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * player object database
 */
@Repository
public interface mysqlDatabase extends JpaRepository<Player, Integer> {
}
