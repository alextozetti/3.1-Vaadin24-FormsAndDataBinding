package com.vaadin.training.forms.exercises.ex1;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.accordion.AccordionPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent; // Necessário para JustifyContentMode
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.training.forms.exercises.menu.MenuEntity;
import com.vaadin.training.forms.exercises.menu.MenuEntityService;

import java.io.Serial;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Route(value = Test.ROUTE)
public class Test extends VerticalLayout {

    @Serial
    private static final long serialVersionUID = 1L;

    public static final String ROUTE = "testando";
    public static final String TITLE = "Accordion Example";

    public Test() {

        setSizeFull();
        setPadding(false);
        setSpacing(false);
        getStyle().set("overflow", "hidden"); // Garante que o layout principal não tenha scroll

        SplitLayout mainSplit = new SplitLayout();
        mainSplit.setSizeFull();
        mainSplit.setSplitterPosition(15); // Posição do divisor

        // Menu principal (pai)
        var mainMenu = new VerticalLayout();
        mainMenu.getStyle().set("background", "blue");
        mainMenu.setSizeFull(); // Ocupa todo o espaço na primary side

        var secondo = new VerticalLayout();
        secondo.getStyle().set("background", "yellow");
        secondo.setSizeFull(); // Ocupa todo o espaço na secondary side
        secondo.setPadding(false); // Remove padding padrão
        secondo.setSpacing(false); // Remove espaçamento padrão
        secondo.getStyle().set("overflow", "hidden"); // Garante que 'secondo' não tenha scroll

        var a1 = new HorizontalLayout();
        a1.setWidthFull(); // Ocupa 100% da largura do 'secondo'
        a1.setSpacing(false); // **Remova o espaçamento padrão**
        a1.setPadding(true); // **Remova o padding padrão**
        a1.getStyle().set("overflow", "hidden"); // Garante que 'a1' não tenha scroll

        var b1 = new Button("b1");
        b1.setWidth("200px"); // Defina uma largura fixa (ou um percentual pequeno)
        // b1.getStyle().set("flex-shrink", "0"); // Opcional: Garante que b1 não encolha

        var b2 = new Button("b2");
        // REMOVER b2.setWidthFull() - Isso faz com que ele tente ocupar TODO o espaço,
        // o que causa o estouro quando combinado com a largura de b1 e qualquer espaçamento.
        // A.expand(b2) fará o trabalho de preencher o restante.

        a1.add(b1, b2); // Adiciona os botões ao layout horizontal
        a1.expand(b2); // Faz b2 expandir para preencher o espaço restante

        secondo.add(a1); // Adiciona o layout a1 ao secondo
        secondo.expand(a1); // Faz a1 expandir para preencher o espaço restante verticalmente em secondo

        mainSplit.addToPrimary(mainMenu);
        mainSplit.addToSecondary(secondo);

        add(mainSplit);
    }
}
