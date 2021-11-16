package ${package}.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import ${package}.infrastructure.common.WarehouseOperationType;
import com.bluetron.nb.common.base.entity.IBaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author wangxingyue
 * @since 2021-08-13
 */
@Data
@TableName(value = "warehouse_in_out_type")
public class WarehouseInOutTypeEntity implements IBaseEntity {


    @TableId(type = IdType.ASSIGN_ID)
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
