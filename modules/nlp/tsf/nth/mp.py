import tensorflow as tf

sess = tf.InteractiveSession()

l = [[
    [[0], [1], [2], [3]],
    [[4], [5], [6], [7]],
    [[8], [9], [10], [11]],
]]

print(tf.nn.max_pool(l,
                     ksize=[1, 2, 2, 1],
                     strides=[1, 2, 2, 1],
                     padding='SAME').eval()
      )
