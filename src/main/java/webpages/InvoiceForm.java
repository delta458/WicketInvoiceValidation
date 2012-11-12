/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webpages;

import java.util.Arrays;
import java.util.List;
import model.Invoice;
import model.ValidationProcess;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import template.BasePage;
import template.ErrorClassAppender;

/**
 * Dies ist die korrespondierende Klasse zu InvoiceForm.java. Fügt die nötigen
 * Komponenten mittels add(...) dem Formular hizu.
 *
 * @author Dave
 */
public final class InvoiceForm extends BasePage {

    static final List<String> NUMBERS = Arrays.asList("10", "20");
    Invoice r = new Invoice();
    
    /**
     * Im Konstruktor erfolgt die benötigte Initialisierung der Komponenten für
     * das InvoiceForm.
     */
    public InvoiceForm() {
        
        setPageTitle("Invoice-Form");

        final FeedbackPanel feedback = new FeedbackPanel("feedback");
        feedback.setOutputMarkupId(true);
        add(feedback);

        CompoundPropertyModel<Invoice> formModel = new CompoundPropertyModel<Invoice>(r);

        /*
         * Add Behaviours to Textfields. Needed for Error Highlighting.
         */
        final TextField<String> recipient = new TextField<String>("Recipient");
        recipient.add(new ErrorClassAppender());
        final TextField<String> details = new TextField<String>("Details");
        details.add(new ErrorClassAppender());
        final TextField<Double> price = new TextField<Double>("Price");
        price.add(new ErrorClassAppender());
   
        final RadioChoice<String> rc = new RadioChoice<String>("Tax", NUMBERS).setSuffix("");
        rc.setLabel(new Model<String>("number"));
        rc.add(new ErrorClassAppender());

        Form<Invoice> form = new Form("inputForm", formModel) {
            
            //onSubmit wird nur benötigt wenn man zusätzlich ausser dem submitten (Objekt füllen) noch etwas machen will.
            @Override
            public void onSubmit() {
                //  doSomething
            }

            @Override
            public void onValidateModelObjects() {
                ValidationProcess vp = new ValidationProcess();
                vp.validate("Rules.js", r);

                String[] splittedP = Arrays.copyOf(vp.geterrorMessages(),vp.geterrorMessages().length, String[].class);
               
                for (int i = 0; i < splittedP.length; i++) {
                    
                    //check for elementnames
                    if (splittedP[i].contains("error.details")) {
                        details.error(getString(splittedP[i]));
                    }
                    if (splittedP[i].contains("error.price")) {
                         price.error(getString(splittedP[i]));
                    }
                    if (splittedP[i].contains("error.recipient")) {
                         recipient.error(getString(splittedP[i]));
                    }
                    if (splittedP[i].contains("error.tax")) {
                         rc.error(getString(splittedP[i]));
                    }
                }
            }
        };

        form.add(recipient);
        form.add(details);
        form.add(price);
        form.add(rc);

        add(form);
        form.setOutputMarkupId(true);
    }
}
