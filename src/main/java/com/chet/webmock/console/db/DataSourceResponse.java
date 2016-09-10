package com.chet.webmock.console.db;

import java.util.List;
import java.util.stream.Collectors;

public class DataSourceResponse {
	private List<DatasourceDetails> datasource;

	/**
	 * @return the datasource
	 */
	public List<DatasourceDetails> getDatasource() {
		return datasource;
	}

	/**
	 * @param datasource the datasource to set
	 */
	public void setDatasource(List<DatasourceDetails> datasource) {
		this.datasource = datasource;
	}

	@Override
	public String toString() {
		return datasource.stream().map(i -> i.toString()).collect(Collectors.joining(",", "[", "]"));
	}
}
