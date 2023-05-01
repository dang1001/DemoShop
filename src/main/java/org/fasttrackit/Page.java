package org.fasttrackit;

import static com.codeborne.selenide.Selenide.*;

public class Page {
    public Page() {
        System.out.println("Opened a new page.");
    }

    public void openDemoShopApp() {
        String demoShopUrl = "https://fasttrackit-test.netlify.app/#/";
        System.out.println("Opening: " + demoShopUrl);
        open(demoShopUrl);
    }

    public void refresh() {
        System.out.println("Refreshing the page.");
    }
}
