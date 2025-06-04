package com.vaadin.training.forms.exercises.test;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * Alfa: Um layout base que estende AppLayout.
 * Define uma estrutura de AppLayout com sua própria navbar e drawer.
 * O conteúdo principal será fornecido por classes que estendem Alfa.
 */
@PageTitle("Alfa Layout") // Título padrão para páginas que usam este layout
public class AlfaLayout extends AppLayout {

    private Div subclassContentSlot; // Onde o conteúdo da subclasse será inserido
    private Footer globalFooter;
    private VerticalLayout mainContentWrapper; // Para organizar o slot de conteúdo e o rodapé

    public AlfaLayout() {
        setPrimarySection(Section.DRAWER); // A gaveta empurra o conteúdo

        // --- TOPO (NAVBAR GLOBAL) ---
        DrawerToggle drawerToggle = new DrawerToggle();
        H3 appTitle = new H3("Meu Aplicativo Principal");
        appTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        HorizontalLayout navbarContent = new HorizontalLayout(drawerToggle, appTitle);
        navbarContent.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        navbarContent.setWidthFull();
        navbarContent.addClassNames(LumoUtility.Padding.Horizontal.MEDIUM, LumoUtility.BoxShadow.SMALL);
        // Adicionando uma cor de fundo para destacar a navbar global
        navbarContent.getStyle().set("background-color", "var(--lumo-base-color)");


        addToNavbar(false, navbarContent); // false: navbar não sobrepõe o conteúdo

        // --- GAVETA GLOBAL (DRAWER DO ALFA) ---
        // Pode conter links de navegação globais, perfil do usuário, etc.
        // Se o menu principal for sempre o da Base, esta gaveta pode ter outros usos ou ser mínima.
        SideNav globalDrawerNav = new SideNav();
        globalDrawerNav.setLabel("Menu Global Alfa");
        // Exemplo de itens na gaveta global
        globalDrawerNav.addItem(new SideNavItem("Início Global", "/", VaadinIcon.GLOBE.create()));
        globalDrawerNav.addItem(new SideNavItem("Sobre Nós", "/about", VaadinIcon.INFO_CIRCLE.create()));
        addToDrawer(globalDrawerNav);

        // --- ESTRUTURA DO CONTEÚDO CENTRAL E FUNDO GLOBAL ---
        subclassContentSlot = new Div();
        subclassContentSlot.setSizeFull(); // Ocupa o espaço disponível no VerticalLayout
        subclassContentSlot.getStyle().set("overflow", "auto"); // Garante scroll se necessário

        globalFooter = new Footer(new Span("Rodapé Principal do Aplicativo © " + java.time.Year.now()));
        globalFooter.addClassNames(
                LumoUtility.Padding.MEDIUM,
                LumoUtility.Background.CONTRAST_5,
                LumoUtility.TextAlignment.CENTER,
                LumoUtility.BoxShadow.MEDIUM // Sombra para destacar
        );
        globalFooter.setWidthFull();
        // Fixa o rodapé na parte inferior se o conteúdo for menor que a tela
        // Pode ser necessário CSS adicional para um "sticky footer" perfeito em todos os casos
        globalFooter.getStyle().set("margin-top", "auto");


        mainContentWrapper = new VerticalLayout(subclassContentSlot, globalFooter);
        mainContentWrapper.setSizeFull();
        mainContentWrapper.setPadding(false);
        mainContentWrapper.setSpacing(false);
        mainContentWrapper.setFlexGrow(1, subclassContentSlot); // Faz o slot de conteúdo expandir

        // Define o mainContentWrapper (que inclui o slot e o rodapé) como o conteúdo do AppLayout
        super.setContent(mainContentWrapper);
    }

    /**
     * Método protegido para que as subclasses (como Base) possam definir
     * seu conteúdo principal, que será inserido no 'subclassContentSlot'.
     * @param content O componente a ser inserido como conteúdo principal.
     */
    protected void setCustomMainContent(Component content) {
        this.subclassContentSlot.removeAll();
        if (content != null) {
            this.subclassContentSlot.add(content);
        }
    }
}
