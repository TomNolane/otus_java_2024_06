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
    private static final String VALUES = "values";

    private static final String PARAMETER = "?";
    private static final String EQUALS_PARAM = "=?";
    private static final String COMMA = ",";
    private static final String OPEN_BRACKET = "(";
    private static final String CLOSE_BRACKET = ") ";

    private final String names;
    private final String allNames;
    private String selectAllSQL = "";
    private String selectByIdSQL = "";
    private String insertSQL = "";
    private String updateSQL = "";

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        table = entityClassMetaData.getName();
        idColumn = entityClassMetaData.getIdField().getName();
        names = createNames();
        allNames = createAllNames();
    }

    @Override
    public String getSelectAllSql() {
        if (selectAllSQL.isEmpty()) {
            selectAllSQL = SELECT + names + FROM + table;
        }

        return selectAllSQL;
    }

    @Override
    public String getSelectByIdSql() {
        if (selectByIdSQL.isEmpty()) {
            selectByIdSQL = SELECT + allNames + FROM + table + WHERE + idColumn + EQUALS_PARAM;
        }

        return selectByIdSQL;
    }

    @Override
    public String getInsertSql() {
        if (insertSQL.isEmpty()) {
            final StringBuilder sb = new StringBuilder();
            final StringBuilder params = new StringBuilder(PARAMETER);
            for (int i = 1; i < entityClassMetaData.getFieldsWithoutId().size(); i++) {
                params.append(COMMA).append(PARAMETER);
            }

            insertSQL = INSERT_INTO + table + OPEN_BRACKET + names + CLOSE_BRACKET + VALUES + OPEN_BRACKET + params
                    + CLOSE_BRACKET;
        }

        return insertSQL;
    }

    @Override
    public String getUpdateSql() {
        if (updateSQL.isEmpty()) {
            final String names = createParameters();

            updateSQL = UPDATE + table + SELECT + names + WHERE + idColumn + EQUALS_PARAM;
        }

        return updateSQL;
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
