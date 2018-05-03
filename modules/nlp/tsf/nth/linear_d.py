import tensorflow as tf

W = tf.Variable([.3], tf.float32, name="W")
b = tf.Variable([-.3], tf.float32, name="b")
x = tf.placeholder(tf.float32, name="x")

linear_model = W * x + b
y = tf.placeholder(tf.float32, name="y")

square_delta = tf.square(linear_model - y)
loss = tf.reduce_sum(square_delta)

optimizer = tf.train.GradientDescentOptimizer(0.01)
train = optimizer.minimize(loss)

sess = tf.InteractiveSession()

tf.global_variables_initializer().run()

for i in range(1000):
    sess.run(train, {x: [0, 1, 2, 3], y: [-1, -2, -3, -4]})

print(sess.run([W, b]))

writer = tf.summary.FileWriter("/Users/xiachen/IdeaProjects/scala99/model/test/", sess.graph)
writer.close()
