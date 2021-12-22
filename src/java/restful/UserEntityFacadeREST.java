
   /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import cifrado.Cifrado;
import entidades.UserEntity;
import entidades.UserPrivilegeType;
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

/**
 *
 * @author 2dam
 */
@Stateless
@Path("entidades.userentity")
public class UserEntityFacadeREST extends AbstractFacade<UserEntity> {
    
    @PersistenceContext(unitName = "LauserriServidorPU")
    private EntityManager em;
    
    private final Logger LOGGER = Logger.getLogger(UserEntityFacadeREST.class.getName());

    public UserEntityFacadeREST() {
        super(UserEntity.class);
    }
    
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML})
    public void create(UserEntity entity) {
        super.create(entity);
    }
    
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML})
    public void edit(@PathParam("id") Long id, UserEntity entity) {
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
    public UserEntity find(@PathParam("id") Long id) {
        return super.find(id);
    }
    
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML})
    public List<UserEntity> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("validarLogin/{username}/{password}")
    @Produces({MediaType.APPLICATION_XML})
    public List<UserEntity> validarLogin(@PathParam("username") String username, @PathParam("password") String password) {
        List<UserEntity> users = null;
        try {
            users = em.createNamedQuery("validarLogin").setParameter("username", username).setParameter("password", password).getResultList();
            
        } catch (Exception e) {
            
        }
        return users;
    }

    @GET
    @Path("registro")    
    @Produces({MediaType.APPLICATION_XML})
    public List<UserEntity> registro(UserEntity userEntity) {
        userEntity = new UserEntity();
        List<UserEntity> users = null;
        userEntity.setUserPrivilege(UserPrivilegeType.GRANJERO);
        System.out.println(userEntity.getUserPrivilege());
        userEntity.setEmail("p@gmail.com");
        userEntity.setUsername("p1");
        try {
            users = em.createNamedQuery("validarRegistro").setParameter("username", userEntity.getUsername()).setParameter("email", userEntity.getEmail()).getResultList();
            if (users.isEmpty()) {
                em.merge(userEntity);
                LOGGER.info("El usuario ha sido registrado correctamente");
            }
             
            /*if(!em.contains(userEntity)){
                em.merge(userEntity);   
            }
            em.flush();*/
        } catch (Exception e) {
            
        }
        return users;
    }

    @GET
    @Path("reset/{username}")
    @Produces({MediaType.APPLICATION_XML})
    public void resetContra(@PathParam("username") String username) {
	
	List<UserEntity> users = usuarioPorLogin(username);
	if(!users.isEmpty()){
        	Cifrado cf = new Cifrado();
		String contra = cf.generarContra();
		users.get(0).setPassword(contra);
		em.merge(users.get(0));
                
                em.flush();
	}
    }

    @GET
    @Path("usuarioPorLogin/{username}")
    @Produces({MediaType.APPLICATION_XML})
    public List<UserEntity> usuarioPorLogin(@PathParam("username") String username) {
        List<UserEntity> users = null;
        try {
            users = em.createNamedQuery("usuarioPorLogin").setParameter("username", username).getResultList();
            //em.merge(users);
        } catch (Exception e) {
            
        }
        return users;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
