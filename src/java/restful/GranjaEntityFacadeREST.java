/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entidades.GranjaEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Alejandro Gómez
 */
@Stateless
@Path("entidades.granjaentity")
public class GranjaEntityFacadeREST extends AbstractFacade<GranjaEntity> {

    @PersistenceContext(unitName = "LauserriServidorPU")
    private EntityManager em;

    public GranjaEntityFacadeREST() {
        super(GranjaEntity.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(GranjaEntity entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, GranjaEntity entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public GranjaEntity find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<GranjaEntity> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<GranjaEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("granjasGranjero/{username}")
    @Produces({MediaType.APPLICATION_XML})
    public List<GranjaEntity> granjasPorLoginDelGranjero(@PathParam("username") String username) {
        List<GranjaEntity> granjas = null;
        try {
            LOGGER.info("Listado de las granjas del granjero");
            granjas = em.createNamedQuery("granjasPorLoginDelGranjero").setParameter("username", username).getResultList();
        } catch (Exception e) {
            LOGGER.severe("No hay granjas corrrespondientes a ese granjero " +e.getLocalizedMessage());
        }
        return granjas;
    }
    

    @GET
    @Path("nombreGranja/{nombreGranja}")
    @Produces({MediaType.APPLICATION_XML})
    public GranjaEntity granjaPorNombre(@PathParam("nombreGranja") String nombreGranja) {
        GranjaEntity granja = null;
        try {
            LOGGER.info("Listado de la granja con ese nombre");
            granja = (GranjaEntity) em.createNamedQuery("granjaPorNombre").setParameter("nombreGranja", nombreGranja).getSingleResult();
        } catch (Exception e) {
            LOGGER.severe("No hay ninguna granja existente con ese nombre " +e.getLocalizedMessage());
        }
        return granja;
    }

    @GET
    @Path("GranjaPerteneceZona/{nombreZona}")
    @Produces({MediaType.APPLICATION_XML})
    public GranjaEntity GranjaALaQuePerteneceEsazona(@PathParam("nombreZona") String nombreZona) {
       GranjaEntity granja = null;
        try {
            LOGGER.info("Listado de la granja a la que pertenece esa zona");
            granja =  (GranjaEntity) em.createNamedQuery("GranjaALaQuePerteneceEsazona").setParameter("nombreZona", nombreZona).getSingleResult();
        } catch (Exception e) {
            LOGGER.severe("No hay ninguna zona asociada a esa granja " +e.getLocalizedMessage());
        }
        return granja;
    }

    @GET
    @Path("granjasTrabajador/{username}")
    @Produces({MediaType.APPLICATION_XML})
    public List<GranjaEntity> GranjasEnLasQueTrabajaEseTrabajador(@PathParam("username") String username) {
        List<GranjaEntity> granjas = null;
        try {
            LOGGER.info("Listado de las granjas donde trabaja ese trabajador");
            granjas = em.createNamedQuery("GranjasEnLasQueTrabajaEseTrabajador").setParameter("username", username).getResultList();
        } catch (Exception e) {
            LOGGER.severe("No hay ningun trabajador que trabaje en esa granja " +e.getLocalizedMessage());
        }
        return granjas;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
