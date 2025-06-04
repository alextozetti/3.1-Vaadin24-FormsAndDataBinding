package com.vaadin.training.forms.exercises.menu;

import com.vaadin.flow.component.Component;
import lombok.Data;

import java.util.List;


@Data
public class MenuItem {
    private String title;
    private Class<? extends Component> targetClass;
    private List<MenuItem> subItems;

    public MenuItem(String title, Class<? extends Component> targetClass) {
        this.title = title;
        this.targetClass = targetClass;
    }

    public MenuItem(String title, List<MenuItem> subItems) {
        this.title = title;
        this.subItems = subItems;
    }

    public boolean hasSubItems() {
        return subItems != null && !subItems.isEmpty();
    }

    public boolean isLink() {
        return targetClass != null;
    }
}
