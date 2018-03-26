import re

import jieba

path = "/Users/xiachen/IdeaProjects/scala99/model/nmt/en-zh/"

punct = set(u''':!),.:;?]}¢'"、。〉》」』】〕〗〞︰︱︳﹐､﹒
﹔﹕﹖﹗﹚﹜﹞！），．：；？｜｝︴︶︸︺︼︾﹀﹂﹄﹏､～￠
々‖•·ˇˉ―--′’”([{£¥'"‵〈《「『【〔〖（［｛￡￥〝︵︷︹︻
︽︿﹁﹃﹙﹛﹝（｛“‘-—_…''')


def tokenizer_file(file_name, read):
    res = [re.sub(' +', ' ', " ".join(jieba.cut(line)).strip().lower()) for line in read]
    res = [re.sub('\' ', '\'', line) for line in res]
    write_to_file(file_name, res)


def write_to_file(file_name, res):
    print("%s lens: %d" % (file_name, len(res)))
    with open(file_name, mode="w") as f:
        f.writelines("\n".join(filter(None, res)))
