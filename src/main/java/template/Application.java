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
