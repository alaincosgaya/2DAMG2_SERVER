/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import entidades.AnimalEntity;
import entidades.EstadoAnimal;
import entidades.SexoAnimal;
import entidades.TipoAnimal;
import java.util.List;
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
@Path("entidades.animalentity")
public class AnimalEntityFacadeREST extends AbstractFacade<AnimalEntity> {
    private static final Logger logger = Logger.getLogger(AnimalEntityFacadeREST.class.getName());

    @PersistenceContext(unitName = "LauserriServidorPU")
    private EntityManager em;

    public AnimalEntityFacadeREST() {
        super(AnimalEntity.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(AnimalEntity entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, AnimalEntity entity) {
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
    public AnimalEntity find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AnimalEntity> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AnimalEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GET
    @Path("nombreAnimal/{nombreAnimal}")
    @Produces({MediaType.APPLICATION_XML})
    public List<AnimalEntity> animalesPorNombre(@PathParam("nombreAnimal") String nombreAnimal) {

        List<AnimalEntity> animales = null;
        try {
            logger.info("realizando listado de animales según su nombre");
            animales = em.createNamedQuery("animalesPorNombre").setParameter("nombreAnimal", nombreAnimal).getResultList();

        } catch (Exception e) {
            logger.severe("Error al listar los animales según su nombre");
            throw new NotFoundException("no se ha encontrado la ruta especificada");
        }
        return animales;
    }

    @GET
    @Path("TipoAnimal/{tipo}")
    @Produces({MediaType.APPLICATION_XML})
    public List<AnimalEntity> animalesPorTipo(@PathParam("tipo") TipoAnimal tipo) {

        List<AnimalEntity> animales = null;
        try {
            animales = em.createNamedQuery("animalesPorTipo").setParameter("tipo", tipo).getResultList();

        } catch (Exception e) {
            throw new NotFoundException(e);
        }
        return animales;
    }

    @GET
    @Path("animalSexo/{sexo}")
    @Produces({MediaType.APPLICATION_XML})
    public List<AnimalEntity> animalesPorTipo(@PathParam("sexo") SexoAnimal sexo) {

        List<AnimalEntity> animales = null;
        try {
            //LOGGER.info("realizando listado de animales según su sexo");
            animales = em.createNamedQuery("animalesPorSexo").setParameter("sexo", sexo).getResultList();

        } catch (Exception e) {
            //LOGGER.severe("Error al listar los animales según su sexo");
        }
        return animales;
    }

    @GET
    @Path("estadoAnimal/{estado}")
    @Produces({MediaType.APPLICATION_XML})
    public List<AnimalEntity> animalesPorTipo(@PathParam("estado") EstadoAnimal estado) {

        List<AnimalEntity> animales = null;
        try {
            //LOGGER.info("realizando listado de animales según su estado");
            animales = em.createNamedQuery("animalesPorEstado").setParameter("estado", estado).getResultList();

        } catch (Exception e) {
            //LOGGER.severe("Error al listar los animales según su estado");

        }
        return animales;
    }

    @GET
    @Path("cambiarEstadoAnimal/{idAnimal}/{estado}")
    @Produces({MediaType.APPLICATION_XML})
    public void cambiarEstadoAnimal(@PathParam("idAnimal") Long idAnimal,@PathParam("estado") EstadoAnimal estado){
        List<AnimalEntity> animales = null;
        try{
           animales = em.createNamedQuery("cambiarEstadoAnimal").setParameter("idAnimal", idAnimal).getResultList();
           animales.get(0).setEstado(estado);
           em.merge(animales.get(0));
           em.flush();
        }catch(Exception e){

        }   
    }
   /* 
    @GET
    @Path("eliminarAnimal/{idAnimal}")
    @Produces({MediaType.APPLICATION_XML})
    public void eliminarAnimal(@PathParam("idAnimal") Long idAnimal){
        List<AnimalEntity> animales = null;
        try{
           animales = em.createNamedQuery("eliminarAnimal").setParameter("idAnimal", idAnimal).getResultList();
           animales.remove(0);
           em.merge(animales.get(0));
           em.flush();
        }catch(Exception e){

        }   
    }
    */
    
    @DELETE
    @Path("eliminarAnimalSegunEstado/{idAnimal}")
    public void eliminarAnimal(@PathParam("idAnimal") Long idAnimal){

        //AnimalEntity animalEntity = find(idAnimal);
        
        try{
            //getEntityManager().remove(getEntityManager().merge(entity));
            
           em.createNamedQuery("eliminarAnimal").setParameter("idAnimal", idAnimal).executeUpdate();
                   
                  // em.remove(animalEntity);
                   //em.merge(animalEntity);
           //animales.get(0);

            //em.createNamedQuery("eliminarAnimal").setParameter("idAnimal", idAnimal)
            //em.remove(getEntityManager().merge(entity));
        }catch(Exception e){
            
        }
        
    }

    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
