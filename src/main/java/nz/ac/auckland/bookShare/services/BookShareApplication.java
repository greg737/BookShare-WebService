package nz.ac.auckland.bookShare.services;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

/**
 * Application class for the User Web service. This class is required by the 
 * JAX-RS implementation to deploy the Web service.
 *  
 * The relative path of the Web service beings with "services". If the Web 
 * server's address is "http://localhost:8080", the URI for hosted Web services
 * thus begins "http://localhost:8080/services". 
 * 
 * The UserResource specifies a URI path of "Users", so this is appended, 
 * making the Users Web service URI "http://localhost:8080/services/Users".
 * 
 * The UserApplication has only one Resource class (UserResourceImpl) and 
 * this is to be deployed as a singleton, encapsulating a list of User 
 * objects maintained by the Web service.
 *  
 * @author Ian Warren
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
