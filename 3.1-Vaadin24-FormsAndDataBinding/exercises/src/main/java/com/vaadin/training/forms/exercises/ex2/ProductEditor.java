package com.vaadin.training.forms.exercises.ex2;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.function.Consumer;

public class ProductEditor extends Composite<VerticalLayout> implements HasComponents, HasSize{
    public ProductEditor(Product product, Consumer<Product> productConsumer){
        // TODO Create a new class that extends a layout for editing the product

        // TODO Create a Binder and bind it together with the input fields
        // on the editor component you created. Note that after the bindings
        // have been defined, you should have the binder read the Product bean
        // given as a parameter.

        // TODO Create a Save button which will write the values from the binder
        // to the Product bean. A successful save should also refresh the
        // read-only view

        // TODO Create a Cancel button which will read the values from the
        // Product bean to the binder
    }
}
