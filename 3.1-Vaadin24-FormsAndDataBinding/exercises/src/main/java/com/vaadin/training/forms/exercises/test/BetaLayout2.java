//package com.vaadin.training.forms.exercises.test;
//
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.HasElement;
//import com.vaadin.flow.component.UI;
//import com.vaadin.flow.component.applayout.AppLayout;
//import com.vaadin.flow.component.button.Button;
//import com.vaadin.flow.component.html.Div;
//import com.vaadin.flow.component.html.Hr;
//import com.vaadin.flow.component.html.Image;
//import com.vaadin.flow.component.html.Span;
//import com.vaadin.flow.component.icon.Icon;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.component.orderedlayout.FlexComponent;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.orderedlayout.VerticalLayout;
//import com.vaadin.flow.component.sidenav.SideNav;
//import com.vaadin.flow.component.sidenav.SideNavItem;
//import com.vaadin.flow.component.splitlayout.SplitLayout;
//import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.router.RouterLayout;
//import com.vaadin.flow.router.RouterLink;
//import com.vaadin.training.forms.exercises.menu.MenuEntity;
//import com.vaadin.training.forms.exercises.menu.MenuEntityService;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Route("beta")
//public class BetaLayout2 extends AppLayout implements RouterLayout {
//
//    private final Div contentArea;
//    private final SideNav secondarySideNav;
//    private final MenuEntityService service;
//    private final Span submenuTitle;
//    private boolean darkMode = false;
//    private Map<Long, List<MenuEntity>> subMenusByParentId;
//    private List<MenuEntity> topLevelMenus;
//
//    private Button selectedParentButton;
//
//    public BetaLayout2(MenuEntityService service) {
//        this.service = service;
//
//        // --- 2. Preparar dados do menu ---
//        prepareMenuData();
//
//        // --- 3. Menu Principal (VerticalLayout com botões/links) ---
//        VerticalLayout primaryMenuContainer = createPrimaryMenuContainer();
//        primaryMenuContainer.setWidth("120px");
//        primaryMenuContainer.setHeightFull();
//        primaryMenuContainer.setSpacing(false);
//        primaryMenuContainer.setPadding(true);
//        primaryMenuContainer.getStyle().set("border-right", "1px solid var(--lumo-contrast-10pct)");
//        primaryMenuContainer.getStyle().set("min-width", "200px");
//        primaryMenuContainer.getStyle().set("max-width", "300px");
//        primaryMenuContainer.getStyle().set("flex-shrink", "0");
//
//        // --- 4. Submenu Dinâmico (SideNav para submenus hierárquicos) ---
//        secondarySideNav = new SideNav();
//        secondarySideNav.setWidth("250px");
//        secondarySideNav.setHeightFull();
//        secondarySideNav.setVisible(false);
//        secondarySideNav.setCollapsible(false);
//
//        submenuTitle = new Span();
//        submenuTitle.getStyle().set("font-weight", "bold");
//        submenuTitle.getStyle().set("padding", "var(--lumo-space-m)");
//        submenuTitle.getStyle().set("display", "block");
//
//        VerticalLayout submenuLayout = new VerticalLayout(submenuTitle, secondarySideNav);
//        submenuLayout.getStyle().set("border-right", "1px solid var(--lumo-contrast-10pct)");
//        submenuLayout.setSpacing(false);
//        submenuLayout.setPadding(false);
//        submenuLayout.setWidth(null);
//        submenuLayout.setHeightFull();
//
//        // --- 5. Área de Conteúdo ---
//        contentArea = new Div();
//        contentArea.getStyle().set("overflow", "visible");
//        contentArea.setSizeFull();
//
//        // --- 6. Secondary Content Wrapper (HorizontalLayout para Submenu e Conteúdo) ---
//        HorizontalLayout secondaryContentWrapper = new HorizontalLayout();
//        secondaryContentWrapper.getStyle().set("overflow", "visible");
//        secondaryContentWrapper.setSizeFull();
//        secondaryContentWrapper.setSpacing(false);
//        secondaryContentWrapper.setPadding(false);
//        secondaryContentWrapper.removeAll();
//        secondaryContentWrapper.add(submenuLayout, contentArea);
//        secondaryContentWrapper.setFlexGrow(0, submenuLayout);
//        secondaryContentWrapper.setFlexGrow(1, contentArea);
//
//        // --- TOPO: Header "Sistema de Compras" ---
//        Div comprasHeader = new Div(new Span("Sistema de Compras"));
//        comprasHeader.setWidthFull();
//        comprasHeader.setHeight("80px");
//
//        // --- FUNDO: Bottom "Develop" ---
//        Div sicoiFooter = new Div(new Span("Developed by Vaadin"));
//        sicoiFooter.setWidthFull();
//        sicoiFooter.setHeight("80px");
//
//        // --- Layout vertical para header + secondaryContentWrapper ---
//        VerticalLayout contentWithHeader = new VerticalLayout(comprasHeader, secondaryContentWrapper, sicoiFooter);
//        contentWithHeader.setPadding(false);
//        contentWithHeader.getStyle().set("overflow", "visible");
//        contentWithHeader.setSpacing(false);
//        contentWithHeader.setSizeFull();
//        contentWithHeader.setFlexGrow(1, secondaryContentWrapper);
//
//        // --- 7. SplitLayout Principal (Menu Principal | [Header + Submenu + Conteúdo]) ---
//        SplitLayout mainSplitLayout = new SplitLayout();
//        mainSplitLayout.setSizeFull();
//        mainSplitLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
//        mainSplitLayout.addToPrimary(primaryMenuContainer);
//        mainSplitLayout.addToSecondary(contentWithHeader);
//        setContent(mainSplitLayout);
//
//        // --- 8. Lógica para abrir o menu e submenu por padrão ---
//        Optional<MenuEntity> defaultParent = topLevelMenus.stream()
//                .filter(item -> subMenusByParentId.containsKey(item.getId()))
//                .findFirst();
//        if (defaultParent.isPresent()) {
//            updateSecondaryMenu(defaultParent.get().getId());
//        } else {
//            secondarySideNav.setVisible(false);
//        }
//    }
//
//    private void prepareMenuData() {
//        List<MenuEntity> allMenus = this.service.getMenus();
//        topLevelMenus = allMenus.stream()
//                .filter(item -> item.getParentId() == null)
//                .collect(Collectors.toList());
//
//        subMenusByParentId = allMenus.stream()
//                .filter(item -> item.getParentId() != null)
//                .collect(Collectors.groupingBy(MenuEntity::getParentId));
//    }
//
//    private VerticalLayout createPrimaryMenuContainer() {
//        VerticalLayout navContainer = new VerticalLayout();
//        navContainer.setSpacing(false);
//        navContainer.setPadding(false);
//
//        VerticalLayout logoLayout = new VerticalLayout();
//        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
//        logoLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
//        logoLayout.setPadding(false);
//        logoLayout.setSpacing(false);
//
//        Image icon = new Image("icons/logo.png", "Gabinete");
//        icon.setWidth("40px");
//        icon.setHeight("40px");
//
//        Span appName = new Span("Sicoi 2.0");
//        appName.getStyle().set("font-weight", "bold");
//        appName.getStyle().set("font-size", "1.1em");
//        appName.getStyle().set("margin-top", "4px");
//
//        var line = new Hr();
//        line.getStyle().set("width", "100%").set("margin", "var(--lumo-space-m) 0");
//
//        logoLayout.add(icon, appName, line);
//        navContainer.add(logoLayout);
//
//        for (MenuEntity item : topLevelMenus) {
//            // Imagem para cada item (ajuste o caminho conforme necessário)
//            Image itemIcon = new Image("icons/file.png", "Item");
//            itemIcon.setWidth("20px");
//            itemIcon.setHeight("20px");
//
//            Span itemName = new Span(item.getNome());
//            itemName.getStyle().set("margin-left", "8px");
//
//            HorizontalLayout itemLayout = new HorizontalLayout(VaadinIcon.COG.create(), itemName);
//            itemLayout.setAlignItems(FlexComponent.Alignment.CENTER);
//            itemLayout.setPadding(false);
//            itemLayout.setSpacing(false);
//
//            if (subMenusByParentId.containsKey(item.getId())) {
//                Button parentButton = new Button(itemLayout);
//                parentButton.getStyle().set("background-color", "white");
//                parentButton.setWidthFull();
//                parentButton.addClickListener(e -> {
//                    updateSecondaryMenu(item.getId());
//                    if (selectedParentButton != null) {
//                        selectedParentButton.getStyle().set("background-color", "white");
//                    }
//                    parentButton.getStyle().set("background-color", "var(--lumo-primary-color-10pct)");
//                    selectedParentButton = parentButton;
//                });
//                navContainer.add(parentButton);
//            } else {
//                Class<? extends Component> targetClass = getClassByName(item.getRotaClass());
//                if (targetClass != null) {
//                    RouterLink link = new RouterLink();
//                    link.add(itemLayout);
//                    link.setRoute(targetClass);
//                    navContainer.add(link);
//                } else {
//                    navContainer.add(itemLayout);
//                }
//            }
//        }
//
//        Div spacer = new Div();
//        navContainer.add(spacer);
//        navContainer.setFlexGrow(1, spacer);
//
//        var divider = new Hr();
//        divider.getStyle().set("width", "100%").set("margin", "var(--lumo-space-m) 0");
//
//        Button themeToggle = new Button(new Icon(VaadinIcon.MOON_O));
//        themeToggle.setWidthFull();
//        themeToggle.getElement().setProperty("title", "Alternar tema claro/escuro");
//        themeToggle.addClickListener(e -> {
//            darkMode = !darkMode;
//            if (darkMode) {
//                UI.getCurrent().getElement().getThemeList().add("dark");
//                themeToggle.setIcon(new Icon(VaadinIcon.SUN_O));
//            } else {
//                UI.getCurrent().getElement().getThemeList().remove("dark");
//                themeToggle.setIcon(new Icon(VaadinIcon.MOON_O));
//            }
//        });
//
//        navContainer.add(divider, themeToggle);
//
//        return navContainer;
//    }
//
//    private SideNavItem createSideNavItemHierarchy(MenuEntity menuEntity) {
//        Class<? extends Component> targetClass = getClassByName(menuEntity.getRotaClass());
//        SideNavItem navItem;
//
//        if (targetClass != null) {
//            navItem = new SideNavItem(menuEntity.getNome(), targetClass);
//        } else {
//            navItem = new SideNavItem(menuEntity.getNome());
//        }
//
//        if (subMenusByParentId.containsKey(menuEntity.getId())) {
//            List<MenuEntity> children = subMenusByParentId.get(menuEntity.getId());
//            if (children != null) {
//                for (MenuEntity child : children) {
//                    navItem.addItem(createSideNavItemHierarchy(child));
//                }
//            }
//        }
//        return navItem;
//    }
//
//    private void updateSecondaryMenu(Long parentId) {
//        MenuEntity parent = topLevelMenus.stream()
//                .filter(item -> item.getId().equals(parentId))
//                .findFirst().orElse(null);
//        submenuTitle.setText(parent != null ? parent.getNome() : "");
//
//        secondarySideNav.removeAll();
//        List<MenuEntity> children = subMenusByParentId.get(parentId);
//
//        if (children != null && !children.isEmpty()) {
//            for (MenuEntity child : children) {
//                secondarySideNav.addItem(createSideNavItemHierarchy(child));
//            }
//            secondarySideNav.setVisible(true);
//            submenuTitle.setVisible(true);
//        } else {
//            secondarySideNav.setVisible(false);
//            submenuTitle.setVisible(false);
//        }
//    }
//
//    @Override
//    public void showRouterLayoutContent(HasElement content) {
//        contentArea.removeAll();
//        if (content instanceof Component) {
//            contentArea.add((Component) content);
//        } else {
//            contentArea.getElement().appendChild(content.getElement());
//        }
//    }
//
//    private Class<? extends Component> getClassByName(String className) {
//        if (className == null || className.trim().isEmpty()) {
//            return null;
//        }
//        try {
//            return Class.forName(className).asSubclass(Component.class);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}
