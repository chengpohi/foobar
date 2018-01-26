import numpy
import numpy as np
import tensorflow as tf
import matplotlib.pyplot as plt


# linear regression:
# y = a*x + b
# given a train data: [(x, y) ...]
# and find a best linear algebra to classify data

def model_fn(features, labels, mode):
    W = tf.get_variable("W", [1], dtype=tf.float64)
    b = tf.get_variable("b", [1], dtype=tf.float64)
    y = W * features['x'] + b
    # Loss sub-graph
    loss = tf.reduce_sum(tf.square(y - labels))
    # Training sub-graph
    train = build_train(loss)
    # EstimatorSpec connects subgraphs we built to the
    # appropriate functionality.
    return tf.estimator.EstimatorSpec(
        mode=mode,
        predictions=y,
        loss=loss,
        train_op=train)


def build_train(loss):
    global_step = tf.train.get_global_step()
    optimizer = tf.train.GradientDescentOptimizer(0.01)
    train = tf.group(optimizer.minimize(loss),
                     tf.assign_add(global_step, 1))
    return train


estimator = tf.estimator.Estimator(model_fn=model_fn)

# (x, y)
x_train = np.array([1., 2., 3., 4.])
y_train = np.array([0., -1., -2., -3.])

# (x, y)
x_eval = np.array([2., 5., 8., 1.])
y_eval = np.array([-1.01, -4.1, -7., 0.])

input_fn = tf.estimator.inputs.numpy_input_fn(
    {"x": x_train}, y_train, batch_size=4, num_epochs=None, shuffle=True)

train_input_fn = tf.estimator.inputs.numpy_input_fn(
    {"x": x_train}, y_train, batch_size=4, num_epochs=1000, shuffle=False)

eval_input_fn = tf.estimator.inputs.numpy_input_fn(
    {"x": x_eval}, y_eval, batch_size=4, num_epochs=1000, shuffle=False)

estimator.train(input_fn=input_fn, steps=1000)

train_metrics = estimator.evaluate(input_fn=train_input_fn)
eval_metrics = estimator.evaluate(input_fn=eval_input_fn)
print("Loss: %s" % train_metrics["loss"])
print("Loss: %s" % eval_metrics["loss"])
print("train metrics: %r" % train_metrics)
print("eval metrics: %r" % eval_metrics)

W = estimator.get_variable_value("W")
b = estimator.get_variable_value("b")
print(W)
print(b)
plt.plot(x_eval, y_eval, 'bo', label='Testing data')
plt.plot(numpy.asarray(x_eval), W * numpy.asarray(x_eval) + b, label="Fited line")
plt.legend()
plt.show()
