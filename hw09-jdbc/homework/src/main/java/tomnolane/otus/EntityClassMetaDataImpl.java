package tomnolane.otus;

import tomnolane.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    @Override
    public String getName() {
        return "";
    }

    @Override
    public Constructor<T> getConstructor() {
        return null;
    }

    @Override
    public Field getIdField() {
        return null;
    }

    @Override
    public List<Field> getAllFields() {
        return List.of();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return List.of();
    }
}
