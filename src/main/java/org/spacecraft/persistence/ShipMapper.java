package org.spacecraft.persistence;

import org.apache.ibatis.annotations.*;
import org.spacecraft.domain.Ship;
import org.springframework.stereotype.Repository;

import java.util.Collection;

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

    @Select("SELECT * FROM SHIPS WHERE ID = #{id}")
    Ship getById(long id);

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
