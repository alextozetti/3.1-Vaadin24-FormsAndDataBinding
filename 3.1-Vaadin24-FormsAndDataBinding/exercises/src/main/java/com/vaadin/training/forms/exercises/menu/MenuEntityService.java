package com.vaadin.training.forms.exercises.menu;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuEntityService {

    private final MenuEntityRepository repository;

    public List<MenuEntity> getMenus() {
        return repository.findAll();
    }

    public MenuEntity save(MenuEntity entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
