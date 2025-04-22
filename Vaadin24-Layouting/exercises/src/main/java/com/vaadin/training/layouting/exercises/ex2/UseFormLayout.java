package com.vaadin.training.layouting.exercises.ex2;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.training.layouting.exercises.MainLayout;

@Route(value = UseFormLayout.ROUTE, layout = MainLayout.class)
public class UseFormLayout extends VerticalLayout {
    public static final String ROUTE = "ex2";
    public static final String TITLE = "Exercise 2";

    public UseFormLayout(){
        setSizeFull();
        Div div = new Div();
        div.setText("Replace me with a form layout");
        add(div);
    }
}
