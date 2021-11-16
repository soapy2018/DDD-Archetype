package ${package}.infrastructure.persistence.converter;

import ${package}.common.WarehouseType;
import ${package}.domain.model.WarehouseInOutTypeModel;
import ${package}.infrastructure.persistence.entity.WarehouseInOutTypeEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class WarehouseInOutTypeConverter implements IConverter<WarehouseInOutTypeEntity, WarehouseInOutTypeModel> {

    @Override
    public WarehouseInOutTypeModel deserialize(WarehouseInOutTypeEntity entity) {
        WarehouseInOutTypeModel model = new WarehouseInOutTypeModel();
        BeanUtils.copyProperties(entity, model);
        model.setWarehouseTypeSet(WarehouseType.parseFromCodeText(entity.getWarehouseType()));
        return model;
    }

    @Override
    public WarehouseInOutTypeEntity serialize(WarehouseInOutTypeModel domainObject) {
        WarehouseInOutTypeEntity entity = new WarehouseInOutTypeEntity();
        BeanUtils.copyProperties(domainObject, entity);
        entity.setWarehouseType(WarehouseType.formatToCodeText(domainObject.getWarehouseTypeSet()));
        return entity;
    }
}
