/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import cifrado.Cifrado;
import cifrado.Hash;
import cifrado.Mail;
import entidades.UserEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
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
@Path("entidades.userentity")
public class UserEntityFacadeREST extends AbstractFacade<UserEntity> {

    @PersistenceContext(unitName = "LauserriServidorPU")
    private EntityManager em;

    public UserEntityFacadeREST() {
        super(UserEntity.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(UserEntity entity) {

        Cifrado cf = new Cifrado();
        Hash hash = new Hash();
        //String text = new String(cf.descifrarTexto(entity.getPassword().getBytes()));
        String text = Cifrado.decrypt(entity.getPassword());

        System.out.println(text);

        text = hash.cifrarTexto(text);
        entity.setPassword(text);
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Long id, UserEntity entity) {

        Cifrado cf = new Cifrado();
        Hash hash = new Hash();
        //String text = new String(cf.descifrarTexto(entity.getPassword().getBytes()));
        String text = Cifrado.decrypt(entity.getPassword());

        System.out.println("edit servidor : " + text);

        text = hash.cifrarTexto(text);
        entity.setPassword(text);
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
    public UserEntity find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserEntity> findAll() {
        return super.findAll();
    }

    @GET
    @Path("validarLogin/{username}/{password}")
    @Produces({MediaType.APPLICATION_XML})
    public List<UserEntity> validarLogin(@PathParam("username") String username, @PathParam("password") String password) {

        List<UserEntity> users = null;
        //Cifrado cf = new cifrado();
        Hash hash = new Hash();
        password = Cifrado.decrypt(password);
        password = hash.cifrarTexto(password);
        try {
            users = em.createNamedQuery("validarLogin").setParameter("username", username).setParameter("password", password).getResultList();

        } catch (Exception e) {

        }
        return users;
    }

    @GET
    @Path("validatePassword/{username}/{passwd}")
    @Produces({MediaType.APPLICATION_XML})
    public List<UserEntity> validatePassword(@PathParam("username") String username,
            @PathParam("passwd") String passwd) {

        List<UserEntity> user = null;
        Hash hash = new Hash();
        try {

            String decryptPassword = Cifrado.decrypt(passwd);

            user = em.createNamedQuery("usuarioPorLogin")
                    .setParameter("username", username)
                    .getResultList();

            if (!user.get(0).getPassword().equalsIgnoreCase(hash.cifrarTexto(decryptPassword))) {
                throw new NotAuthorizedException("Las contraseñas no coinciden");
            }
            if (!user.isEmpty()) {
                StoredProcedureQuery query = em.createStoredProcedureQuery("G2Lauserri.login").registerStoredProcedureParameter(1, Long.class, ParameterMode.IN).setParameter(1, user.get(0).getId());
                query.execute();
                System.out.println("furrula");
            }

        } catch (NoResultException e) {
            throw new NotFoundException();
        }
        return user;
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @GET
    @Path("reset/{email}")
    @Produces({MediaType.APPLICATION_XML})
    public void resetContra(@PathParam("email") String email) {

        List<UserEntity> users = usuarioPorEmail(email);
        if (!users.isEmpty()) {
            Mail ml = new Mail();
            Cifrado cf = new Cifrado();
            String contra = cf.generarContra();
            String hash = cf.encriptarContra(contra);
            users.get(0).setPassword(hash);
            String mail = users.get(0).getEmail();
            Mail.sendEmail(mail, contra);
            em.merge(users.get(0));

            
        }
        em.flush();
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

    @GET
    @Path("usuarioPorEmail/{email}")
    @Produces({MediaType.APPLICATION_XML})
    public List<UserEntity> usuarioPorEmail(@PathParam("email") String email) {
        List<UserEntity> users = null;
        try {
            users = em.createNamedQuery("usuarioPorEmail").setParameter("email", email).getResultList();
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
