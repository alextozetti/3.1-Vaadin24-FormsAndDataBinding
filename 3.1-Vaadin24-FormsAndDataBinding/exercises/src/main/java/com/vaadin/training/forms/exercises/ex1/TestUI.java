package com.vaadin.training.forms.exercises.ex1;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.training.forms.exercises.menu.MainLayout;
import com.vaadin.training.forms.exercises.test.BetaLayout;

import java.io.Serial;

@Route(value = TestUI.ROUTE, layout = BetaLayout.class)
public class TestUI extends VerticalLayout implements HasSize{

	@Serial
	private static final long serialVersionUID = 1L;

	public static final String ROUTE = "";
	public static final String TITLE = "Validation2222";

	public TestUI() {

		setPadding(true);
		setSpacing(true);
		setHeightFull();
		setWidthFull();
		 setJustifyContentMode(JustifyContentMode.CENTER); // Para centralizar cards
		 setAlignItems(Alignment.CENTER); // Para centralizar cards verticalmente

		var h1 = new HorizontalLayout();
		// Adiciona alguns cards
		h1.add(new MyCard("Card de Produto", "Este é um produto incrível com muitas funcionalidades."));
		h1.add(new MyCard("Serviço Premium", "Nosso serviço premium oferece suporte 24/7 e acesso exclusivo."));
		h1.add(new MyCard("Serviço Premium", "Nosso serviço premium oferece suporte 24/7 e acesso exclusivo."));
		h1.add(new MyCard("Serviço Premium", "Nosso serviço premium oferece suporte 24/7 e acesso exclusivo."));
		add(h1);
	}

}
