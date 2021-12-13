/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entidades.ContratoEntity;
import entidades.ContratoId;
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
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author 2dam
 */
@Stateless
@Path("entidades.contratoentity")
public class ContratoEntityFacadeREST extends AbstractFacade<ContratoEntity> {

    @PersistenceContext(unitName = "LauserriServidorPU")
    private EntityManager em;

    private ContratoId getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;trabajadorId=trabajadorIdValue;granjaId=granjaIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entidades.ContratoId key = new entidades.ContratoId();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> trabajadorId = map.get("trabajadorId");
        if (trabajadorId != null && !trabajadorId.isEmpty()) {
            key.setTrabajadorId(new java.lang.Long(trabajadorId.get(0)));
        }
        java.util.List<String> granjaId = map.get("granjaId");
        if (granjaId != null && !granjaId.isEmpty()) {
            key.setGranjaId(new java.lang.Long(granjaId.get(0)));
        }
        return key;
    }

    public ContratoEntityFacadeREST() {
        super(ContratoEntity.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(ContratoEntity entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, ContratoEntity entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entidades.ContratoId key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ContratoEntity find(@PathParam("id") PathSegment id) {
        entidades.ContratoId key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ContratoEntity> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ContratoEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
