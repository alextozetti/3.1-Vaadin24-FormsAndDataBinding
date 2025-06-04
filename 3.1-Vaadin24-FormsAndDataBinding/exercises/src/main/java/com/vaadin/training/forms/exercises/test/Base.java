package com.vaadin.training.forms.exercises.test;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * Base: A página principal que estende Alfa.
 * Ela injeta sua própria estrutura (topo, SideNav, conteúdo, fundo)
 * na área de conteúdo do layout Alfa.
 */
@PageTitle("Página Base Principal")
@Route("base") // Define "base" como a rota para esta página
public class Base extends AlfaLayout implements RouterLayout { // Estende Alfa e é um RouterLayout

    private VerticalLayout baseViewContentSlot; // Onde o conteúdo das views da Base será mostrado

    public Base() {
        super(); // Chama o construtor de Alfa, que configura o topo, fundo e gaveta globais

        // --- Cria os componentes específicos da estrutura interna de Base ---

        // 1. SideNav da Página Base
        SideNav baseSideNav = new SideNav();
        baseSideNav.setLabel("Menu da Base");
        baseSideNav.addItem(new SideNavItem("Dashboard Base", BaseDashboardView.class, VaadinIcon.DASHBOARD.create()));
        baseSideNav.addItem(new SideNavItem("Relatórios Base", BaseReportsView.class, VaadinIcon.CHART_TIMELINE.create()));

        baseSideNav.setWidth("280px");
        baseSideNav.getStyle().set("flex-shrink", "0"); // Para não encolher
        baseSideNav.getStyle().set("border-right", "1px solid var(--lumo-contrast-20pct)");
        baseSideNav.getStyle().set("background-color", "var(--lumo-contrast-5pct)"); // Um leve fundo


        // 2. Slot de Conteúdo para as Views da Base
        baseViewContentSlot = new VerticalLayout();
        baseViewContentSlot.setSizeFull();
        baseViewContentSlot.setPadding(true); // Padding interno para o conteúdo das views
        baseViewContentSlot.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);
        baseViewContentSlot.add(new Span("Bem-vindo à seção Base. Selecione um item no 'Menu da Base'."));

        // 3. Layout Central da Base (SideNav + Slot de Conteúdo das Views)
        HorizontalLayout baseCoreLayout = new HorizontalLayout(baseSideNav, baseViewContentSlot);
        baseCoreLayout.setSizeFull(); // Ocupa todo o espaço no Scroller
        baseCoreLayout.setFlexGrow(1, baseViewContentSlot); // Slot de conteúdo expande

        // Adiciona um Scroller para o layout central da Base
        Scroller scrollerForBaseCoreLayout = new Scroller(baseCoreLayout);
        scrollerForBaseCoreLayout.setSizeFull(); // Scroller ocupa todo o espaço do slot no Alfa

        // --- "e coloque no meio" ---
        // Define o layout central da Base como o conteúdo principal no slot fornecido por Alfa
        setCustomMainContent(scrollerForBaseCoreLayout);
    }

    // Implementação de RouterLayout para Base (para exibir BaseDashboardView, BaseReportsView, etc.)
    @Override
    public void showRouterLayoutContent(HasElement contentElement) {
        baseViewContentSlot.removeAll();
        if (contentElement != null) {
            baseViewContentSlot.add((Component) contentElement);
        } else {
            baseViewContentSlot.add(new Span("Conteúdo da sub-rota não encontrado."));
        }
    }

    // --- Views de Exemplo para a Navegação da Base ---
    // Crie estas classes como arquivos .java separados ou classes internas estáticas.

    @PageTitle("Dashboard (Base)")
    @Route(value = "base/dashboard", layout = Base.class)
    public static class BaseDashboardView extends Div {
        public BaseDashboardView() {
            super(new Span("Conteúdo do Dashboard da Base."));
            addClassNames(LumoUtility.Padding.LARGE, LumoUtility.FontSize.LARGE);
        }
    }

    @PageTitle("Relatórios (Base)")
    @Route(value = "base/reports", layout = Base.class)
    public static class BaseReportsView extends Div {
        public BaseReportsView() {
            super(new Span("Conteúdo da Página de Relatórios da Base."));
            addClassNames(LumoUtility.Padding.LARGE, LumoUtility.FontSize.LARGE);
        }
    }
}
