package com.vaadin.training.forms.exercises.ex2;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.training.forms.exercises.menu.MainLayout;
import com.vaadin.training.forms.exercises.test.BetaLayout;

import java.time.LocalDate;

@Route(value = BindingForms.ROUTE, layout = BetaLayout.class)
public class BindingForms extends HorizontalLayout {

	public static final String ROUTE = "ex2";
	public static final String TITLE = "Binding Forms";

	public BindingForms() {
		final Product product = createProduct();

		ProductViewer productViewer = new ProductViewer(product);
		productViewer.setWidth("30%");
		ProductEditor productEditor = new ProductEditor(product, productViewer::refresh);
		productEditor.setWidth("30%");

		add(productEditor, productViewer);

		getThemeList().set("spacing", true);
	}

	private static Product createProduct() {
		final Product product = new Product();
		product.setName("Testproduct");
		product.setAvailable(LocalDate.now());
		return product;
	}
}
