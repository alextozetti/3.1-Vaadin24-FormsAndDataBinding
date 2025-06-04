package com.vaadin.training.forms.exercises.test;
// src/main/java/com/seuprojeto/views/DashboardView.java

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "dashboard", layout = BetaLayout.class) // Define a rota e associa ao MainLayout
@PageTitle("Dashboard | Meu App") // Título da aba do navegador
public class ProductViewer extends VerticalLayout {

    public ProductViewer() {
        setSpacing(true);
        setPadding(true);
        setAlignItems(Alignment.CENTER); // Centraliza o conteúdo

        add(new H2("Página do Dashboard"));
        //add(new Div("Bem-vindo ao painel principal do aplicativo!"));
    }
}
