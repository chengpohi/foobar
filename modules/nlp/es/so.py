from datetime import datetime
from xml.etree import ElementTree

import os
from elasticsearch import Elasticsearch, helpers

es = Elasticsearch("http://localhost:9200")
print(es.cat.indices())

"""
<row Id="4" PostTypeId="1" AcceptedAnswerId="7" CreationDate="2008-07-31T21:42:52.667" Score="441" ViewCount="29333" Body="&lt;p&gt;I want to use a track-bar to change a form's opacity.&lt;/p&gt;&#xA;&#xA;&lt;p&gt;This is my code:&lt;/p&gt;&#xA;&#xA;&lt;pre&gt;&lt;code&gt;decimal trans = trackBar1.Value / 5000;&#xA;this.Opacity = trans;&#xA;&lt;/code&gt;&lt;/pre&gt;&#xA;&#xA;&lt;p&gt;When I try to build it, I get this error:&lt;/p&gt;&#xA;&#xA;&lt;blockquote&gt;&#xA;  &lt;p&gt;Cannot implicitly convert type 'decimal' to 'double'.&lt;/p&gt;&#xA;&lt;/blockquote&gt;&#xA;&#xA;&lt;p&gt;I tried making &lt;code&gt;trans&lt;/code&gt; a &lt;code&gt;double&lt;/code&gt;, but then the control doesn't work. This code has worked fine for me in VB.NET in the past. &lt;/p&gt;&#xA;" OwnerUserId="8" LastEditorUserId="5455605" LastEditorDisplayName="Rich B" LastEditDate="2015-12-23T21:34:28.557" LastActivityDate="2016-07-17T20:33:18.217" Title="When setting a form's opacity should I use a decimal or double?" Tags="&lt;c#&gt;&lt;winforms&gt;&lt;type-conversion&gt;&lt;decimal&gt;&lt;opacity&gt;" AnswerCount="13" CommentCount="3" FavoriteCount="36" CommunityOwnedDate="2012-10-31T16:42:47.213" />
"""

index_name = "so"
ANSWER_POST_TYPE = "2"
QUESTION_POST_TYPE = "1"

if es.indices.exists(index=index_name):
    es.indices.delete(index=index_name)

with open(os.path.dirname(__file__) + '/stopwords.txt') as f:
    stopwords = [l.strip() for l in f.readlines()]

es.indices.create(index=index_name, body={
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

# without bulk 1900000: nearly 50mins
# bulk with size: 20000: nearly 5mins
# so use queue to cache index docs
# bulk files: 2g
# final index size: 4g
# when delete es, it will immediately delete index
start = datetime.now()
with open("/Users/xiachen/IdeaProjects/data/tail.xml", "r") as ins:
    i = 0
    res = []
    for line in ins:
        if "<row" in line:
            try:
                tree = ElementTree.fromstring(line)
                i = i + 1
                print(i)
                if i >= 300000:
                    break
                doc = tree.attrib
                res.append({"_index": index_name,
                            "_id": doc["Id"],
                            "_type": "question",
                            "_source": doc
                            })
                if i % 20000 == 0:
                    helpers.bulk(es, res)
                    res = []
                if i == 300000:
                    break
            except Exception as e:
                continue

helpers.bulk(es, res)
tt = datetime.now() - start
print(tt)
