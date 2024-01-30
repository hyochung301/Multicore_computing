import matplotlib.pyplot as plt

x1, y1 = [1,2,4,8], [48,143,186,590]	# bakery
x2, y2 = [1,2,4,8], [8,31,73,218]		# atomic
x3, y3 = [1,2,4,8], [15,13,41,72]		# sync
x4, y4 = [1,2,4,8], [15,24,20,51]		# reent

plt.plot(x1, y1, label='Bakery')
plt.plot(x2, y2, label='Atomic')
plt.plot(x3, y3, label='Synchronized')
plt.plot(x4, y4, label='Reentrant')

plt.title('Increment time vs thread count')
plt.xlabel('Num Threads')
plt.ylabel('Time elapsed (ms)')

plt.legend()

plt.savefig('plot.png')
