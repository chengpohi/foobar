# Copyright 2017 Google Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# ==============================================================================

"""TensorFlow NMT model implementation."""
from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import argparse
import random
import sys
import os

# import matplotlib.image as mpimg
import numpy as np
import tensorflow as tf

from nmt import inference
from nmt import train
from nmt.nmt_input import create_or_load_hparams, create_hparams, FLAGS, add_arguments
from nmt.utils import misc_utils as utils

utils.check_tensorflow_version()


def run_main(flags, default_hparams, train_fn, inference_fn, target_session=""):
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
    # Train
    train_fn(hparams, target_session=target_session)


def main(unused_argv):
    MODEL_HOME = os.environ.get("MODEL_HOME", "/Users/xiachen/IdeaProjects/scala99/")
    OUTPUT_HOME = os.environ.get("OUTPUT_HOME", "/Users/xiachen/IdeaProjects/scala99/")
    FLAGS.src = "en"
    FLAGS.tgt = "zh"
    FLAGS.vocab_prefix = MODEL_HOME + "model/nmt/en-zh/vocab"
    FLAGS.train_prefix = MODEL_HOME + "model/nmt/en-zh/train"
    FLAGS.dev_prefix = MODEL_HOME + "model/nmt/en-zh/dev"
    FLAGS.test_prefix = MODEL_HOME + "model/nmt/en-zh/test"
    FLAGS.out_dir = OUTPUT_HOME + "/nmt/model/en-zh/"
    FLAGS.num_train_steps = 12000
    FLAGS.steps_per_stats = 1000
    FLAGS.num_layers = 2
    FLAGS.num_units = 128
    FLAGS.dropout = 0.2
    FLAGS.metrics = "bleu"
    FLAGS.batch_size = 8

    default_hparams = create_hparams(FLAGS)
    train_fn = train.train
    inference_fn = inference.inference
    run_main(FLAGS, default_hparams, train_fn, inference_fn)


if __name__ == "__main__":
    nmt_parser = argparse.ArgumentParser()
    add_arguments(nmt_parser)
    FLAGS, unparsed = nmt_parser.parse_known_args()
    tf.app.run(main=main, argv=[sys.argv[0]] + unparsed)
