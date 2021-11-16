package ${package}.infrastructure.common;

import com.bluetron.nb.common.base.constant.ICodeAndNameEnum;

public enum WarehouseOperationType implements ICodeAndNameEnum {
    Inbound("Inbound", "入库"), Outbound("Outbound", "出库");

    private final String code;
    private final String label;

    WarehouseOperationType(String code, String label) {
        this.code = code;
        this.label = label;
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
