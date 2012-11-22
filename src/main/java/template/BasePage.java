package template;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import webpages.Intro;
import webpages.InvoiceForm;

/**
 *
 * @author Dave
 * @version
 */
public abstract class BasePage extends WebPage {

    private String pageTitle = "Invoice2XML";

    public BasePage() {
        super();
        add(new Label("title", new PropertyModel<String>(this, "pageTitle")));
        add(new HeaderPanel("headerpanel", "Creating, validating invoices and converting them to XML!"));
        add(new BookmarkablePageLink<Void>("intro", Intro.class));
        add(new BookmarkablePageLink<Void>("rechnung", InvoiceForm.class));
        add(new FooterPanel("footerpanel", "Praktikum in Wirtschaftsinformatik, Ifraimov David - e0726371"));
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }
}
