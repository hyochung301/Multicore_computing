import subprocess
import numpy as np
import matplotlib.pyplot as plt

def generate_matrix_file(matrix, filename):
    m, n = matrix.shape
    with open(filename, 'w') as f:
        f.write(f"{m} {n}\n")
        for row in matrix:
            f.write(" ".join(map(str, row)) + "\n")

def read_matrix_and_time_from_output(output):
    lines = output.strip().split('\n')
    execution_time = float(lines[-1].split()[-1])
    m, n = map(int, lines[0].split())
    matrix = np.array([list(map(float, line.split())) for line in lines[1:-1]])
    return matrix, execution_time

def run_and_get_time(matrix_file1, matrix_file2, threads):
    result = subprocess.run(['./run', matrix_file1, matrix_file2, str(threads)], capture_output=True, text=True)
    result_matrix, execution_time = read_matrix_and_time_from_output(result.stdout)
    return result_matrix, execution_time

A = np.random.randint(0, 10, (100, 100))
B = np.random.randint(0, 10, (100, 100))
C_correct = np.dot(A, B)

generate_matrix_file(A, 'mfile1.txt')
generate_matrix_file(B, 'mfile2.txt')

threads_range = range(1, 9)
execution_times = []

for threads in threads_range:
    result_matrix, execution_time = run_and_get_time('mfile1.txt', 'mfile2.txt', threads)
    assert np.allclose(result_matrix, C_correct), "Matrix multiplication result is incorrect."
    execution_times.append(execution_time)
    print(f"Threads: {threads}, Execution Time: {execution_time} us")

plt.figure(figsize=(10, 6))
plt.plot(threads_range, execution_times, marker='o')
plt.title('Execution Time by Number of Threads')
plt.xlabel('Number of Threads')
plt.ylabel('Execution Time (us)')
plt.xticks(threads_range)
plt.grid(True)
plt.savefig("mp_exec_plot.png")
