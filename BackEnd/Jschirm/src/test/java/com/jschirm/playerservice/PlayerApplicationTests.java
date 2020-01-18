package com.jschirm.playerservice;

import com.jschirm.playerservice.api.PlayerController;
import com.jschirm.playerservice.database.mysqlDatabase;
import com.jschirm.playerservice.model.Location;
import com.jschirm.playerservice.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerApplicationTests {

	@InjectMocks
	PlayerController playerController;

	@Mock
	mysqlDatabase mysqlDatabase;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void getPlayerFromId() {
		Player player = new Player();
		player.setId(1);
		when(mysqlDatabase.findById(1)).thenReturn(java.util.Optional.of(player));


		Player testPlayer = playerController.getPlayerByIdDatabase(1);

		//System.out.println(testPlayer.getId() + " id \n" + testPlayer.getCharacterState() + " char state \n" + testPlayer.getHealth() + " health \n" + testPlayer.getLocation() + " location \n");

		assertEquals(1, testPlayer.getId().intValue());
		assertEquals(0, testPlayer.getCharacterState());
		assertEquals(100.0, testPlayer.getHealth(), 0);
		assertEquals(player.getLocation(), testPlayer.getLocation());
	}

	@Test
	public void testListOfPlayer() {
		List<Player> players = new ArrayList<>();
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();

		p1.setId(1);
		p2.setId(2);
		p3.setId(3);

		players.add(p1);
		players.add(p2);
		players.add(p3);

		when(mysqlDatabase.findAll()).thenReturn(players);


		List<Player> testPlayers = playerController.getAllPlayersDatabase();

		assertEquals(testPlayers.size(), players.size());
		verify(mysqlDatabase, times(1)).findAll();

	}



}