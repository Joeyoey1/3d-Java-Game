import com.barbarian.game.models.Location;
import com.barbarian.game.models.Player;
import com.barbarian.game.network.Network;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class FrontEndTesting {

    @Mock
    Player player_mock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void frontEndToServer()
    {
        when(player_mock.getId()).thenReturn(9);
        when(player_mock.getLocation()).thenReturn(new Location(6f, 6f, 6f, 0f));

        Player player = new Player(player_mock.getId(), player_mock.getLocation());
        Network net = new Network();
        boolean found = false, stay = true;

        net.sendPlayerUpdate(player);

        do {
            if (net.is_fresh())
            {
                for ( Player up_player : net.get_updated_players() )
                    if (up_player.getId().equals(player_mock.getId())){
                        assertTrue(location_is_equal(up_player.getLocation(), player_mock.getLocation()));
                        found = true;
                    }
                stay = false;
            }
        }while(stay);

        assertTrue(found);
        net.exit();
    }

    private boolean location_is_equal(Location l1, Location l2)
    {
        return l1.getVelX() == l2.getVelX() && l1.getVelZ() == l2.getVelZ() && l1.getW() == l2.getW()
                && l1.getX() == l2.getX() && l1.getY() == l2.getY() && l1.getZ() == l2.getZ();
    }
}

