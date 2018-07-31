package com.qfang.examples.cloud;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author huxianyong
 * @date 2017/7/20
 * @since 1.0
 */
public abstract class SolrTemplate<T> {
    private static final Logger logger=Logger.getLogger(SolrTemplate.class);

    private SolrConfig solrConfig;

    protected Object execute(Function<CloudSolrClient,Object> solrCallBack){
        CloudSolrClient solrClient=CloudSolrFactory.getCloudSolrClient(solrConfig);
        return solrCallBack.apply(solrClient);
    }

    public abstract SolrInputDocument entityToSolrDocument(T entity);
    public abstract T solrDocumentToEntity(SolrDocument document);

    public  void save(T entity,Boolean isCommit){
        saveList(new ArrayList<T>(){{add(entity);}}, isCommit);
    }

    public  void saveList(List<T> entitys,Boolean isCommit){
        if(entitys==null || entitys.size()==0){
            return ;
        }
        final List<SolrInputDocument> documents=entitys.stream().map( obj->entityToSolrDocument(obj)).collect(Collectors.toList());
        this.execute(solrClient-> {
            try {
                solrClient.add(documents);
                solrClient.commit(isCommit,isCommit);
            } catch (SolrServerException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return true;
        });
    }

    public Pagination<T> query(SolrQuery solrQuery,Pagination<T> pagination){
        return (Pagination<T>) this.execute(solrClient ->{
            if(pagination != null && pagination.getSortField() != null) {
                solrQuery.addSort(pagination.getSortField(), SolrQuery.ORDER.desc);
            }
            solrQuery.setStart(pagination.getCurrentPageStartIndex());
            solrQuery.setRows(pagination.getPageSize());

            logger.info(solrQuery.toString());

            try {
                QueryResponse queryResponse= solrClient.query(solrQuery);
                SolrDocumentList solrDocuments=queryResponse.getResults();
                List<T> list=solrDocuments.stream().map(document->solrDocumentToEntity(document)).collect(Collectors.toList());
                int totalCount = Long.valueOf(queryResponse.getResults().getNumFound()).intValue();
                pagination.setItems(list);
                pagination.setRecordCount(totalCount);

            } catch (SolrServerException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return pagination;
        });
    }

    protected  void deleteList(List<String> entityIds){
        if(entityIds==null || entityIds.size()==0){
            return ;
        }
        this.execute(solrClient ->{
            try {
                solrClient.deleteById(entityIds);
                solrClient.commit();
            } catch (SolrServerException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return true;
        });
    }

    public SolrConfig getSolrConfig() {
        return solrConfig;
    }

    public void setSolrConfig(SolrConfig solrConfig) {
        this.solrConfig = solrConfig;
    }
}
