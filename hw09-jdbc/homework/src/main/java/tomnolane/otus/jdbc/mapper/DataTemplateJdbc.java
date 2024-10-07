package tomnolane.otus.jdbc.mapper;

import tomnolane.otus.core.repository.DataTemplate;
import tomnolane.otus.core.repository.DataTemplateException;
import tomnolane.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DataTemplateJdbc<T> implements DataTemplate<T> {
    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(
            DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return createEntity(rs);
                }

                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor
            .executeSelect(connection, entitySQLMetaData.getSelectAllSql(), List.of(), rs -> {
                final List<T> records = new ArrayList<>();
                try {
                    while (rs.next()) {
                        records.add(createEntity(rs));
                    }

                    return records;
                } catch (Exception e) {
                    throw new DataTemplateException(e);
                }
            }).orElseThrow(() -> new DataTemplateException(new RuntimeException("Error getting data")));
    }

    @Override
    public long insert(Connection connection, T client) {
        final List<Object> params = getParameters(client);

        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), params);
    }

    @Override
    public void update(Connection connection, T client) {
        final List<Object> params = getParameters(client);
        params.add(getId(client));
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), params);
    }

    private List<Object> getParameters(T client) {
        final List<Field> fieldsWithoutId = entityClassMetaData.getFieldsWithoutId();
        final List<Object> params = new ArrayList<>();

        for (Field field : fieldsWithoutId) {
            field.setAccessible(true);
            try {
                params.add(field.get(client));
            } catch (IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        }
        return params;
    }

    private Object getId(T client) {
        final Field id = entityClassMetaData.getIdField();
        id.setAccessible(true);

        try {
            return id.get(client);
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        }
    }

    private T createEntity(ResultSet rs) {
        try {
            T entity = entityClassMetaData.getConstructor().newInstance();

            for (Field field : entityClassMetaData.getAllFields()) {
                field.setAccessible(true);
                Object value = rs.getObject(field.getName().toLowerCase());
                field.set(entity, value);
            }
            return entity;
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
