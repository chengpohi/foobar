import matplotlib.pyplot as plt
import numpy as np


a, b, c, d = np.random.randn(4, 100)

fix, ax = plt.subplots(1, 1)

ax.plot(a, b)

plt.show()

