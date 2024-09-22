package tomnolane.otus;

import tomnolane.otus.jdbc.mapper.EntityClassMetaData;
import tomnolane.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> entityClassMetaData;
    private final String table;
    private final String idColumn;

    private static final String SELECT = "select ";
    private static final String INSERT_INTO = "insert into ";
    private static final String UPDATE = "update ";

    private static final String FROM = " from ";
    private static final String WHERE = " where ";
    private static final String SET = " set ";
    private static final String VALUES = "values";

    private static final String PARAMETER = "?";
    private static final String EQUALS_PARAM = "=?";
    private static final String COMMA = ",";
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ") ";

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        table = entityClassMetaData.getName();
        idColumn = entityClassMetaData.getIdField().getName();
    }

    @Override
    public String getSelectAllSql() {
        final String names = createNames();
        final StringBuilder sb =
                new StringBuilder(SELECT).append(names).append(FROM).append(table);

        return sb.toString();
    }

    @Override
    public String getSelectByIdSql() {
        final String names = createAllNames();
        final StringBuilder sb = new StringBuilder(SELECT)
                .append(names)
                .append(FROM)
                .append(table)
                .append(WHERE)
                .append(idColumn)
                .append(EQUALS_PARAM);

        return sb.toString();
    }

    @Override
    public String getInsertSql() {
        final StringBuilder sb = new StringBuilder();
        final String names = createNames();

        final StringBuilder params = new StringBuilder(PARAMETER);
        for (int i = 1; i < entityClassMetaData.getFieldsWithoutId().size(); i++) {
            params.append(COMMA).append(PARAMETER);
        }

        sb.append(INSERT_INTO)
                .append(table)
                .append(OPEN_BRACKET)
                .append(names)
                .append(CLOSE_BRACKET)
                .append(VALUES)
                .append(OPEN_BRACKET)
                .append(params)
                .append(CLOSE_BRACKET);

        return sb.toString();
    }

    @Override
    public String getUpdateSql() {
        final StringBuilder sb = new StringBuilder();
        final String names = createParameters();

        sb.append(UPDATE)
                .append(table)
                .append(SET)
                .append(names)
                .append(WHERE)
                .append(idColumn)
                .append(EQUALS_PARAM);

        return sb.toString();
    }

    private String createAllNames() {
        return String.join(
                COMMA,
                entityClassMetaData.getAllFields().stream()
                        .map(Field::getName)
                        .toList());
    }

    private String createNames() {
        return String.join(
                COMMA,
                entityClassMetaData.getFieldsWithoutId().stream()
                        .map(Field::getName)
                        .toList());
    }

    private String createParameters() {
        return String.join(
                COMMA,
                entityClassMetaData.getFieldsWithoutId().stream()
                        .map(Field::getName)
                        .map(x -> x + EQUALS_PARAM)
                        .toList());
    }
}
