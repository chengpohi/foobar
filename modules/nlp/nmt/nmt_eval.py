import argparse
import random
import sys

import tensorflow as tf
import numpy as np

from nmt import train, inference
from nmt.nmt_input import add_arguments
from nmt.nmt_train import create_hparams, create_or_load_hparams
from nmt.utils import misc_utils as utils, evaluation_utils


def eval(flags, default_hparams, train_fn, inference_fn, target_session=""):
    """Run main."""
    # Job
    jobid = flags.jobid
    num_workers = flags.num_workers
    utils.print_out("# Job id %d" % jobid)

    # Random
    random_seed = flags.random_seed
    if random_seed is not None and random_seed > 0:
        utils.print_out("# Set random seed to %d" % random_seed)
        random.seed(random_seed + jobid)
        np.random.seed(random_seed + jobid)

    ## Train / Decode
    out_dir = flags.out_dir
    if not tf.gfile.Exists(out_dir): tf.gfile.MakeDirs(out_dir)

    # Load hparams.
    hparams = create_or_load_hparams(
        out_dir, default_hparams, flags.hparams_path, save_hparams=(jobid == 0))

    # Inference indices
    hparams.inference_indices = None
    if flags.inference_list:
        (hparams.inference_indices) = (
            [int(token) for token in flags.inference_list.split(",")])

    # Inference
    trans_file = flags.inference_output_file
    ckpt = flags.ckpt
    if not ckpt:
        ckpt = tf.train.latest_checkpoint(out_dir)
    inputs = ["Cám ơn rất nhiều ."]
    result = inference_fn(ckpt, inputs, trans_file, hparams, num_workers, jobid)
    print(result.decode("utf-8"))

    # Evaluation
    ref_file = flags.inference_ref_file
    if ref_file and tf.gfile.Exists(trans_file):
        for metric in hparams.metrics:
            score = evaluation_utils.evaluate(
                ref_file,
                trans_file,
                metric,
                hparams.subword_option)
            utils.print_out("  %s: %.1f" % (metric, score))


def main(unused_argv):
    FLAGS.out_dir = "/Users/xiachen/IdeaProjects/scala99/model/nmt/vi-en/"
    FLAGS.inference_input_file = "/Users/xiachen/IdeaProjects/scala99/model/nmt/test.vi"
    FLAGS.inference_output_file = "/Users/xiachen/IdeaProjects/scala99/model/nmt/vi-en"
    default_hparams = create_hparams(FLAGS)
    train_fn = train.train
    inference_fn = inference.translate
    eval(FLAGS, default_hparams, train_fn, inference_fn)


if __name__ == "__main__":
    nmt_parser = argparse.ArgumentParser()
    add_arguments(nmt_parser)
    FLAGS, unparsed = nmt_parser.parse_known_args()
    tf.app.run(main=main, argv=[sys.argv[0]] + unparsed)
