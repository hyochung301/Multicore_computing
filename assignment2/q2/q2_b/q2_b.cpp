#include <stdio.h>
#include <stdlib.h>
#include <omp.h>
#include <chrono>

int Sieve(int N, int threads)
{
    omp_set_num_threads(threads);
    char* isPrime = new char[N+1];
    // initialize array
    #pragma omp parallel for
    for (int i = 2; i <= N; i++){
        isPrime[i] = 1;
    }

    // put in the sieve
    for (int i = 2; i*i <= N; i++){
        if (isPrime[i]){
            #pragma omp parallel for
            for (int j = i*i; j <= N; j += i){
                isPrime[j] = 0;
            }
        }
    }

    // count primes
    int found = 0;
    #pragma omp parallel for reduction(+:found)
    for (int i = 2; i <= N; i++){
        found += isPrime[i];
    }

    delete[] isPrime;

    return found;

}

int main()
{
    int num_primes;
    int N = 1000000;
    int num_threads = 8;

    auto start = std::chrono::high_resolution_clock::now();
    num_primes = Sieve(N, num_threads);
    //1000000 given
    auto end = std::chrono::high_resolution_clock::now();

    std::chrono::duration<double> elapsed = end - start;
    printf("Number of primes: %d\n", num_primes);
    printf("Time taken: %.3f seconds with %i threads \n", elapsed.count(), num_threads);

    return 0;
}
