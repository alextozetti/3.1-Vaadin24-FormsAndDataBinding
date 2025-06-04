package com.vaadin.training.forms.solutions.ex2;
// src/main/java/com/seuprojeto/views/AboutView.java

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.training.forms.solutions.MainLayout;

@Route(value = "about", layout = MainLayout.class) // Define a rota e associa ao MainLayout
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
