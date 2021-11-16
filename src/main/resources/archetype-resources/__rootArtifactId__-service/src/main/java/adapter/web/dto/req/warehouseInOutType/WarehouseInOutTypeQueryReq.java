package ${package}.adapter.web.dto.req.warehouseInOutType;

import ${package}.infrastructure.common.WarehouseOperationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class WarehouseInOutTypeQueryReq {
    private WarehouseOperationType operationType;
    private String businessType;
    private Boolean isEnable;
    private String createRealname;
    private Date createTimeStart;
    private Date createTimeEnd;
    private String updateRealname;
    private Date updateTimeStart;
    private Date updateTimeEnd;
}
