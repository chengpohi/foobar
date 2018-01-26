import tensorflow as tf

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
