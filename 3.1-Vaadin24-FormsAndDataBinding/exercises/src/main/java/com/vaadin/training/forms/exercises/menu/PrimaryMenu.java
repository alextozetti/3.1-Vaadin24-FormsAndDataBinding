package com.vaadin.training.forms.exercises.menu;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PrimaryMenu extends VerticalLayout {

    private final List<Consumer<String>> categoryClickListeners = new ArrayList<>();

    private final MenuEntityService service;

    public PrimaryMenu(MenuEntityService service) {
        this.service = service;
        setWidth("100px");
        setHeightFull();
        setPadding(false);
        setSpacing(false);

        getStyle().set("border-right", "1px solid var(--lumo-contrast-10pct)");
        getStyle().set("background-color", "var(--lumo-base-color)");

        addSingleMenuItem();
    }

    // Método privado auxiliar para adicionar um único item de menu (usado internamente)
    private void addSingleMenuItem() {
        VerticalLayout itemLayout = new VerticalLayout();
        itemLayout.setWidthFull();
        itemLayout.setPadding(false);
        itemLayout.setSpacing(false);
        itemLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        itemLayout.getStyle().set("margin-bottom", "10px");

        List<MenuEntity> menu = this.service.getMenus()
                .stream()
                .filter(res -> res.getParentId() == null)
                .toList();

        menu.forEach(res -> {
            Image icon = new Image(res.getLinkImage(), res.getNome());
            icon.setWidth("40px");
            icon.setHeight("40px");

            Span textSpan = new Span(res.getNome());
            textSpan.getStyle().set("font-size", "0.8em");
            textSpan.getStyle().set("text-align", "center");
            textSpan.getStyle().set("margin-top", "4px");

            VerticalLayout content = new VerticalLayout(icon, textSpan);
            content.setAlignItems(FlexComponent.Alignment.CENTER);
            content.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            content.setSpacing(false);
            content.setPadding(false);

            Button button = new Button(content);
            button.addThemeNames("tertiary", "small");
            button.setWidthFull();
            button.setHeight("80px");
            button.getStyle().set("display", "flex");
            button.getStyle().set("flex-direction", "column");
            button.getStyle().set("align-items", "center");
            button.getStyle().set("justify-content", "center");
            button.getStyle().set("background-color", "transparent");
            button.getStyle().set("border", "none");
            button.getStyle().set("cursor", "pointer");
            button.getStyle().set("padding", "5px 1");

            button.addClickListener(e -> notifyCategoryClick(res.getCategoria()));

            itemLayout.add(button);
            add(itemLayout);
        });
    }

    public void addCategoryClickListener(Consumer<String> listener) {
        categoryClickListeners.add(listener);
    }

    private void notifyCategoryClick(String category) {
        categoryClickListeners.forEach(listener -> listener.accept(category));
    }

}
