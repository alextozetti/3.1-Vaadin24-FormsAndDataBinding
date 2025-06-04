package com.vaadin.training.forms.exercises.menu;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MainLayout extends AppLayout {

    private final SecondaryMenu secondaryMenu; // Referência ao SecondaryMenu
    private final PrimaryMenu primaryMenu; // Referência ao PrimaryMenu

    private final MenuEntityService service;

    public MainLayout(MenuEntityService service) {

        this.service = service;

        primaryMenu = new PrimaryMenu(this.service);
        secondaryMenu = new SecondaryMenu(this.service);

        var h = new HorizontalLayout(primaryMenu, secondaryMenu);
        h.setWidth("200px");
        addToNavbar(h);

        primaryMenu.addCategoryClickListener(category -> {
            secondaryMenu.updateMenu(category);
            if (!isDrawerOpened()) {
                setDrawerOpened(true);
            }
        });
    }
}
