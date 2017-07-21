# qfang-examples-solr
#部署solrcloud
1、下载solr安装文件 http://www.apache.org/dyn/closer.lua/lucene/solr/6.3.0。
2、解压solr tar zxvf solr-6.3.0.zip\
3、安装并启动 zookeeper\
4、启动solr节点1 ./bin/solr restart -cloud -s /solr数据文件目录1 -p 8984 -z 192.168.0.40:2181/erpsolr\
5、启动solr节点2 ./bin/solr restart -cloud -s /solr数据文件目录2 -p 8983  -z 192.168.0.40:2181/erpsolr\
6、创建core   ./bin/solr create -c tradeSearch -d data_driven_schema_configs -s 3 -rf 3 -n tradeSearchConf\
7、修改配置文件\
（下载配置）./bin/solr zk -downconfig -z 192.168.0.40:2181/erpsolr -n tradeSearchConf -d /data/solr/tradeSearchConf\
（上传配置）./bin/solr zk -upconfig -z 192.168.0.40:2181/erpsolr -n tradeSearchConf  -d /data/solr/tradeSearchConf

