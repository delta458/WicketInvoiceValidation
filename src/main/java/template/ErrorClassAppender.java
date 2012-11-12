/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package template;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.FormComponent;

/**
 *
 * @author Dave
 */
public class ErrorClassAppender extends Behavior {

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        if (((FormComponent<?>) component).isValid() == false) {
            String cl = tag.getAttribute("class");
            if (cl == null) {
                tag.put("class", "error");
            } else {
                tag.put("class", "error " + cl);
            }
        }
    }
    
   
}
