package org.spacecraft.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spacecraft.domain.PassengerDTO;
import org.spacecraft.domain.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class ShipMapperTest {

    @Autowired
    private ShipMapper shipMapper;

    @Test
    public void createdShipWithPassengersShouldLoadPassengers() {
        // Given any ship with passengers
        Ship ship = new Ship("new ship");
        shipMapper.create(ship);

        PassengerDTO passenger = new PassengerDTO("pax1");
        passenger.setShip(ship);
        shipMapper.createPassenger(passenger);

        // And DB has the saved ship
        Ship savedShip = shipMapper.getById(ship.getId());
        assertEquals(savedShip.getPassengers().size(), 1);
        assertNotNull("Id should not be null #290 bug!!!", savedShip.getId());
        assertNotNull(savedShip.getPassengers().get(0).getId());
    }
}
