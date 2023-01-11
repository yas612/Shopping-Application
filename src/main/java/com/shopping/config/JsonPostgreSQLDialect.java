package com.shopping.config;

import org.hibernate.dialect.PostgreSQL9Dialect;

import java.sql.Types;

/**
 * Wrap default PostgreSQL9Dialect with 'json' type.
 *
 * @author timfulmer
 */
public class JsonPostgreSQLDialect extends PostgreSQL9Dialect {

    public JsonPostgreSQLDialect() {

        super();

        this.registerColumnType(Types.JAVA_OBJECT, "json");
    }
}
