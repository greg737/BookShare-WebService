package nz.ac.auckland.bookShare.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * Application class for the BookShare Web service. This class is required by the 
 * JAX-RS implementation to deploy the Web service.
 *  
 * The relative path of the Web service beings with "services". If the Web 
 * server's address is "http://localhost:8080", the URI for hosted Web services
 * thus begins "http://localhost:8080/services". 
 * 
 * @author Greggory Tan
 *
 */
@ApplicationPath("/services")
public class BookShareApplication extends Application
{
   private Set<Object> singletons = new HashSet<Object>();
   private Set<Class<?>> classes = new HashSet<Class<?>>();

   public BookShareApplication()
   {
      singletons.add(new EntityManagerFactorySingleton());
      classes.add(UserResource.class);
      classes.add(UserResolver.class);
      classes.add(BookResource.class);
      classes.add(RequestResource.class);
   }

   @Override
   public Set<Object> getSingletons()
   {
      return singletons;
   }
   
   @Override
   public Set<Class<?>> getClasses()
   {
      return classes;
   }
}
