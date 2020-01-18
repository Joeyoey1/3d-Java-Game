package com.jschirm.playerservice.database;

import com.jschirm.playerservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * user repository
 */
public interface MysqlUserDatabase extends JpaRepository<User, Integer> {
}
