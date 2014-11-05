package org.spacecraft.persistence;

import org.apache.ibatis.annotations.*;
import org.spacecraft.domain.PassengerDTO;
import org.spacecraft.domain.Ship;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author Anton Pashkin
 * @version 1.0 2013/07/24
 */
@Repository
public interface ShipMapper {

    @Select("SELECT * FROM SHIPS " +
            "ORDER BY ${sortField} ${order} " +
            "LIMIT #{perPage} OFFSET #{offset} ")
    Collection<Ship> getShips(@Param("offset") long offset, @Param("perPage") int perPage,
                              @Param("sortField") Ship.SortField sortField, @Param("order") Order order);

    @Select("SELECT s.id, s.name FROM SHIPS s WHERE ID = #{id}")
    @Results(value = {
            @Result(property = "passengers", column = "id", many = @Many(select = "getPassengers"))
    })
    Ship getById(long id);

    @Select("SELECT p.id as id, p.name FROM PASSENGERS p where p.ship_id = #{shipId}")
    List<PassengerDTO> getPassengers(long shipId);

    @Insert("INSERT INTO PASSENGERS(name, ship_id) VALUES (#{name}, #{ship.id})")
    @Options(useGeneratedKeys = true, keyColumn = "ID", keyProperty="id")
    void createPassenger(PassengerDTO passengerDTO);

    @Insert("INSERT INTO SHIPS(name) VALUES (#{name})")
    @Options(useGeneratedKeys = true, keyColumn = "ID", keyProperty="id")
    void create(Ship ship);

    @Update("UPDATE SHIPS SET name = #{name} WHERE ID = #{id}")
    void update(Ship ship);

    @Delete("DELETE FROM SHIPS WHERE ID = #{id}")
    void delete(long id);

    @Select("SELECT count(*) from SHIPS")
    long count();
}
