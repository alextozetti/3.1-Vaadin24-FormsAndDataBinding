package com.vaadin.training.forms.exercises.menu;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PrimaryMenuBackup extends VerticalLayout {

    private final List<Consumer<String>> categoryClickListeners = new ArrayList<>();

    public PrimaryMenuBackup() {
        setWidth("100px");
        setHeightFull();
        setPadding(false);
        setSpacing(false);

        getStyle().set("border-right", "1px solid var(--lumo-contrast-10pct)");
        getStyle().set("background-color", "var(--lumo-base-color)");

        // Adiciona os itens de menu
        addDefaultMenuItems();
    }

    // Método privado auxiliar para adicionar um único item de menu (usado internamente)
    private void addSingleMenuItem(PrimaryMenuItemData itemData) {
        VerticalLayout itemLayout = new VerticalLayout();
        itemLayout.setWidthFull();
        itemLayout.setPadding(false);
        itemLayout.setSpacing(false);
        itemLayout.setAlignItems(Alignment.CENTER);

        itemLayout.getStyle().set("margin-bottom", "10px");

        Image icon = new Image(itemData.getImagePath(), itemData.getText());
        icon.setWidth("40px");
        icon.setHeight("40px");

        Span textSpan = new Span(itemData.getText());
        textSpan.getStyle().set("font-size", "0.8em");
        textSpan.getStyle().set("text-align", "center");
        textSpan.getStyle().set("margin-top", "4px");

        VerticalLayout content = new VerticalLayout(icon, textSpan);
        content.setAlignItems(Alignment.CENTER);
        content.setJustifyContentMode(JustifyContentMode.CENTER);
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

        button.addClickListener(e -> notifyCategoryClick(itemData.getCategory()));

        itemLayout.add(button);
        add(itemLayout);
    }

    public void addCategoryClickListener(Consumer<String> listener) {
        categoryClickListeners.add(listener);
    }

    private void notifyCategoryClick(String category) {
        categoryClickListeners.forEach(listener -> listener.accept(category));
    }

    public void addDefaultMenuItems() {
        // Simula os dados vindos de um "banco de dados" ou serviço
        List<PrimaryMenuItemData> defaultItems = List.of(
                new PrimaryMenuItemData("icons/report.png", "Relatórios", "reports"),
                new PrimaryMenuItemData("icons/help-desk.png", "Config", "settings"),
                new PrimaryMenuItemData("icons/file.png", "Files", "files"),
                new PrimaryMenuItemData("icons/gab.png", "Usuários", "users")
        );

        // Itera sobre a lista e adiciona cada item
        defaultItems.forEach(this::addSingleMenuItem);
    }

    // Classe auxiliar para o item de dados do menu primário
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PrimaryMenuItemData {
        private String imagePath;
        private String text;
        private String category;
    }
}
