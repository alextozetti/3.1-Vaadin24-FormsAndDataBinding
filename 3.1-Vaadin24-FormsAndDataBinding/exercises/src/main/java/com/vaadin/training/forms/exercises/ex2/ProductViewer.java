package com.vaadin.training.forms.exercises.ex2;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.formlayout.FormLayout;

public class ProductViewer extends Composite<FormLayout> implements HasSize{

    public ProductViewer(Product product) {
        final FormLayout layout = getContent();

        //TODO adding paragraphs to formlayout to show product info

    }

    void refresh(Product product){
        // TODO Update the three paragraphs with values from the Product bean.

    }
}
