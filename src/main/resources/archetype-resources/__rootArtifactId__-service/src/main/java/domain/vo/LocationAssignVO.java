package ${package}.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author genx
 * @date 2021/8/16 14:55
 */
@Getter
@Setter
public class LocationAssignVO {

    /**
     * 库区编码
     */
    @ApiModelProperty(value = "库区编码", name = "areaCode", required = true)
    private String areaCode;

    /**
     * 库位编号
     */
    @ApiModelProperty(value = "库位编号", name = "locationCode", required = true)
    private String locationCode;

    /**
     * 分配数量
     */
    @ApiModelProperty(value = "分配数量", name = "num", required = true)
    private BigDecimal num;

}
