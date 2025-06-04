package com.vaadin.training.forms.exercises.ex1;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.router.Route;
import com.vaadin.training.forms.exercises.menu.MainLayout;
import com.vaadin.training.forms.exercises.menu.MenuEntity;
import com.vaadin.training.forms.exercises.menu.MenuEntityService;
import com.vaadin.training.forms.exercises.test.BetaLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.Serial;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// ... [imports continuam os mesmos]

@Route(value = ExampleAccordion.ROUTE, layout = BetaLayout.class)
public class ExampleAccordion extends VerticalLayout {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final String ROUTE = "accordion";
    public static final String TITLE = "Accordion Example";

    private final Div contentArea;
    private final MenuEntityService service;

    VerticalLayout mainMenu = new VerticalLayout();
    VerticalLayout submenuArea = new VerticalLayout();

    public ExampleAccordion(MenuEntityService service) {
        this.service = service;

        setSizeFull();
        setPadding(false);
        setSpacing(false);

        SplitLayout mainSplit = new SplitLayout();
        mainSplit.setSizeFull();
        mainSplit.setSplitterPosition(15);
        mainSplit.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);

        // Menu principal (pai)
        mainMenu.setWidth("280px");
        mainMenu.getStyle().set("minWidth", "80px");
        mainMenu.getStyle().set("maxWidth", "400px");
        mainMenu.getStyle().set("overflow", "auto");
        mainMenu.setPadding(true);

        submenuArea.setWidth("270px");
        submenuArea.setPadding(true);
        submenuArea.getStyle().set("overflow", "hidden");

        contentArea = new Div();
        contentArea.getStyle().set("padding", "1rem");

        var rightSide = new HorizontalLayout();
        rightSide.setWidthFull();
        rightSide.setSpacing(false);
        rightSide.setPadding(false);
        rightSide.getStyle().set("border", "2px solid #cccccc");
        rightSide.getStyle().set("border-radius", "16px");
        rightSide.getStyle().set("overflow", "hidden");
        rightSide.add(submenuArea, contentArea);
        rightSide.expand(contentArea);

        mainSplit.addToPrimary(mainMenu);
        mainSplit.addToSecondary(rightSide);

        add(new Span("TOPO"), mainSplit, new Span("FUNDO"));

        List<MenuEntity> menuList = this.service.getMenus();
        Map<Long, List<MenuEntity>> groupedByParent = menuList.stream()
                .filter(m -> m.getParentId() != null)
                .collect(Collectors.groupingBy(MenuEntity::getParentId));

        List<MenuEntity> menuPais = menuList.stream()
                .filter(m -> m.getParentId() == null)
                .toList();

        loadMenu(menuPais, groupedByParent);

        if (!menuPais.isEmpty()) {
            loadSubmenu(menuPais.get(0).getId(), groupedByParent);
        }
    }

    private void loadMenu(List<MenuEntity> menuPais, Map<Long, List<MenuEntity>> groupedByParent) {
        for (MenuEntity pai : menuPais) {
            Image icon = new Image(pai.getLinkImage(), pai.getNome());
            icon.setWidth("40px");
            icon.setHeight("40px");

            Span textSpan = new Span(pai.getNome());
            textSpan.getStyle().set("font-size", "0.8em");

            VerticalLayout content = new VerticalLayout(icon, textSpan);
            content.setAlignItems(FlexComponent.Alignment.CENTER);
            content.setSpacing(false);
            content.setPadding(false);

            Button button = new Button(content);
            button.setWidthFull();
            button.setHeight("80px");
            button.getStyle().set("background-color", "transparent");
            button.getStyle().set("border", "none");
            button.getStyle().set("cursor", "pointer");

            button.addClickListener(e -> loadSubmenu(pai.getId(), groupedByParent));

            mainMenu.add(button);
        }
    }

    private void loadSubmenu(Long paiId, Map<Long, List<MenuEntity>> groupedByParent) {
        submenuArea.removeAll();
        contentArea.removeAll();

        Accordion accordion = new Accordion();
        accordion.setWidthFull();

        List<MenuEntity> filhos = groupedByParent.getOrDefault(paiId, List.of());
        for (MenuEntity filho : filhos) {
            List<MenuEntity> netos = groupedByParent.getOrDefault(filho.getId(), List.of());

            if (!netos.isEmpty()) {
                VerticalLayout panelContent = new VerticalLayout();
                panelContent.setPadding(false);
                panelContent.setSpacing(false);

                for (MenuEntity neto : netos) {
                    Button netoBtn = new Button(neto.getNome());
                    netoBtn.setWidthFull();
                    netoBtn.getStyle().set("text-align", "left");
                    netoBtn.addClickListener(e -> showContent(neto));
                    panelContent.add(netoBtn);
                }

                AccordionPanel panel = accordion.add(filho.getNome(), panelContent);
                panel.setOpened(true);
            } else {
                Button filhoBtn = new Button(filho.getNome());
                filhoBtn.setWidthFull();
                filhoBtn.getStyle().set("text-align", "left");
                filhoBtn.addClickListener(e -> showContent(filho));
                submenuArea.add(filhoBtn);
            }
        }

        submenuArea.add(accordion);
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
