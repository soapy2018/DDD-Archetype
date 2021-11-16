package ${package}.common;

import com.bluetron.nb.common.base.constant.ICodeAndNameEnum;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 仓库属性
 *
 * @author zhuerwei
 * @date 2021/8/12 16:37
 */
@AllArgsConstructor
public enum WarehouseType implements ICodeAndNameEnum {

    /**
     * 仓库属性
     */
//    ALL("所有"),
    MATERIAL("0001", "源物料"),
    SUB_FG("0002", "半成品"),
    FG("0003", "成品"),
    SPARE_PARTS("0004", "备品备件"),
    GOODS_IN_PROCESS("0005", "在制品"),
    KNIFE_TOOL("0006", "刀具"),
    EQUIPMENT("0007", "设备"),
    TOOLING("0008", "工装"),
    MEASURE_TOOL("0009", "量检具"),
    CONSUMABLE("0010", "耗材"),
    PACKING_MATERIAL("0011", "包材"),
    ;

    private final String code;
    private final String chineseName;

    public static Set<WarehouseType> parseFromCodeText(String warehouseType) {
        String[] typeArray = StringUtils.split(warehouseType, ",");
        if (typeArray == null || typeArray.length == 0) {
            return new HashSet<>();
        }
        return Stream.of(typeArray)
                .filter(type -> StringUtils.isNotBlank(type))
                .map(type -> {
                    try {
                        return WarehouseType.valueOf(type);
                    } catch (Exception e) {
                        // do nothing
                    }
                    return null;
                })
                .filter(typeEnum -> typeEnum != null)
                .collect(Collectors.toSet());
    }

    public static String formatToCodeText(Set<WarehouseType> typeSet) {
        if (CollectionUtils.isEmpty(typeSet)) {
            return "";
        }
        return StringUtils.join(typeSet, ",");
    }

    public static String formatToNameText(Set<WarehouseType> typeSet) {
        if (CollectionUtils.isEmpty(typeSet)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        typeSet.forEach(type -> stringBuilder.append(type.getChineseName()).append(","));
        return StringUtils.strip(stringBuilder.toString(), ",");
    }

    public static String formatToCodeText() {
        StringBuilder stringBuilder = new StringBuilder();
        List<WarehouseType> all = Arrays.asList(WarehouseType.values());
        all.forEach(warehouseType -> stringBuilder.append(warehouseType.getCode()).append(","));
        return StringUtils.strip(stringBuilder.toString(), ",");
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getChineseName() {
        return this.chineseName;
    }

}
