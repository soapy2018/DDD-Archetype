package ${package}.application.query.query;

import ${package}.infrastructure.common.WarehouseOperationType;
import lombok.Data;

import java.util.Date;

@Data
public class WarehouseInOutTypeQuery {

    private String tenantId;
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
