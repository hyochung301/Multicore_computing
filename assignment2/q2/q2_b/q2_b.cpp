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
int main (int argc, char *argv[]) {
    int num_primes;
    int N = 1000000;
    int num_threads = 8;

    if (argc>1) {
        N = atoi(argv[1]);
        num_threads = atoi(argv[2]);
    }

    auto start = std::chrono::high_resolution_clock::now();
    num_primes = Sieve(N, num_threads);
    auto end = std::chrono::high_resolution_clock::now();

    // changed formatting slightly so script can read it easily
    auto elapsed = std::chrono::duration_cast<std::chrono::nanoseconds>(end - start);
    printf("Number of primes: \n%d\n", num_primes);
    printf("Time taken: \n%.3f us\nwith %i threads \n", elapsed.count()/1000.f, num_threads);

    return 0;
}
