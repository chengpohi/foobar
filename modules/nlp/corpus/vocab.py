import collections

import jieba

from corpus.extract_data import write_to_file

path = "/Users/xiachen/IdeaProjects/scala99/model/nmt/en-zh/"


def file_vocab(files):
    vocabs = []
    for file in files:
        with open(path + file, mode="r") as f:
            read = f.read()
            vocabs.extend([w.strip() for w in jieba.cut(read)])
    counter = collections.Counter(vocabs)
    count_pairs = sorted(counter.items(), key=lambda x: (-x[1], x[0]))
    words, _ = list(zip(*count_pairs))
    return words


en_files = ["dev.en", "test.en", "train.en"]
zh_files = ["dev.zh", "test.zh", "train.zh"]

write_to_file("/Users/xiachen/IdeaProjects/scala99/model/nmt/en-zh/vocab.en", filter(None, file_vocab(en_files)))
write_to_file("/Users/xiachen/IdeaProjects/scala99/model/nmt/en-zh/vocab.zh", filter(None, file_vocab(zh_files)))
