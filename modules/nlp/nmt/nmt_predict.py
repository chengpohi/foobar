import argparse
import random
import sys

import numpy as np
import tensorflow as tf

from nmt import inference
from nmt.nmt_input import add_arguments
from nmt.nmt_train import create_hparams, create_or_load_hparams
from nmt.utils import misc_utils as utils


def eval(flags, default_hparams, target_session=""):
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

    ## Decode
    out_dir = flags.out_dir
    if not tf.gfile.Exists(out_dir):
        raise IOError("%s path not exist." % out_dir)

    # Load hparams.
    hparams = create_or_load_hparams(
        out_dir, default_hparams, flags.hparams_path, False)

    # Inference indices
    hparams.inference_indices = None

    ckpt = tf.train.latest_checkpoint(out_dir)
    # get ckpt from out dir
    if not ckpt:
        raise IOError("%s ckpt not find in path." % ckpt)
    inference.predicate(ckpt, hparams, num_workers, jobid)


def main(unused_argv):
    # FLAGS.out_dir = "/Users/xiachen/IdeaProjects/scala99/model/nmt/vi-en/"
    FLAGS.out_dir = "/Users/xiachen/IdeaProjects/scala99/model/nmt/en-zh/model/"
    default_hparams = create_hparams(FLAGS)
    eval(FLAGS, default_hparams)


if __name__ == "__main__":
    nmt_parser = argparse.ArgumentParser()
    add_arguments(nmt_parser)
    FLAGS, unparsed = nmt_parser.parse_known_args()
    tf.app.run(main=main, argv=[sys.argv[0]] + unparsed)


    # I couldn ' t give them money , nothing .
    # I couldn give them money , nothing .
