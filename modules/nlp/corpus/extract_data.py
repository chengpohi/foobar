from bs4 import BeautifulSoup

dir = "/Users/xiachen/IdeaProjects/scala99/model/nmt/en-zh/"
en_res = []
zh_res = []


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


def main(_):
    for i in range(2010, 2016):
        en_file = dir + "IWSLT17.TED.tst%s.en-zh.en.xml" % i
        zh_file = dir + "IWSLT17.TED.tst%s.en-zh.zh.xml" % i
        en_res.extend(extract_file(en_file))
        zh_res.extend(extract_file(zh_file))

    write_to_file("raw.en.1", en_res)
    write_to_file("raw.zh.1", zh_res)


if __name__ == '__main__':
    main()
