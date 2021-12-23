/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entidades.GranjeroEntity;
import entidades.UserEntity;
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
import java.util.logging.Logger;

/**
 *
 * @author 2dam
 */
@Stateless
@Path("entidades.granjeroentity")
public class GranjeroEntityFacadeREST extends AbstractFacade<GranjeroEntity> {

    @PersistenceContext(unitName = "LauserriServidorPU")
    private final Logger LOGGER = Logger.getLogger(TrabajadorEntityFacadeREST.class.getName());
    private EntityManager em;

    public GranjeroEntityFacadeREST() {
        super(GranjeroEntity.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(GranjeroEntity entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, GranjeroEntity entity) {
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
    public GranjeroEntity find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<GranjeroEntity> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<GranjeroEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("registro")
    @Produces({MediaType.APPLICATION_XML})
    public void registro(GranjeroEntity granjeroEntity) {
        granjeroEntity = new GranjeroEntity();
        List<UserEntity> users = null;

        try {
            LOGGER.info("Recogiendo listado de usuarios registrados");
            users = em.createNamedQuery("validarRegistro")
                    .setParameter("username", granjeroEntity.getUsername())
                    .setParameter("email", granjeroEntity.getEmail()).getResultList();
            if (users.isEmpty()) {
                em.merge(granjeroEntity);
                LOGGER.info("El granjero ha sido registrado correctamente");
            } else {

            }

        } catch (Exception e) {
            LOGGER.severe("Error registrando al usuario. " + e.getLocalizedMessage());
        }

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
