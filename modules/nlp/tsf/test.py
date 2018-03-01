from pprint import pprint

from elasticsearch import Elasticsearch

# es = Elasticsearch("http://10.202.128.190:9200")
es = Elasticsearch("http://localhost:9200")

qa_index_name = "qa-index"
qa_index_type = "qa"

for i in range(20):
    es.index(index=qa_index_name,
             doc_type=qa_index_type,
             body={
                 "title": "asdf",
                 "text": "Just trying this out...",
                 "published_date": "2018-01-01"
             })

res = es.search(index=qa_index_name, doc_type=qa_index_type, body={
    "from": 0,
    "size": 10000,
    "query": {
        "bool": {
            "filter": [
                # {"term": {"title": "asdf"}}
                {"range": {"published_date": {"gte": "2015-01-01"}}}
            ]}
    }
})

pprint(es.indices.stats(index=qa_index_name))

pprint(es.indices.clear_cache(index=qa_index_name))

pprint(es.count(index=qa_index_name, doc_type=qa_index_type))

es.indices.flush(index=qa_index_name)
