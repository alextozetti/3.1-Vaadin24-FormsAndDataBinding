package com.vaadin.training.forms.exercises.test;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "dashboard", layout = BetaLayout.class)
@PageTitle("Dashboard | Meu App")
public class ProductViewer extends VerticalLayout {

    public ProductViewer() {
        setSpacing(true);
        setPadding(true);
        setAlignItems(Alignment.CENTER);
        setSizeFull();

        add(new H2("Página do Dashboard"));

        // Conteúdo expansível para empurrar a linha para o rodapé
        VerticalLayout content = new VerticalLayout();
        content.setWidthFull();
        content.setPadding(false);
        content.setSpacing(false);
        setFlexGrow(1, content);

        add(content);

        // Linha do rodapé
        add(menuRodape());
    }

    private Component menuRodape() {
        HorizontalLayout line = new HorizontalLayout();
        line.setPadding(true);
        line.setSpacing(true);
        line.setWidthFull();
        line.getStyle().set("background-color", "yellow");

        var left = new HorizontalLayout();
        left.setWidthFull();
        left.getStyle().set("background-color", "orange");
        left.setAlignItems(Alignment.CENTER);
        left.setJustifyContentMode(JustifyContentMode.START);
        var b1 = new Button(VaadinIcon.FILE_TEXT.create());
        left.add(b1);

        var center = new HorizontalLayout();
        center.setWidthFull();
        center.getStyle().set("background-color", "cyan");
        center.setAlignItems(Alignment.CENTER);
        center.setJustifyContentMode(JustifyContentMode.CENTER);
        var c = new Button(VaadinIcon.COG.create());
        center.add(c);

        var right = new HorizontalLayout();
        right.setWidthFull();
        right.getStyle().set("background-color", "white");
        right.setAlignItems(Alignment.CENTER);
        right.setJustifyContentMode(JustifyContentMode.END);
        var b2 = new Button(VaadinIcon.USER_CARD.create());
        right.add(b2);

        line.add(left, center, right);
        return line;
    }
}
