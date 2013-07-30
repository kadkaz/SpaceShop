package org.spacecraft.web;

import org.spacecraft.domain.Ship;
import org.spacecraft.persistence.Order;
import org.spacecraft.persistence.ShipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 *
 * @author Anton Pashkin
 * @version 1.0 2013/07/23
 */
@Controller
@RequestMapping("/ship")
public class ShipController {

    @Autowired
    private ShipMapper shipMapper;

    /**
     * Create request ship
     * @param ship filled request ship
     * @return the created ship
     */
    @ResponseBody
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public Ship create(@RequestBody Ship ship) {
        shipMapper.create(ship);
        return ship;
    }

    /**
     * Update the ship by identity
     * @param ship ship
     * @return true if it was saved, otherwise it was not saved
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public boolean update(@RequestBody Ship ship) {
        shipMapper.update(ship);
        return true;
    }

    /**
     * Delete a ship by identity
     * @param id identity
     * @return  true if it was deleted, otherwise it was not deleted
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable long id) {
        shipMapper.delete(id);
        return true;
    }

    /**
     * find a ship by identity
     * @param id identity
     * @return found ship, otherwise null
     */
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Ship getShip(@PathVariable long id) {
        return shipMapper.getById(id);
    }

    /**
     * count ships in DB
     * @return number of ships were saved in DB
     */
    @ResponseBody
    @RequestMapping(value = "/list/count", method = RequestMethod.GET)
    public long countShips() {
        return shipMapper.count();
    }

    /**
     * get list of ships by paging settings and sort it by one field
     * @param page current page to get
     * @param perPage number of ships per a page
     * @param sortField a sortable field
     * @param order type of order by the sortable field
     * @return found ships, otherwise null
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Collection<Ship> getShips(@RequestParam long page, @RequestParam(value = "per_page") int perPage,
                                     @RequestParam(value = "sort_by") Ship.SortField sortField,
                                     @RequestParam Order order) {
        return shipMapper.getShips(page * perPage, perPage, sortField, order);
    }
}
