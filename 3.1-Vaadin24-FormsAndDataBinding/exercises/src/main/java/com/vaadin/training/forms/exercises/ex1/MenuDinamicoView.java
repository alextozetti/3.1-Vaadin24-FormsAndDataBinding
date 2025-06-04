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
import com.vaadin.training.forms.exercises.menu.MenuEntity;
import com.vaadin.training.forms.exercises.menu.MenuEntityService;
import com.vaadin.training.forms.exercises.test.BetaLayout;

import java.util.List;

@Route(value = MenuDinamicoView.ROUTE, layout = BetaLayout.class)
public class MenuDinamicoView extends VerticalLayout {

    public static final String ROUTE = "dinamic-menu";
    public static final String TITLE = "Menu Simples Dinamico (TreeGrid)";

    private TreeGrid<MenuEntity> treeGrid;
    private final MenuEntityService service;

    public MenuDinamicoView(MenuEntityService service) {
        this.service = service;
        setSizeFull();
        setSpacing(true);
        setPadding(true);

        setupTreeGrid(); // Configura o TreeGrid

        add(treeGrid); // Já existente
    }

    private void setupTreeGrid() {
        treeGrid = new TreeGrid<>();
        treeGrid.setHeightFull();
        treeGrid.setWidthFull();
        treeGrid.addHierarchyColumn(MenuEntity::getNome).setHeader("Nome do Menu");
        treeGrid.addColumn(MenuEntity::getCategoria).setHeader("Categoria");
        treeGrid.addColumn(MenuEntity::getRota).setHeader("Rota do Menu");
//        treeGrid.addColumn(MenuEntity::getId).setHeader("ID");
//        treeGrid.addColumn(MenuEntity::getParentId).setHeader("Parent ID");

        treeGrid.addColumn(new ComponentRenderer<Component, MenuEntity>(item -> {
            Button modalBtn = new Button(new Icon(VaadinIcon.PLUS));
            modalBtn.getElement().setProperty("title", "Abrir Modal");
            modalBtn.addClickListener(e -> {
                editar(item);
            });

            Button deleteBtn = new Button(new Icon(VaadinIcon.TRASH));
            deleteBtn.getElement().setProperty("title", "Excluir");
            deleteBtn.getStyle().set("color", "red");
            deleteBtn.addClickListener(e -> {
                excluir(item.getId());
            });

            HorizontalLayout layout = new HorizontalLayout(modalBtn, deleteBtn);
            layout.setSpacing(true);
            return layout;
        })).setHeader("Ações").setAutoWidth(true);

        reloadTreeGridItems();
    }

    private void reloadTreeGridItems() {
        List<MenuEntity> allMenuItems = this.service.getMenus();
        treeGrid.setItems(
                allMenuItems.stream()
                        .filter(item -> item.getParentId() == null).toList(),
                parent -> allMenuItems.stream()
                        .filter(item -> parent.getId().equals(item.getParentId())).toList()
        );

        treeGrid.expandRecursively(allMenuItems, Integer.MAX_VALUE);
    }

    public void editar(MenuEntity item) {
        createDialog(item).open();
    }

    public void excluir(Long id) {
        service.delete(id);
        reloadTreeGridItems();
    }

    public Dialog createDialog(MenuEntity item) {
        var dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "SetAtributes");

        dialog.setHeaderTitle("Header");

        var nome = new TextField("Nome do SubItem");
        var rota = new TextField("Rota do SubItem");
        var pacote = new TextField("Pacote do SubItem");
        var linkImagem = new TextField("Link Imagem: /icons/xxx");

        // Adicione conteúdo ao diálogo (opcional)
        VerticalLayout dialogLayout = new VerticalLayout();
        dialogLayout.add(nome, rota, pacote, linkImagem);
        dialog.add(dialogLayout);

        // Adicione botões ao diálogo (opcional)
        var closeButton = new Button("Close", event -> {
            dialog.close();
        });
        var saveButton = new Button("Save", event -> {
            var newItem = new MenuEntity();
            newItem.setParentId(item.getId());
            newItem.setNome(nome.getValue());
            newItem.setCategoria(item.getCategoria());
            newItem.setRota(rota.getValue());
            newItem.setRotaClass(pacote.getValue());
            newItem.setLinkImage(linkImagem.getValue());
            service.save(newItem);
            Notification.show("Salvo com sucesso", 3000, Notification.Position.TOP_CENTER);
            reloadTreeGridItems();
            dialog.close();
        });

        dialog.getFooter().add(saveButton, closeButton);

        return dialog; // Retorne a instância do diálogo
    }

}
