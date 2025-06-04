package com.vaadin.training.forms.exercises.ex1;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.training.forms.exercises.menu.MainLayout;
import com.vaadin.training.forms.exercises.test.BetaLayout;

import java.io.Serial;

@Route(value = Validation.ROUTE, layout = BetaLayout.class)
public class Validation extends VerticalLayout implements HasSize{

	@Serial
	private static final long serialVersionUID = 1L;

	public static final String ROUTE = "ex1";
	public static final String TITLE = "Validation";

	public Validation() {

		var binder = new Binder<>(Person.class);
		binder.setBean(new Person());

		final TextField emailField = new TextField("Email validator");
		binder.forField(emailField)
				.withValidator(new EmailValidator("Are you sure the given value is an email address"))
				.bind(Person::getEmail, Person::setEmail);

		// TODO Bind field and add validation which accepts strings between 1
		// and 10 in length
		final TextField stringField = new TextField("String length validator");
		binder.forField(stringField)
				.withValidator(new StringLengthValidator("Maximum of 10 characters allowed", 0, 10))
				.bind(Person::getText, Person::setText);

		stringField.addValueChangeListener(event -> {
			if (event.getValue().length() > 10) {
				Notification.show("Máximo de 10 caracteres permitidos",3000, Notification.Position.MIDDLE);;
			} else {
				stringField.setErrorMessage(null);
			}
		});

		// TODO Bind field and add a custom Validator which only accepts
		// "Vaadin"
		final TextField vaadinField = new TextField("Vaadin validator");
		binder.forField(vaadinField)
				.withValidator((value, context) -> {
					if (value == null || "".equals(value) || value.equals("Vaadin")) {
						return ValidationResult.ok();
					} else {
						return ValidationResult.error(value + " not accepted");
					}
				})
				.bind(Person::getValid, Person::setValid);

		add(emailField, stringField, vaadinField);

	}

}
