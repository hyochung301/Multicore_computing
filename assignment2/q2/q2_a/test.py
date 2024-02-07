import subprocess
import numpy as np
import random

def generate_random_matrix(max_size):
    """Generate a random matrix with a size not exceeding max_size."""
    rows = random.randint(1, max_size)
    cols = random.randint(1, max_size)
    return np.random.randint(0, 10, (rows, cols)), rows, cols

def generate_matrix_file(matrix, filename):
    """Save a matrix to a file."""
    m, n = matrix.shape
    with open(filename, 'w') as f:
        f.write(f"{m} {n}\n")
        for row in matrix:
            f.write(" ".join(map(str, row)) + "\n")

def run_program(file1, file2, threads):
    """Run the matrix multiplication program with the given files and threads."""
    process = subprocess.run(['./run', file1, file2, str(threads)], capture_output=True, text=True)
    return process.stdout

def read_matrix_from_output(output):
    """Parse the resulting matrix from the program's output."""
    lines = output.strip().split('\n')
    m, n = map(int, lines[0].split())
    matrix = np.array([list(map(int, line.split())) for line in lines[1:]])
    return matrix

def test_program(num_tests=100, max_size=300):
    """Test the program with num_tests random matrices."""
    matrix_a_filename = 'matrix_a.txt'
    matrix_b_filename = 'matrix_b.txt'
    
    for test in range(num_tests):
        A, rows_a, cols_a = generate_random_matrix(max_size)
        B, rows_b, cols_b = generate_random_matrix(max_size)
        
        # this is horrible haha sorry
        while cols_a != rows_b:
            B, rows_b, cols_b = generate_random_matrix(max_size)
        
        generate_matrix_file(A, matrix_a_filename)
        generate_matrix_file(B, matrix_b_filename)
        
        output = run_program(matrix_a_filename, matrix_b_filename, 1)
        
        C_result = read_matrix_from_output(output)
        
        C_correct = np.dot(A, B)
        assert np.array_equal(C_result, C_correct), f"Test failed on matrices of sizes ({rows_a},{cols_a}) and ({rows_b},{cols_b})"
        
        print(f"Test {test + 1} passed for matrix sizes ({rows_a},{cols_a}) x ({rows_b},{cols_b})")

if __name__ == "__main__":
    test_program()
