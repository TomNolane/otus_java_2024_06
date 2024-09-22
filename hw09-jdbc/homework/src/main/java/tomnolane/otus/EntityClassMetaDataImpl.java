package tomnolane.otus;

import tomnolane.otus.annotation.Id;
import tomnolane.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> entityClass;

    public EntityClassMetaDataImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String getName() {
        return entityClass.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        Class<?>[] parameterTypes = getConstructorWithMaxParameters().getParameterTypes();

        try {
            return entityClass.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            throw new EntityClassException(e);
        }
    }

    @Override
    public Field getIdField() {
        final var fields = getAllFields();

        return fields.stream().filter(x -> x.isAnnotationPresent(Id.class)).findFirst().get();
    }

    @Override
    public List<Field> getAllFields() {
        final Field[] fields = entityClass.getDeclaredFields();

        return Arrays.asList(fields);
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields().stream()
                .filter(x -> !x.isAnnotationPresent(Id.class))
                .toList();
    }

    private Constructor<?> getConstructorWithMaxParameters() {
        final Constructor<?>[] constructors = entityClass.getConstructors();
        Arrays.sort(constructors, Comparator.comparingInt(Constructor::getParameterCount));

        return constructors[constructors.length - 1];
    }
}
