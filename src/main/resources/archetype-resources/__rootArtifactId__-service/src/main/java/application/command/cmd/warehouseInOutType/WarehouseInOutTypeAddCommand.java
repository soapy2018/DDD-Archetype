package ${package}.application.command.cmd.warehouseInOutType;

import ${package}.infrastructure.common.WarehouseOperationType;
import lombok.Data;

/**
 * 添加出入库类型
 */
@Data
public class WarehouseInOutTypeAddCommand {

    private String tenantId;

    /**
     * 仓库编号
     */
    private String code;

    /**
     * 仓库名称
     */
    private String businessName;

    /**
     * 操作类型
     */
    private WarehouseOperationType operationType;

    /**
     * 适用仓库属性
     */
    private String warehouseType;

    /**
     * 说明
     */
    private String description;


    private String createUsername;

    private String createRealname;


}
