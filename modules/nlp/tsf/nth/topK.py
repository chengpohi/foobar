import tensorflow as tf

logits = tf.Variable(tf.truncated_normal(shape=[10, 5], stddev=1.0))
labels = tf.constant([0, 0, 0, 0, 0, 0, 0, 0, 0, 0])

top_1_op = tf.nn.in_top_k(logits, labels, 1)
top_2_op = tf.nn.in_top_k(logits, labels, 2)

with tf.Session() as sess:
    sess.run(tf.global_variables_initializer())

    print(logits.eval())
    print(labels.eval())
    print(top_1_op.eval())
    print(top_2_op.eval())

