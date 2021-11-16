package ${package}.application.query;

import ${package}.application.query.dto.warehouseInOutType.WarehouseInOutTypeDTO;
import ${package}.application.query.query.WarehouseInOutTypeQuery;
import ${package}.common.WarehouseType;
import ${package}.infrastructure.common.WarehouseOperationType;

import java.util.List;

/**
 * 出入库类型查询
 */
public interface IWarehouseInOutTypeQueryService {

    List<WarehouseInOutTypeDTO> findBy(WarehouseInOutTypeQuery query);

    List<WarehouseInOutTypeDTO> findBy(String tenantId, WarehouseOperationType warehouseOperationType, WarehouseType warehouseType, Boolean isEnable);

    WarehouseInOutTypeDTO findBy(String tenantId, String code);

}
