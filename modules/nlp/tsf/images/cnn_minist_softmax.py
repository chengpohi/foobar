from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

from pprint import pprint
from subprocess import Popen

import numpy as np
import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data
import matplotlib.pyplot as plt

tf.logging.set_verbosity(tf.logging.INFO)


# input -> cov1 -> conv2 -> pool -> flatten -> dense -> dropout -> logit -> cross_entropy
def cnn_model_fn(features, labels, mode):
    input_layer = tf.reshape(features['x'], [-1, 28, 28, 1])
    conv1 = tf.layers.conv2d(inputs=input_layer,
                             filters=32,
                             kernel_size=[5, 5],
                             padding="same",
                             activation=tf.nn.relu)
    pool1 = tf.layers.max_pooling2d(inputs=conv1, pool_size=[2, 2], strides=2)
    #
    conv2 = tf.layers.conv2d(inputs=pool1,
                             filters=64,
                             kernel_size=[5, 5],
                             padding="same",
                             activation=tf.nn.relu)

    pool2 = tf.layers.max_pooling2d(inputs=conv2, pool_size=[2, 2], strides=2)

    pool2_flat = tf.reshape(pool2, [-1, 7 * 7 * 64])

    # fully connected layers
    dense = tf.layers.dense(inputs=pool2_flat, units=1024, activation=tf.nn.relu)
    # 0.4 it means 40% of the elements will be randomly dropped
    dropout = tf.layers.dropout(inputs=dense,
                                rate=0.4,
                                training=mode == tf.estimator.ModeKeys.TRAIN)
    logits = tf.layers.dense(inputs=dropout, units=10)

    predictions = {
        "classes": tf.argmax(input=logits, axis=1),
        "probabilities": tf.nn.softmax(logits, name="softmax_tensor")
    }

    if mode == tf.estimator.ModeKeys.PREDICT:
        return tf.estimator.EstimatorSpec(mode=mode, predictions=predictions)

    loss = tf.losses.sparse_softmax_cross_entropy(labels=labels, logits=logits)

    if mode == tf.estimator.ModeKeys.TRAIN:
        optimizer = tf.train.GradientDescentOptimizer(learning_rate=0.001)
        train_op = optimizer.minimize(loss=loss, global_step=tf.train.get_global_step())
        return tf.estimator.EstimatorSpec(mode=mode, loss=loss, train_op=train_op)

    eval_metric_ops = {
        "accuracy": tf.metrics.accuracy(
            labels=labels,
            predictions=predictions["classes"]
        )
    }

    return tf.estimator.EstimatorSpec(mode=mode, loss=loss, eval_metric_ops=eval_metric_ops)


def main(unused_argv):
    p = Popen(['tensorboard', '--logdir=/Users/xiachen/IdeaProjects/scala99/model/tensorflow/cnn_mnist'])
    mnist = input_data.read_data_sets("/Users/xiachen/IdeaProjects/scala99/model/tensorflow/cnn_mnist")

    train_data = mnist.train.images
    train_labels = np.asarray(mnist.train.labels, dtype=np.int32)
    eval_data = mnist.test.images
    eval_labels = np.asarray(mnist.test.labels, dtype=np.int32)
    mnist_classifier = tf.estimator.Estimator(model_fn=cnn_model_fn,
                                              model_dir="/Users/xiachen/IdeaProjects/scala99/model/tensorflow/cnn_mnist"
                                              )
    tensors_to_log = {"probabilities": "softmax_tensor"}
    logging_hook = tf.train.LoggingTensorHook(tensors=tensors_to_log, every_n_iter=50)
    train_input_fn = tf.estimator.inputs.numpy_input_fn(
        x={"x": train_data},
        y=train_labels,
        batch_size=100,
        num_epochs=None,
        shuffle=True
    )
    # mnist_classifier.train(
    #     input_fn=train_input_fn,
    #     steps=20000,
    #     hooks=[logging_hook]
    # )
    # eval_input_fn = tf.estimator.inputs.numpy_input_fn(
    #     x={"x": eval_data},
    #     y=eval_labels,
    #     num_epochs=1,
    #     shuffle=False
    # )
    #
    # eval_results = mnist_classifier.evaluate(input_fn=eval_input_fn)
    # print(eval_results)

    predict_input_fn = tf.estimator.inputs.numpy_input_fn(
        x={"x": eval_data[:5]},
        num_epochs=1,
        shuffle=False
    )

    # for i in eval_data[:5]:
    #     plt.imshow(i.reshape(28, 28))
    #     plt.show()
    results = mnist_classifier.predict(input_fn=predict_input_fn)
    pprint(list(results))
    p.communicate()


if __name__ == "__main__":
    tf.app.run()
