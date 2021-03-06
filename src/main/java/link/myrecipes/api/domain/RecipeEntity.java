package link.myrecipes.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"recipeMaterialEntityList", "recipeStepEntityList", "recipeTagEntityList"})
@JsonIgnoreProperties("hibernateLazyInitializer")
public class RecipeEntity extends BaseEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private Integer estimatedTime;

    @Column(nullable = false)
    @Max(5)
    @Min(1)
    private Integer difficulty;

    @Column(nullable = false)
    private Integer people;

    @Column(nullable = false)
    private Integer readCount;

    @OneToMany(mappedBy = "recipeEntity", cascade = CascadeType.ALL)
    private List<RecipeMaterialEntity> recipeMaterialEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "recipeEntity", cascade = CascadeType.ALL)
    private List<RecipeStepEntity> recipeStepEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "recipeEntity", cascade = CascadeType.ALL)
    private List<RecipeTagEntity> recipeTagEntityList = new ArrayList<>();

    @Builder
    public RecipeEntity(String title, String image, Integer estimatedTime, Integer difficulty, Integer people,
                        Integer registerUserId, Integer modifyUserId) {
        this.title = title;
        this.image = image;
        this.estimatedTime = estimatedTime;
        this.difficulty = difficulty;
        this.people = people;
        this.registerUserId = registerUserId;
        this.modifyUserId = modifyUserId;
    }

    @PrePersist
    public void prePersist() {
        this.readCount = (this.readCount == null ? 0 : this.readCount);
    }

    public void addRecipeMaterial(RecipeMaterialEntity recipeMaterialEntity) {
        this.recipeMaterialEntityList.add(recipeMaterialEntity);
    }

    public void addRecipeStep(RecipeStepEntity recipeStepEntity) {
        this.recipeStepEntityList.add(recipeStepEntity);
    }

    public void addRecipeTag(RecipeTagEntity recipeTagEntity) {
        this.recipeTagEntityList.add(recipeTagEntity);
    }

    public void clearRecipeMaterialEntityList() {
        this.recipeMaterialEntityList.clear();
    }

    public void clearRecipeStepEntityList() {
        this.recipeStepEntityList.clear();
    }

    public void clearRecipeTagEntityList() {
        this.recipeTagEntityList.clear();
    }

    public void update(RecipeEntity recipeEntity, int userId) {
        this.title = recipeEntity.getTitle();
        this.image = recipeEntity.getImage();
        this.estimatedTime = recipeEntity.getEstimatedTime();
        this.difficulty = recipeEntity.getDifficulty();
        this.people = recipeEntity.getPeople();
        this.modifyUserId = userId;
    }
}