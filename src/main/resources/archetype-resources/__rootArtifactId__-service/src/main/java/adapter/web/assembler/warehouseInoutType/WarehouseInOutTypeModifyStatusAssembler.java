package ${package}.adapter.web.assembler.warehouseInoutType;

import ${package}.adapter.web.assembler.IAssembler;
import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeModifyStatusCommand;
import com.bluetron.nb.common.base.dto.LoginUserDTO;
import org.springframework.stereotype.Component;

@Component
public class WarehouseInOutTypeModifyStatusAssembler implements IAssembler<Long, WarehouseInOutTypeModifyStatusCommand> {

    @Override
    public WarehouseInOutTypeModifyStatusCommand apply(LoginUserDTO loginUserDTO, Long id) {
        WarehouseInOutTypeModifyStatusCommand command = new WarehouseInOutTypeModifyStatusCommand();
        command.setTenantId(loginUserDTO.getTenantId());
        command.setId(id);
        command.setUpdateRealname(loginUserDTO.getRealname());
        command.setUpdateUsername(loginUserDTO.getUsername());
        return command;
    }
}
