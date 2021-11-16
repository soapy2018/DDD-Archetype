package ${package}.adapter.web.assembler.warehouseInoutType;

import ${package}.adapter.web.assembler.IAssembler;
import ${package}.adapter.web.dto.req.warehouseInOutType.WarehouseInOutTypeModifyReq;
import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeModifyCommand;
import com.bluetron.nb.common.base.dto.LoginUserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class WarehouseInOutTypeModifyAssembler implements IAssembler<WarehouseInOutTypeModifyReq, WarehouseInOutTypeModifyCommand> {

    @Override
    public WarehouseInOutTypeModifyCommand apply(LoginUserDTO loginUserDTO, WarehouseInOutTypeModifyReq warehouseInOutTypeUpdateReq) {
        WarehouseInOutTypeModifyCommand command = new WarehouseInOutTypeModifyCommand();
        BeanUtils.copyProperties(warehouseInOutTypeUpdateReq, command);
        command.setTenantId(loginUserDTO.getTenantId());
        command.setUpdateRealname(loginUserDTO.getRealname());
        command.setUpdateUsername(loginUserDTO.getUsername());
        return command;
    }
}
