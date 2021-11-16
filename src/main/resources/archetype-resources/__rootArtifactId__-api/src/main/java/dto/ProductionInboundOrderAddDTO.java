package ${package}.dto;

import lombok.Data;


/**
 * 新增生产入库单
 *
 * @author linguang.lv
 * @date 2021-08-17
 */
@Data
public class ProductionInboundOrderAddDTO {

    /**
     * 仓库ID
     */
    private Long warehouseId;

    /**
     * 生产工单编码/工单号
     */
    private String workOrderCode;

    /**
     * 报工id
     */
    private Long workOrderReportId;

    /**
     * 报工工序
     */
    private String workOperationName;

    /**
     * 报工方式（1累计报工，2累计刷新）
     */
    private Integer workOrderReportType;

    /**
     * 报工数量
     */
    private Integer number;

    /**
     * 合格数量
     */
    private Integer qualifiedNumber;

    /**
     * 产品ID
     */
    private Long productId;

    /**
     * 批次号
     */
    private String lotNum;

}
