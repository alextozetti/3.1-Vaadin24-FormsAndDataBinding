package com.vaadin.training.forms.exercises.ex2;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

import java.util.Locale;

public class CurrencyConverter implements Converter<String, Double> {

	public static String removeCurrencySymbols(String value) {
		String valueWithoutSymbol = value;
		if (value.endsWith("€")) {
			valueWithoutSymbol = value.substring(0, value.length() - 1).trim();
		} else if (value.endsWith("EUR")) {
			valueWithoutSymbol = value.substring(0, value.length() - 3).trim();
		}
		return valueWithoutSymbol;
	}

	@Override
	public Result<Double> convertToModel(String value, ValueContext context) {
		try {
			return Result.ok(Double.parseDouble(removeCurrencySymbols(value)));
		}
		catch (NumberFormatException ex) {
			return Result.error(ex.getMessage()) ;
		}
	}

	@Override
	public String convertToPresentation(Double value, ValueContext context) {
		return String.format(Locale.US, "%1$.2f", value);
	}
}
