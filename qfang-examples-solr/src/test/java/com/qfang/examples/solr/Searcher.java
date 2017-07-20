package com.qfang.examples.solr;

import java.util.List;

public interface Searcher<T> {
	void save(T entity,Boolean isCommit);
	void saveList(List<T> entitys,Boolean isCommit);
}
