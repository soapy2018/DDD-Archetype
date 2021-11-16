//package ${package}.domain.factory;
//
//
//import ${package}.application.command.cmd.*;
//import ${package}.constant.OutboundState;
//import ${package}.domain.model.GeneralOutboundGoodsModel;
//import ${package}.domain.model.GeneralOutboundModel;
//import ${package}.domain.model.GeneralOutboundModelGoodsLocationInfo;
//import ${package}.domain.model.WarehouseModel;
//import ${package}.domain.repository.IInventoryRepository;
//import ${package}.domain.repository.IOutboundRepository;
//import ${package}.domain.repository.IWarehouseInOutTypeRepository;
//import ${package}.domain.repository.IWarehouseRepository;
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import com.google.common.collect.Sets;
//import org.apache.commons.collections4.MapUtils;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.BeanWrapper;
//import org.springframework.beans.BeanWrapperImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Component
//public class GeneralOutboundFactory {
//
//    @Autowired
//    private IWarehouseRepository warehouseRepository;
//
//    @Autowired
//    private IInventoryRepository inventoryRepository;
//
//    @Autowired
//    private IWarehouseInOutTypeRepository inOutTypeRepository;
//
//    @Autowired
//    private IOutboundRepository outboundRepository;
//
//    public GeneralOutboundModel addCommandToModel(GeneralOutboundOrderAddCommand cmd) {
//        GeneralOutboundModel model = new GeneralOutboundModel();
//        BeanUtils.copyProperties(cmd, model);
//        model.setTenantId(RequestLoginContextHolder.getCurrentTenantIdV2());
//        model.setUpdateRealname(cmd.getCreateRealname());
//        model.setUpdateUsername(cmd.getCreateUsername());
//        model.setGoodsList(new ArrayList<>());
//        model.setState(OutboundState.Pending);
//        for (int i = 0; i < cmd.getGoodsList().size(); i++) {
//            GeneralOutboundGoodsModel detail = new GeneralOutboundGoodsModel();
//            BeanUtils.copyProperties(cmd.getGoodsList().get(i), detail);
//            detail.setState(OutboundState.Pending);
//            // detail.setGoodsType(GoodsType.Product);
//
//            detail.setLocationInfoList(new ArrayList<>());
//            if (CollectionUtils.isNotEmpty(cmd.getGoodsList().get(i).getLocationInfoList())) {
//                for (int j = 0; j < cmd.getGoodsList().get(i).getLocationInfoList().size(); j++) {
//                    GeneralOutboundModelGoodsLocationInfo locationInfo = new GeneralOutboundModelGoodsLocationInfo();
//                    GeneralOutboundOrderAddCommandDetailLocationInfo commandDetailLocationInfo = cmd.getGoodsList().get(i).getLocationInfoList().get(j);
//                    BeanUtils.copyProperties(commandDetailLocationInfo, locationInfo);
//                    detail.getLocationInfoList().add(locationInfo);
//                }
//            }
//            model.getGoodsList().add(detail);
//        }
//        List<WarehouseModel> warehouseModel = warehouseRepository.getWarehouseInfoByIds(Sets.newHashSet(cmd.getWarehouseId()));
//        if (CollectionUtils.isEmpty(warehouseModel)) {
//            throw new AutoPartsException("找不到指定仓库");
//        }
//        model.setWarehouseCode(warehouseModel.get(0).getCode());
//        model.setWarehouseName(warehouseModel.get(0).getName());
//        model.setWarehouseType(warehouseModel.get(0).getWarehouseType());
//
//        String tenantId = RequestLoginContextHolder.getCurrentTenantId();
//        Map<String, String> outboundTypeMap = inOutTypeRepository.findCodeToNameMap(tenantId);
//        if (MapUtils.isEmpty(outboundTypeMap) || outboundTypeMap.get(model.getOutboundType()) == null) {
//            throw new AutoPartsException("找不到出入库类型");
//        }
//        model.setOutboundTypeName(outboundTypeMap.get(model.getOutboundType()));
//        return model;
//    }
//
//
//    private String[] getNullPropertyNames (Object source) {
//        final BeanWrapper src = new BeanWrapperImpl(source);
//        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
//        Set<String> emptyNames = new HashSet<String>();
//        for(java.beans.PropertyDescriptor pd : pds) {
//            Object srcValue = src.getPropertyValue(pd.getName());
//            if (srcValue == null) emptyNames.add(pd.getName());
//        }
//        String[] result = new String[emptyNames.size()];
//        return emptyNames.toArray(result);
//    }
//
//    public GeneralOutboundModel updateCommandToModel(GeneralOutboundOrderUpdateCommand cmd) {
//        String tenantId = RequestLoginContextHolder.getCurrentTenantIdV2();
//        GeneralOutboundModel model = new GeneralOutboundModel();
//        BeanUtils.copyProperties(cmd, model);
//        model.setGoodsList(new ArrayList<>());
//        Set<Long> detailIdSet = cmd.getGoodsList().stream().map(e -> e.getId()).collect(Collectors.toSet());
//        List<GeneralOutboundGoodsModel> outboundGoodsModels = outboundRepository.findOutboundGoodsBy(tenantId, detailIdSet);
//        Map<Long, GeneralOutboundGoodsModel> id2GoodsMap = outboundGoodsModels.stream().collect(Collectors.toMap(GeneralOutboundGoodsModel::getId, m -> m, (m1, m2) -> m1));
//        for (int i = 0; i < cmd.getGoodsList().size(); i++) {
//            GeneralOutboundGoodsModel goodsModel = id2GoodsMap.get(cmd.getGoodsList().get(i).getId());
//            BeanUtils.copyProperties(cmd.getGoodsList().get(i), goodsModel, getNullPropertyNames(cmd.getGoodsList().get(i)));
//            if (CollectionUtils.isNotEmpty(cmd.getGoodsList().get(i).getLocationInfoList())) {
//                goodsModel.setLocationInfoList(new ArrayList<>());
//                for (int j = 0; j < cmd.getGoodsList().get(i).getLocationInfoList().size(); j++) {
//                    GeneralOutboundModelGoodsLocationInfo locationInfo = new GeneralOutboundModelGoodsLocationInfo();
//                    GeneralOutboundOrderUpdateCommandGoodsLocationInfo commandDetailLocationInfo = cmd.getGoodsList().get(i).getLocationInfoList().get(j);
//                    BeanUtils.copyProperties(commandDetailLocationInfo, locationInfo);
//                    goodsModel.getLocationInfoList().add(locationInfo);
//                }
//            }
//
//            model.getGoodsList().add(goodsModel);
//        }
//        List<WarehouseModel> warehouseModel = warehouseRepository.getWarehouseInfoByIds(Sets.newHashSet(cmd.getWarehouseId()));
//        if (CollectionUtils.isEmpty(warehouseModel)) {
//            throw new AutoPartsException("找不到指定仓库");
//        }
//        model.setWarehouseCode(warehouseModel.get(0).getCode());
//        model.setWarehouseName(warehouseModel.get(0).getName());
//        model.setWarehouseType(warehouseModel.get(0).getWarehouseType());
//
//        // todo: 是否应该下放获取当前请求用户信息
//        Map<String, String> outboundTypeMap = inOutTypeRepository.findCodeToNameMap(tenantId);
//        if (MapUtils.isEmpty(outboundTypeMap) || outboundTypeMap.get(model.getOutboundType()) == null) {
//            throw new AutoPartsException("找不到出入库类型");
//        }
//        model.setOutboundTypeName(outboundTypeMap.get(model.getOutboundType()));
//        return model;
//    }
//
//    public void confirmCommandToModel(GeneralOutboundOrderConfirmCommand cmd,  GeneralOutboundModel model) {
//        model.setGoodsList(new LinkedList());
//        for (int i = 0; i < cmd.getGoodsList().size(); i++) {
//            GeneralOutboundGoodsModel detail = new GeneralOutboundGoodsModel();
//            BeanUtils.copyProperties(cmd.getGoodsList().get(i), detail);
//            detail.setState(OutboundState.Pending);
//            detail.setLocationInfoList(new ArrayList<>());
//            if (CollectionUtils.isNotEmpty(cmd.getGoodsList().get(i).getLocationInfoList())) {
//                for (int j = 0; j < cmd.getGoodsList().get(i).getLocationInfoList().size(); j++) {
//                    GeneralOutboundModelGoodsLocationInfo locationInfo = new GeneralOutboundModelGoodsLocationInfo();
//                    GeneralOutboundOrderUpdateCommandGoodsLocationInfo commandDetailLocationInfo = cmd.getGoodsList().get(i).getLocationInfoList().get(j);
//                    BeanUtils.copyProperties(commandDetailLocationInfo, locationInfo);
//                    detail.getLocationInfoList().add(locationInfo);
//                }
//            }
//
//            model.getGoodsList().add(detail);
//        }
//    }
//
//}
