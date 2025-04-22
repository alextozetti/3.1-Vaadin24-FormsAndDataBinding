package com.vaadin.training.layouting.solutions;


import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.training.layouting.solutions.ex1.ApplicationLayout;
import com.vaadin.training.layouting.solutions.ex2.UseFormLayout;
import com.vaadin.training.layouting.solutions.ex3.UseVaadinBoard;

public class MainLayout extends AppLayout {

    public MainLayout() {
        addToNavbar(new DrawerToggle());
        addToNavbar(new H2("Layout Exercise") );

        final VerticalLayout menuBar = new VerticalLayout();
        menuBar.add(new RouterLink(ApplicationLayout.TITLE, ApplicationLayout.class));
        menuBar.add(new RouterLink(UseFormLayout.TITLE, UseFormLayout.class));
        menuBar.add(new RouterLink(UseVaadinBoard.TITLE, UseVaadinBoard.class));
        addToDrawer(menuBar);
    }
}
