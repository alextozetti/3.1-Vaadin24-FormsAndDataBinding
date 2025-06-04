package com.vaadin.training.forms.exercises.ex1;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

public class MyCard extends VerticalLayout {

    public MyCard(String titleText, String descriptionText) {
        // Define o próprio Card como um VerticalLayout para organizar o conteúdo
        setSpacing(false); // Remove espaçamento padrão entre os filhos
        setPadding(true); // Adiciona padding interno

        // Adiciona estilos CSS para dar a aparência de um Card
        getStyle().set("background-color", "var(--lumo-base-color)"); // Fundo claro do Lumo
        getStyle().set("border-radius", "var(--lumo-border-radius-m)"); // Cantos arredondados
        getStyle().set("box-shadow", "var(--lumo-box-shadow-s)"); // Sombra sutil
        getStyle().set("margin", "var(--lumo-space-m)"); // Margem externa

        // Opcional: Definir largura ou altura para o card
        // setWidth("300px");
        // setHeight("auto"); // Ajusta altura automaticamente ao conteúdo

        // Título do Card
        H3 title = new H3(titleText);
        title.getStyle().set("margin-top", "0"); // Remove margem superior padrão de H3
        title.getStyle().set("margin-bottom", "var(--lumo-space-s)");

        // Descrição do Card
        Paragraph description = new Paragraph(descriptionText);
        description.getStyle().set("margin-bottom", "var(--lumo-space-m)");

        // Botão de Ação (exemplo)
        Button actionButton = new Button("Saiba Mais");
        actionButton.getStyle().set("margin-top", "auto"); // Empurra o botão para baixo se o card for flexbox e tiver espaço livre

        // Adiciona os componentes ao Card
        add(title, description, actionButton);

        // Alinhamento dos itens dentro do card (se for VerticalLayout)
        setAlignItems(FlexComponent.Alignment.START); // Alinha itens à esquerda
    }
}
