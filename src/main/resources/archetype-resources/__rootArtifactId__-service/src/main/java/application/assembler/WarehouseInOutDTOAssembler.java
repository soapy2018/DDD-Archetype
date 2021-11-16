package ${package}.application.assembler;

import ${package}.application.query.dto.warehouseInOutType.WarehouseInOutTypeDTO;
import ${package}.common.WarehouseType;
import ${package}.domain.model.WarehouseInOutTypeModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class WarehouseInOutDTOAssembler implements Function<WarehouseInOutTypeModel, WarehouseInOutTypeDTO> {
    @Override
    public WarehouseInOutTypeDTO apply(WarehouseInOutTypeModel warehouseInOutTypeModel) {
        WarehouseInOutTypeDTO warehouseInOutDTO = new WarehouseInOutTypeDTO();
        BeanUtils.copyProperties(warehouseInOutTypeModel, warehouseInOutDTO);
        warehouseInOutDTO.setWarehouseType(WarehouseType.formatToNameText(warehouseInOutTypeModel.getWarehouseTypeSet()));
        return warehouseInOutDTO;
    }
}
