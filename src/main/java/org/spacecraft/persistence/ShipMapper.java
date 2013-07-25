package org.spacecraft.persistence;

import org.spacecraft.domain.Ship;

import java.util.Collection;

/**
 * @author Anton Pashkin
 * @version 1.0 2013/07/24
 */
public interface ShipMapper {

    Collection<Ship> getShips(long page, int perPage);
    Ship getById(long id);
    Ship create(Ship ship);
    void update(Ship ship);
    long count();
}
