import jieba

path = "/Users/xiachen/IdeaProjects/scala99/model/nmt/en-zh/"


def tokenizer_file(file_name, read):
    res = [" ".join(jieba.cut(line)).strip() for line in read]
    write_to_file(file_name, res)


def write_to_file(file_name, res):
    print("%s lens: %d" % (file_name, len(res)))
    with open(file_name, mode="w") as f:
        f.writelines("\n".join(filter(None, res)))
