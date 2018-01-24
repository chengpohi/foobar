import tensorflow as tf

sess = tf.InteractiveSession()

# 3 * 3
print(tf.reshape(list(range(0, 9)), [3, 3]).eval())

print("-----" * 20)
print(tf.reshape(list(range(0, 9)), [3, 3]).eval())

print("-----" * 20)
r = tf.reshape(list(range(0, 9)), [3, 3]).eval()

print("-----" * 20)
print(tf.reshape(r, [-1]).eval())

print("-----" * 20)
print(tf.reshape(r, [3, 3]).eval())

print("-----" * 20)
print(tf.reshape(list(range(0, 18)), [2, 3, 3]).eval())
print("-----" * 20)
print(tf.reshape(list(range(0, 18)), [2, 3, 3, 1]).eval())

print("-----" * 20)
print(tf.reshape(list(range(0, 18)), [1, 3, 3, 2]).eval())

print("-----" * 20)
print(tf.reshape(list(range(0, 18)), [2, 1, 3, 3]).eval())

print("-----" * 20)
print(tf.reshape(list(range(0, 18)), [2, 3, 1, 3]).eval())
