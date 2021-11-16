//package cn.bluetron.nb.autoparts.warehouse.adapter.mobile.controller;
//
//import cn.bluetron.nb.autoparts.app.common.controller.BaseController;
//import cn.bluetron.nb.autoparts.gateway.dto.LoginPersonnelDTO;
//import cn.bluetron.nb.autoparts.warehouse.adapter.web.assembler.WarehouseAssembler;
//import cn.bluetron.nb.autoparts.warehouse.adapter.web.dto.res.warehouse.WarehouseDetailRes;
//import cn.bluetron.nb.autoparts.warehouse.application.query.IWarehouseQueryService;
//import cn.bluetron.nb.autoparts.warehouse.constant.WarehouseType;
//import cn.bluetron.nb.autoparts.warehouse.domain.model.WarehouseModel;
//import cn.bluetron.nb.autoparts.warehouse.domain.repository.IWarehouseLocationRepository;
//import com.bluetron.app.common.response.ApiResponse;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import springfox.documentation.annotations.ApiIgnore;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * 仓库
// *
// * @author Jelas
// * @date 2021/8/11 8:50
// */
//@RestController
//@RequestMapping("/mobile/warehouse/api/v1/warehouse")
//@Api(value = "移动端-仓库", tags = {"移动端-仓库"})
//public class MobileWarehouseController extends BaseController {
//
//    @Autowired
//    private IWarehouseQueryService warehouseQueryService;
//
//    @Autowired
//    private IWarehouseLocationRepository warehouseLocationRepository;
//
//    @ApiOperation(value = "仓库搜索")
//    @GetMapping("/search")
//    public ApiResponse<List<WarehouseDetailRes>> search(
//            @ApiIgnore LoginPersonnelDTO loginPersonnelDTO,
//            @RequestParam(required = false) @ApiParam(value = "关键词") String key,
//            @RequestParam(required = false) @ApiParam(value = "是否启用") Boolean isEnabled,
//            @RequestParam(required = false) @ApiParam(value = "仓库属性") WarehouseType warehouseType,
//            @RequestParam(required = false) @ApiParam(value = "需要查询的ID(会放在返回数据的第一条)") Long id
//    ) {
//        List<WarehouseModel> warehouseModelList = warehouseQueryService.mobileSearch(loginPersonnelDTO.getTenantId(), key, isEnabled, warehouseType, id);
//        Map<Long, Integer> quantityMap = warehouseLocationRepository.getWarehouseLocationQuantity(loginPersonnelDTO.getTenantId());
//        return success(WarehouseAssembler.convert2DetailResList(warehouseModelList, quantityMap));
//    }
//
//}
