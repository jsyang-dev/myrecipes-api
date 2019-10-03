package io.myrecipes.api.service;

import io.myrecipes.api.domain.MaterialEntity;
import io.myrecipes.api.domain.UnitEntity;
import io.myrecipes.api.dto.Material;
import io.myrecipes.api.dto.Unit;
import io.myrecipes.api.exception.DuplicateDataException;
import io.myrecipes.api.exception.NotExistDataException;
import io.myrecipes.api.repository.MaterialRepository;
import io.myrecipes.api.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BaseInfoServiceImpl implements BaseInfoService {
    private final MaterialRepository materialRepository;
    private final UnitRepository unitRepository;

    public BaseInfoServiceImpl(MaterialRepository materialRepository, UnitRepository unitRepository) {
        this.materialRepository = materialRepository;
        this.unitRepository = unitRepository;
    }

    @Override
    public Material readMaterial(int id) {
        Optional<MaterialEntity> materialEntityOptional = this.materialRepository.findById(id);

        if (!materialEntityOptional.isPresent()) {
            throw new NotExistDataException(MaterialEntity.class, id);
        }

        return materialEntityOptional.get().toDTO();
    }

    @Override
    public Material createMaterial(Material material, int userId) {
        MaterialEntity materialEntity = material.toEntity();
        materialEntity.setRegisterUserId(userId);

        Optional<UnitEntity> unitEntityOptional = this.unitRepository.findByName(material.getUnitName());
        if (!unitEntityOptional.isPresent()) {
            throw new NotExistDataException(UnitEntity.class, material.getUnitName());
        }

        materialEntity.setUnitEntity(unitEntityOptional.get());
        return this.materialRepository.save(materialEntity).toDTO();
    }

    @Override
    public Unit readUnit(String name) {
        Optional<UnitEntity> unitEntityOptional = this.unitRepository.findByName(name);
        if (!unitEntityOptional.isPresent()) {
            throw new NotExistDataException(UnitEntity.class, name);
        }

        return unitEntityOptional.get().toDTO();
    }

    @Override
    public Unit createUnit(Unit unit, int userId) {
        UnitEntity unitEntity = unit.toEntity();
        unitEntity.setRegisterUserId(userId);

        Optional<UnitEntity> unitEntityOptional = this.unitRepository.findByName(unitEntity.getName());
        if (unitEntityOptional.isPresent()) {
            throw new DuplicateDataException(UnitEntity.class, unitEntity.getName());
        }

        return this.unitRepository.save(unitEntity).toDTO();
    }
}