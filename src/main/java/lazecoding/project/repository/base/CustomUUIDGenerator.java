package lazecoding.project.repository.base;

import org.hibernate.HibernateException;

import java.io.Serializable;

import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDHexGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.util.Properties;

/**
 * 自定义主键生成策略。
 *
 * 优先使用自定义 Id，否则使用 UUID。
 * 调用的保存方法需为 Repository.save() 或 EntityManager.merge()
 * 若调用的保存方法为 EntityManager.persist()，且传入对象有id值时，仍会报错！
 *
 * @author lazecoding
 */
public class CustomUUIDGenerator extends UUIDHexGenerator {

    /**
     * 生成策略名称
     */
    public static final String GENERATOR_NAME = "UUID";

    /**
     * 类路径
     */
    public static final String CLASS_PATH = "lazecoding.project.repository.base.CustomUUIDGenerator";


    private String entityName;

    @Override
    public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
        entityName = params.getProperty(ENTITY_NAME);
        if (entityName == null) {
            throw new MappingException("no entity name");
        }
        super.configure(type, params, serviceRegistry);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        Serializable id = session.getEntityPersister(entityName, object).getIdentifier(object, session);
        if (id != null) {
            return id;
        }
        return super.generate(session, object);
    }
}
