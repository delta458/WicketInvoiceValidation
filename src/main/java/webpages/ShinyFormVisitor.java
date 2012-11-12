/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webpages;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.border.BorderBehavior;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

/**
 *
 * @author Dave
 */
public class ShinyFormVisitor implements IVisitor<Component,Void>,Serializable {

    Set visited = new HashSet();

//    public Object component(Component c) {
//
//        if (!visited.contains(c)) {
//            visited.add(c);
//            c.add(new RequiredBorder());
//            c.add(new ValidationMsgBehavior());
//            c.add(new ErrorHighlightBehavior());
//        }
//        return IVisitor.CONTINUE_TRAVERSAL;
//    }
//
//    //New VisitorPattern
//    
//    @Override
//    public void component(final Component c, final IVisit<Void> visit) {
//        if (!visited.contains(c)) {
//            visited.add(c);
//            c.add(new RequiredBorder());
//            c.add(new ValidationMsgBehavior());
//            c.add(new ErrorHighlightBehavior());
//        }
//    }

    public void component(final Component c, final IVisit<Void> visit /*[2]*/) {
//		if (value.equals(component.getDefaultModelObject()))
//		{
//			addFormComponentValue(formComponent,((Radio<?>)component).getValue());
//			visit.stop(); /*[3]*/
//		}
//		else
//		{
//			visit.dontGoDeeper(); /*[4]*/
//		}
        if (!visited.contains(c)) {
            visited.add(c);
            c.add(new RequiredBorder());
            c.add(new ValidationMsgBehavior());
            c.add(new ErrorHighlightBehavior());
        }
   //    visit.dontGoDeeper();
    }

    private class RequiredBorder extends BorderBehavior {

        public void renderAfter(Component component) {
            FormComponent fc = (FormComponent) component;
            if (fc.isRequired()) {
                super.afterRender(component);
            }
        }
    }

    private class ValidationMsgBehavior extends Behavior {

        public void onRendered(Component c) {
            FormComponent fc = (FormComponent) c;
            if (!fc.isValid()) {
                String error;
                if (fc.hasFeedbackMessage()) {
                   //error = fc.getFeedbackMessages().toString();
                    error = fc.getFeedbackMessages().first().getLevelAsString();
                    //or error = fc.getFeedbackMessages().first(Level.ERROR); ??
                } else {
                    error = "Your input is invalid.";
                }
                fc.getResponse().write(
                        "<div class=\"validationMsg\">" + error + "</div>");
            }
        }
    }

    private class ErrorHighlightBehavior extends Behavior {

        @Override
        public void onComponentTag(Component c, ComponentTag tag) {
            FormComponent fc = (FormComponent) c;
            if (!fc.isValid()) {
                tag.put("class", "error");
            }
        }
    }
}
