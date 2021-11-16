package ${package}.application.command.impl;

import ${package}.application.command.IWarehouseInOutTypeCommandService;
import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeAddCommand;
import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeModifyCommand;
import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeModifyStatusCommand;
import ${package}.domain.model.WarehouseInOutTypeModel;
import ${package}.domain.repository.IWarehouseInOutTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 出入库类型的command实现类
 */
@Service
public class WarehouseInOutTypeCommandServiceImpl implements IWarehouseInOutTypeCommandService {

    @Autowired
    private IWarehouseInOutTypeRepository warehouseInOutTypeRepository;

    @Override
    public void addWarehouseInOutType(WarehouseInOutTypeAddCommand command) {
        WarehouseInOutTypeModel model = WarehouseInOutTypeModel.create(command);
        warehouseInOutTypeRepository.save(model);
        command.setCode(model.getCode());
    }


    @Override
    public void enableWarehouseInOutType(WarehouseInOutTypeModifyStatusCommand command) {
        WarehouseInOutTypeModel model = warehouseInOutTypeRepository.findBy(command.getTenantId(), command.getId());
        model.enable(command);
        warehouseInOutTypeRepository.save(model);
    }

    @Override
    public void disableWarehouseInOutType(WarehouseInOutTypeModifyStatusCommand command) {
        WarehouseInOutTypeModel model = warehouseInOutTypeRepository.findBy(command.getTenantId(), command.getId());
        model.disable(command);
        warehouseInOutTypeRepository.save(model);
    }


    @Override
    public void modifyWarehouseInOutType(WarehouseInOutTypeModifyCommand command) {
        WarehouseInOutTypeModel model = warehouseInOutTypeRepository.findBy(command.getTenantId(), command.getId());
        model.modify(command);
        warehouseInOutTypeRepository.save(model);
    }
}
