package dao.mysql;

import dao.interfaces.GunDao;
import model.Gun;

import java.sql.Connection;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.function.Supplier;

@FunctionalInterface
public interface MySqlGunDao extends GunDao {

    static MySqlGunDao from(Supplier<Connection> connectionSupplier) {
        return connectionSupplier::get;
    }

    @Override
    default Optional<Gun> getGunById(int id) {
        return executeQuery(
                "SELECT name, caliber FROM Gun WHERE id = " + id,
                rs -> rs.next()
                        ? new Gun(id, rs.getString("name"), rs.getDouble("caliber"))
                        : null
        ).toOptional();
    }

    @Override
    default Collection<Gun> getAll() {
        return executeQuery(
                "SELECT id, name, caliber FROM Gun",
                rs -> {
                    Collection<Gun> guns = new HashSet<>();
                    while (rs.next())
                        guns.add(new Gun(rs.getInt("id"), rs.getString("name"), rs.getDouble("caliber")));
                    return guns;
                }).toOptional().orElse(Collections.emptySet());
    }
}
