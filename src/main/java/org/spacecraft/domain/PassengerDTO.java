package org.spacecraft.domain;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Anton Pashkin
 * @version 1.0 2013/07/23
 */
public class PassengerDTO {

    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;

    private Ship ship;

    public PassengerDTO() {
    }

    public PassengerDTO(String name) {
        this.name = name;
    }

    public PassengerDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }
}
