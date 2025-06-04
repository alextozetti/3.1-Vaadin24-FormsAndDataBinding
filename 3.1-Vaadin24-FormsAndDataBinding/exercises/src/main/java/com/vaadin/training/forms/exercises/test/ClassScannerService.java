package com.vaadin.training.forms.exercises.test;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.Route;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

@Service
public class ClassScannerService implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public List<Class<? extends Component>> findRouteClasses() {
        // Define o pacote base para o escaneamento
        // É CRUCIAL que este pacote (ou uma lista de pacotes) inclua onde suas Views estão.
        // Exemplo: "com.vaadin.training.forms.exercises"
        String basePackage = "com.vaadin.training.forms.exercises";

        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false); // false para não incluir classes anotadas com @Component

        // Adiciona um filtro para incluir apenas classes anotadas com @Route
        scanner.addIncludeFilter(new AnnotationTypeFilter(Route.class));

        Set<BeanDefinition> candidates = scanner.findCandidateComponents(basePackage);

        return candidates.stream()
                .map(beanDefinition -> {
                    try {
                        // Carrega a classe
                        Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                        // Verifica se é um Component do Vaadin e tem a anotação Route
                        if (Component.class.isAssignableFrom(clazz) && clazz.isAnnotationPresent(Route.class)) {
                            return (Class<? extends Component>) clazz;
                        }
                    } catch (ClassNotFoundException e) {
                        System.err.println("Classe não encontrada: " + beanDefinition.getBeanClassName());
                    }
                    return null;
                })
                .filter(java.util.Objects::nonNull) // Remove nulos
                .sorted(Comparator.comparing(Class::getName)) // Opcional: ordena por nome
                .collect(Collectors.toList());
    }
}
