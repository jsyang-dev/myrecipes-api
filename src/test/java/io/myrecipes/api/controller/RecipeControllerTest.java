package io.myrecipes.api.controller;

import io.myrecipes.api.domain.Recipe;
import io.myrecipes.api.service.RecipeServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeServiceImpl recipeService;

    @Test
    public void Should_첫페이지_조회_When_쿼리스트링_없이_레시피_페이지_호출() throws Exception {
        Recipe recipe = new Recipe("test1", "test1.jpg", 30, "1", 1001);
        given(this.recipeService.readRecipePageSortedByParam(eq(0), eq(10), eq("registerDate"), eq(false))).willReturn(Collections.singletonList(recipe));

        final ResultActions actions = this.mockMvc.perform(get("/recipes"));

        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

}