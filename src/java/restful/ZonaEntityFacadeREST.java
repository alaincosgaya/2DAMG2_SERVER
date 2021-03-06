/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import java.util.logging.Logger;
import entidades.TipoAnimal;
import entidades.TrabajadorEntity;
import entidades.ZonaEntity;
import java.util.List;
import java.util.logging.Level;
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
@Path("entidades.zonaentity")
public class ZonaEntityFacadeREST extends AbstractFacade<ZonaEntity> {

    Logger logger = Logger.getLogger(ZonaEntityFacadeREST.class.getName());
    @PersistenceContext(unitName = "LauserriServidorPU")
    private EntityManager em;

    public ZonaEntityFacadeREST() {
        super(ZonaEntity.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(ZonaEntity entity) {
        if(!em.contains(entity)){
            em.merge(entity);
        }
        em.flush();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, ZonaEntity entity) {
        super.edit(entity);
    }

    @GET
    @Path("cambiarNombreZona/{nombreZona}/{nombreZona1}")
    @Produces({MediaType.APPLICATION_XML})
    public void cambiarNombreZona(@PathParam("nombreZona") String nombreZona, @PathParam("nombreZona1") String nombreZona1) {
        List<ZonaEntity> zonas = null;
        zonas = (List<ZonaEntity>) em.createNamedQuery("cambiarNombreZona").setParameter("nombreZona", nombreZona).getResultList();
        zonas.get(0).setNombreZona(nombreZona1);
        em.merge(zonas.get(0));
        em.flush();
    }

    @GET
    @Path("quitarTrabajadorZona/{username}/{idZona}")
    @Produces({MediaType.APPLICATION_XML})
    public void quitarTrabajadorZona(@PathParam("username") String username, @PathParam("idZona") Long idZona) {
        ZonaEntity zona = null;
        zona = (ZonaEntity) em.createNamedQuery("quitarTrabajadorZona").setParameter("username", username).setParameter("idZona", idZona).getSingleResult();

        for (TrabajadorEntity trabajador : zona.getTrabajadores()) {
            if (trabajador.getUsername().equals(username)) {
                zona.getTrabajadores().remove(trabajador);
                break;
            }

            em.merge(zona);
        }

        em.flush();
    }

    @GET
    @Path("asignarTrabajador/{username}/{idZona}")
    @Produces({MediaType.APPLICATION_XML})
    public void asignarTrabajador(@PathParam("username") String username, @PathParam("idZona") Long idZona, TrabajadorEntity entity) {
        //List<ZonaEntity> zonas = null;
        //zonas = em.createNamedQuery("asignarTrabajador").setParameter("idZona", idZona).setParameter("username", username).getResultList();
        //for (ZonaEntity zona : zonas) {

        ZonaEntity zona = find(idZona);
        entity = (TrabajadorEntity) em.createNamedQuery("asignarTrabajador").setParameter("username", username).getSingleResult();
        zona.getTrabajadores().add(entity);

        em.merge(zona);
        //}

        em.flush();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ZonaEntity find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ZonaEntity> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ZonaEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("zonaNombre/{nombreZona}")
    @Produces({MediaType.APPLICATION_XML})
    public List<ZonaEntity> zonasPorNombre(@PathParam("nombreZona") String nombreZona) {

        List<ZonaEntity> zonas = null;
        try {
            zonas = em.createNamedQuery("zonasPorNombre").setParameter("nombreZona", nombreZona).getResultList();
        } catch (Exception e) {

        }
        return zonas;
    }

    @GET
    @Path("zonaTipo/{tipo}")
    @Produces({MediaType.APPLICATION_XML})
    public List<ZonaEntity> zonasPorAnimal(@PathParam("tipo") TipoAnimal tipo) throws NotFoundException {
        List<ZonaEntity> zonas;

        logger.info("El try");
        zonas = em.createNamedQuery("zonasPorAnimal").setParameter("tipo", tipo).getResultList();

        return zonas;
    }

    @GET
    @Path("zonaUsername/{username}")
    @Produces({MediaType.APPLICATION_XML})
    public List<ZonaEntity> zonasPorTrabajador(@PathParam("username") String username) throws NotFoundException {

        List<ZonaEntity> zonas = null;
        try {
            logger.info("El try");
            zonas = em.createNamedQuery("zonasPorTrabajador").setParameter("username", username).getResultList();
        } catch (Exception e) {
            logger.severe("ERROR");
            throw new NotFoundException(e);
        }
        return zonas;
    }

    @GET
    @Path("zonaidGranja/{idGranja}")
    @Produces({MediaType.APPLICATION_XML})
    public List<ZonaEntity> zonasPorGranja(@PathParam("idGranja") long idGranja) throws NotFoundException {

        List<ZonaEntity> zonas = null;
        try {
            logger.info("El try");
            zonas = em.createNamedQuery("zonasPorGranja").setParameter("idGranja", idGranja).getResultList();
        } catch (Exception e) {
            logger.severe("ERROR");
            throw new NotFoundException(e);
        }
        return zonas;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
