package com.vaadin.training.forms.exercises.test;
// src/main/java/com/seuprojeto/views/AboutView.java

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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
	}
}
