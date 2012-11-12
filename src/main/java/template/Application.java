/*
 * Application.java
 *
 * Created on September 19, 2012, 1:31 PM
 */
 
package template;           

import org.apache.wicket.protocol.http.WebApplication;
import webpages.Intro;
/** 
 *
 * @author Dave
 * @version 
 */

public class Application extends WebApplication {

    public Application() {
    }

    public Class getHomePage() {
        return Intro.class;
    }

}
