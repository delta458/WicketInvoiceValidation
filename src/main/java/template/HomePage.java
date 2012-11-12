/*
 * HomePage.java
 *
 * Created on September 19, 2012, 1:31 PM
 */

package template;           

import org.apache.wicket.markup.html.basic.Label;

public class HomePage extends BasePage {

    public HomePage() {
        add(new Label("message", "Hello, World!"));
    }

}
