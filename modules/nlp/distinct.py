import json


input_file = "./circ.json"
target_file = "./result.json"
duplicate_key = "title"
res = []
with open(input_file, mode='r', encoding="utf-8") as fp:
    model = json.load(fp)
    print(len(model))
    for i in model:
        notEmpty = [obj for obj in res if obj[duplicate_key] == i[duplicate_key]]
        if not notEmpty:
            res.append(i)

print(len(res))
with open(target_file, mode="w+", encoding="utf-8") as fp:
    fp.write(json.dumps(res, ensure_ascii=False, sort_keys=True, indent=4))
