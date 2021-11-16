//package cn.bluetron.nb.autoparts.warehouse.adapter.remote.assembler;
//
//import cn.bluetron.nb.autoparts.app.common.exception.AutoPartsException;
//import cn.bluetron.nb.autoparts.warehouse.application.command.cmd.GeneralOutboundOrderAddCommand;
//import cn.bluetron.nb.autoparts.warehouse.application.command.cmd.GeneralOutboundOrderAddCommandDetail;
//import cn.bluetron.nb.autoparts.warehouse.application.command.cmd.PickOutboundOrderAddCommand;
//import cn.bluetron.nb.autoparts.warehouse.constant.GoodsType;
//import cn.bluetron.nb.autoparts.warehouse.constant.WarehouseInOutType;
//import cn.bluetron.nb.autoparts.warehouse.dto.request.outbound.GeneralOutboundOrder;
//import cn.bluetron.nb.autoparts.warehouse.dto.request.outbound.GeneralOutboundOrderGoods;
//import cn.bluetron.nb.autoparts.warehouse.dto.request.outbound.PickOutboundOrderAddReq;
//import org.springframework.beans.BeanUtils;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class PickOutboundOrderAssembler {
//
//
//    public static final PickOutboundOrderAddCommand  convert(PickOutboundOrderAddReq addReq) {
//        PickOutboundOrderAddCommand command = new PickOutboundOrderAddCommand();
//        BeanUtils.copyProperties(addReq, command);
//        List<GeneralOutboundOrderAddCommand> generalOutboundOrderAddCommandList = new ArrayList<>(addReq.getGeneralOutboundOrderList().size());
//        for(GeneralOutboundOrder generalOutboundOrder : addReq.getGeneralOutboundOrderList()) {
//            // 申请详情的Id集合
//            Set<Long> applyDetailIdSet = new HashSet<>();
//            GeneralOutboundOrderAddCommand generalOutboundOrderAddCommand = new GeneralOutboundOrderAddCommand();
//            BeanUtils.copyProperties(generalOutboundOrder, generalOutboundOrderAddCommand);
//            generalOutboundOrderAddCommand.setCreateUsername(addReq.getCreateUsername());
//            generalOutboundOrderAddCommand.setCreateRealname(addReq.getCreateRealname());
//            generalOutboundOrderAddCommand.setOutboundType(WarehouseInOutType.PickOutbound.getCode());
//            List<GeneralOutboundOrderAddCommandDetail> goodsAddCommandList = new ArrayList<>(generalOutboundOrder.getGeneralOutboundOrderGoodsList().size());
//            for(GeneralOutboundOrderGoods orderGoods : generalOutboundOrder.getGeneralOutboundOrderGoodsList()) {
//                applyDetailIdSet.add(orderGoods.getApplyDetailId());
//                GeneralOutboundOrderAddCommandDetail addCommandDetail = new GeneralOutboundOrderAddCommandDetail();
//                BeanUtils.copyProperties(orderGoods, addCommandDetail);
//                addCommandDetail.setGoodsType(GoodsType.valueOf(orderGoods.getGoodsType()));
//                goodsAddCommandList.add(addCommandDetail);
//            }
//            if(applyDetailIdSet.size() != generalOutboundOrder.getGeneralOutboundOrderGoodsList().size()) {
//                throw new AutoPartsException("申请详情的Id重复");
//            }
//            generalOutboundOrderAddCommand.setGoodsList(goodsAddCommandList);
//            generalOutboundOrderAddCommandList.add(generalOutboundOrderAddCommand);
//        }
//        command.setGeneralOutboundOrderAddCommandList(generalOutboundOrderAddCommandList);
//        return command;
//    }
//}
