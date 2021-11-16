package ${package}.infrastructure.common;

import com.bluetron.nb.common.base.constant.ICodeAndNameEnum;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author genx
 * @date 2021/8/17 15:12
 */
public enum
WarehouseInOutType implements ICodeAndNameEnum {
    /**
     * 生产入库
     */
    ProductionInbound("001", "生产入库"),
    /**
     * 采购入库
     */
    PurchaseInbound("003", "采购入库"),
    /**
     * 领料出库
     */
    PickOutbound("002", "领料出库"),
    /**
     * 盘盈入库
     */
    InventoryGain("010", "盘盈入库"),
    /**
     * 盘盈入库
     */
    InventoryLoss("011", "盘盈入库"),

    ;


    private final String code;
    private final String label;

    WarehouseInOutType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public static String getLabelByCode(String code) {
        for (WarehouseInOutType value : values()) {
            if (value.code.equals(code)) {
                return value.getChineseName();
            }
        }
        return "";
    }

    public static WarehouseInOutType getByCode(String code) {
        for (WarehouseInOutType value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getChineseName() {
        return this.label;
    }

}
