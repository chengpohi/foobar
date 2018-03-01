import tensorflow as tf
from tensorflow.python.data.ops.dataset_ops import TensorSliceDataset

sess = tf.InteractiveSession()

print(tf.reshape(list(range(0, 4)), [-1, 2, 2, 1]).eval())
print("-----" * 5)
print(tf.reshape(list(range(0, 9)), [3, 3]).eval())

print("-----" * 5)
r = tf.reshape(list(range(0, 9)), [3, 3]).eval()

print("-----" * 5)
print(tf.reshape(r, [-1]).eval())

print("-----" * 5)
print(tf.reshape(r, [3, 3]).eval())

print("-----" * 5)
print(tf.reshape(r, [9]).eval())

print("-----" * 5)
print(tf.reshape(list(range(0, 18)), [2, 3, 3]).eval())
print("-----" * 5)
print(tf.reshape(list(range(0, 18)), [2, 3, 3, 1]).eval())

print("-----" * 5)
print(tf.reshape(list(range(0, 18)), [1, 3, 3, 2]).eval())

print("-----" * 5)
print(tf.reshape(list(range(0, 18)), [2, 1, 3, 3]).eval())

print("-----" * 5)
print(tf.reshape(list(range(0, 18)), [2, 3, 1, 3]).eval())

dataset1: TensorSliceDataset = tf.data.Dataset.from_tensor_slices(tf.random_uniform([4, 10]))
print(dataset1.output_types)
print(dataset1.output_shapes)

dataset2: TensorSliceDataset = tf.data.Dataset.from_tensor_slices(
    (tf.random_uniform([4]),
     tf.random_uniform([4, 100], maxval=100, dtype=tf.int32)))

print(dataset2.output_types)
print(dataset2.output_shapes)

dataset = tf.data.Dataset.range(100)
iterator = dataset.make_one_shot_iterator()
next_element = iterator.get_next()

for i in range(100):
    value = sess.run(next_element)
    assert i == value

x = tf.placeholder(tf.float32)
y = tf.placeholder(tf.float32)

z = x + y

print(sess.run(z, feed_dict={x: 1, y: 2}))
print(sess.run(z, feed_dict={x: [1, 3], y: [2, 4]}))

my_data = [
    [0, 1, ],
    [2, 3, ],
    [4, 5, ],
    [6, 7, ],
]

slices = tf.data.Dataset.from_tensor_slices(my_data)
next_item = slices.make_one_shot_iterator().get_next()

while True:
    try:
        print(sess.run(next_item))
    except tf.errors.OutOfRangeError:
        break


x = tf.placeholder(tf.float32, shape=[None, 3])
y = tf.layers.dense(x, units=1)

init = tf.global_variables_initializer()
sess.run(init)

print(sess.run(y, {x: [[1, 2, 3], [4, 5, 6]]}))

constant = tf.constant([1, 2, 3])
tensor = constant * constant

print(tensor.eval())
