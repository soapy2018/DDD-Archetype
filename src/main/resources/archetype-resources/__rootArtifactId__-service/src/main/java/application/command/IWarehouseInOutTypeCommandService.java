package ${package}.application.command;

import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeAddCommand;
import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeModifyCommand;
import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeModifyStatusCommand;

/**
 * 出入库类型的command service
 */
public interface IWarehouseInOutTypeCommandService {


    /**
     * 添加出入库类型
     *
     * @param command
     */
    void addWarehouseInOutType(WarehouseInOutTypeAddCommand command);

    /**
     * 启用出入库类型的状态
     *
     * @param command
     */
    void enableWarehouseInOutType(WarehouseInOutTypeModifyStatusCommand command);

    /**
     * 禁用出入库类型的状态
     *
     * @param command
     */
    void disableWarehouseInOutType(WarehouseInOutTypeModifyStatusCommand command);

    /**
     * 更新出入库类型
     *
     * @param command
     */
    void modifyWarehouseInOutType(WarehouseInOutTypeModifyCommand command);
}
