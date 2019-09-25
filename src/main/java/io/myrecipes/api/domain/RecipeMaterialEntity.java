package io.myrecipes.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "recipe_material")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@JsonIgnoreProperties("hibernateLazyInitializer")
public class RecipeMaterialEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipeEntity;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private MaterialEntity materialEntity;

    @Builder
    public RecipeMaterialEntity(Integer quantity, RecipeEntity recipeEntity, MaterialEntity materialEntity) {
        this.quantity = quantity;
        this.recipeEntity = recipeEntity;
        this.materialEntity = materialEntity;
    }
}