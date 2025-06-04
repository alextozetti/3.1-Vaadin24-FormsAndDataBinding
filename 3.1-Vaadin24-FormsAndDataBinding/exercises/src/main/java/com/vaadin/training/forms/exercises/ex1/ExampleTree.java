package com.vaadin.training.forms.exercises.ex1;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.training.forms.exercises.menu.MenuEntity;
import com.vaadin.training.forms.exercises.menu.MenuEntityService;

import java.io.Serial;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Route(value = ExampleTree.ROUTE)
public class ExampleTree extends VerticalLayout {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final String ROUTE = "tree";
    public static final String TITLE = "Tree Example";

    private final VerticalLayout submenuArea;
    private final Div contentArea;
    private final MenuEntityService service;

    public ExampleTree(MenuEntityService service) {
        this.service = service;
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // Split principal: Menu principal | Área de submenu + conteúdo
        SplitLayout mainSplit = new SplitLayout();
        mainSplit.setSizeFull();
        mainSplit.setSplitterPosition(5);

        // Menu principal (pai)
        VerticalLayout mainMenu = new VerticalLayout();
        mainMenu.setWidth("280px");
        mainMenu.getStyle().set("minWidth", "80px");
        mainMenu.getStyle().set("maxWidth", "400px");
        mainMenu.getStyle().set("overflow", "auto");

        mainMenu.setPadding(true);
        mainMenu.getStyle().set("background", "#f5f5f5");

        // Submenu e conteúdo
        submenuArea = new VerticalLayout();
        submenuArea.setWidth("500px");
        submenuArea.setPadding(true);
        submenuArea.getStyle().set("background", "#eeeeee");

        contentArea = new Div();
        contentArea.setSizeFull();
        contentArea.getStyle().set("padding", "1rem");

        HorizontalLayout rightSide = new HorizontalLayout(submenuArea, contentArea);
        rightSide.setSizeFull();
        rightSide.setSpacing(false);
        rightSide.setPadding(false);
        rightSide.expand(contentArea);

        mainSplit.addToPrimary(mainMenu);
        mainSplit.addToSecondary(rightSide);
        add(mainSplit);

        // Popula menus
        List<MenuEntity> menuList = this.service.getMenus();
        List<MenuEntity> menuPais = menuList.stream().filter(m -> m.getParentId() == null).toList();

        for (MenuEntity pai : menuPais) {
            Image icon = new Image(pai.getLinkImage(), pai.getNome());
            icon.setWidth("40px");
            icon.setHeight("40px");

            Span textSpan = new Span(pai.getNome());
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

            button.addClickListener(e -> loadMenuTree(menuList, pai.getId()));
            mainMenu.add(button);
        }

        if (!menuPais.isEmpty()) {
            loadMenuTree(menuList, menuPais.get(0).getId());
        }
    }

    private void loadMenuTree(List<MenuEntity> menuList, Long parentRootId) {
        submenuArea.removeAll();
        contentArea.removeAll();

        TreeGrid<MenuEntity> tree = new TreeGrid<>();
        tree.setWidthFull();
        tree.addHierarchyColumn(MenuEntity::getNome).setHeader("Menu");

        TreeData<MenuEntity> treeData = new TreeData<>();
        Set<Long> added = new HashSet<>();

        // Apenas um ramo da árvore
        MenuEntity root = findById(menuList, parentRootId);
        if (root != null) {
            addChildren(treeData, root, menuList, added);
        }

        tree.setDataProvider(new TreeDataProvider<>(treeData));

        tree.addItemClickListener(event -> {
            MenuEntity clicked = event.getItem();
            if (clicked.getRotaClass() != null && !clicked.getRotaClass().isEmpty()) {
                showContent(clicked);
            }
        });

        submenuArea.add(tree);
    }

    private void addChildren(TreeData<MenuEntity> data, MenuEntity parent, List<MenuEntity> allMenus, Set<Long> added) {
        if (!added.contains(parent.getId())) {
            data.addItem(parent.getParentId() == null ? null : findById(allMenus, parent.getParentId()), parent);
            added.add(parent.getId());
        }

        List<MenuEntity> children = allMenus.stream()
                .filter(m -> parent.getId().equals(m.getParentId()))
                .toList();

        for (MenuEntity child : children) {
            addChildren(data, child, allMenus, added); // recursivo
        }
    }

    private MenuEntity findById(List<MenuEntity> list, Long id) {
        return list.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
    }

    private void showContent(MenuEntity menu) {
        contentArea.removeAll();
        try {
            Class<?> clazz = Class.forName(menu.getRotaClass());
            Component view = (Component) clazz.getDeclaredConstructor().newInstance();
            contentArea.add(view);
        } catch (Exception e) {
            contentArea.add(new Div(new Span("Erro ao carregar: " + menu.getNome())));
            e.printStackTrace();
        }
    }
}
