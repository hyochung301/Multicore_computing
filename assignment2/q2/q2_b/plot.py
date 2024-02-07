import subprocess
import matplotlib.pyplot as plt
import numpy as np

known_num_primes = 78498

def run_program(N, num_threads):
    result = subprocess.run(['./run', str(N), str(num_threads)], capture_output=True, text=True)
    lines = result.stdout.strip().split('\n')
    num_primes = int(lines[1])
    execution_time = float(lines[3].split()[0]) 
    return num_primes, execution_time

def main():
    N = 1000000
    thread_counts = range(1, 9)
    execution_times = []
    num_primes_list = []

    for num_threads in thread_counts:
        num_primes, execution_time = run_program(N, num_threads)
        print(f"Threads: {num_threads}, Num Primes: {num_primes}, Execution Time: {execution_time} us")
        
        if known_num_primes is not None and num_primes != known_num_primes:
            print(f"Warning: Incorrect number of primes for {num_threads} threads. Expected: {known_num_primes}, Got: {num_primes}")
        
        execution_times.append(execution_time)
        num_primes_list.append(num_primes)

    plt.figure(figsize=(10, 6))
    plt.plot(thread_counts, execution_times, marker='o')
    plt.title('Execution Time by Number of Threads (N=1000000)')
    plt.xlabel('Number of Threads')
    plt.ylabel('Execution Time (us)')
    plt.xticks(thread_counts)
    plt.grid(True)
    plt.savefig('plot.png')

if __name__ == "__main__":
    main()
