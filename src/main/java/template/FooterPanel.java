package template;           

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/** 
 *
 * @author Dave
 * @version 
 */

public final class FooterPanel extends Panel {

    public FooterPanel(String id, String text) {
        super(id);
        add(new Label("footerpanel_text", text));
    }

}
