package rest.example;

import dao.interfaces.GunDao;
import listeners.DbInitializer;
import model.Gun;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.Collection;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("gunList")
@Produces(APPLICATION_JSON)
public class GunService implements JsonRestfulWebService {

    private GunDao gunDao;

    @Context
    public void setContext(ServletContext context) {
        gunDao = (GunDao) context.getAttribute(DbInitializer.GUN_DAO);
    }

    @GET
    public Response getAll() {
        final Collection<Gun> guns = gunDao.getAll();
        return guns.size() > 0 ? ok(guns) : noContent();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") int id) {
        return gunDao.getGunById(id)
                .map(this::ok)
                .orElse(noContent());
    }
}
