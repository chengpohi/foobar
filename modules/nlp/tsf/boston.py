from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import itertools
from urllib.request import urlopen

import os
import pandas as pd
import tensorflow as tf

tf.logging.set_verbosity(tf.logging.INFO)


def download_data(url, file_name):
    if not os.path.exists(file_name):
        os.makedirs(os.path.dirname(file_name), exist_ok=True)
        raw = urlopen(url).read()
        with open(file_name, "wb") as f:
            f.write(raw)


BOSTON_TRAIN_URL = "http://download.tensorflow.org/data/boston_train.csv"
BOSTON_TRAIN = "../../../model/tensorflow/boston/boston_train.csv"
BOSTON_TEST_URL = "http://download.tensorflow.org/data/boston_test.csv"
BOSTON_TEST = "../../../model/tensorflow/boston/boston_test.csv"
BOSTON_PREDICT_URL = "http://download.tensorflow.org/data/boston_predict.csv"
BOSTON_PREDICT = "../../../model/tensorflow/boston/boston_predict.csv"

download_data(BOSTON_TRAIN_URL, BOSTON_TRAIN)
download_data(BOSTON_TEST_URL, BOSTON_TEST)
download_data(BOSTON_PREDICT_URL, BOSTON_PREDICT)

COLUMNS = ["crim", "zn", "indus", "nox", "rm", "age",
           "dis", "tax", "ptratio", "medv"]
FEATURES = ["crim", "zn", "indus", "nox", "rm",
            "age", "dis", "tax", "ptratio"]
LABEL = "medv"

training_set = pd.read_csv(BOSTON_TRAIN, skipinitialspace=True,
                           skiprows=1, names=COLUMNS)
test_set = pd.read_csv(BOSTON_TEST, skipinitialspace=True,
                       skiprows=1, names=COLUMNS)
prediction_set = pd.read_csv(BOSTON_PREDICT, skipinitialspace=True,
                             skiprows=1, names=COLUMNS)

feature_cols = [tf.feature_column.numeric_column(k) for k in FEATURES]

regressor = tf.estimator.DNNRegressor(feature_columns=feature_cols,
                                      hidden_units=[10, 10],
                                      model_dir="../../../model/tensorflow/boston")


def get_input_fn(data_set, num_epochs=None, shuffle=True):
    return tf.estimator.inputs.pandas_input_fn(
        x=pd.DataFrame({k: data_set[k].values for k in FEATURES}),
        y=pd.Series(data_set[LABEL].values),
        num_epochs=num_epochs,
        shuffle=shuffle)


regressor.train(input_fn=get_input_fn(training_set), steps=5000)

ev = regressor.evaluate(
    input_fn=get_input_fn(test_set, num_epochs=1, shuffle=False))

loss_score = ev["loss"]
print("Loss: {0:f}".format(loss_score))

y = regressor.predict(input_fn=get_input_fn(prediction_set, num_epochs=1, shuffle=False))

predictions = list(p["predictions"] for p in itertools.islice(y, 6))

print("Predictions: {}".format(predictions))
