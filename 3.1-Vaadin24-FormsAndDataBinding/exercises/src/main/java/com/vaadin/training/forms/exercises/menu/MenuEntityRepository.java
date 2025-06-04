package com.vaadin.training.forms.exercises.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuEntityRepository extends JpaRepository<MenuEntity, Long> {}
