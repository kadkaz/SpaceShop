package org.spacecraft.persistence;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.spacecraft.domain.Ship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Test every function provided by {@link org.spacecraft.persistence.ShipMapper} class
 * to be sure that persistence works for {@link org.spacecraft.domain.Ship} domain class
 *
 * @see org.spacecraft.persistence.ShipMapper
 * @see org.spacecraft.domain.Ship
 *
 * @author Anton Pashkin
 * @version 1.0 2013/07/25
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:application-context.xml"})
public class ShipMapperTest {

    @Autowired
    private ShipMapper shipMapper;

    @Test
    public void countTest() {
        // Given test db has count
        long dbCount = 11;

        // When count ships
        long shipsCount = shipMapper.count();

        // Then test db ships real count equals to ships count
        assertEquals(dbCount, shipsCount);
    }

    @Test
    public void getByIdExistenceShipTest() {
        // Given a ship with
        Ship ship = new Ship(4L , "Schooner");

        // When loading ship
        Ship loadedShip = shipMapper.getById(ship.getId());

        // Then db real count equals to ships count
        assertEquals(ship.getId(), loadedShip.getId());
        assertEquals(ship.getName(), loadedShip.getName());
    }

    @Test
    public void getByIdNotExistenceShipTest() {
        // Given
        long notExistenceShipId = Long.MAX_VALUE;

        // When count ships
        Ship loadedShip = shipMapper.getById(notExistenceShipId);

        // Then db does not have it
        assertNull(loadedShip);
    }

    @Test
    public void getShipsTest() {
        //Given 2 page if ships per 10
        int perPage = 10;
        assertEquals(11, shipMapper.count());

        // When get first page = 0
        Collection<Ship> ships = shipMapper.getShips(0, perPage, Ship.SortField.NAME, Order.ASC);
        // Then it is 10
        assertEquals(perPage, ships.size());

        // When get the second page
        ships = shipMapper.getShips(perPage, perPage, Ship.SortField.NAME, Order.ASC);
        // Then it is 1
        assertEquals(1, ships.size());
    }

    @Test
    public void sortingTest() {
        //Given sorting fields
        Ship.SortField[] fields = Ship.SortField.values();
        int perPage = 10;

        // When apply sorting for every fields and order
        for (Ship.SortField sortBy : fields) {
            Collection<Ship> ships = shipMapper.getShips(0, perPage, sortBy, Order.ASC);
            // then the size the same like requested
            assertEquals(perPage, ships.size());

            ships = shipMapper.getShips(0, perPage, sortBy, Order.DESC);
            // then order does not change the result
            assertEquals(perPage, ships.size());
        }

        // Then no exceptions
    }

    @Test
    public void createAndGeneratedIdTest() {
        // Given any ship
        Ship ship = new Ship("new ship");

        long oldCount = shipMapper.count();

        // When
        shipMapper.create(ship);

        // Then was generated id
        assertNotNull(ship.getId());
        assertEquals(oldCount + 1, shipMapper.count());

        // And DB has the saved ship
        Ship savedShip = shipMapper.getById(ship.getId());
        assertEquals(ship.getName(), savedShip.getName());
    }

    @Test
    public void updateShipTest() {
        // Given saved ship
        Ship ship = shipMapper.getById(1L);

        // When change fields and update
        String newName = "New Name";
        assertNotSame(newName, ship.getName());
        ship.setName(newName);
        shipMapper.update(ship);

        // Then ship was updated in db
        Ship updatedShip = shipMapper.getById(ship.getId());
        assertEquals(updatedShip.getName(), ship.getName());
    }

    @Test
    public void deleteShipTest() {
        // Given any ship
        Ship ship = shipMapper.getById(1L);
        assertNotNull(ship);

        // When delete it
        shipMapper.delete(ship.getId());

        // Then ship was deleted in db
        Ship deletedShip = shipMapper.getById(ship.getId());
        assertNull(deletedShip);
    }
}
