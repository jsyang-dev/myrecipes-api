package link.myrecipes.api.dto.view;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class RecipeStepView implements Serializable {

    private Integer step;

    private String content;

    private String image;

    @Builder
    public RecipeStepView(Integer step, String content, String image) {

        this.step = step;
        this.content = content;
        this.image = image;
    }
}
