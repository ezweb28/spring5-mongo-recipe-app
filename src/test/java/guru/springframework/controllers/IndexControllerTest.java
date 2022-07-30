package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.thymeleaf.spring5.ISpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by jt on 6/17/17.
 */
//@RunWith(SpringRunner.class)
//@WebFluxTest
//@Import(IndexController.class)
@Ignore
public class IndexControllerTest {

    WebTestClient webTestClient;

    @Autowired
    ApplicationContext applicationContext;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController controller;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new IndexController(recipeService);

        webTestClient = WebTestClient.bindToController(controller).build();

    }

    @Test
    public void testMockMVC() throws Exception {
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(recipeService.getRecipes()).thenReturn(Flux.empty());

//        mockMvc.perform(get("/"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("index"));

        webTestClient.get().uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody();
    }

    @Test
    public void getIndexPage() throws Exception {

//        //given
//        Set<Recipe> recipes = new HashSet<>();
//        recipes.add(new Recipe());
//
//        Recipe recipe = new Recipe();
//        recipe.setId("1");
//
//        recipes.add(recipe);
//
//        when(recipeService.getRecipes()).thenReturn(Flux.fromIterable(recipes));
//
//        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);
//
//        //when
//        String viewName = controller.getIndexPage(model);
//
//
//        //then
//        assertEquals("index", viewName);
//        verify(recipeService, times(1)).getRecipes();
//        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
////        Flux<Recipe> fluxInController = argumentCaptor.getValue();
////        List<Recipe> recipeList = fluxInController.collectList().block();
//        List<Recipe> recipeList = argumentCaptor.getValue();
//        assertEquals(2, recipeList.size());
    }

    @Configuration
    static class WebConfig {

        @Bean
        public SpringResourceTemplateResolver defaultTemplateResolver(ApplicationContext applicationContext) {
            SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
            resolver.setApplicationContext(applicationContext);
            resolver.setPrefix("classpath:/template/");
            resolver.setSuffix(".html");
            resolver.setTemplateMode(TemplateMode.HTML);
            resolver.setCharacterEncoding("UTF-8");
            resolver.setCacheable(false);
            resolver.setCheckExistence(true);
            return resolver;
        }

        @Bean
        public SpringWebFluxTemplateEngine templateEngine(SpringResourceTemplateResolver defaultTemplateResolver) {
            SpringWebFluxTemplateEngine engine = new SpringWebFluxTemplateEngine();
            engine.addTemplateResolver(defaultTemplateResolver);
            return engine;
        }

        @Bean
        public ThymeleafReactiveViewResolver thymeleafReactiveViewResolver(final ISpringWebFluxTemplateEngine templateEngine) {
            final ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
            viewResolver.setTemplateEngine(templateEngine);
            return viewResolver;
        }
    }

}