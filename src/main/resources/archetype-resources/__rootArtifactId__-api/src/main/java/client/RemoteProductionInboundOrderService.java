package ${package}.client;

import ${package}.common.ServiceConstant;
import ${package}.dto.ProductionInboundOrderAddDTO;
import com.bluetron.nb.common.base.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 生产入库单
 *
 * @author linguang.lv
 * @date 2021-08-18
 */
@FeignClient(contextId = "remoteProductionInboundOrderService", value = ServiceConstant.SERVICE_NAME)
public interface RemoteProductionInboundOrderService {

    /**
     * 新增生产入库单
     *
     * @param req
     * @return
     */
    @PostMapping("/api/v1/production-inbound-order")
    Result saveProductionInboundOrder(@RequestBody ProductionInboundOrderAddDTO req);

}
