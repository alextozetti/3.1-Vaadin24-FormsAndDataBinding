package com.vaadin.training.forms.exercises.ex1;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.training.forms.exercises.menu.MainLayout;
import com.vaadin.training.forms.exercises.test.BetaLayout;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route(value = SimpleMenuView.ROUTE, layout = BetaLayout.class)
public class SimpleMenuView extends VerticalLayout {

    public static final String ROUTE = "simple-menu";
    public static final String TITLE = "Menu Simples (TreeGrid)";

    private TreeGrid<SimpleMenuItem> treeGrid;
    private List<SimpleMenuItem> allMenuItems; // Simula seu "banco de dados" de itens

    public SimpleMenuView() {
        setSizeFull();
        setSpacing(true);
        setPadding(true);

        createSampleData(); // Preenche a lista inicial de itens

        setupTreeGrid(); // Configura o TreeGrid

        Button expandAllBtn = new Button("Abrir Tudo");
        Button collapseAllBtn = new Button("Fechar Tudo");
        HorizontalLayout controls = new HorizontalLayout(expandAllBtn, collapseAllBtn);
        add(controls); // Adiciona os botões acima do treeGrid

        expandAllBtn.addClickListener(e ->
                treeGrid.expandRecursively(allMenuItems, Integer.MAX_VALUE)
        );

        collapseAllBtn.addClickListener(e ->
                treeGrid.collapseRecursively(allMenuItems, Integer.MAX_VALUE)
        );

        add(treeGrid); // Já existente
    }

    // --- Dados de Exemplo ---
    private void createSampleData() {
        allMenuItems = new ArrayList<>(Arrays.asList(
                new SimpleMenuItem(1L, "Relatórios", null), // null indica item raiz
                new SimpleMenuItem(2L,"Configurações", null),
                new SimpleMenuItem(3L,"Usuários", 2L), // Filho do Item Raiz 2 (ID 2)
                new SimpleMenuItem(4L,"Logs", 2L),
                new SimpleMenuItem(5L,"Arquivos", 3L), // Filho do Subitem 2.1 (ID 3)
                new SimpleMenuItem(6L,"Desktop", null)
        ));

    }

    private void setupTreeGrid() {
        treeGrid = new TreeGrid<>();
        treeGrid.setHeightFull();
        treeGrid.setWidthFull();
        treeGrid.addHierarchyColumn(SimpleMenuItem::getName).setHeader("Nome do Menu");
        treeGrid.addColumn(SimpleMenuItem::getId).setHeader("ID");
        treeGrid.addColumn(SimpleMenuItem::getParentId).setHeader("Parent ID");

        treeGrid.addColumn(new ComponentRenderer<Component, SimpleMenuItem>(item -> {
            Button modalBtn = new Button(new Icon(VaadinIcon.PLUS));
            modalBtn.getElement().setProperty("title", "Abrir Modal");
            modalBtn.addClickListener(e -> {
                editar(item);
            });

            Button deleteBtn = new Button(new Icon(VaadinIcon.TRASH));
            deleteBtn.getElement().setProperty("title", "Excluir");
            deleteBtn.getStyle().set("color", "red");
            deleteBtn.addClickListener(e -> {
                excluir(item);
            });

            HorizontalLayout layout = new HorizontalLayout(modalBtn, deleteBtn);
            layout.setSpacing(true);
            return layout;
        })).setHeader("Ações").setAutoWidth(true);

        reloadTreeGridItems();

    }

    private void reloadTreeGridItems() {
        treeGrid.setItems(
                allMenuItems.stream().filter(item ->
                        item.getParentId() == null).toList(),
                parent ->
                        allMenuItems.stream().filter(item ->
                                parent.getId().equals(item.getParentId())).toList()
        );
    }

    private void editar(SimpleMenuItem item) {
        Dialog dialog = new Dialog();
        dialog.setWidth("450px");

        TextField nameField = new TextField("Nome do Submenu");

        Button saveBtn = new Button("Salvar", e -> {
            Long newId = allMenuItems.stream().mapToLong(SimpleMenuItem::getId).max().orElse(0L) + 1;
            SimpleMenuItem newItem = new SimpleMenuItem(newId, nameField.getValue(), item.getId());
            allMenuItems.add(newItem);
            reloadTreeGridItems();
            Notification.show("Novo subitem criado: " + newItem.getName());
            dialog.close();
        });

        Button closeBtn = new Button("Fechar", e -> dialog.close());

        HorizontalLayout actions = new HorizontalLayout(saveBtn, closeBtn);
        actions.setSpacing(true);

        VerticalLayout content = new VerticalLayout(nameField, actions);
        content.setSpacing(true);
        content.setPadding(false);

        dialog.add(content);
        dialog.open();
    }

    private void excluir(SimpleMenuItem item) {
        allMenuItems.remove(item);
        reloadTreeGridItems();
        Notification.show("Item excluído: " + item.getName());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SimpleMenuItem {
        private Long id;
        private String name;
        private Long parentId; // null para itens de nível superior
    }
}
