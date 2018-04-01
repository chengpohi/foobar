import tensorflow as tf

a = tf.placeholder(tf.float32)
b = tf.placeholder(tf.float32)

adder_node = a + b

sess = tf.InteractiveSession()

tf.global_variables_initializer().run()

result = sess.run(adder_node, {a: 2., b: 3.})

print(result)

result = sess.run(adder_node, {a: [2., 5.], b: [3., 6.]})
print(result)

with tf.variable_scope("triple_adder"):
    triple_adder = adder_node * 3

print(sess.run(triple_adder, {a: 2., b: 3.}))

c = tf.multiply(a, b, name="multiply_a_b")

output = sess.run(c, {a: 2., b: 3.})

writer = tf.summary.FileWriter("./my_graph", sess.graph)

writer.close()
sess.close()
