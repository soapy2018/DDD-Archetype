package ${package}.application.query.dto.warehouseInOutType;

import ${package}.infrastructure.common.WarehouseOperationType;
import lombok.Data;

import java.util.Date;

@Data
public class WarehouseInOutTypeDTO {
    private Long id;

    /**
     * 租户id
     */
    private String tenantId;

    /**
     * 仓库编号
     */
    private String code;

    /**
     * 业务类型
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
     * 适用仓库属性
     */
    private String warehouseTypeName;

    /**
     * 说明
     */
    private String description;

    /**
     * 是否系统默认
     */
    private Boolean isDefault;

    /**
     * 是否启用
     */
    private Boolean isEnable;

    private String createUsername;

    private String createRealname;

    private Date createTime;

    private String updateUsername;

    private String updateRealname;

    private Date updateTime;
}
