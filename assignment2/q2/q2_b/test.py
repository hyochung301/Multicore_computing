import subprocess
from sympy import primerange

def count_primes(N):
    primes = list(primerange(1, N+1))
    return len(primes)

def run_program(N, num_threads):
    result = subprocess.run(['./run', str(N), str(num_threads)], capture_output=True, text=True)
    lines = result.stdout.strip().split('\n')
    num_primes = int(lines[1])
    execution_time = float(lines[3].split()[0])  
    return num_primes, execution_time

def main():
    thread_counts = range(1, 17)
    N_values = [10, 100, 1000, 10000, 100000, 1000000, 12345678]  
    
    for N in N_values:
        known_num_primes = count_primes(N)
        print(f"Testing with N={N}, known number of primes: {known_num_primes}")
        
        for num_threads in thread_counts:
            num_primes, execution_time = run_program(N, num_threads)
            assert num_primes == known_num_primes, f"Test failed for N={N}, Threads={num_threads}. Expected: {known_num_primes}, Got: {num_primes}"
            print(f"Threads: {num_threads}, Num Primes: {num_primes}, Execution Time: {execution_time} us")

if __name__ == "__main__":
    main()
