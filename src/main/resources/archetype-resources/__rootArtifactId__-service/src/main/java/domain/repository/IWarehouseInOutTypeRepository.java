package ${package}.domain.repository;

import ${package}.application.query.query.WarehouseInOutTypeQuery;
import ${package}.common.WarehouseType;
import ${package}.domain.model.WarehouseInOutTypeModel;
import ${package}.infrastructure.common.WarehouseOperationType;

import java.util.List;
import java.util.Map;

/**
 * 出入库类型
 */
public interface IWarehouseInOutTypeRepository {

    WarehouseInOutTypeModel findBy(String tenantId, Long id);

    WarehouseInOutTypeModel findBy(String tenantId, String code);

    List<WarehouseInOutTypeModel> findBy(String tenantId, WarehouseType warehouseType);

    List<WarehouseInOutTypeModel> findBy(String tenantId, WarehouseOperationType warehouseOperationType, WarehouseType warehouseType, Boolean isEnable);

    List<WarehouseInOutTypeModel> findBy(WarehouseInOutTypeQuery query);

    void save(WarehouseInOutTypeModel model);

    Map<String, String> findCodeToNameMap(String tenantId);

}
