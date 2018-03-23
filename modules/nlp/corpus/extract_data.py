from bs4 import BeautifulSoup

from corpus.corpus_tokenizer import tokenizer_file

dir = "/Users/xiachen/IdeaProjects/data/en-zh/"


def extract_file(file_name):
    result = []
    with open(file_name, mode="r", encoding="utf-8") as f:
        e = BeautifulSoup(f, features="html5lib")
        result.append(e.find("description").text)
        result.append(e.find("title").text)
        for atype in e.findAll('seg'):
            result.append(atype.text)
    return result


def write_to_file(file_name, res):
    with open(file_name, mode="w") as f:
        f.writelines("\n".join([s.strip() for s in res]))


def get_TED_corpus():
    en_res = []
    zh_res = []

    for i in range(2010, 2016):
        print(i)
        en_file = dir + "IWSLT17.TED.tst%s.en-zh.en.xml" % i
        zh_file = dir + "IWSLT17.TED.tst%s.en-zh.zh.xml" % i
        en_res.extend(extract_file(en_file))
        zh_res.extend(extract_file(zh_file))
    return (en_res, zh_res)


def main(args=None):
    (en_res, zh_res) = get_TED_corpus()

    en_train_corpus = en_res[0:5000]
    zh_train_corpus = zh_res[0:5000]
    en_dev_corpus = en_res[5000:6800]
    zh_dev_corpus = zh_res[5000:6800]
    en_test_corpus = en_res[6800:7882]
    zh_test_corpus = zh_res[6800:7882]
    target = "/Users/xiachen/IdeaProjects/scala99/model/nmt/en-zh/"
    tokenizer_file(target + "train.en", en_train_corpus)
    tokenizer_file(target + "train.zh", zh_train_corpus)
    tokenizer_file(target + "dev.en", en_dev_corpus)
    tokenizer_file(target + "dev.zh", zh_dev_corpus)
    tokenizer_file(target + "test.en", en_test_corpus)
    tokenizer_file(target + "test.zh", zh_test_corpus)


if __name__ == '__main__':
    main()
