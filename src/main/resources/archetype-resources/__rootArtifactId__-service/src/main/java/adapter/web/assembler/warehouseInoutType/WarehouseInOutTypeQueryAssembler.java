package ${package}.adapter.web.assembler.warehouseInoutType;

import ${package}.adapter.web.assembler.IAssembler;
import ${package}.adapter.web.dto.req.warehouseInOutType.WarehouseInOutTypeQueryReq;
import ${package}.application.query.query.WarehouseInOutTypeQuery;
import com.bluetron.nb.common.base.dto.LoginUserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class WarehouseInOutTypeQueryAssembler implements IAssembler<WarehouseInOutTypeQueryReq, WarehouseInOutTypeQuery> {


    @Override
    public WarehouseInOutTypeQuery apply(LoginUserDTO loginUserDTO, WarehouseInOutTypeQueryReq warehouseInOutTypeQueryReq) {
        WarehouseInOutTypeQuery query = new WarehouseInOutTypeQuery();
        BeanUtils.copyProperties(warehouseInOutTypeQueryReq, query);
        query.setTenantId(loginUserDTO.getTenantId());
        return query;
    }
}
