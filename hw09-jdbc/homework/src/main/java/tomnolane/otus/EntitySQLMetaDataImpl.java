package tomnolane.otus;

import tomnolane.otus.jdbc.mapper.EntityClassMetaData;
import tomnolane.otus.jdbc.mapper.EntitySQLMetaData;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {
    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaDataManager) {
    }

    @Override
    public String getSelectAllSql() {
        return "";
    }

    @Override
    public String getSelectByIdSql() {
        return "";
    }

    @Override
    public String getInsertSql() {
        return "";
    }

    @Override
    public String getUpdateSql() {
        return "";
    }
}
