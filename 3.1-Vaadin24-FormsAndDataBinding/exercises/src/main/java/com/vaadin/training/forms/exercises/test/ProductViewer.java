package com.vaadin.training.forms.exercises.test;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route(value = "dashboard", layout = BetaLayout.class)
@PageTitle("Dashboard | Meu App")
public class ProductViewer extends VerticalLayout {

    public ProductViewer() {
        setSpacing(true);
        setPadding(true);
        setAlignItems(Alignment.CENTER);
        setSizeFull();

        add(new H2("Página do Dashboard"));

        SideNav sideNav = new SideNav();
        sideNav.setLabel("Menu Acordeão");

        // 1. Lista para gerenciar todos os itens que podem ser expandidos/colapsados
        List<SideNavItem> collapsibleItems = new ArrayList<>();

        // --- Criando os Itens do Menu ---

        // Item 1 (Pai)
        SideNavItem parent1 = new SideNavItem("Clientes");
        parent1.addItem(new SideNavItem("Novo Cliente"));
        parent1.addItem(new SideNavItem("Listar Clientes"));
        collapsibleItems.add(parent1); // Adiciona à lista de gerenciamento

        // Item 2 (Pai)
        SideNavItem parent2 = new SideNavItem("Produtos");
        parent2.addItem(new SideNavItem("Novo Produto"));
        parent2.addItem(new SideNavItem("Estoque"));
        collapsibleItems.add(parent2); // Adiciona à lista de gerenciamento

        // Item 3 (Pai)
        SideNavItem parent3 = new SideNavItem("Relatórios");
        parent3.setExpanded(true); // Pode definir um item para começar aberto
        parent3.addItem(new SideNavItem("Vendas por Período"));
        parent3.addItem(new SideNavItem("Clientes Ativos"));
        collapsibleItems.add(parent3); // Adiciona à lista de gerenciamento

        // Item 4 (Item simples, sem filhos)
        SideNavItem simpleItem = new SideNavItem("Configurações");


        // Adiciona os itens ao SideNav
        sideNav.addItem(parent1, parent2, parent3, simpleItem);


        // 2. Adicionar o listener para criar o comportamento de acordeão
        for (SideNavItem itemToListenOn : collapsibleItems) {
            itemToListenOn.getElement().addEventListener("click", e -> {
                // 3. Quando um item é clicado, fecha todos os outros
                for (SideNavItem otherItem : collapsibleItems) {
                    // A condição `!=` garante que não fechamos o item que acabamos de clicar
                    if (otherItem != itemToListenOn) {
                        otherItem.setExpanded(false);
                    }
                }
            });
        }

        add(sideNav);
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);



        // Conteúdo expansível para empurrar a linha para o rodapé
        VerticalLayout content = new VerticalLayout();
        content.setWidthFull();
        content.setPadding(false);
        content.setSpacing(false);
        setFlexGrow(1, content);

        add(content);

        // Linha do rodapé
        add(menuRodape());
    }

    private Component menuRodape() {
        HorizontalLayout line = new HorizontalLayout();
        line.setPadding(true);
        line.setSpacing(true);
        line.setWidthFull();
        line.getStyle().set("background-color", "yellow");

        var left = new HorizontalLayout();
        left.setWidthFull();
        left.getStyle().set("background-color", "orange");
        left.setAlignItems(Alignment.CENTER);
        left.setJustifyContentMode(JustifyContentMode.START);
        var b1 = new Button(VaadinIcon.FILE_TEXT.create());
        left.add(b1);

        var center = new HorizontalLayout();
        center.setWidthFull();
        center.getStyle().set("background-color", "cyan");
        center.setAlignItems(Alignment.CENTER);
        center.setJustifyContentMode(JustifyContentMode.CENTER);
        var c = new Button(VaadinIcon.COG.create());
        center.add(c);

        var right = new HorizontalLayout();
        right.setWidthFull();
        right.getStyle().set("background-color", "white");
        right.setAlignItems(Alignment.CENTER);
        right.setJustifyContentMode(JustifyContentMode.END);
        var b2 = new Button(VaadinIcon.USER_CARD.create());
        right.add(b2);

        line.add(left, center, right);
        return line;
    }
}
