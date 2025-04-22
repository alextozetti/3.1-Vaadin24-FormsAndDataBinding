package com.vaadin.training.layouting.exercises.ex1;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.training.layouting.exercises.MainLayout;

import java.io.Serial;

@Route(value = ApplicationLayout.ROUTE, layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ApplicationLayout extends VerticalLayout {

	@Serial
	private static final long serialVersionUID = 1L;

	public static final String ROUTE = "ex1";
	public static final String TITLE = "Exercise 1";

	public ApplicationLayout() {
		setSizeFull();
		setPadding(false);
		setSpacing(false);

		final Div header = new Div();
		header.setText("This is the header. My height is 150 pixels");
		header.setClassName("header");

		final Div navigation = new Div();
		navigation.setClassName("navigation");
		navigation.setText("This is the navigation area. My width is 25% of the ApplicationLayout.");

		final Div content = new Div();
		content.setClassName("content");
		content.setText("This is the content area");

		final Div footer = new Div();
		footer.setText("This is the footer area. My height is 100 pixels");
		footer.setClassName("footer");

		add(header, navigation, content, footer);
	}

	/**
	 * Ignore this method for now.
	 *
	 * @return
	 */
	private Div createBlock() {
		final Div button = new Div();
		button.setText("Block");
		button.getStyle().set("background", "white");
		button.setHeight("100px");
		button.setWidth("100px");
		button.getStyle().set("margin", "2px");
		return button;
	}

}