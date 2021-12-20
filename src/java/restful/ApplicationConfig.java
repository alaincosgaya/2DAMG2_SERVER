/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restful;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author 2dam
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(restful.AnimalEntityFacadeREST.class);
        resources.add(restful.ContratoEntityFacadeREST.class);
        resources.add(restful.GranjaEntityFacadeREST.class);
        resources.add(restful.GranjeroEntityFacadeREST.class);
        resources.add(restful.TrabajadorEntityFacadeREST.class);
        resources.add(restful.UserEntityFacadeREST.class);
        resources.add(restful.ZonaEntityFacadeREST.class);
    }
    
}
