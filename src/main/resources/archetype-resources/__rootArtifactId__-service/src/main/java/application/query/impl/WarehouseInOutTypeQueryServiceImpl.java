package ${package}.application.query.impl;

import ${package}.application.assembler.WarehouseInOutDTOAssembler;
import ${package}.application.assembler.WarehouseInOutDetailDTOAssembler;
import ${package}.application.query.IWarehouseInOutTypeQueryService;
import ${package}.application.query.dto.warehouseInOutType.WarehouseInOutTypeDTO;
import ${package}.application.query.query.WarehouseInOutTypeQuery;
import ${package}.common.WarehouseType;
import ${package}.domain.model.WarehouseInOutTypeModel;
import ${package}.domain.repository.IWarehouseInOutTypeRepository;
import ${package}.infrastructure.common.WarehouseOperationType;
import com.bluetron.nb.common.base.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 出入库类型查询实现类
 */
@Service
@Slf4j
public class WarehouseInOutTypeQueryServiceImpl implements IWarehouseInOutTypeQueryService {


    @Autowired
    private IWarehouseInOutTypeRepository warehouseInOutTypeRepository;
    @Autowired
    private WarehouseInOutDTOAssembler warehouseInOutDTOAssembler;
    @Autowired
    private WarehouseInOutDetailDTOAssembler warehouseInOutDetailDTOAssembler;


    @Override
    public List<WarehouseInOutTypeDTO> findBy(WarehouseInOutTypeQuery query) {
        List<WarehouseInOutTypeModel> modelList = warehouseInOutTypeRepository.findBy(query);
        return modelList.stream().map(warehouseInOutDetailDTOAssembler::apply).collect(Collectors.toList());
    }

    @Override
    public List<WarehouseInOutTypeDTO> findBy(String tenantId, WarehouseOperationType warehouseOperationType, WarehouseType warehouseType, Boolean isEnable) {
        List<WarehouseInOutTypeModel> modelList = warehouseInOutTypeRepository.findBy(tenantId, warehouseOperationType, warehouseType, isEnable);
        return modelList.stream().map(warehouseInOutDetailDTOAssembler::apply).collect(Collectors.toList());
    }

    @Override
    public WarehouseInOutTypeDTO findBy(String tenantId, String code) {
        WarehouseInOutTypeModel model = warehouseInOutTypeRepository.findBy(tenantId, code);
        if (model == null) {
            throw new GeneralException("未找到出入库信息");
        }
        return warehouseInOutDetailDTOAssembler.apply(model);
    }
}
