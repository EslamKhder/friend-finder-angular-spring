package com.spring.frindfinder.config.audit;

import com.spring.frindfinder.exceptions.EntityIdAuditingException;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.aspectj.ConfigurableObject;

import javax.persistence.PrePersist;
import java.lang.reflect.Field;

@Configurable
public class EntityAuditingEventListener implements ConfigurableObject {

    @PrePersist
    public void onPrePersist(Object target)
                    throws NoSuchFieldException, IllegalAccessException {
        Class<?> entityClass = target.getClass();
        Field idField;
        try {
            Field[] fields = entityClass.getDeclaredFields();
            idField = entityClass.getDeclaredField("id");
        } catch (Exception exception) {
            return;
        }


        if (idField == null) {
            return;
        }

        idField.setAccessible(true);
        Long entityId = (Long) idField.get(target);
        idField.setAccessible(false);

        if (entityId != null) {
            throw new EntityIdAuditingException("Entity_id_001", "entity_id");
        }
    }
}
