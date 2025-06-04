package com.vaadin.training.forms.solutions.ex2;
// src/main/java/com/seuprojeto/views/DashboardView.java

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.training.forms.solutions.MainLayout;

@Route(value = "dashboard", layout = MainLayout.class) // Define a rota e associa ao MainLayout
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
