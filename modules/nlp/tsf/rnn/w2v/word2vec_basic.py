"""Basic word2vec example."""

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import argparse
import os
import sys
from tempfile import gettempdir

import numpy as np
from six.moves import xrange  # pylint: disable=redefined-builtin

# Give a folder path as an argument with '--log_dir' to save
# TensorBoard summaries. Default is a log folder in current directory.
from tsf.rnn.w2v.w2v_input import maybe_download, read_data, generate_batch, plot_with_labels
from tsf.rnn.w2v.w2v_model import build_dataset, build_model, train

current_path = os.path.dirname(os.path.realpath(sys.argv[0]))

parser = argparse.ArgumentParser()
parser.add_argument(
    '--log_dir',
    type=str,
    default='/Users/xiachen/IdeaProjects/scala99/model/w2v',
    help='The log directory for TensorBoard summaries.')
FLAGS, unparsed = parser.parse_known_args()

# Create the directory for TensorBoard variables if there is not.
if not os.path.exists(FLAGS.log_dir):
    os.makedirs(FLAGS.log_dir)

# Step 1: Download the data.
filename = maybe_download('text8.zip', 31344016)

vocabulary = read_data(filename)
print('Data size', len(vocabulary))

# Step 2: Build the dictionary and replace rare words with UNK token.
vocabulary_size = 50000

# Filling 4 global variables:
# data - list of codes (integers from 0 to vocabulary_size-1).
#   This is the original text but words are replaced by their codes
# count - map of words(strings) to count of occurrences
# dictionary - map of words(strings) to their codes(integers)
# reverse_dictionary - maps codes(integers) to words(strings)
data, count, dictionary, reverse_dictionary = build_dataset(
    vocabulary, vocabulary_size)
del vocabulary  # Hint to reduce memory.
print('Most common words (+UNK)', count[:5])
print('Sample data', data[:10], [reverse_dictionary[i] for i in data[:10]])

# Step 3: Function to generate a training batch for the skip-gram model.
batch, labels = generate_batch(batch_size=8, num_skips=2, skip_window=1, data=data)
for i in range(8):
    print(batch[i], reverse_dictionary[batch[i]], '->', labels[i, 0],
          reverse_dictionary[labels[i, 0]])

# Step 4: Build and train a skip-gram model.
valid_size = 16  # Random set of words to evaluate similarity on.
valid_window = 100  # Only pick dev samples in the head of the distribution.
valid_examples = np.random.choice(valid_window, valid_size, replace=False)

graph, init, optimizer, merged, loss, similarity, normalized_embeddings, saver, embeddings, train_inputs, train_labels = \
    build_model(valid_examples=valid_examples, vocabulary_size=vocabulary_size)

# Step 5: Begin training.
final_embeddings = train(FLAGS, graph=graph,
                         init=init,
                         optimizer=optimizer, merged=merged, loss=loss,
                         similarity=similarity,
                         reverse_dictionary=reverse_dictionary,
                         normalized_embeddings=normalized_embeddings,
                         saver=saver,
                         embeddings=embeddings,
                         vocabulary_size=vocabulary_size,
                         valid_size=valid_size,
                         valid_examples=valid_examples,
                         data=data,
                         train_inputs=train_inputs,
                         train_labels=train_labels)

# Step 6: Visualize the embeddings.


try:
    # pylint: disable=g-import-not-at-top
    from sklearn.manifold import TSNE
    import matplotlib.pyplot as plt

    tsne = TSNE(
        perplexity=30, n_components=2, init='pca', n_iter=5000, method='exact')
    plot_only = 500
    low_dim_embs = tsne.fit_transform(final_embeddings[:plot_only, :])
    labels = [reverse_dictionary[i] for i in xrange(plot_only)]
    plot_with_labels(low_dim_embs, labels, os.path.join(gettempdir(), 'tsne.png'))
except ImportError as ex:
    print('Please install sklearn, matplotlib, and scipy to show embeddings.')
    print(ex)
