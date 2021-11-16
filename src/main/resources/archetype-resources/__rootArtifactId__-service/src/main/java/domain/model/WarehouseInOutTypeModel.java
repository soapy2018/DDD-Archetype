package ${package}.domain.model;

import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeAddCommand;
import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeModifyCommand;
import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeModifyStatusCommand;
import ${package}.common.WarehouseType;
import ${package}.infrastructure.common.WarehouseOperationType;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Set;

/**
 * 出入库类型的领域对象
 */
@Data
public class WarehouseInOutTypeModel {

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
    private Set<WarehouseType> warehouseTypeSet;

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


    public static final WarehouseInOutTypeModel create(WarehouseInOutTypeAddCommand command) {
        WarehouseInOutTypeModel model = new WarehouseInOutTypeModel();
        BeanUtils.copyProperties(command, model);
        model.setWarehouseTypeSet(WarehouseType.parseFromCodeText(command.getWarehouseType()));
        model.setIsDefault(false);
        model.setIsEnable(true);
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        model.setUpdateRealname(command.getCreateRealname());
        model.setUpdateUsername(command.getCreateUsername());
        return model;
    }

    public void modify(WarehouseInOutTypeModifyCommand command) {
        this.updateRealname = command.getUpdateRealname();
        this.updateUsername = command.getUpdateUsername();
        this.setUpdateTime(new Date());
        this.setBusinessName(command.getBusinessName());
        this.setDescription(command.getDescription());
        this.setOperationType(command.getOperationType());
        this.setWarehouseTypeSet(WarehouseType.parseFromCodeText(command.getWarehouseType()));
    }

    public void enable(WarehouseInOutTypeModifyStatusCommand command) {
        this.updateRealname = command.getUpdateRealname();
        this.updateUsername = command.getUpdateUsername();
        this.setUpdateTime(new Date());
        this.setIsEnable(true);
    }

    public void disable(WarehouseInOutTypeModifyStatusCommand command) {
        this.updateRealname = command.getUpdateRealname();
        this.updateUsername = command.getUpdateUsername();
        this.setUpdateTime(new Date());
        this.setIsEnable(false);
    }
}
