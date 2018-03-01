from elasticsearch import Elasticsearch, helpers

from es.so import stopwords

es = Elasticsearch("http://localhost:9200")

so_version = "so-v1"
es.indices.create(index=so_version, body={
    "settings": {
        "number_of_shards": 1,
        "analysis": {
            "analyzer": {
                "so_analyzer": {
                    "char_filter": [
                        "html_strip"
                    ],
                    "tokenizer": "standard",
                    "filter": ["standard", "lowercase", "my_stop"],
                }
            },
            "filter": {
                "my_stop": {
                    "type": "stop",
                    "stopwords": stopwords
                }
            },
        }
    },
    "mappings": {
        "question": {
            "properties": {
                "Title": {
                    "type": "text",
                    "analyzer": "so_analyzer",
                    "fielddata": True,
                },
                "Body": {
                    "type": "text",
                    "analyzer": "so_analyzer",
                    "fielddata": True
                },
            }
        }
    },
})
helpers.reindex(client=es, source_index="so", target_index=so_version)
