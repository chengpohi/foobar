import json
from pprint import pprint

from elasticsearch import Elasticsearch

# es = Elasticsearch("http://10.202.128.190:9200")
es = Elasticsearch("http://localhost:9200")
print(es.cat.indices())


def analyze_print(analyzer=None, tokenizer=None, content=None, index=None):
    body = {"text": content}
    if tokenizer is not None:
        body["tokenizer"] = tokenizer
    if analyzer is not None:
        body["analyzer"] = analyzer
    res = es.indices.analyze(index, body=body)
    print("-" * 20)
    print(json.dumps(res, sort_keys=True, indent=True, ensure_ascii=False))
    print("-" * 20)


analyze_print(analyzer="smartcn", content="招盈六号")

# analyze_print("ik_max_word", "美国留给伊拉克的是个烂摊子吗")

if es.indices.exists(index="test-index"):
    es.indices.delete(index="test-index")
es.indices.create(index="test-index", body={
    "settings": {
        "number_of_shards": 1,
        "analysis": {
            "analyzer": {
                "city_name": {
                    "type": "custom",
                    "tokenizer": "keyword",
                    "filter": ["lowercase", "asciifolding"]
                },
                "fnLowerReverse": {
                    "type": "custom",
                    "tokenizer": "keyword",
                    "filter": ["lowercase", "reverse"]
                },
                "patternAnalyzer": {
                    "char_filter": [
                        "my_char_filter"
                    ],
                    "tokenizer": "pattern_tokenizer",
                },
                "product_name_analyzer": {
                    "type": "custom",
                    "tokenizer": "whitespace",
                    "filter": [
                        "my_synonyms"
                    ]
                },
                "analyzer_shingle": {
                    "tokenizer": "standard",
                    "filter": ["standard", "lowercase", "filter_stop", "filter_shingle"]
                }
            },
            "tokenizer": {
                "pattern_tokenizer": {
                    "type": "pattern",
                    "pattern": ",\s*"
                }
            },
            "char_filter": {
                "my_char_filter": {
                    "type": "pattern_replace",
                    "pattern": "(\\d+)-(?=\\d)",
                    "replacement": "$1_"
                }
            },
            "filter": {
                "my_synonyms": {
                    "type": "synonym",
                    "synonyms": [  # or use synonyms_path
                        "foo => bar",
                        "baz => bot, blah",
                    ]
                },
                "filter_shingle": {
                    "type": "shingle",
                    "max_shingle_size": 5,
                    "min_shingle_size": 2,
                    "output_unigrams": "true"
                },
                "filter_stop": {
                    "type": "stop",
                }
            }
        }
    },
    "mappings": {
        "qa": {
            "_source": {
                "enabled": True
            },
            "properties": {
                "title": {
                    "type": "text",
                    "analyzer": "city_name",
                },
                "fileName": {
                    "type": "text",
                    "analyzer": "fnLowerReverse"
                },
                "tags": {
                    "type": "text",
                    "analyzer": "patternAnalyzer"
                }
            }
        }
    }
})

analyze_print(analyzer="city_name", content="ã", index="test-index")
analyze_print(analyzer="fnLowerReverse", content="zip", index="test-index")
analyze_print(analyzer="patternAnalyzer", content="123-456tag1,tag2,  tag3", index="test-index")
analyze_print(analyzer="product_name_analyzer", content="foo bar baz", index="test-index")
es.index(index="test-index", doc_type="qa", body={
    "fileName": "test.zip",
    "tags": "tag1,tag2, tag3"
})

es.indices.refresh(index="test-index")
pprint(es.search(index="test-index", doc_type="qa", body={
    "query": {
        "match_phrase_prefix": {
            "fileName": ".zip"
        }
    }
}))


analyze_print(content="hello", tokenizer="nGram")
analyze_print(analyzer="analyzer_shingle", content="foo bar baz", index="test-index")

es.indices.delete(index="test-index")
es.indices.delete(index=".kibana")
