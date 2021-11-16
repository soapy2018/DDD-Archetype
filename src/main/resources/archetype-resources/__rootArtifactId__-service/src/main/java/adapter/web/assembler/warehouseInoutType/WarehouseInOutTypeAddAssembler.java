package ${package}.adapter.web.assembler.warehouseInoutType;

import ${package}.adapter.web.assembler.IAssembler;
import ${package}.adapter.web.dto.req.warehouseInOutType.WarehouseInOutTypeAddReq;
import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeAddCommand;
import com.bluetron.nb.common.base.dto.LoginUserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class WarehouseInOutTypeAddAssembler implements IAssembler<WarehouseInOutTypeAddReq, WarehouseInOutTypeAddCommand> {
    @Override
    public WarehouseInOutTypeAddCommand apply(LoginUserDTO loginUserDTO, WarehouseInOutTypeAddReq warehouseInOutTypeAddReq) {
        WarehouseInOutTypeAddCommand command = new WarehouseInOutTypeAddCommand();
        BeanUtils.copyProperties(warehouseInOutTypeAddReq, command);
        command.setTenantId(loginUserDTO.getTenantId());
        command.setCreateRealname(loginUserDTO.getRealname());
        command.setCreateUsername(loginUserDTO.getUsername());
        return command;
    }
}
