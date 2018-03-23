import jieba


path = "/Users/xiachen/IdeaProjects/scala99/model/nmt/en-zh/"


def tokenizer_file(files):
    for file in files:
        with open(path + file, mode="r") as f:
            read = f.readlines()
            res = [" ".join(jieba.cut(line)).strip() for line in read]
            write_to_file(f.name.replace('raw-', ''), res)


def write_to_file(file_name, res):
    with open(file_name, mode="w") as f:
        f.writelines("\n".join(filter(None, res)))


en_files = ["raw-dev.en", "raw-test.en", "raw-train.en"]
zh_files = ["raw-dev.zh", "raw-test.zh", "raw-train.zh"]


tokenizer_file(en_files)
tokenizer_file(zh_files)
