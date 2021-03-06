// Copyright 2017, Yahoo Holdings Inc.
// Licensed under the terms of the Apache License 2.0. Please see LICENSE file in project root for terms.
package com.yahoo.maha.maha_druid_lookups.server.lookup.namespace.entity;

import io.druid.java.util.common.logger.Logger;
import com.yahoo.maha.maha_druid_lookups.query.lookup.namespace.JDBCExtractionNamespace;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RowMapper implements ResultSetMapper<Void> {

    private static final Logger LOG = new Logger(RowMapper.class);
    private JDBCExtractionNamespace extractionNamespace;
    private Map<String, List<String>> cache;

    public RowMapper(JDBCExtractionNamespace extractionNamespace, Map<String, List<String>> cache) {
        this.extractionNamespace = extractionNamespace;
        this.cache = cache;
    }

    @Override
    public Void map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {

        List<String> strings = new ArrayList<>(extractionNamespace.getColumnList().size());
        for(String columnName: extractionNamespace.getColumnList()) {
            strings.add(resultSet.getString(columnName));
        }
        cache.put(resultSet.getString(extractionNamespace.getPrimaryKeyColumn()), strings);
        return null;
    }
}
