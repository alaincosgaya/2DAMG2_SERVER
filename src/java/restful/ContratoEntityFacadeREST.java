/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entidades.ContratoEntity;
import entidades.ContratoId;
import java.util.List;
import java.util.logging.Logger;
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
 * RESTful de la entidad de contratos
 *
 * @author Alain Cosgaya
 */
@Stateless
@Path("entidades.contratoentity")
public class ContratoEntityFacadeREST extends AbstractFacade<ContratoEntity> {

    @PersistenceContext(unitName = "LauserriServidorPU")
    private EntityManager em;
    private final Logger LOGGER = Logger.getLogger(ContratoEntityFacadeREST.class.getName());

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

    /**
     * Constructor de contratos.
     */
    public ContratoEntityFacadeREST() {
        super(ContratoEntity.class);
    }

    /**
     * Metodo de creacion de contratos
     *
     * @param entity
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(ContratoEntity entity) {
        if (!em.contains(entity)) {
            em.merge(entity);
        }
        em.flush();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") PathSegment id, ContratoEntity entity) {
        super.edit(entity);
    }

    /**
     * Metodo de borrado de contratos.
     *
     * @param id
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        entidades.ContratoId key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    /**
     * Metodo de busqueda de un contrato por id
     *
     * @param id
     * @return
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public ContratoEntity find(@PathParam("id") PathSegment id) {
        entidades.ContratoId key = getPrimaryKey(id);
        return super.find(key);
    }

    /**
     * Metodo de busqueda de todos los contratos
     *
     * @return Coleccion de contratos
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<ContratoEntity> findAll() {
        return super.findAll();
    }

    /**
     * Metodo para el despido de un trabajador
     *
     * @param idTrabajador id del trabajador a despedir
     * @param idGranja id de la granja en la que está contratado
     */
    @DELETE
    @Path("despedir/{idTrabajador}/{idGranja}")
    public void despedirTrabajador(@PathParam("idTrabajador") Long idTrabajador, @PathParam("idGranja") Long idGranja) {
        try {
            em.createNamedQuery("despedirTrabajador").setParameter("idTrabajador", idTrabajador)
                    .setParameter("idGranja", idGranja).executeUpdate();
            LOGGER.info("Despido del trabajador realizado correctamente");
        } catch (Exception e) {
            LOGGER.severe("Error al despedir trabajador. "
                    + e.getLocalizedMessage());
        }

    }

    /*@GET
    @Path("cambiarSueldo")
    public void cambiarSueldo(ContratoEntity contrato) {
        try {
            
            em.createNamedQuery("cambiarSueldo").setParameter("salario",10).setParameter("idTrabajador",1).setParameter("idGranja",1).executeUpdate();
            if (!em.contains(contrato)) {
                em.merge(contrato);
            }
            em.flush();
        } catch (Exception e) {

        }
    }*/
    /**
     * Metodo para el cambio de salario definido en un contrato.
     *
     * @param idTrabajador id del trabajador del contrato
     * @param idGranja id de la granja del contrato
     * @param salario nuevo salario del contrato
     */
    @GET
    @Path("cambiarSueldo/{idTrabajador}/{idGranja}/{salario}")
    @Produces({MediaType.APPLICATION_XML})
    public void cambiarSueldo(@PathParam("idTrabajador") Long idTrabajador,
            @PathParam("idGranja") Long idGranja, @PathParam("salario") Long salario) {
        ContratoEntity contrato = null;
        try {
            contrato = (ContratoEntity) em.createNamedQuery("contratoTrabajador")
                    .setParameter("idTrabajador", idTrabajador)
                    .setParameter("idGranja", idGranja).getSingleResult();
            LOGGER.info("Recogiendo contrato del trabajador");

            contrato.setSalario(salario);
            if (!em.contains(contrato)) {
                em.merge(contrato);
                LOGGER.info("Cambios del sueldo del trabajador exitoso");
            }
            em.flush();
        } catch (Exception e) {
            LOGGER.severe("Error al modificar datos del trabajador. "
                    + e.getLocalizedMessage());
        }

    }

    /**
     * Metodo de busqueda de contratos de un granjero
     * @param idGranjero id del granjero
     * @return Coleccion de contratos
     */
    @GET
    @Path("contratosGranjero/{idGranjero}")
    @Produces({MediaType.APPLICATION_XML})
    public List<ContratoEntity> contratosGranjero(@PathParam("idGranjero") Long idGranjero) {
        List<ContratoEntity> contratos = null;
        try {
            contratos = em.createNamedQuery("contratosGranjero").setParameter("idGranjero", idGranjero).getResultList();
        } catch (Exception e) {
            LOGGER.severe("Error al listar los contratos por granjero. "
                    + e.getLocalizedMessage());
        }
        return contratos;
    }

    /**
     * Metodo de busqueda de contratos de un trabajador
     * @param idTrabajador id del trabajador
     * @return Coleccion de contratos
     */
    @GET
    @Path("contratosTrabajador/{idTrabajador}")
    @Produces({MediaType.APPLICATION_XML})
    public List<ContratoEntity> contratosTrabajador(@PathParam("idTrabajador") Long idTrabajador) {
        List<ContratoEntity> contratos = null;
        try {
            contratos = em.createNamedQuery("contratosTrabajador").setParameter("idTrabajador", idTrabajador).getResultList();
        } catch (Exception e) {
            LOGGER.severe("Error al listar los contratos por granjero. "
                    + e.getLocalizedMessage());
        }
        return contratos;
    }

    /**
     * Metodo de busqueda de contratos en una granja
     * @param idGranja id de la granja
     * @return Coleccion de contratos
     */
    @GET
    @Path("contratosGranja/{idGranja}")
    @Produces({MediaType.APPLICATION_XML})
    public List<ContratoEntity> contratosGranja(@PathParam("idGranja") Long idGranja) {
        List<ContratoEntity> contratos = null;
        try {
            contratos = em.createNamedQuery("contratosGranja").setParameter("idGranja", idGranja).getResultList();
        } catch (Exception e) {
            LOGGER.severe("Error al listar los contratos por granjero. "
                    + e.getLocalizedMessage());
        }
        return contratos;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}