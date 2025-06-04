DROP TABLE menu_entity;

CREATE TABLE menu_entity (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             nome VARCHAR(255),
                             categoria VARCHAR(255),
                             parent_id BIGINT,
                             rota VARCHAR(255),
                             rota_class VARCHAR(255),
                             link_image VARCHAR(255)
);

INSERT INTO menu_entity (nome, categoria, parent_id, rota, rota_class, link_image) VALUES ('Relatórios', 'reports', NULL, 'ex1', 'com.vaadin.training.forms.exercises.ex1.Validation', 'icons/report.png');
INSERT INTO menu_entity (nome, categoria, parent_id, rota, rota_class, link_image) VALUES ('Relatório de Estoque', 'reports', 1, NULL, NULL, NULL);
INSERT INTO menu_entity (nome, categoria, parent_id, rota, rota_class, link_image) VALUES ('Estoque Atual', 'reports', 2, 'ex1', 'com.vaadin.training.forms.exercises.ex2.BindingForms', NULL);
INSERT INTO menu_entity (nome, categoria, parent_id, rota, rota_class, link_image) VALUES ('Test UI', 'reports', 2, 'accordion', 'com.vaadin.training.forms.exercises.ex1.ExampleAccordion', NULL);
INSERT INTO menu_entity (nome, categoria, parent_id, rota, rota_class, link_image) VALUES ('Usuários', 'settings', NULL, NULL, NULL, 'icons/help-desk.png');
INSERT INTO menu_entity (nome, categoria, parent_id, rota, rota_class, link_image) VALUES ('Listar Usuários', 'settings', 5, 'dashboard', 'com.vaadin.training.forms.exercises.test.ProductViewer', NULL);
INSERT INTO menu_entity (nome, categoria, parent_id, rota, rota_class, link_image) VALUES ('Adicionar Usuário', 'settings', 5, 'about', 'com.vaadin.training.forms.exercises.test.BindingForms', NULL);
INSERT INTO menu_entity (nome, categoria, parent_id, rota, rota_class, link_image) VALUES ('Permissões', 'reports', 3, 'ex1', 'com.vaadin.training.forms.exercises.ex1.Validation', NULL);
INSERT INTO menu_entity (nome, categoria, parent_id, rota, rota_class, link_image) VALUES ('Exemplo Menus Dinâmicos', 'reports', 1, 'simple-menu', 'com.vaadin.training.forms.exercises.ex1.SimpleMenuView', NULL);
INSERT INTO menu_entity (nome, categoria, parent_id, rota, rota_class, link_image) VALUES ('Menu do Database', 'reports', 1, 'dinamic-menu', 'com.vaadin.training.forms.exercises.ex1.MenuDinamicoView', NULL);
