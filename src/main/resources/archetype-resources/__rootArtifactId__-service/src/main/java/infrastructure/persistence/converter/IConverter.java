package ${package}.infrastructure.persistence.converter;

/**
 * Entity、DomainObject的转换接口
 */
public interface IConverter<E, D> {

    /**
     * Entity反序列化为DomainObject
     *
     * @param entity
     * @return
     */
    D deserialize(E entity);

    /**
     * DomainObject序列化为Entity
     *
     * @param domainObject
     * @return
     */
    E serialize(D domainObject);
}
