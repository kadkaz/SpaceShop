package org.spacecraft.web;

import org.spacecraft.domain.Ship;
import org.spacecraft.persistence.ShipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Anton Pashkin
 * @version 1.0 2013/07/23
 */
@Controller
@RequestMapping("/ship")
public class ShipController {

    @Autowired
    private ShipMapper shipMapper;

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Ship getShip(@PathVariable long id) {
        return shipMapper.getById(id);
    }

    @ResponseBody
    @RequestMapping(value = "/list/count", method = RequestMethod.GET)
    public long countShips() {
        return shipMapper.count();
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Collection<Ship> getShips(@RequestParam long page, @RequestParam(value = "per_page") int perPage) {
        return shipMapper.getShips(page * perPage, perPage);
    }
}
