package link.myrecipes.api.service;

import link.myrecipes.api.domain.MaterialEntity;
import link.myrecipes.api.domain.UnitEntity;
import link.myrecipes.api.dto.Material;
import link.myrecipes.api.dto.request.MaterialRequest;
import link.myrecipes.api.exception.NotExistDataException;
import link.myrecipes.api.repository.MaterialRepository;
import link.myrecipes.api.repository.UnitRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MaterialServiceImplTest {

    private MaterialEntity materialEntity;
    private UnitEntity unitEntity;

    @InjectMocks
    private MaterialServiceImpl materialService;

    @Mock
    private MaterialRepository materialRepository;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private ModelMapper modelMapper;

    @Before
    public void setUp() {

        this.unitEntity = UnitEntity.builder()
                .name("kg")
                .exchangeUnitName("g")
                .exchangeQuantity(1000D)
                .build();

        this.materialEntity = MaterialEntity.builder()
                .name("재료")
                .registerUserId(1001)
                .modifyUserId(1001)
                .unitEntity(this.unitEntity)
                .build();
    }

    @Test
    public void When_존재하는_재료_조회_Then_정상_반환() {

        // Given
        Material material = makeMaterial(this.materialEntity);

        given(this.materialRepository.findById(10)).willReturn(Optional.ofNullable(this.materialEntity));
        given(this.modelMapper.map(any(MaterialEntity.class), eq(Material.class))).willReturn(material);

        // When
        final Material readMaterial = this.materialService.readMaterial(10);

        // Then
        assertThat(readMaterial, instanceOf(Material.class));
        assertThat(readMaterial.getName(), is(this.materialEntity.getName()));
        assertThat(readMaterial.getUnitName(), is(this.materialEntity.getUnitEntity().getName()));
    }

    @Test(expected = NotExistDataException.class)
    public void When_존재하지_않는_재료_조회_Then_예외_발생() {

        // When
        this.materialService.readMaterial(11);
    }

    @Test
    public void When_재료_리스트_조회_Then_정상_반환() {

        // Given
        Page<MaterialEntity> materialEntityPage = new PageImpl<>(Collections.singletonList(this.materialEntity));
        Material material = makeMaterial(this.materialEntity);

        given(this.materialRepository.findAll(any(Pageable.class))).willReturn(materialEntityPage);
        given(this.modelMapper.map(any(MaterialEntity.class), eq(Material.class))).willReturn(material);

        // When
        final Page<Material> materialPage = this.materialService.readMaterialList(PageRequest.of(0, 10));

        // Then
        assertThat(materialPage.getTotalElements(), is(1L));
        assertThat(materialPage.getContent().get(0), instanceOf(Material.class));
        assertThat(materialPage.getContent().get(0).getName(), is(this.materialEntity.getName()));
        assertThat(materialPage.getContent().get(0).getUnitName(), is(this.materialEntity.getUnitEntity().getName()));
    }

    @Test
    public void When_존재하는_단위로_재료_저장_Then_정상_반환() {

        // Given
        MaterialRequest materialRequest = makeMaterialRequest(this.materialEntity);
        Material material = makeMaterial(this.materialEntity);

        given(this.unitRepository.findByName(this.unitEntity.getName())).willReturn(Optional.ofNullable(this.unitEntity));
        given(this.materialRepository.save(any(MaterialEntity.class))).willReturn(this.materialEntity);
        given(this.modelMapper.map(any(MaterialRequest.class), eq(MaterialEntity.class))).willReturn(this.materialEntity);
        given(this.modelMapper.map(any(MaterialEntity.class), eq(Material.class))).willReturn(material);

        // When
        final Material savedMaterial = this.materialService.createMaterial(materialRequest, 10001);

        // Then
        assertThat(savedMaterial, instanceOf(Material.class));
        assertThat(savedMaterial.getName(), is(this.materialEntity.getName()));
        assertThat(savedMaterial.getUnitName(), is(this.materialEntity.getUnitEntity().getName()));
    }

    @Test(expected = NotExistDataException.class)
    public void When_존재하는_않는_단위로_재료_저장_Then_예외_발생() {

        // Given
        MaterialRequest materialRequest = makeMaterialRequest(this.materialEntity);

        given(this.modelMapper.map(any(MaterialRequest.class), eq(MaterialEntity.class))).willReturn(this.materialEntity);

        // When
        this.materialService.createMaterial(materialRequest, 10001);
    }

    private MaterialRequest makeMaterialRequest(MaterialEntity materialEntity) {
        return MaterialRequest.builder()
                .name(materialEntity.getName())
                .unitName(materialEntity.getUnitEntity().getName())
                .build();
    }

    private Material makeMaterial(MaterialEntity materialEntity) {
        return Material.builder()
                .id(materialEntity.getId())
                .name(materialEntity.getName())
                .unitName(materialEntity.getUnitEntity().getName())
                .build();
    }
}