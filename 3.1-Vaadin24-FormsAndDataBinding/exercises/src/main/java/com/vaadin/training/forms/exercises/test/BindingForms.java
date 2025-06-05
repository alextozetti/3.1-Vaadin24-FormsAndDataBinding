package com.vaadin.training.forms.exercises.test;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "about", layout = BetaLayout.class)
@PageTitle("Sobre | Meu App")
public class BindingForms extends VerticalLayout {

	public BindingForms() {
		setSpacing(true);
		setPadding(true);
		setAlignItems(Alignment.CENTER);
		setSizeFull(); //Ocupa toda a altura da pagina

		add(new H2("Sobre o Aplicativo"));

		// Botão que abre o Dialog
		Button abrirDialogButton = new Button("Abrir Diálogo à Direita");

		// Criação do Dialog
		Dialog meuDialog = new Dialog();
		meuDialog.addClassName("dialog-no-lado-direito");
		meuDialog.setHeaderTitle("Painel Direito");
		meuDialog.setWidth("800px");
		meuDialog.setHeight("400px");

		VerticalLayout dialogLayout = new VerticalLayout(new Span("Conteúdo do diálogo aqui..."));
		dialogLayout.setPadding(true); // Adicionar padding dentro do conteúdo do diálogo
		dialogLayout.setSpacing(true);
		dialogLayout.setSizeFull(); // Fazer o layout interno ocupar todo o espaço do ::part(content)
		dialogLayout.setAlignItems(Alignment.CENTER); // Já é o padrão para VL

		meuDialog.add(dialogLayout);

		// Se ainda quiser o footer:
		Button fecharButton = new Button("Fechar", e -> meuDialog.close());
		meuDialog.getFooter().add(fecharButton);

		abrirDialogButton.addClickListener(event -> meuDialog.open());

		// Adicione o botão à sua view
		add(abrirDialogButton);


		// Organização de 01 horizontal line com 03 horizontal lines para alinhamentos
		var line = new HorizontalLayout();
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

		add(line);

		var bottom = new VerticalLayout();
		bottom.setHeightFull();
		bottom.getStyle().set("background-color", "gray");
		bottom.setAlignItems(Alignment.CENTER);
		bottom.setJustifyContentMode(JustifyContentMode.END);
		bottom.add(new Span("Fundo da pagina"));
		add(bottom);

	}
}
