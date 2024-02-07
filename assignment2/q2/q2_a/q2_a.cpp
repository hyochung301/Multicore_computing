#include <iostream>
#include <vector>
#include <fstream>
#include <chrono>
#include <omp.h>

void printMatrix(std::vector<std::vector<int>> matrix) {
    std::cout << matrix.size() << " " << matrix[0].size() << std::endl;
    for(int i = 0; i < matrix.size(); i++) {
        for(int j = 0; j < matrix[0].size(); j++) {
            std::cout << matrix[i][j] << " ";
        }
        std::cout << std::endl;
    }
}

std::vector<std::vector<int>> readMatrix(char input[]) {
    std::ifstream file(input);
    if (!file) {
        std::cout << "Unable to open file";
    }

    int rows, cols;
    file >> rows >> cols;

    std::vector<std::vector<int>> matrix(rows, std::vector<int>(cols));

    for(int i = 0; i < rows; i++) {
        for(int j = 0; j < cols; j++) {
            file >> matrix[i][j];
        }
    }
    return matrix;
}

void MatrixMult(char file1[], char file2[], int T)
{
    std::vector<std::vector<int>> matrix1 = readMatrix(file1);
    std::vector<std::vector<int>> matrix2 = readMatrix(file2);
    std::vector<std::vector<int>> result(matrix1.size(), std::vector<int>(matrix2[0].size()));

    omp_set_num_threads(T); // T threads

    auto start = std::chrono::high_resolution_clock::now();

#pragma omp parallel for
    for (int i = 0; i < matrix1.size(); i++) {
        for (int j = 0; j < matrix2[0].size(); j++) {
            int sum = 0;
#pragma omp parallel for reduction(+:sum)
            for (int k = 0; k < matrix1[0].size(); k++) {
                sum  += matrix1[i][k] * matrix2[k][j];
            }
            result[i][j] = sum;
        }
    }

    auto end = std::chrono::high_resolution_clock::now();
    auto elapsed = std::chrono::duration_cast<std::chrono::microseconds>(end-start);

    printMatrix(result);

    std::cout << elapsed.count() << "\n"; // REMOVE THIS BEFORE TURN IN!!!!
}

int main(int argc, char *argv[])
{
    char *file1, *file2;
    file1 = argv[1];
    file2 = argv[2];
    int T = atoi(argv[3]);
    MatrixMult(file1, file2, T);
    return 0;
}
