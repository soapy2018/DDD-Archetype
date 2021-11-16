package ${package}.application.command.cmd.warehouseInOutType;

import lombok.Data;

/**
 * 添加出入库类型
 */
@Data
public class WarehouseInOutTypeModifyStatusCommand {

    private String tenantId;

    private Long id;

    private String updateUsername;

    private String updateRealname;
}
