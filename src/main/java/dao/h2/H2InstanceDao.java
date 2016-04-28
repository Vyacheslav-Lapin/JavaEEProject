package dao.h2;

import dao.interfaces.InstanceDao;
import model.Gun;
import model.Instance;

import java.sql.Connection;
import java.util.Optional;
import java.util.function.Supplier;

@FunctionalInterface
public interface H2InstanceDao extends InstanceDao {

    static H2InstanceDao from(Supplier<Connection> connectionSupplier) {
        return connectionSupplier::get;
    }

    @Override
    default Optional<Instance> getInstanceById(int id) {
        return executeQuery(
                "SELECT g.id, name, caliber " +
                        "FROM Gun g, Instance i " +
                        "WHERE model_id = g.id AND  i.id = " + id,
                rs -> rs.next()
                        ? new Instance(id,
                                new Gun(rs.getInt("id"),
                                        rs.getString("name"),
                                        rs.getDouble("caliber")))
                        : null
        ).toOptional();
    }
}
