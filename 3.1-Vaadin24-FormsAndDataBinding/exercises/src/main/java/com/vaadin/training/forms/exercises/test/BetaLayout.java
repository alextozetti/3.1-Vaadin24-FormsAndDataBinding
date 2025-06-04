package com.vaadin.training.forms.exercises.test;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle; // Importado
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1; // Importado para o título
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
// SplitLayout e RouterLink não são mais diretamente usados para o layout principal
// import com.vaadin.flow.component.splitlayout.SplitLayout;
// import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
// import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.training.forms.exercises.menu.MenuEntity;
import com.vaadin.training.forms.exercises.menu.MenuEntityService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Route("beta")
public class BetaLayout extends AppLayout implements RouterLayout {

    private final Div contentArea;
    private final SideNav secondarySideNav;
    private final MenuEntityService service;
    private final Span submenuTitle;
    private boolean darkMode = false;
    private Map<Long, List<MenuEntity>> subMenusByParentId;
    private List<MenuEntity> topLevelMenus;

    // Campos para gerenciamento da seleção do item no menu primário (drawer)
    private SideNavItem currentlySelectedPrimaryItem = null;
    private static final String SELECTED_PRIMARY_ITEM_CLASS = "manually-selected-primary-item";
    // O campo selectedParentButton do código original não é mais necessário com SideNavItem

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
        // createPrimaryMenuContainer agora usa SideNavItem e lida com a seleção inicial
        VerticalLayout drawerMenuContent = createPrimaryMenuContainer(initialParentIdToSelect);
        drawerMenuContent.setWidth("250px"); // Defina a largura desejada para o drawer
        drawerMenuContent.setHeightFull();
        addToDrawer(drawerMenuContent);
        setPrimarySection(Section.DRAWER);

        // 3. Criar Conteúdo da Navbar
        DrawerToggle toggle = new DrawerToggle(); // Botão para abrir/fechar o drawer
        toggle.getElement().setAttribute("aria-label", "Alternar Menu");

        H1 appTitle = new H1("Sistema de Compras"); // Título que estava no antigo comprasHeader
        appTitle.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("line-height", "var(--lumo-line-height-m)") // Ajuste para alinhamento vertical
                .set("margin", "0 var(--lumo-space-m)");

        addToNavbar(toggle, appTitle);

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

        VerticalLayout submenuLayout = new VerticalLayout(submenuTitle, secondarySideNav);
        submenuLayout.getStyle().set("border-right", "1px solid var(--lumo-contrast-10pct)");
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

        // 5. Configurar Rodapé
        Div sicoiFooter = new Div(new Span("Developed by Vaadin"));
        sicoiFooter.setWidthFull();
        sicoiFooter.setHeight("60px"); // Ajuste a altura conforme necessário
        sicoiFooter.getStyle().set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("background-color", "var(--lumo-contrast-5pct)");


        // 6. Montar Layout da Página Principal para AppLayout
        VerticalLayout mainPageLayout = new VerticalLayout(secondaryContentWrapper, sicoiFooter);
        mainPageLayout.setSizeFull();
        mainPageLayout.setPadding(false);
        mainPageLayout.setSpacing(false);
        mainPageLayout.setFlexGrow(1, secondaryContentWrapper);
        setContent(mainPageLayout);

        // 7. Estado Inicial do Submenu
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
        navContainer.setPadding(true); // Padding interno para o conteúdo do drawer
        navContainer.setHeightFull();
        navContainer.getStyle().set("overflow-y", "auto"); // Habilitar rolagem se necessário

        // Logo
        VerticalLayout logoLayout = new VerticalLayout();
        logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        logoLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); // Ou .START
        logoLayout.setPadding(false);
        logoLayout.setSpacing(false);

        Image icon = new Image("icons/logo.png", "Gabinete");
        icon.setWidth("40px");
        icon.setHeight("40px");

        Span appName = new Span("Sicoi 2.0");
        appName.getStyle().set("font-weight", "bold");
        appName.getStyle().set("font-size", "1.1em");
        appName.getStyle().set("margin-top", "4px");

        var lineInLogo = new Hr(); // Linha que estava no logo no código original
        lineInLogo.getStyle().set("width", "100%").set("margin", "var(--lumo-space-m) 0");
        logoLayout.add(icon, appName, lineInLogo);
        navContainer.add(logoLayout);

        // Itens do Menu Principal usando SideNav
        SideNav actualMenu = new SideNav();
        actualMenu.setWidthFull(); // Para preencher a largura do navContainer

        for (MenuEntity item : topLevelMenus) {
            final MenuEntity currentItem = item;
            Component itemPrefixIcon; // Usaremos Component para o prefixo do ícone

            if (subMenusByParentId.containsKey(currentItem.getId())) { // Item pai (abre submenu)
                // No código original, itemLayout para parentButton usava VaadinIcon.COG.create()
                itemPrefixIcon = VaadinIcon.COG.create();

                SideNavItem parentTriggerItem = new SideNavItem(currentItem.getNome(), (String) null, itemPrefixIcon);
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

            } else { // Item de link ou estático
                // No código original, itemLayout para RouterLink usava new Image("icons/file.png", "Item")
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
                        // A seleção baseada em rota do SideNav deve funcionar automaticamente
                    });
                } else { // Item estático
                    navOrStaticItem = new SideNavItem(currentItem.getNome(), (String) null, itemPrefixIcon);
                }
                actualMenu.addItem(navOrStaticItem);
            }
        }
        navContainer.add(actualMenu);

        // Spacer e Seletor de Tema
        Div spacer = new Div();
        navContainer.add(spacer);
        navContainer.setFlexGrow(1, spacer); // Empurra o seletor de tema para baixo

        var divider = new Hr();
        divider.getStyle().set("width", "100%").set("margin", "var(--lumo-space-m) 0");

        Button themeToggle = new Button(new Icon(VaadinIcon.MOON_O));
        themeToggle.setWidthFull();
        themeToggle.getElement().setProperty("title", "Alternar tema claro/escuro");
        themeToggle.addClickListener(e -> {
            darkMode = !darkMode;
            // Alternar tema usando atributo no elemento <html>
            String themeAttribute = darkMode ? "dark" : ""; // Lumo usa 'dark'. String vazia pode remover o atributo.
            UI.getCurrent().getPage().executeJs("document.documentElement.setAttribute('theme', $0)", themeAttribute);
            themeToggle.setIcon(new Icon(darkMode ? VaadinIcon.SUN_O : VaadinIcon.MOON_O));
        });
        navContainer.add(divider, themeToggle);

        return navContainer;
    }

    private SideNavItem createSideNavItemHierarchy(MenuEntity menuEntity) {
        Class<? extends Component> targetClass = getClassByName(menuEntity.getRotaClass());
        SideNavItem navItem;
        // Opcional: Adicionar ícones aos itens do submenu também
        // Image subItemIcon = menuEntity.getIconPath() != null ? new Image(menuEntity.getIconPath(), "icon") : null;
        // if (subItemIcon != null) { subItemIcon.setWidth("16px"); subItemIcon.setHeight("16px"); }

        Component prefix = null; // Defina seu ícone aqui se desejar
        // Exemplo: prefix = VaadinIcon.CIRCLE_THIN.create();

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

        if (parent == null) { // Verificação de segurança
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
            // submenuTitle.setVisible(false); // Opcional: esconder título se não há itens no submenu
        }
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        contentArea.removeAll();
        if (content != null) { // Verificação de segurança
            if (content instanceof Component) {
                contentArea.add((Component) content);
            } else if (content.getElement() != null) { // Checagem adicional
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
            // Em vez de printStackTrace, considere um log mais apropriado para produção
            System.err.println("Classe de navegação não encontrada: " + className + ". " + e.getMessage());
            return null;
        } catch (ClassCastException e) {
            System.err.println("A classe " + className + " não é um Componente Vaadin. " + e.getMessage());
            return null;
        }
    }
}
