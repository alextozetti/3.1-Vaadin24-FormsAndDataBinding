//package com.vaadin.training.forms.exercises.test;
//
//import com.vaadin.flow.component.Component;
//import com.vaadin.flow.component.applayout.AppLayout;
//import com.vaadin.flow.component.html.Div;
//import com.vaadin.flow.component.html.H1;
//import com.vaadin.flow.component.orderedlayout.FlexComponent;
//import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
//import com.vaadin.flow.component.sidenav.SideNav;
//import com.vaadin.flow.component.sidenav.SideNavItem;
//import com.vaadin.flow.component.splitlayout.SplitLayout;
//import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
//import com.vaadin.flow.router.Route;
//import com.vaadin.flow.router.RouterLayout;
//import com.vaadin.training.forms.exercises.menu.MenuEntity;
//import com.vaadin.training.forms.exercises.menu.MenuEntityService;
//
//import java.util.List;
//
//@Route("beta")
//public class BetaLayoutBackup extends AppLayout implements RouterLayout {
//
//    private final Div contentArea;
//    private final SplitLayout mainSplitLayout;
//    private final MenuEntityService service;
//
//    public BetaLayoutBackup(MenuEntityService service) {
//        this.service = service;
//        // --- 1. Topo (Navbar) ---
//        H1 title = new H1("Meu Aplicativo com SideNav Fixo");
//        title.getStyle().set("margin-left", "1em");
//        HorizontalLayout header = new HorizontalLayout(title);
//        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
//        header.setWidthFull();
//        header.setHeight("60px");
//        addToNavbar(header);
//
//        // --- 2. SideNav (Menu Lateral Fixo) ---
//        SideNav sideNav = new SideNav();
//        List<MenuEntity> menuItems = this.service.getMenus();
//
//        for (MenuEntity item : menuItems) {
//            Class<? extends Component> targetClass = getClassByName(item.getRotaClass());
//            if (targetClass != null) {
//                SideNavItem navItem = new SideNavItem(item.getNome(), targetClass);
//                sideNav.addItem(navItem);
//            } else {
//                System.err.println("Erro: Classe de rota não encontrada para o item de menu: " + item.getNome() + " (" + item.getRotaClass() + ")");
//                sideNav.addItem(new SideNavItem(item.getNome()));
//            }
//        }
//
//        sideNav.setCollapsible(true);
//        sideNav.setWidth("250px");
//
//        // --- 3. Área de Conteúdo (Onde as Views serão carregadas) ---
//        contentArea = new Div();
//        contentArea.setSizeFull(); // Ocupa todo o espaço dentro da sua parte do SplitLayout
//
//        // --- 4. SplitLayout para Menu e Conteúdo ---
//        mainSplitLayout = new SplitLayout();
//        mainSplitLayout.setSizeFull();
//        mainSplitLayout.setSplitterPosition(10);
//        mainSplitLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
//
//        mainSplitLayout.addToPrimary(sideNav);
//        mainSplitLayout.addToSecondary(contentArea);
//        setContent(mainSplitLayout);
//    }
//
//    @Override
//    public void showRouterLayoutContent(com.vaadin.flow.component.HasElement content) {
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
