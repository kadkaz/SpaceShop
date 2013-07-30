package org.spacecraft.domain;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author Anton Pashkin
 * @version 1.0 2013/07/23
 */
public class Ship {

    public static enum SortField {
        NAME
    }

    @JsonProperty
    private Long id;
    @JsonProperty
    private String name;

    public Ship() {
    }

    public Ship(String name) {
        this.name = name;
    }

    public Ship(Long id, String name) {
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
}
