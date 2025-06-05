package com.vaadin.training.forms.exercises.test;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.training.forms.exercises.menu.MenuEntity;
import com.vaadin.training.forms.exercises.menu.MenuEntityService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class BetaLayout extends AppLayout implements RouterLayout {

    private final Div contentArea;
    private final SideNav secondarySideNav;
    private final MenuEntityService service;
    private final Span submenuTitle;
    private Map<Long, List<MenuEntity>> subMenusByParentId;
    private List<MenuEntity> topLevelMenus;
    private SideNavItem currentlySelectedPrimaryItem = null;
    private static final String SELECTED_PRIMARY_ITEM_CLASS = "manually-selected-primary-item";

    public BetaLayout(MenuEntityService service) {
        this.service = service;
        prepareMenuData();

        // 1. Determinar seleção inicial para o menu primário
        Long initialParentIdToSelect = null;
        Optional<MenuEntity> defaultParentOptional = topLevelMenus.stream()
                .filter(menu -> subMenusByParentId.containsKey(menu.getId()))
                .findFirst();
        if (defaultParentOptional.isPresent()) {
            initialParentIdToSelect = defaultParentOptional.get().getId();
        }

        // 2. Criar Conteúdo do Drawer (Menu Primário)
        VerticalLayout drawerMenuContent = createPrimaryMenuContainer(initialParentIdToSelect);
        drawerMenuContent.setHeightFull();
        addToDrawer(drawerMenuContent);
        setPrimarySection(Section.DRAWER); // Topo cheio ou depois do drawer

        // 3. Adicionar Topo
        addToNavbar(itensTopo());

        // 4. Configurar Menu Secundário e Área de Conteúdo Principal
        secondarySideNav = new SideNav();
        secondarySideNav.setWidth("250px");
        secondarySideNav.setHeightFull();
        secondarySideNav.setVisible(false);
        secondarySideNav.setCollapsible(false);

        submenuTitle = new Span();
        submenuTitle.getStyle().set("font-weight", "bold");
        submenuTitle.getStyle().set("padding", "var(--lumo-space-m)");
        submenuTitle.getStyle().set("display", "block");

        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Alternar Menu");

        HorizontalLayout submenuHeader = new HorizontalLayout(toggle, submenuTitle);
        submenuHeader.setAlignItems(FlexComponent.Alignment.BASELINE);
        submenuHeader.setPadding(false);
        submenuHeader.setSpacing(true);
        submenuHeader.setWidthFull();

        VerticalLayout submenuLayout = new VerticalLayout(submenuHeader, secondarySideNav);
        submenuLayout.getStyle().set("border-right", "1px solid var(--lumo-contrast-5pct)");
        submenuLayout.getStyle().set("background", "linear-gradient(to bottom, var(--lumo-primary-color-10pct), transparent 80%)");
        submenuLayout.setSpacing(false);
        submenuLayout.setPadding(false);
        submenuLayout.setWidth(null); // Permitir que a largura seja definida pelo conteúdo
        submenuLayout.setHeightFull();

        contentArea = new Div();
        contentArea.getStyle().set("overflow", "visible");
        contentArea.setSizeFull();

        HorizontalLayout secondaryContentWrapper = new HorizontalLayout(submenuLayout, contentArea);
        secondaryContentWrapper.setSizeFull();
        secondaryContentWrapper.setSpacing(false);
        secondaryContentWrapper.setPadding(false);
        secondaryContentWrapper.setFlexGrow(0, submenuLayout);
        secondaryContentWrapper.setFlexGrow(1, contentArea);

        // 5. Montar Layout da Página Principal para AppLayout
        VerticalLayout mainPageLayout = new VerticalLayout(secondaryContentWrapper, sicoiFooter());
        mainPageLayout.getStyle().set("background", "linear-gradient(to left, var(--lumo-primary-color-5pct), transparent 80%)");

        mainPageLayout.setSizeFull();
        mainPageLayout.setPadding(false);
        mainPageLayout.setSpacing(false);
        mainPageLayout.setFlexGrow(1, secondaryContentWrapper);
        setContent(mainPageLayout);

        // 6. Estado Inicial do Submenu
        if (defaultParentOptional.isPresent()) {
            updateSecondaryMenu(defaultParentOptional.get().getId());
        } else {
            secondarySideNav.setVisible(false);
            submenuTitle.setVisible(false);
        }
    }

    private void prepareMenuData() {
        List<MenuEntity> allMenus = this.service.getMenus();
        topLevelMenus = allMenus.stream()
                .filter(item -> item.getParentId() == null)
                .collect(Collectors.toList());
        subMenusByParentId = allMenus.stream()
                .filter(item -> item.getParentId() != null)
                .collect(Collectors.groupingBy(MenuEntity::getParentId));
    }

    private VerticalLayout createPrimaryMenuContainer(Long initiallySelectedParentId) {
        VerticalLayout navContainer = new VerticalLayout();
        navContainer.setSpacing(false);
        navContainer.setPadding(true);
        navContainer.setHeightFull();
        navContainer.getStyle().set("overflow-y", "auto");
        VerticalLayout logoLayout = new VerticalLayout();
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        logoLayout.setPadding(false);
        logoLayout.setSpacing(false);
        Image icon = new Image("icons/logo.png", "Gabinete");
        icon.setWidth("40px");
        icon.setHeight("40px");
        Span appName = new Span("Sicoi 2.0");
        appName.getStyle().set("font-weight", "bold");
        appName.getStyle().set("font-size", "1.1em");
        appName.getStyle().set("margin-top", "4px");
        var lineInLogo = new Hr();
        lineInLogo.getStyle().set("width", "0%").set("margin", "var(--lumo-space-m) 0");
        logoLayout.add(icon, appName, lineInLogo);
        navContainer.add(logoLayout);
        SideNav actualMenu = new SideNav();
        actualMenu.setWidthFull();
        for (MenuEntity item : topLevelMenus) {
            final MenuEntity currentItem = item;
            Component itemPrefixIcon;
            if (subMenusByParentId.containsKey(currentItem.getId())) {
                if (currentItem.getLinkImage() == null || currentItem.getLinkImage().isEmpty()) {
                    currentItem.setLinkImage("icons/gab.png");
                }
                Image icon2 = new Image(currentItem.getLinkImage(), "Ícone");
                icon2.setWidth("2em");
                icon2.setHeight("2em");
                SideNavItem parentTriggerItem = new SideNavItem(currentItem.getNome(), (String) null, icon2);
                parentTriggerItem.getStyle().set("cursor", "pointer");
                if (initiallySelectedParentId != null && initiallySelectedParentId.equals(currentItem.getId())) {
                    parentTriggerItem.getElement().getClassList().add(SELECTED_PRIMARY_ITEM_CLASS);
                    currentlySelectedPrimaryItem = parentTriggerItem;
                }
                parentTriggerItem.getElement().addEventListener("click", event -> {
                    if (currentlySelectedPrimaryItem != null && currentlySelectedPrimaryItem != parentTriggerItem) {
                        currentlySelectedPrimaryItem.getElement().getClassList().remove(SELECTED_PRIMARY_ITEM_CLASS);
                    }
                    parentTriggerItem.getElement().getClassList().add(SELECTED_PRIMARY_ITEM_CLASS);
                    currentlySelectedPrimaryItem = parentTriggerItem;
                    UI.getCurrent().access(() -> updateSecondaryMenu(currentItem.getId()));
                });
                actualMenu.addItem(parentTriggerItem);
            } else {
                itemPrefixIcon = new Image("icons/file.png", "Item");
                ((Image) itemPrefixIcon).setWidth("20px");
                ((Image) itemPrefixIcon).setHeight("20px");

                Class<? extends Component> targetClass = getClassByName(currentItem.getRotaClass());
                SideNavItem navOrStaticItem;

                if (targetClass != null) {
                    navOrStaticItem = new SideNavItem(currentItem.getNome(), targetClass, itemPrefixIcon);
                    navOrStaticItem.getElement().addEventListener("click", event -> {
                        if (currentlySelectedPrimaryItem != null) {
                            currentlySelectedPrimaryItem.getElement().getClassList().remove(SELECTED_PRIMARY_ITEM_CLASS);
                            currentlySelectedPrimaryItem = null;
                        }
                    });
                } else {
                    navOrStaticItem = new SideNavItem(currentItem.getNome(), (String) null, itemPrefixIcon);
                }
                actualMenu.addItem(navOrStaticItem);
            }
        }
        navContainer.add(actualMenu);
        return navContainer;
    }

    private SideNavItem createSideNavItemHierarchy(MenuEntity menuEntity) {
        Class<? extends Component> targetClass = getClassByName(menuEntity.getRotaClass());
        SideNavItem navItem;
        Component prefix = null;
        if (targetClass != null) {
            navItem = new SideNavItem(menuEntity.getNome(), targetClass, prefix);
        } else {
            navItem = new SideNavItem(menuEntity.getNome(), (String) null, prefix);
        }
        if (subMenusByParentId.containsKey(menuEntity.getId())) {
            List<MenuEntity> children = subMenusByParentId.get(menuEntity.getId());
            if (children != null) {
                for (MenuEntity child : children) {
                    navItem.addItem(createSideNavItemHierarchy(child));
                }
            }
        }
        return navItem;
    }

    private void updateSecondaryMenu(Long parentId) {
        MenuEntity parent = topLevelMenus.stream()
                .filter(item -> item.getId().equals(parentId))
                .findFirst().orElse(null);
        if (parent == null) {
            submenuTitle.setVisible(false);
            secondarySideNav.setVisible(false);
            return;
        }
        submenuTitle.setText(parent.getNome());
        submenuTitle.setVisible(true);
        secondarySideNav.removeAll();
        List<MenuEntity> children = subMenusByParentId.get(parentId);
        if (children != null && !children.isEmpty()) {
            for (MenuEntity child : children) {
                secondarySideNav.addItem(createSideNavItemHierarchy(child));
            }
            secondarySideNav.setVisible(true);
        } else {
            secondarySideNav.setVisible(false);
        }
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        contentArea.removeAll();
        if (content != null) {
            if (content instanceof Component) {
                contentArea.add((Component) content);
            } else if (content.getElement() != null) {
                contentArea.getElement().appendChild(content.getElement());
            }
        }
    }

    private Class<? extends Component> getClassByName(String className) {
        if (className == null || className.trim().isEmpty()) {
            return null;
        }
        try {
            return Class.forName(className).asSubclass(Component.class);
        } catch (ClassNotFoundException e) {
            System.err.println("Classe de navegação não encontrada: " + className + ". " + e.getMessage());
            return null;
        } catch (ClassCastException e) {
            System.err.println("A classe " + className + " não é um Componente Vaadin. " + e.getMessage());
            return null;
        }
    }

    // Topo dividido em 02 partes: left e right
    private Component itensTopo() {
        var line = new HorizontalLayout();
        line.getStyle().set("background", "linear-gradient(to left, var(--lumo-primary-color-10pct), transparent 80%)");
        line.setPadding(true);
        line.setSpacing(true);
        line.setWidthFull();

        var left = new HorizontalLayout();
        left.setWidth("80%");
        left.setAlignItems(FlexComponent.Alignment.CENTER);
        left.setJustifyContentMode(FlexComponent.JustifyContentMode.START);

        Icon homeIcon = VaadinIcon.HOME_O.create();
        homeIcon.getStyle().set("margin-right", "8px");
        Span orgaoSpan = new Span("Órgão: CEBW");
        orgaoSpan.getStyle().set("font-weight", "bold").set("margin-right", "24px");

        Span perfilLabel = new Span("Perfil Atual: Procurement Manager");
        perfilLabel.getStyle().set("margin-right", "4px");

        Span perfilDisponivel = new Span("Perfis Disponíveis: ");
        perfilLabel.getStyle().set("margin-right", "4px");

        ComboBox<String> perfilCombo = new ComboBox<>();
        perfilCombo.setItems("Administrador", "Usuário", "Convidado");
        perfilCombo.setValue("Administrador");
        left.add(homeIcon, orgaoSpan, perfilLabel, perfilDisponivel, perfilCombo);

        var right = new HorizontalLayout();
        right.setWidth("20%");
        right.setAlignItems(FlexComponent.Alignment.CENTER);
        right.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        var b1 = new Button(VaadinIcon.COMMENT_ELLIPSIS_O.create());
        var b2 = new Button(VaadinIcon.BELL_SLASH_O.create());
        right.add(b1, b2, avatar());

        line.add(left, right);
        return line;
    }

    private Component avatar() {
        // Avatar
        String name = "Administrador";
        String pictureUrl = "icons/icon.png";

        Avatar avatar = new Avatar(name);
        avatar.setImage(pictureUrl);

        ContextMenu popoverMenu = new ContextMenu();
        popoverMenu.setTarget(avatar);
        popoverMenu.setOpenOnClick(true);

        popoverMenu.addItem(VaadinIcon.USER.create(), e -> {/* ação Perfil */}).getElement().appendChild(new Span(" Perfil").getElement());
        popoverMenu.addItem(VaadinIcon.COG.create(), e -> {/* ação Preferências */}).getElement().appendChild(new Span(" Preferências").getElement());

        // Item "Tema" com submenu
        MenuItem temaItem = popoverMenu.addItem(VaadinIcon.ADJUST.create(), e -> {});
        temaItem.getElement().appendChild(new Span(" Tema").getElement());
        SubMenu temaSubMenu = temaItem.getSubMenu();
        temaSubMenu.addItem(VaadinIcon.SUN_O.create(), e -> {
            UI.getCurrent().getPage().executeJs("document.documentElement.setAttribute('theme', '')");
        }).getElement().appendChild(new Span(" Claro").getElement());
        temaSubMenu.addItem(VaadinIcon.MOON_O.create(), e -> {
            UI.getCurrent().getPage().executeJs("document.documentElement.setAttribute('theme', 'dark')");
        }).getElement().appendChild(new Span(" Escuro").getElement());

        popoverMenu.addItem(VaadinIcon.QUESTION_CIRCLE.create(), e -> {/* ação Ajuda */}).getElement().appendChild(new Span(" Ajuda").getElement());
        popoverMenu.addItem(VaadinIcon.SIGN_OUT.create(), e -> {/* ação Logout */}).getElement().appendChild(new Span(" Logout").getElement());

        return avatar;
    }

    private Component sicoiFooter() {
        // Texto "Developed with:" em itálico acima da imagem
        Span developedWith = new Span("Developed with:");
        developedWith.getStyle()
                .set("font-style", "italic")
                .set("font-size", "0.7em")
                .set("margin-bottom", "0.2em");

        // Imagem do rodapé
        Image foot = new Image();
        foot.setSrc("icons/VaadinLogo.png");
        foot.setMaxHeight("2em");

        // Layout do rodapé
        Div sicoiFooter = new Div(developedWith, foot);
        sicoiFooter.setWidthFull();
        sicoiFooter.setHeight("60px");
        sicoiFooter.getStyle()
                .set("display", "flex")
                .set("flex-direction", "column")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("background", "linear-gradient(to right, var(--lumo-primary-color-10pct), transparent 80%)");

        return sicoiFooter;
    }
}

