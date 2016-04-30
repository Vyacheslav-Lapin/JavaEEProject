package rest.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dao.interfaces.GunDao;
import listeners.DbInitializer;
import model.Gun;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("gunList")
public class GunService {

    @Context
    private ServletContext context;

    private GunDao gunDao;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return toString(getGunDao().getAll());
    }

    private String toString(Collection<Gun> guns) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(guns);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public GunDao getGunDao() {
        if (gunDao == null)
            gunDao = (GunDao) context.getAttribute(DbInitializer.GUN_DAO);
        return gunDao;
    }
}
