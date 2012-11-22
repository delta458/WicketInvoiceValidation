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
        final TextField<String> adresseName = new TextField<String>("Adresse.Name");
        adresseName.add(new ErrorClassAppender());
        adresseName.setConvertEmptyInputStringToNull(false);
        final TextField<String> adresseOrtName = new TextField<String>("Adresse.Ort.Name");
        adresseOrtName.add(new ErrorClassAppender());
        adresseOrtName.setConvertEmptyInputStringToNull(false);
        final TextField<String> adresseOrtPlz = new TextField<String>("Adresse.Ort.Plz");
        adresseOrtPlz.add(new ErrorClassAppender());
        adresseOrtPlz.setConvertEmptyInputStringToNull(false);
        final TextField<String> zweck = new TextField<String>("Zweck");
        zweck.add(new ErrorClassAppender());
        zweck.setConvertEmptyInputStringToNull(false);
        final TextField<Double> betrag = new TextField<Double>("Betrag");
        betrag.add(new ErrorClassAppender());
        betrag.setConvertEmptyInputStringToNull(false);
        final TextField<Double> ust = new TextField<Double>("Ust");
        ust.add(new ErrorClassAppender());
        ust.setConvertEmptyInputStringToNull(false);

        Form<Invoice> form = new Form("inputForm", formModel) {
            //onSubmit wird nur benötigt wenn man zusätzlich ausser dem submitten (Objekt füllen) noch etwas machen will.
            @Override
            public void onSubmit() {
                //  doSomething
            }

            /**
             * This method will be called when validating the formModel. A
             * custom java class called ValidationProcess will be initialised
             * which takes a javascript with Rules and validates it against the
             * Invoice r. If there is an error, the proper String key of the
             * error will be added to the feedbackMessage Panel. The keys are
             * defined in a properties file.
             */
            @Override
            public void onValidateModelObjects() {
                ValidationProcess vp = new ValidationProcess();
                vp.validate("Rules_1.js", r);
                String[] splitted = {""};
                if (vp.geterrorMessages() != null) {
                    splitted = Arrays.copyOf(vp.geterrorMessages(), vp.geterrorMessages().length, String[].class);
                }
                for (int i = 0; i < splitted.length; i++) {
                    System.out.println("ERROR MESSAGE CONTAINS: " + splitted[i]);
                    if (splitted[i].contains("error.adresse.ort.name")) {
                        adresseOrtName.error(getString(splitted[i]));
                    } else if (splitted[i].contains("error.adresse.ort.plz")) {
                        adresseOrtPlz.error(getString(splitted[i]));
                    } else if (splitted[i].contains("error.adresse")) {
                        adresseName.error(getString(splitted[i]));
                    } else if (splitted[i].contains("error.zweck")) {
                        zweck.error(getString(splitted[i]));
                    } else if (splitted[i].contains("error.betrag")) {
                        betrag.error(getString(splitted[i]));
                    } else if (splitted[i].contains("error.ust")) {
                        ust.error(getString(splitted[i]));
                    } else {
                        error(splitted[i]);
                    }
                }
            }
        };

        form.add(adresseName);
        form.add(adresseOrtName);
        form.add(adresseOrtPlz);
        form.add(zweck);
        form.add(betrag);
        form.add(ust);

        add(form);
        form.setOutputMarkupId(true);
    }
}
