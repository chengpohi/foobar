# coding:utf-8
import gensim
from gensim.models import Word2Vec
from nltk.tokenize import RegexpTokenizer


def load():
    ss = []
    for line in open('../../model/corpus/movie_lines.txt'):
        sentence = line.split('+++$+++ ')[-1]
        tokenizer = RegexpTokenizer(r'\w+')
        res = tokenizer.tokenize(sentence)
        ss.append(res)
    return ss


def train(ss):
    m = Word2Vec(ss, size=100, window=5, min_count=5, workers=4)
    m.save('../../model/corpus/movie_lines_word2vec.bin')
    return m


def train_with_phrase(ss):
    bigram_transformer = gensim.models.Phrases(sentences)
    model = Word2Vec(bigram_transformer[sentences], size=100)


def test(model):
    result = model.accuracy('../../model/corpus/questions-words.txt')
    print(result)


sentences = load()
print('Total sentences: %-8s' % len(sentences))
print('Start training sentences')
model = train(sentences)
test(model)
print(model.wv['like'])
model.wv.most_similar(positive=['woman', 'king'], negative=['man'])
model.wv.most_similar_cosmul(positive=['woman', 'king'], negative=['man'])

model.wv.doesnt_match("breakfast cereal dinner lunch".split())

model.wv.similarity('woman', 'man')
model.wv.similarity('you', 'I')
