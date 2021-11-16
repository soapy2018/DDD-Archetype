package ${package}.infrastructure.persistence.repository;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import ${package}.application.query.query.WarehouseInOutTypeQuery;
import ${package}.common.WarehouseType;
import ${package}.domain.model.WarehouseInOutTypeModel;
import ${package}.domain.repository.IWarehouseInOutTypeRepository;
import ${package}.infrastructure.common.WarehouseOperationType;
import ${package}.infrastructure.persistence.converter.WarehouseInOutTypeConverter;
import ${package}.infrastructure.persistence.entity.WarehouseInOutTypeEntity;
import ${package}.infrastructure.persistence.mapper.WarehouseInOutTypeMapper;
import com.bluetron.nb.common.base.exception.GeneralException;
import com.bluetron.nb.common.util.tools.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class WarehouseInOutTypeRepositoryImpl implements IWarehouseInOutTypeRepository {

    private static final String DEFAULT_FILE_NAME = "warehouse_in_out_type_default.json";
    private static final List<WarehouseInOutTypeModel> DEFAULT_LIST = new ArrayList<>(32);

    static {
        try {
            String content = IOUtils.toString(WarehouseInOutTypeRepositoryImpl.class.getResourceAsStream("/" + DEFAULT_FILE_NAME), StandardCharsets.UTF_8);
            List<WarehouseInOutTypeEntity> list = JSON.parseArray(content, WarehouseInOutTypeEntity.class);
            list.forEach(entity -> {
                if ("ALL".equals(entity.getWarehouseType())) {
                    entity.setWarehouseType(WarehouseType.formatToCodeText());
                }
                entity.setIsDefault(true);
                entity.setIsEnable(true);
            });
            WarehouseInOutTypeConverter converter = new WarehouseInOutTypeConverter();
            DEFAULT_LIST.addAll(list.stream().map(e -> converter.deserialize(e)).collect(Collectors.toList()));
        } catch (IOException e) {
            log.error("fail to load default WarehouseInOutType list");
        }
    }


    @Autowired
    private WarehouseInOutTypeConverter warehouseInOutTypeConverter;

    @Autowired
    private WarehouseInOutTypeMapper warehouseInOutTypeMapper;

    @Override
    public WarehouseInOutTypeModel findBy(String tenantId, Long id) {
        LambdaQueryWrapper<WarehouseInOutTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WarehouseInOutTypeEntity::getTenantId, tenantId);
        queryWrapper.eq(WarehouseInOutTypeEntity::getId, id);
        WarehouseInOutTypeEntity entity = warehouseInOutTypeMapper.selectOne(queryWrapper);
        if (entity == null) {
            log.error("Can not find warehouseInOutType[tenantId={}, id={}]", tenantId, id);
            throw new GeneralException("未找到该出入库类型信息");
        }
        return warehouseInOutTypeConverter.deserialize(entity);
    }

    @Override
    public WarehouseInOutTypeModel findBy(String tenantId, String code) {
        WarehouseInOutTypeEntity entity = this.findByUniqueKey(tenantId, code);
        if (entity == null) {
            for (WarehouseInOutTypeModel model : DEFAULT_LIST) {
                if (model.getCode().equals(code)) {
                    WarehouseInOutTypeModel model1 = new WarehouseInOutTypeModel();
                    BeanUtils.copyProperties(model, model1);
                    model1.setTenantId(tenantId);
                    return model1;
                }
            }
            log.error("Can not find warehouseInOutType[tenantId={}, code={}]", tenantId, code);
            throw new GeneralException("未找到该出入库类型信息");
        }
        return warehouseInOutTypeConverter.deserialize(entity);
    }

    @Override
    public List<WarehouseInOutTypeModel> findBy(String tenantId, WarehouseType warehouseType) {
        List<WarehouseInOutTypeModel> resultList = findAll(tenantId);
        return resultList.stream()
                .filter(type -> type.getWarehouseTypeSet().contains(warehouseType))
                .collect(Collectors.toList());
    }

    @Override
    public List<WarehouseInOutTypeModel> findBy(String tenantId, WarehouseOperationType warehouseOperationType, WarehouseType warehouseType, Boolean isEnable) {
        // 默认的出入库类型
        List<WarehouseInOutTypeModel> defaultList = new ArrayList<>(DEFAULT_LIST.size());
        for (WarehouseInOutTypeModel model : DEFAULT_LIST) {
            WarehouseInOutTypeModel newModel = new WarehouseInOutTypeModel();
            if (warehouseOperationType != null && !warehouseOperationType.equals(model.getOperationType())) {
                continue;
            }
            if (warehouseType != null) {
                boolean isContain = false;
                for (WarehouseType type : model.getWarehouseTypeSet()) {
                    if (type.equals(warehouseType)) {
                        isContain = true;
                    }
                }
                if (!isContain) {
                    continue;
                }
            }
            BeanUtils.copyProperties(model, newModel);
            newModel.setTenantId(tenantId);
            defaultList.add(newModel);
        }
        // 来自数据库的出入库类型
        LambdaQueryWrapper<WarehouseInOutTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WarehouseInOutTypeEntity::getTenantId, tenantId);
        queryWrapper.eq(warehouseOperationType != null, WarehouseInOutTypeEntity::getOperationType, warehouseOperationType);
        queryWrapper.eq(isEnable != null, WarehouseInOutTypeEntity::getIsEnable, isEnable);
        List<WarehouseInOutTypeEntity> list = warehouseInOutTypeMapper.selectList(queryWrapper);
        List<WarehouseInOutTypeModel> listFromDb = list.stream().map(warehouseInOutTypeConverter::deserialize).collect(Collectors.toList());
        // 根据仓库属性过滤
        List<WarehouseInOutTypeModel> dbList = new ArrayList<>();
        for (WarehouseInOutTypeModel warehouseInOutTypeModel : listFromDb) {
            if (warehouseType != null) {
                boolean isContain = false;
                for (WarehouseType type : warehouseInOutTypeModel.getWarehouseTypeSet()) {
                    if (type.equals(warehouseType)) {
                        isContain = true;
                    }
                }
                if (!isContain) {
                    continue;
                }
            }
            dbList.add(warehouseInOutTypeModel);
        }
        List<WarehouseInOutTypeModel> resultList = new ArrayList<>(dbList.size() + defaultList.size());
        resultList.addAll(defaultList);
        resultList.addAll(dbList);
        return resultList;
    }

    private List<WarehouseInOutTypeModel> findAll(String tenantId) {
        // 默认的出入库类型
        List<WarehouseInOutTypeModel> defaultList = new ArrayList<>(DEFAULT_LIST.size());
        for (WarehouseInOutTypeModel model : DEFAULT_LIST) {
            WarehouseInOutTypeModel newModel = new WarehouseInOutTypeModel();
            BeanUtils.copyProperties(model, newModel);
            newModel.setTenantId(tenantId);
            defaultList.add(newModel);
        }
        // 来自数据库的出入库类型
        LambdaQueryWrapper<WarehouseInOutTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WarehouseInOutTypeEntity::getTenantId, tenantId);
        List<WarehouseInOutTypeEntity> list = warehouseInOutTypeMapper.selectList(queryWrapper);
        List<WarehouseInOutTypeModel> listFromDb = list.stream().map(warehouseInOutTypeConverter::deserialize).collect(Collectors.toList());
        List<WarehouseInOutTypeModel> resultList = new ArrayList<>(listFromDb.size() + defaultList.size());
        resultList.addAll(defaultList);
        resultList.addAll(listFromDb);
        return resultList;
    }

    @Override
    public List<WarehouseInOutTypeModel> findBy(WarehouseInOutTypeQuery query) {
        List<WarehouseInOutTypeModel> resultList = findAll(query.getTenantId());
        resultList = resultList.stream().filter(dto -> {
            return (
                    matchLike(dto, WarehouseInOutTypeModel::getBusinessName, query.getBusinessType()) &&

                            (query.getOperationType() == null || query.getOperationType() == dto.getOperationType()) &&

                            (query.getIsEnable() == null || query.getIsEnable().equals(dto.getIsEnable())) &&

                            matchLike(dto, WarehouseInOutTypeModel::getCreateRealname, query.getCreateRealname()) &&

                            timeBetween(dto, WarehouseInOutTypeModel::getCreateTime, query.getCreateTimeStart(), query.getCreateTimeEnd()) &&

                            matchLike(dto, WarehouseInOutTypeModel::getUpdateRealname, query.getUpdateRealname()) &&

                            timeBetween(dto, WarehouseInOutTypeModel::getUpdateTime, query.getUpdateTimeStart(), query.getUpdateTimeEnd())
            );
        }).collect(Collectors.toList());
        return resultList;
    }

    private boolean matchLike(WarehouseInOutTypeModel data, Function<WarehouseInOutTypeModel, String> fun, String text) {
        if (StringUtils.isBlank(text)) {
            return true;
        }
        String str = fun.apply(data);
        return StringUtils.isNotBlank(str) && str.contains(text);
    }

    private boolean timeBetween(WarehouseInOutTypeModel data, Function<WarehouseInOutTypeModel, Date> fun, Date startTime, Date endTime) {
        Date value = fun.apply(data);
        if (startTime != null) {
            if (value == null) {
                return false;
            }
            if (value.before(startTime)) {
                return false;
            }
        }
        if (endTime != null) {
            if (value == null) {
                return false;
            }
            return !value.after(endTime);
        }
        return true;
    }

    @Override
    public void save(WarehouseInOutTypeModel model) {
        WarehouseInOutTypeEntity entity = warehouseInOutTypeConverter.serialize(model);
        if (model.getId() != null) {
            WarehouseInOutTypeEntity dbEntity = this.warehouseInOutTypeMapper.selectById(model.getId());
            if (dbEntity == null) {
                throw new GeneralException("该出入库类型不存在");
            }
            if (!dbEntity.getTenantId().equals(model.getTenantId())) {
                throw new GeneralException("租户Id异常");
            }
            boolean isUniqueName = judgeByUniqueName(model.getTenantId(), model.getBusinessName(), model.getId());
            if (!isUniqueName) {
                throw new GeneralException("该出入库类型名称重复");
            }
            this.warehouseInOutTypeMapper.updateById(entity);
        } else {
            String code = IdUtil.randomUUID();
            entity.setCode(code);
            model.setCode(code);
            boolean isUniqueName = judgeByUniqueName(model.getTenantId(), model.getBusinessName(), null);
            if (!isUniqueName) {
                throw new GeneralException("该出入库类型名称重复");
            }
            this.warehouseInOutTypeMapper.insert(entity);
        }
    }

    @Override
    public Map<String, String> findCodeToNameMap(String tenantId) {
        List<WarehouseInOutTypeModel> list = findAll(tenantId);
        Map<String, String> codeToNameMap = new HashMap(32);
        for (WarehouseInOutTypeModel warehouseInOutTypeModel : list) {
            codeToNameMap.put(warehouseInOutTypeModel.getCode(), warehouseInOutTypeModel.getBusinessName());
        }
        return codeToNameMap;
    }

    private WarehouseInOutTypeEntity findByUniqueKey(String tenantId, String code) {
        LambdaQueryWrapper<WarehouseInOutTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WarehouseInOutTypeEntity::getTenantId, tenantId);
        queryWrapper.eq(WarehouseInOutTypeEntity::getCode, code);
        return warehouseInOutTypeMapper.selectOne(queryWrapper);
    }

    private boolean judgeByUniqueName(String tenantId, String name, Long id) {
        boolean result = false;
        // 默认业务类型名称重复性校验
        for (WarehouseInOutTypeModel warehouseInOutTypeModel : DEFAULT_LIST) {
            if (warehouseInOutTypeModel.getBusinessName().equals(name)) {
                return false;
            }
        }
        // 数据库业务类型名称重复性校验
        LambdaQueryWrapper<WarehouseInOutTypeEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WarehouseInOutTypeEntity::getTenantId, tenantId);
        queryWrapper.eq(WarehouseInOutTypeEntity::getBusinessName, name);
        if (id != null) {
            queryWrapper.ne(WarehouseInOutTypeEntity::getId, id);
        }
        List<WarehouseInOutTypeEntity> warehouseInOutTypeEntityList = warehouseInOutTypeMapper.selectList(queryWrapper);
        if (warehouseInOutTypeEntityList == null || warehouseInOutTypeEntityList.isEmpty()) {
            result = true;
        }
        return result;
    }

}
