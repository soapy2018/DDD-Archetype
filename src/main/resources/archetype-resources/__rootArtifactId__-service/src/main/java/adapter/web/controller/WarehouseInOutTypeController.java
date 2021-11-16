package ${package}.adapter.web.controller;

import ${package}.adapter.web.assembler.warehouseInoutType.WarehouseInOutTypeAddAssembler;
import ${package}.adapter.web.assembler.warehouseInoutType.WarehouseInOutTypeModifyAssembler;
import ${package}.adapter.web.assembler.warehouseInoutType.WarehouseInOutTypeModifyStatusAssembler;
import ${package}.adapter.web.assembler.warehouseInoutType.WarehouseInOutTypeQueryAssembler;
import ${package}.adapter.web.dto.req.warehouseInOutType.WarehouseInOutTypeAddReq;
import ${package}.adapter.web.dto.req.warehouseInOutType.WarehouseInOutTypeModifyReq;
import ${package}.adapter.web.dto.req.warehouseInOutType.WarehouseInOutTypeQueryReq;
import ${package}.application.command.IWarehouseInOutTypeCommandService;
import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeAddCommand;
import ${package}.application.command.cmd.warehouseInOutType.WarehouseInOutTypeModifyCommand;
import ${package}.application.query.IWarehouseInOutTypeQueryService;
import ${package}.application.query.dto.warehouseInOutType.WarehouseInOutTypeDTO;
import ${package}.application.query.query.WarehouseInOutTypeQuery;
import com.bluetron.nb.common.base.controller.BaseController;
import com.bluetron.nb.common.base.dto.LoginUserDTO;
import com.bluetron.nb.common.base.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 出入库类型
 */
@RestController
@RequestMapping("/api/v1/warehouse-in-out-type")
@Api(value = "出入库类型", tags = {"出入库类型"})
public class WarehouseInOutTypeController extends BaseController {

    @Autowired
    private IWarehouseInOutTypeQueryService warehouseInOutTypeQueryService;

    @Autowired
    private IWarehouseInOutTypeCommandService warehouseInOutTypeCommandService;

    @Autowired
    private WarehouseInOutTypeQueryAssembler warehouseInOutTypeQueryAssembler;

    @Autowired
    private WarehouseInOutTypeAddAssembler warehouseInOutTypeAddAssembler;

    @Autowired
    private WarehouseInOutTypeModifyAssembler warehouseInOutTypeModifyAssembler;

    @Autowired
    private WarehouseInOutTypeModifyStatusAssembler warehouseInOutTypeModifyStatusAssembler;

    @GetMapping("")
    @ApiOperation(value = "查询所有的出入库类型")
    public Result<List<WarehouseInOutTypeDTO>> queryAll(@ApiIgnore LoginUserDTO loginUserDTO, WarehouseInOutTypeQueryReq queryReq) {
        WarehouseInOutTypeQuery query = warehouseInOutTypeQueryAssembler.apply(loginUserDTO, queryReq);
        return success(warehouseInOutTypeQueryService.findBy(query));
    }

    @PostMapping("")
    @ApiOperation(value = "添加所有的出入库类型")
    public Result addWarehouseInOutType(@ApiIgnore LoginUserDTO loginUserDTO, @RequestBody WarehouseInOutTypeAddReq addReq) {
        WarehouseInOutTypeAddCommand command = warehouseInOutTypeAddAssembler.apply(loginUserDTO, addReq);
        warehouseInOutTypeCommandService.addWarehouseInOutType(command);
        return success(warehouseInOutTypeQueryService.findBy(loginUserDTO.getTenantId(), command.getCode()));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改所有的出入库类型")
    public Result modifyWarehouseInOutType(@ApiIgnore LoginUserDTO loginUserDTO, @PathVariable(name = "id") Long id, @RequestBody WarehouseInOutTypeModifyReq modifyReq) {
        modifyReq.setId(id);
        WarehouseInOutTypeModifyCommand command = warehouseInOutTypeModifyAssembler.apply(loginUserDTO, modifyReq);
        warehouseInOutTypeCommandService.modifyWarehouseInOutType(command);
        return success();
    }


    @GetMapping("/code/{code}")
    @ApiOperation(value = "查询某个出入库类型的详情")
    public Result getWarehouseInOutTypeInfo(@ApiIgnore LoginUserDTO loginUserDTO, @PathVariable(name = "code") String code) {
        return success(warehouseInOutTypeQueryService.findBy(loginUserDTO.getTenantId(), code));
    }


}
