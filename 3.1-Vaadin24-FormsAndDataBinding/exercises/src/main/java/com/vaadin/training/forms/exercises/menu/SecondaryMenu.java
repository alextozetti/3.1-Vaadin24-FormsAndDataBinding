package com.vaadin.training.forms.exercises.menu;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.component.details.DetailsVariant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class SecondaryMenu extends VerticalLayout {

    private final Map<String, List<MenuItem>> menuData = new HashMap<>();
    private Accordion currentAccordion;

    // Mapa para auxiliar na construção da hierarquia de MenuEntity
    private Map<Long, List<MenuEntity>> entityParentMap;

    private final MenuEntityService service;

    public SecondaryMenu(MenuEntityService service) {

        this.service = service;

        setSpacing(false);
        setPadding(true);
        setVisible(false);
        getStyle().set("overflow", "hidden");
        getStyle().set("transition", "width 2.5s ease-in-out");

        populateMenuData();
        updateMenu("reports");
    }

    public void updateMenu(String category) {
        removeAll();
        setupControlButtons();

        List<MenuItem> items = menuData.getOrDefault(category,
                List.of(new MenuItem("Nenhum item disponível para esta categoria.", (Class<? extends Component>) null)));

        currentAccordion = new Accordion();
        currentAccordion.setWidthFull();

        items.forEach(item -> addMenuItemToAccordion(currentAccordion, item));
        add(currentAccordion);
        setVisible(true);
    }

    private void setupControlButtons() {
        Button expandAllBtn = new Button("Abrir");
        Button collapseAllBtn = new Button("Fechar");
        HorizontalLayout controlsLayout = new HorizontalLayout(expandAllBtn, collapseAllBtn);
        controlsLayout.setWidthFull();
        controlsLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        controlsLayout.setSpacing(true);
        add(controlsLayout);

        expandAllBtn.addClickListener(event -> toggleAllAccordionPanels(true));
        collapseAllBtn.addClickListener(event -> toggleAllAccordionPanels(false));
    }

    private void addMenuItemToAccordion(Accordion accordion, MenuItem item) {
        if (item.hasSubItems()) {
            VerticalLayout subMenuContent = new VerticalLayout();
            subMenuContent.setSpacing(false);
            subMenuContent.setPadding(false);

            item.getSubItems().forEach(subItem -> {
                if (subItem.hasSubItems()) {
                    Accordion nestedAccordion = new Accordion();
                    nestedAccordion.setWidthFull();
                    addMenuItemToAccordion(nestedAccordion, subItem);
                    nestedAccordion.getChildren()
                            .filter(AccordionPanel.class::isInstance)
                            .map(AccordionPanel.class::cast)
                            .forEach(p -> p.setOpened(false));
                    subMenuContent.add(nestedAccordion);
                } else {
                    subMenuContent.add(createLinkOrSpan(subItem, true));
                }
            });

            AccordionPanel panel = accordion.add(item.getTitle(), subMenuContent);
            panel.addThemeVariants(DetailsVariant.SMALL, DetailsVariant.REVERSE);
            panel.setOpened(false);

            panel.getChildren()
                    .filter(c -> c instanceof Span)
                    .map(c -> (Span) c)
                    .findFirst()
                    .ifPresent(span -> span.getStyle().set("font-size", "0.8em"));

        } else {
            add(createLinkOrSpan(item, false));
        }
    }

    private Component createLinkOrSpan(MenuItem item, boolean isSubItem) {
        Component component;
        if (item.isLink()) {
            RouterLink link = new RouterLink(item.getTitle(), item.getTargetClass());
            link.setHighlightAction((routerLink, event) ->
                    routerLink.getElement().getClassList().set("vaadin-router-link-active", event)
            );
            link.getStyle().set("display", "block");
            component = link;
        } else {
            Span span = new Span(item.getTitle());
            span.setWidthFull();
            component = span;
        }

        component.getStyle().set("padding", isSubItem ? "8px 15px" : "10px 15px");
        component.getStyle().set("margin-left", isSubItem ? "10px" : "0px");
        component.getStyle().set("font-size", "0.8em");

        // Adiciona a linha de borda inferior
        component.getStyle().set("border-bottom", "1px solid var(--lumo-contrast-10pct)");

        return component;
    }

    private void toggleAllAccordionPanels(boolean expand) {
        if (currentAccordion != null) {
            currentAccordion.getChildren()
                    .filter(AccordionPanel.class::isInstance)
                    .map(AccordionPanel.class::cast)
                    .forEach(panel -> panel.setOpened(expand));
        }
    }

    public void populateMenuData() {
        menuData.clear();

        List<MenuEntity> entities = service.getMenus();

        entityParentMap = entities.stream()
                .filter(e -> e.getParentId() != null)
                .collect(Collectors.groupingBy(MenuEntity::getParentId));

        Map<String, List<MenuItem>> grouped = new HashMap<>();
        for (MenuEntity e : entities) {
            if (e.getParentId() == null) {
                grouped.computeIfAbsent(e.getCategoria(), k -> new ArrayList<>())
                        .add(buildMenuItem(e));
            }
        }
        menuData.putAll(grouped);
    }

    private MenuItem buildMenuItem(MenuEntity entity) {
        List<MenuEntity> children = entityParentMap.getOrDefault(entity.getId(), Collections.emptyList());

        if (!children.isEmpty()) {
            List<MenuItem> subItems = children.stream()
                    .map(this::buildMenuItem)
                    .collect(Collectors.toList());
            return new MenuItem(entity.getNome(), subItems);
        } else {
            return new MenuItem(entity.getNome(), getClassByName(entity.getRotaClass()));
        }
    }

    private Class<? extends Component> getClassByName(String className) {
        if (className == null) return null;
        try {
            return Class.forName(className).asSubclass(Component.class);
        } catch (ClassNotFoundException e) {
            System.err.println("Classe não encontrada: " + className);
            return null;
        }
    }
}
