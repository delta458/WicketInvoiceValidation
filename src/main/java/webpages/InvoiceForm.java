/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package webpages;

import java.util.Arrays;
import model.Invoice;
import model.ValidationProcess;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import template.BasePage;
import template.ErrorClassAppender;

/**
 * Dies ist die korrespondierende Klasse zu InvoiceForm.java. Fügt die nötigen
 * Komponenten mittels add(...) dem Formular hizu.
 *
 * @author Dave
 */
public final class InvoiceForm extends BasePage {

    Invoice r = new Invoice();
    private static final JavaScriptResourceReference RULES_JS = new JavaScriptResourceReference(InvoiceForm.class, "Rules.js");

    @Override
    public void renderHead(IHeaderResponse response) {
        response.render(JavaScriptReferenceHeaderItem.forReference(RULES_JS));
    }

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
        final TextField<String> recipient = new TextField<String>("Adresse.Name");
        recipient.add(new ErrorClassAppender());
        final TextField<String> statename = new TextField<String>("Adresse.Ort.Name");
        statename.add(new ErrorClassAppender());
        final TextField<String> plz = new TextField<String>("Adresse.Ort.Plz");
        plz.add(new ErrorClassAppender());

        final TextField<String> details = new TextField<String>("Zweck");
        recipient.add(new ErrorClassAppender());
        final TextField<Double> price = new TextField<Double>("Betrag");
        price.add(new ErrorClassAppender());
        final TextField<Double> tax = new TextField<Double>("Ust");
        tax.add(new ErrorClassAppender());

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

                String[] splittedP = Arrays.copyOf(vp.geterrorMessages(), vp.geterrorMessages().length, String[].class);

                for (int i = 0; i < splittedP.length; i++) {

                    //check for elementnames
                    //TODO: Fragen: Soll auch das automatisiert werden?
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
                        tax.error(getString(splittedP[i]));
                    }
                }
            }
        };

        form.add(recipient);
        form.add(statename);
        form.add(plz);
        form.add(details);
        form.add(price);
        form.add(tax);

        add(form);
        form.setOutputMarkupId(true);
    }
}
