package com.vaadin.training.forms.solutions;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.training.forms.solutions.ex2.BindingForms; // Sua AboutView
import com.vaadin.training.forms.solutions.ex2.ProductViewer; // Sua DashboardView (Home para a raiz)

@Route("") // MainLayout é a rota raiz, conteúdo é inserido por RouterLayout
public class MainLayout extends AppLayout implements RouterLayout {

    private final Div contentArea; // Onde o conteúdo da rota será inserido
    private final SplitLayout mainSplitLayout; // Referência ao SplitLayout principal

    public MainLayout() {
        // --- 1. Topo (Navbar) ---
        H1 title = new H1("Meu Aplicativo com SideNav Fixo");
        title.getStyle().set("margin-left", "1em");
        HorizontalLayout header = new HorizontalLayout(title);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidthFull();
        header.setHeight("60px"); // Defina uma altura para o cabeçalho
        //addToNavbar(header); // Adiciona o cabeçalho à barra de navegação superior do AppLayout

        // --- 2. SideNav (Menu Lateral Fixo) ---
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Dashboard", ProductViewer.class), // Rota para sua DashboardView
                new SideNavItem("Sobre", BindingForms.class),       // Rota para sua AboutView
                new SideNavItem("Gerenciar Menus", com.vaadin.training.forms.solutions.ex2.ProductViewer.class) // Exemplo, pode ser outra View
        );
        sideNav.setCollapsible(true); // Permite recolher/expandir o SideNav (opcional)
        sideNav.setWidth("250px"); // Largura inicial do SideNav

        // --- 3. Área de Conteúdo (Onde as Views serão carregadas) ---
        contentArea = new Div();
        contentArea.setSizeFull(); // Ocupa todo o espaço dentro da sua parte do SplitLayout

        // --- 4. SplitLayout para Menu e Conteúdo ---
        mainSplitLayout = new SplitLayout();
        mainSplitLayout.setSizeFull(); // O SplitLayout deve ocupar 100% do espaço disponível
        mainSplitLayout.setSplitterPosition(20); // Ajuste a posição do divisor (20% para o menu)
        mainSplitLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL); // Estilo menor para o divisor

        // Adiciona o SideNav na parte primária (esquerda/topo)
        mainSplitLayout.addToPrimary(sideNav);
        // A contentArea será a parte secundária (direita/baixo), mas o conteúdo real será injetado nela.
        mainSplitLayout.addToSecondary(contentArea);

        // --- 5. Define o SplitLayout como o conteúdo principal do AppLayout ---
        // O AppLayout cuidará de colocar este split abaixo da navbar.
        setContent(mainSplitLayout);
    }

    @Override
    public void showRouterLayoutContent(com.vaadin.flow.component.HasElement content) {
        // Remove qualquer conteúdo anterior da contentArea
        contentArea.removeAll();

        // Adiciona o novo conteúdo (a View)
        // O 'content' que você recebe é um HasElement.
        // As Views (Componentes @Route) também são Componentes, que implementam HasElement.
        // Você pode fazer um cast seguro para Component.
        if (content instanceof Component) {
            contentArea.add((Component) content);
        } else {
            // Caso raro onde o HasElement não é um Component (ex: apenas um Element puro)
            // Você pode adicionar o Element diretamente ao Div se quiser, mas é menos comum.
            // Para Views roteadas, sempre será um Component.
            contentArea.getElement().appendChild(content.getElement());
        }
    }
}
