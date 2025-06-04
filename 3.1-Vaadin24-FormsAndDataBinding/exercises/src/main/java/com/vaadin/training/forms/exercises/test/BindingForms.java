package com.vaadin.training.forms.exercises.test;
// src/main/java/com/seuprojeto/views/AboutView.java

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "about", layout = BetaLayout.class) // Define a rota e associa ao MainLayout
@PageTitle("Sobre | Meu App")
public class BindingForms extends VerticalLayout {

	public BindingForms() {
		setSpacing(true);
		setPadding(true);
		setAlignItems(Alignment.CENTER);

		add(new H2("Sobre o Aplicativo"));
		//add(new Div("Este é um aplicativo de exemplo usando Vaadin e Spring Boot."));
	}
}
