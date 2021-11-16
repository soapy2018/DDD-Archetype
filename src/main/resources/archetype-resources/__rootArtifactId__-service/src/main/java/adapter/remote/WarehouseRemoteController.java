//package cn.bluetron.nb.autoparts.warehouse.adapter.remote;
//
//import cn.bluetron.nb.autoparts.app.common.controller.BaseController;
//import cn.bluetron.nb.autoparts.warehouse.application.query.IWarehouseQueryService;
//import cn.bluetron.nb.autoparts.warehouse.domain.model.WarehouseModel;
//import cn.bluetron.nb.autoparts.warehouse.domain.repository.IWarehouseRepository;
//import cn.bluetron.nb.autoparts.warehouse.dto.WarehouseDetailResDTO;
//import cn.bluetron.nb.autoparts.warehouse.feign.RemoteWarehouseService;
//import cn.bluetron.nb.autoparts.warehouse.infrastructure.persistence.entity.WarehouseEntity;
//import cn.bluetron.nb.autoparts.warehouse.infrastructure.persistence.entity.WarehouseLocationEntity;
//import cn.bluetron.nb.autoparts.warehouse.infrastructure.persistence.mapper.WarehouseLocationMapper;
//import cn.bluetron.nb.autoparts.warehouse.infrastructure.persistence.mapper.WarehouseMapper;
//import com.bluetron.app.common.response.ApiResponse;
//import com.bluetron.app.common.response.ResultCode;
//import io.swagger.annotations.ApiOperation;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * 仓库远程调用
// *
// * @author linguang.lv
// * @date 2021-08-20
// */
//@RestController
//public class WarehouseRemoteController extends BaseController implements RemoteWarehouseService {
//
//    @Autowired
//    private IWarehouseQueryService warehouseQueryService;
//
//    @Autowired
//    private IWarehouseRepository warehouseRepository;
//
//    @Autowired
//    private WarehouseMapper warehouseMapper;
//
//    @Autowired
//    private WarehouseLocationMapper locationMapper;
//
//    @ApiOperation(value = "根据ids查询仓库数据")
//    public ApiResponse<List<WarehouseDetailResDTO>> listWarehouseByIds(@RequestBody Set<Long> ids) {
//        List<WarehouseModel> data = warehouseQueryService.findModelListByIds(ids);
//        List<WarehouseDetailResDTO> result = new ArrayList<>();
//        for (WarehouseModel warehouseModel : data) {
//            WarehouseDetailResDTO warehouseDetailRes = new WarehouseDetailResDTO();
//            BeanUtils.copyProperties(warehouseModel, warehouseDetailRes);
//            result.add(warehouseDetailRes);
//        }
//        return success(result);
//    }
//
//    @ApiOperation("根据code查仓库信息")
//    public ApiResponse<WarehouseDetailResDTO> byCode(
//            @RequestParam String tenantId,
//            @RequestParam String code) {
//        WarehouseModel model = warehouseRepository.getWarehouseInfoByCode(tenantId, code);
//        if (model == null || model.getId() == null) {
//            return fail(ResultCode.RESULE_DATA_NONE.code(), "找不到仓库信息");
//        }
//        WarehouseDetailResDTO res = new WarehouseDetailResDTO();
//        BeanUtils.copyProperties(model, res);
//        if (model.getIsEnableWarehouseLocation() && CollectionUtils.isNotEmpty(model.getWarehouseLocationList())) {
//            res.setLocationList(model.getWarehouseLocationList().stream()
//                    .map(l -> WarehouseDetailResDTO.WarehouseLocationResDTO.builder().location(
//                                    l.getWarehouseAreaName() + "#" + l.getWarehouseLocationName()).isEnable(l.getIsEnable())
//                            .build())
//                    .collect(Collectors.toList()));
//        }
//        return success(res);
//    }
//
//    @ApiOperation("根据code列表查仓库信息")
//    public ApiResponse<List<WarehouseDetailResDTO>> byCodeList(
//            @RequestParam String tenantId,
//            @RequestParam List<String> codeList
//    ) {
//        if (CollectionUtils.isEmpty(codeList)) {
//            return fail("编号列表不能为空");
//        }
//        List<WarehouseModel> modelList = warehouseRepository.byCodeList(tenantId, codeList);
//        List<WarehouseDetailResDTO> resList = modelList.stream().map(
//                e -> {
//                    WarehouseDetailResDTO res = new WarehouseDetailResDTO();
//                    BeanUtils.copyProperties(e, res);
//                    if (e.getIsEnableWarehouseLocation() && CollectionUtils.isNotEmpty(e.getWarehouseLocationList())) {
//                        res.setLocationList(e.getWarehouseLocationList().stream()
//                                .map(l -> WarehouseDetailResDTO.WarehouseLocationResDTO.builder()
//                                        .location(l.getWarehouseAreaName() + "#" + l.getWarehouseLocationName())
//                                        .isEnable(l.getIsEnable())
//                                        .build())
//                                .collect(Collectors.toList()));
//                    }
//                    return res;
//                }).collect(Collectors.toList());
//        return success(resList);
//    }
//
//    @ApiOperation("根据仓库和库位id查库位信息")
//    public ApiResponse<WarehouseDetailResDTO> byWarehouseIdAndLocationId(
//            @RequestParam String tenantId,
//            @RequestParam(required = false) Long warehouseId,
//            @RequestParam(required = false) Long locationId
//    ) {
//        WarehouseDetailResDTO res = new WarehouseDetailResDTO();
//        if (warehouseId == null && locationId == null) {
//            return fail(ResultCode.RESULE_DATA_NONE.code(), "库位未找到");
//        }
//        if (locationId != null) {
//            WarehouseLocationEntity locationEntity = locationMapper.selectById(locationId);
//            if (locationEntity == null || !locationEntity.getTenantId().equals(tenantId)) {
//                return fail(ResultCode.RESULE_DATA_NONE.code(), "库位未找到");
//            }
//            if (!locationEntity.getIsEnable()) {
//                return fail(ResultCode.DATA_IS_WRONG.code(), "库位被禁用");
//            }
//            if (warehouseId != null && !warehouseId.equals(locationEntity.getWarehouseId())) {
//                return fail(ResultCode.DATA_IS_WRONG.code(), "库位不属于仓库");
//            }
//            WarehouseEntity warehouseEntity = warehouseMapper.selectById(locationEntity.getWarehouseId());
//            if (warehouseEntity == null || !warehouseEntity.getTenantId().equals(tenantId)) {
//                return fail(ResultCode.RESULE_DATA_NONE.code(), "仓库未找到");
//            }
//            if (!warehouseEntity.getIsEnable()) {
//                return fail(ResultCode.DATA_IS_WRONG.code(), "仓库被禁用");
//            }
//            if (!warehouseEntity.getIsEnableWarehouseLocation()) {
//                return fail(ResultCode.DATA_IS_WRONG.code(), "仓库未启用库位管理，不能选择库位");
//            }
//            BeanUtils.copyProperties(warehouseEntity, res);
//            WarehouseDetailResDTO.WarehouseLocationResDTO locationRes = new WarehouseDetailResDTO.WarehouseLocationResDTO();
//            locationRes.setIsEnable(locationEntity.getIsEnable());
//            locationRes.setLocation(locationEntity.getWarehouseAreaName() + "#" + locationEntity.getWarehouseLocationName());
//            res.setLocationList(Arrays.asList(locationRes));
//        } else {
//            WarehouseEntity warehouseEntity = warehouseMapper.selectById(warehouseId);
//            if (warehouseEntity == null || !warehouseEntity.getTenantId().equals(tenantId)) {
//                return fail(ResultCode.RESULE_DATA_NONE.code(), "仓库未找到");
//            }
//            if (!warehouseEntity.getIsEnable()) {
//                return fail(ResultCode.DATA_IS_WRONG.code(), "仓库被禁用");
//            }
//            if (warehouseEntity.getIsEnableWarehouseLocation()) {
//                return fail(ResultCode.DATA_IS_WRONG.code(), "仓库启用了库位管理，请选择库位");
//            }
//            BeanUtils.copyProperties(warehouseEntity, res);
//        }
//        return success(res);
//    }
//
//    @Override
//    public ApiResponse<Map<Long, WarehouseDetailResDTO>> getIdToModelDtoMap(String tenantId, Set<Long> idSet) {
//        List<WarehouseModel> data = warehouseQueryService.findModelListByIds(idSet);
//        Map<Long, WarehouseDetailResDTO> result = new HashMap(data.size(), 1);
//        for (WarehouseModel warehouseModel : data) {
//            if (StringUtils.equals(tenantId, warehouseModel.getTenantId())) {
//                WarehouseDetailResDTO warehouseDetailRes = new WarehouseDetailResDTO();
//                BeanUtils.copyProperties(warehouseModel, warehouseDetailRes);
//                result.put(warehouseDetailRes.getId(), warehouseDetailRes);
//            }
//        }
//        return success(result);
//    }
//
//    @Override
//    public ApiResponse<Map<String, WarehouseDetailResDTO>> getCodeToModelDtoMap(String tenantId, Set<String> codeSet) {
//        List<WarehouseModel> data = warehouseQueryService.findModelListByCodes(tenantId, codeSet);
//        Map<String, WarehouseDetailResDTO> result = new HashMap(data.size(), 1);
//        for (WarehouseModel warehouseModel : data) {
//            WarehouseDetailResDTO warehouseDetailRes = new WarehouseDetailResDTO();
//            BeanUtils.copyProperties(warehouseModel, warehouseDetailRes);
//            result.put(warehouseDetailRes.getCode(), warehouseDetailRes);
//        }
//        return success(result);
//    }
//}
