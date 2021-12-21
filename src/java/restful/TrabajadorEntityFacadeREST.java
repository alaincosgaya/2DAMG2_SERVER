/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entidades.ContratoEntity;
import entidades.TrabajadorEntity;
import entidades.ZonaEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author 2dam
 */
@Stateless
@Path("entidades.trabajadorentity")
public class TrabajadorEntityFacadeREST extends AbstractFacade<TrabajadorEntity> {

    @PersistenceContext(unitName = "LauserriServidorPU")
    private EntityManager em;
    private final Logger LOGGER = Logger.getLogger(TrabajadorEntityFacadeREST.class.getName());

    public TrabajadorEntityFacadeREST() {
        super(TrabajadorEntity.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(TrabajadorEntity entity) {
        super.create(entity);

    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, TrabajadorEntity entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public TrabajadorEntity find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<TrabajadorEntity> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<TrabajadorEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("contratables/{idGranja}")
    @Produces({MediaType.APPLICATION_XML})
    public List<TrabajadorEntity>
            trabajadoresParaContratar(@PathParam("idGranja") Long idGranja)
            throws NotFoundException {
        List<TrabajadorEntity> trabajadores = null;
        try {
            LOGGER.info("Recogiendo listado de trabajadores por contratar");
            trabajadores = em.createNamedQuery("trabajadoresParaContratar")
                    .setParameter("granjaId", idGranja).getResultList();
        } catch (Exception e) {
            LOGGER.severe("Error listando trabajadores por contratar. "
                    + e.getLocalizedMessage());
            throw new NotFoundException(e);
        }
        return trabajadores;
    }

    @GET
    @Path("contratados/{idGranja}")
    @Produces({MediaType.APPLICATION_XML})
    public List<TrabajadorEntity>
            trabajadoresGranja(@PathParam("idGranja") Long idGranja) {
        List<TrabajadorEntity> trabajadores = null;
        try {
            LOGGER.info("Recogiendo listado de trabajadores contratados");
            trabajadores = em.createNamedQuery("trabajadoresGranja")
                    .setParameter("granjaId", idGranja).getResultList();
        } catch (Exception e) {
            LOGGER.severe("Error listando trabajadores contratados. "
                    + e.getLocalizedMessage());

        }
        return trabajadores;
    }

    @GET
    @Path("zonas/{idZona}")
    @Produces({MediaType.APPLICATION_XML})
    public List<TrabajadorEntity>
            trabajadoresZona(@PathParam("idZona") Long idZona) {
        List<TrabajadorEntity> trabajadores = null;
        try {
            LOGGER.info("Recogiendo listado de trabajadores de la zona");
            trabajadores = em.createNamedQuery("trabajadoresZona")
                    .setParameter("zonaId", idZona).getResultList();
        } catch (Exception e) {
            LOGGER.severe("Error listando trabajadores de la zona. "
                    + e.getLocalizedMessage());

        }
        return trabajadores;
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
