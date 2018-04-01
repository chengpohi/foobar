import tensorflow as tf

W = tf.Variable([.3], tf.float32)
b = tf.Variable([-.3], tf.float32)

x = tf.placeholder(tf.float32)

linear_model = W * x + b


sess = tf.InteractiveSession()

tf.global_variables_initializer().run()

print(sess.run(linear_model, {x: [1, 2, 3, 4]}))

y = tf.placeholder(tf.float32)
squared_deltas = tf.square(linear_model - y)
loss = tf.reduce_sum(squared_deltas)

print(sess.run(loss, {x: [1, 2, 3, 4], y: [0., -1, -2, -3]}))

fixW = tf.assign(W, [-1.])
fixB = tf.assign(b, [1.])
sess.run([fixW, fixB])
print(sess.run(loss, {x: [1, 2, 3, 4], y: [0., -1, -2, -3]}))

optimizer = tf.train.GradientDescentOptimizer(0.01)

train = optimizer.minimize(loss)

tf.global_variables_initializer().run()

for i in range(1000):
    sess.run(train, {x: [1, 2, 3, 4], y: [0., -1, -2, -3]})

print(sess.run([W, b]))

train = optimizer.minimize(loss)

writer = tf.summary.FileWriter("./test", sess.graph)

writer.close()


