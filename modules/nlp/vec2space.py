# sentences -> dict -> doc2bow -> SimilarModel -> matrix -> calculate

from collections import defaultdict
from pprint import pprint

from gensim.corpora import Dictionary, MmCorpus
from gensim.models import *
from gensim.similarities import MatrixSimilarity, SparseMatrixSimilarity

stoplist = set('for a of the and to in'.split())

documents = ["Human machine interface for lab abc computer applications",
             "A survey of user opinion of computer system response time",
             "The EPS user interface management system",
             "System and human system engineering testing of EPS",
             "Relation of user perceived response time to error measurement",
             "The generation of random binary unordered trees",
             "The intersection graph of paths in trees",
             "Graph minors IV Widths of trees and well quasi ordering",
             "Graph minors A survey"]

frequency = defaultdict(int)  # type: defaultdict[Any, int]

# tokenize sentence and lowercase
texts = [[word for word in document.lower().split() if word not in stoplist]
         for document in documents]

# calculate the token frequency
for text in texts:
    for token in text:
        frequency[token] += 1

# filter token frequency > 1
# type: List[List[str]]
texts = [[token for token in text if frequency[token] > 1]
         for text in texts]

pprint(texts)

# build the dictionary by words
dictionary = Dictionary(texts)

dictionary.save('../../model/deerwester.dict')

print("Dictionary: ")
print(dictionary)

print(dictionary.token2id)

new_doc = "Human computer interaction"

# calc new vector of new_doc base on dictionary
new_vec = dictionary.doc2bow(new_doc.lower().split())

pprint(new_vec)

# type: List[Union[Tuple[List[Tuple[Any, Any]], Dict[Any, int]], List[Tuple[Any, Any]]]]
corpus = [dictionary.doc2bow(text) for text in texts]

MmCorpus.serialize('../../model/deerwester.mm', corpus)

print("corpus: ")
pprint(corpus)

tfidf = TfidfModel(corpus)

vec = [(0, 1), (4, 1)]
pprint(tfidf[vec])

index = SparseMatrixSimilarity(tfidf[corpus], num_features=12)

sims = index[tfidf[vec]]

print(list(enumerate(sims)))

# ----------------
doc = "Human machine interface for lab abc computer applications"
lsi = LsiModel(corpus, id2word=dictionary, num_topics=2)

vec_bow = dictionary.doc2bow(doc.lower().split())

# convert query to LSI space
vec_lsi = lsi[vec_bow]

# build matrixsimilarity for current lsi corpus
index = MatrixSimilarity(lsi[corpus])

sims = index[vec_lsi]
# sims = list(enumerate(index[vec_lsi]))
sims = sorted(enumerate(sims), key=lambda item: -item[1])

print(sims)
