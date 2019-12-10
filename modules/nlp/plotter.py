import matplotlib.pyplot as plt
from matplotlib.legend_handler import HandlerLine2D
import numpy as np


x = np.arange(0., 1200000., 100000)

#plt.plot([33.33, 66.66], [50000, 100000])
y = x / 250 / 60
y2 = x / 1000 / 60
y3 = x / 3000 / 60

p1=plt.plot(x, y)
p2=plt.plot(x, y2)
p3=plt.plot(x, y3)
# plt.plot(x[5], y[5], 'r*', )
# plt.plot(x[10], y[10], 'r*')

plt.ylabel('Time(mins)')
plt.xlabel('Data')

plt.legend(['250 txc/s', '1000 txc/s', '3000 txc/s'], loc='upper left')
plt.show()
