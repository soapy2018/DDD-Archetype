package ${package}.adapter.web.dto.req.warehouseInOutType;

import ${package}.infrastructure.common.WarehouseOperationType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * 添加出入库类型
 * </p>
 *
 * @author wangxingyue
 * @since 2021-08-13
 */
@Data
public class WarehouseInOutTypeAddReq {

    /**
     * 仓库编号
     */
    @ApiModelProperty(value = "编号", name = "code", required = true)
    @Length(max = 64, message = "编号超出长度限制")
    private String code;

    /**
     * 仓库名称
     */
    @ApiModelProperty(value = "业务类型", name = "businessName", required = true)
    @Length(max = 64, message = "业务类型超出长度限制")
    private String businessName;

    /**
     * 操作类型
     */
    @ApiModelProperty(value = "操作类型", name = "operationType", required = true)
    private WarehouseOperationType operationType;

    /**
     * 适用仓库属性
     */
    @ApiModelProperty(value = "适用仓库属性", name = "warehouseType", required = true)
    @Length(max = 512, message = "适用仓库属性超出长度限制")
    private String warehouseType;

    /**
     * 说明
     */
    @ApiModelProperty(value = "说明", name = "description", required = true)
    @Length(max = 512, message = "说明超出长度限制")
    private String description;


}
