package restful;

import entidades.AnimalEntity;
import entidades.EstadoAnimal;
import entidades.SexoAnimal;
import entidades.TipoAnimal;
import entidades.ZonaEntity;
import java.util.Date;
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
 * Clase destinada a la ejecucion de las diferentes operaciones de la entidad
 * AnimalEntity.
 *
 * La clase contendra tanto operaciones autogeneradas como operaciones
 * personalizadas
 *
 * @author Jonathan Camacho
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

    /**
     * Este metodo trata de una operacion autogenerada destinada a la insercion
     * de animal
     *
     * @param entity
     */
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(AnimalEntity entity) {
        //super.create(entity);
        if (!em.contains(entity)) {
            em.merge(entity);
        }
        em.flush();
    }

    /**
     * Este metodo trata una operacion autogenerada destinada a la actuañizacion
     * del animal
     *
     * @param id sera el parametro con el que localizar a animal a actualizar
     * @param entity sera la entidad Animal
     */
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, AnimalEntity entity) {
        super.edit(entity);
    }

    /**
     * Este metodo trata una operacion autogenerada destinada a la eliminacion
     * de un animal
     *
     * @param id sera el parametro para localizar al animal a eliminar
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    /**
     * Este metodo trata una operacion autogenerada destinada a encontrar a un
     * animal mediante su id
     *
     * @param id
     * @return Al animal
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML})
    public AnimalEntity find(@PathParam("id") Long id) {
        return super.find(id);
    }

    /**
     * Metodo con el cual se listan todos los animales que actualmente estan en
     * la base de datos.
     *
     * @return una lista de animales
     */
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<AnimalEntity> findAll() {
        return super.findAll();
    }

    /**
     * Metodo con el cual se permite la busqueda de animales en un rango
     * concreto
     *
     * @param from
     * @param to
     * @return Una lista de animales
     */
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML})
    public List<AnimalEntity> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    /**
     * Metodo el cual esta destinado a listar los animales por nombre.
     *
     * @param nombreAnimal pasara el nombre del animal que se quiere encontrar
     * @return
     */
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

    /**
     * Metodo que listara los animales en funcion al tipo al que pertenezca.
     *
     * @param tipo sera el tipo de animal que se desea buscar, por ejemplo: VACA
     * @return
     */
    @GET
    @Path("TipoAnimal/{tipo}")
    @Produces({MediaType.APPLICATION_XML})
    public List<AnimalEntity> animalesPorTipo(@PathParam("tipo") TipoAnimal tipo) {

        List<AnimalEntity> animales = null;
        try {
            logger.info("realizando listado de animales según su tipo");
            animales = em.createNamedQuery("animalesPorTipo").setParameter("tipo", tipo).getResultList();

        } catch (Exception e) {
            logger.severe("Error al listar los animales según su tipo");
            throw new NotFoundException(e);
        }
        return animales;
    }

    /**
     * Metodo destinado a los animales en funcion al sexo al que pertenezcan.
     *
     * @param sexo
     * @return
     */
    @GET
    @Path("animalSexo/{sexo}")
    @Produces({MediaType.APPLICATION_XML})
    public List<AnimalEntity> animalesPorSexo(@PathParam("sexo") SexoAnimal sexo) {

        List<AnimalEntity> animales = null;
        try {
            logger.info("realizando listado de animales según su sexo");
            animales = em.createNamedQuery("animalesPorSexo").setParameter("sexo", sexo).getResultList();

        } catch (Exception e) {
            logger.severe("Error al listar los animales según su sexo");

        }
        return animales;
    }

    /**
     * Metodo destinado a listar todos los animales en funcion a su estado
     *
     * @param estado es la variable que le pasara el usuario
     * @return
     */
    @GET
    @Path("estadoAnimal/{estado}")
    @Produces({MediaType.APPLICATION_XML})
    public List<AnimalEntity> animalesPorEstado(@PathParam("estado") EstadoAnimal estado) {

        List<AnimalEntity> animales = null;
        try {
            logger.info("realizando listado de animales según su estado");
            animales = em.createNamedQuery("animalesPorEstado").setParameter("estado", estado).getResultList();

        } catch (Exception e) {
            logger.severe("Error al listar los animales según su estado");

        }
        return animales;
    }

    /**
     * Metodo destinado al cambio de estado de un animal.
     *
     * @param idAnimal Será la id con la que localizar al animal deseado
     * @param estado Será el nuevo estado que tendrá ese animal
     */
    @GET
    @Path("cambiarEstadoAnimal/{idAnimal}/{estado}")
    @Produces({MediaType.APPLICATION_XML})
    public void cambiarEstadoAnimal(@PathParam("idAnimal") Long idAnimal, @PathParam("estado") EstadoAnimal estado) {
        List<AnimalEntity> animales = null;
        boolean muerto = false;
        try {
            logger.info("realizando la busqueda del animal para su cambio de estado");
            animales = em.createNamedQuery("modificacionAnimal").setParameter("idAnimal", idAnimal).getResultList();
            animales.get(0).setEstado(estado);
            //en cado de que el animal este muerto, lo borramos directamente de la base de datos
            for (AnimalEntity animal : animales) {
                if (animal.getEstado().equals(EstadoAnimal.MUERTO)) {
                    muerto = true;
                    break;
                }
            }
            if (muerto) {
                em.remove(animales.get(0));
            }
            em.flush();
        } catch (Exception e) {
            logger.severe("error al realizar la busqueda del animal para su cambio de estado");
        }
    }

    /**
     * Metodo destinado al cambio de sexo de un animal.
     *
     * @param idAnimal Será la id con la que localizar al animal deseado
     * @param sexo Será el nuevo sexo que tendrá ese animal
     */
    @GET
    @Path("cambiarSexoAnimal/{idAnimal}/{sexo}")
    @Produces({MediaType.APPLICATION_XML})
    public void cambiarSexoAnimal(@PathParam("idAnimal") Long idAnimal, @PathParam("sexo") SexoAnimal sexo) {
        List<AnimalEntity> animales = null;
        try {
            logger.info("realizando la busqueda del animal para su cambio de sexo");
            animales = em.createNamedQuery("modificacionAnimal").setParameter("idAnimal", idAnimal).getResultList();
            animales.get(0).setSexo(sexo);
            em.flush();
        } catch (Exception e) {
            logger.severe("error al realizar la busqueda del animal para su cambio de sexo");
        }
    }

    /**
     * Metodo destinado al cambio de tipo de un animal.
     *
     * @param idAnimal Será la id con la que localizar al animal deseado
     * @param tipo Será el nuevo tipo que tendrá ese animal
     */
    @GET
    @Path("cambiarTipoAnimal/{idAnimal}/{tipo}")
    @Produces({MediaType.APPLICATION_XML})
    public void cambiarTipoAnimal(@PathParam("idAnimal") Long idAnimal, @PathParam("tipo") TipoAnimal tipo) {
        List<AnimalEntity> animales = null;
        try {
            logger.info("realizando la busqueda del animal para su cambio de tipo");
            animales = em.createNamedQuery("modificacionAnimal").setParameter("idAnimal", idAnimal).getResultList();
            animales.get(0).setTipo(tipo);
            em.flush();
        } catch (Exception e) {
            logger.severe("error al realizar la busqueda del animal para su cambio de tipo");
        }
    }

    /**
     * Metodo destinado al cambio de nombre de un animal.
     *
     * @param idAnimal Será la id con la que localizar al animal deseado
     * @param nombreAnimal Será el nuevo nombre que tendrá ese animal
     */
    @GET
    @Path("cambiarNombreAnimal/{idAnimal}/{nombreAnimal}")
    @Produces({MediaType.APPLICATION_XML})
    public void cambiarNombreAnimal(@PathParam("idAnimal") Long idAnimal, @PathParam("nombreAnimal") String nombreAnimal) {
        List<AnimalEntity> animales = null;
        try {
            logger.info("realizando la busqueda del animal para su cambio de nombre");
            animales = em.createNamedQuery("modificacionAnimal").setParameter("idAnimal", idAnimal).getResultList();
            animales.get(0).setNombreAnimal(nombreAnimal);
            em.flush();
        } catch (Exception e) {
            logger.severe("error al realizar la busqueda del animal para su cambio de nombre");
        }
    }

    /**
     * Metodo con el cual se controla la entidad
     *
     * @return La entidad
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}