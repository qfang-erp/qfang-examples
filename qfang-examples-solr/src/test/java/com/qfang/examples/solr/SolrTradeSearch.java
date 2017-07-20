package com.qfang.examples.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;


/**
 * 
 * @author huxianyong
 *
 */
public class SolrTradeSearch extends SolrTemplate<TradeSearch> implements Searcher<TradeSearch>{
	
	private final String ID="id";
	private final String NUMBER="number";
	private final String TENEMENTDETAIL="tenementDetail";
	private final String CITY="city";
	private final String TYPE="type";

	@Override
	public TradeSearch solrDocumentToEntity(SolrDocument solrDocument)  {
		TradeSearch tradeSearch=new TradeSearch();
		tradeSearch.setId((String) solrDocument.get(ID));
		tradeSearch.setType((String) solrDocument.get(TYPE));
		tradeSearch.setNumber((String) solrDocument.get(NUMBER));
		tradeSearch.setTenementDetail((String) solrDocument.get(TENEMENTDETAIL));
		tradeSearch.setCity((String) solrDocument.get(CITY));
		return tradeSearch;
	}

	@Override
	public SolrInputDocument  entityToSolrDocument(TradeSearch entity){
		if(entity==null){
			return null;
		}
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		solrInputDocument.setField(ID, entity.getId());
		solrInputDocument.setField(NUMBER, entity.getNumber());
		solrInputDocument.setField(TENEMENTDETAIL, entity.getId());
		solrInputDocument.setField(CITY, entity.getCity());
		solrInputDocument.setField(TYPE, entity.getType());
		return solrInputDocument;
	}

}
