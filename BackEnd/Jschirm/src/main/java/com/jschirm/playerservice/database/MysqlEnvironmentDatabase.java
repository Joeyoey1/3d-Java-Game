package com.jschirm.playerservice.database;

import com.jschirm.playerservice.model.Environment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MysqlEnvironmentDatabase extends JpaRepository<Environment, Integer> {
}
