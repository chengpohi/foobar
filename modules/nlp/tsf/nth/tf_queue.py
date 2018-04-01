from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import tensorflow as tf

dummy_input = tf.random_normal([5], mean=0, stddev=1)
dummy_input = tf.Print(dummy_input, data=[dummy_input],
                       message='New dummy inputs have been created: ', summarize=6)
q = tf.FIFOQueue(capacity=3, dtypes=tf.float32)
enqueue_op = q.enqueue_many(dummy_input)
# now setup a queue runner to handle enqueue_op outside of the main thread asynchronously
qr = tf.train.QueueRunner(q, [enqueue_op] * 1)
tf.train.add_queue_runner(qr)

data = q.dequeue()
data = tf.Print(data, data=[q.size(), data], message='This is how many items are left in q: ')
# create a fake graph that we can call upon
fg = data + 1

with tf.Session() as sess:
    coord = tf.train.Coordinator()
    threads = tf.train.start_queue_runners(coord=coord)
    # now dequeue a few times, and we should see the number of items
    # in the queue decrease
    sess.run(fg)
    sess.run(fg)
    sess.run(fg)
    # previously the main thread blocked / hung at this point, as it was waiting
    # for the queue to be filled.  However, it won't this time around, as we
    # now have a queue runner on another thread making sure the queue is
    # filled asynchronously
    sess.run(fg)
    sess.run(fg)
    sess.run(fg)
    # this will print, but not necessarily after the 6th call of sess.run(fg)
    # due to the asynchronous operations
    print("We're here!")
    # we have to request all threads now stop, then we can join the queue runner
    # thread back to the main thread and finish up
    coord.request_stop()
    coord.join(threads)
