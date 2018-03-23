from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import tensorflow as tf

from tsf.cnn.cifar10 import cifar10

FLAGS = tf.app.flags.FLAGS

tf.app.flags.DEFINE_string('eval_dir', '/Users/xiachen/IdeaProjects/scala99/model/cifar/cifar10_eval',
                           """Directory where to write event logs.""")
tf.app.flags.DEFINE_string('eval_data', 'test',
                           """Either 'test' or 'train_eval'.""")
tf.app.flags.DEFINE_string('checkpoint_dir', '/Users/xiachen/IdeaProjects/scala99/model/cifar/cifar10_train',
                           """Directory where to read model checkpoints.""")


def predict_once(saver, top_k_op):
    """Run Eval once.

    Args:
      saver: Saver.
      summary_writer: Summary writer.
      top_k_op: Top K op.
      summary_op: Summary op.
    """
    with tf.Session() as sess:
        ckpt = tf.train.get_checkpoint_state(FLAGS.checkpoint_dir)
        if ckpt and ckpt.model_checkpoint_path:
            # Restores from checkpoint
            saver.restore(sess, ckpt.model_checkpoint_path)
            # Assuming model_checkpoint_path looks something like:
            #   /my-favorite-path/cifar10_train/model.ckpt-0,
            # extract global_step from it.
            global_step = ckpt.model_checkpoint_path.split('/')[-1].split('-')[-1]
        else:
            print('No checkpoint file found')
            return

        coord = tf.train.Coordinator()
        threads = tf.train.start_queue_runners(coord=coord)
        predictions = sess.run([top_k_op])
        coord.request_stop()
        coord.join(threads)

        #print('%s: precision @ 1 = %.3f' % (datetime.now(), predictions))
        print(predictions)



def predict():
    """Eval CIFAR-10 for a number of steps."""
    with tf.Graph().as_default() as g:
        input_image = tf.image.decode_png(tf.read_file("/Users/xiachen/IdeaProjects/scala99/model/cifar/airplane3.png"), channels=3)
        casted_image = tf.cast(input_image, tf.float32)
        reshaped_image = tf.image.resize_image_with_crop_or_pad(casted_image, 24, 24)
        print(reshaped_image.get_shape())
        images = tf.expand_dims(reshaped_image, 0)
        print(images.get_shape())

        # Build a Graph that computes the logits predictions from the
        # inference model.
        logits = cifar10.inference(images)

        # Calculate predictions.
        top_k_op = tf.nn.top_k(logits, 3)

        # Restore the moving average version of the learned variables for eval.
        variable_averages = tf.train.ExponentialMovingAverage(
            cifar10.MOVING_AVERAGE_DECAY)
        variables_to_restore = variable_averages.variables_to_restore()
        saver = tf.train.Saver(variables_to_restore)
        predict_once(saver, top_k_op)


def main(argv=None):  # pylint: disable=unused-argument
    cifar10.maybe_download_and_extract()
    if tf.gfile.Exists(FLAGS.eval_dir):
        tf.gfile.DeleteRecursively(FLAGS.eval_dir)
    tf.gfile.MakeDirs(FLAGS.eval_dir)
    predict()


if __name__ == '__main__':
    tf.app.run()
