# Software Engineering Project Starter Code

The system will find all the prime numbers less than or equal to a given number positive number and then it will calculate the sum of all prime numbers.
Example if the input number is 10: The system will find the prime numbers (2,3,5,7) , add them and print a sum = 17.

![System Diagram](https://github.com/CPS353-Suny-New-Paltz/project-starter-code-Andradem11/blob/main/DIAGRAM%20PRIME%20COMPUTATION.drawio.png?raw=true)
Multi-Threaded Implementation
I added a multi-threaded version of the user compute class (UserComputeMultiThreaded, it uses a fixed thread pool created with Executors.newFixedThreadPool(maxThreads). It sets the threads to 4, gives good performance without using too many system resources, and allows several user requests to be processed at the same time. The executor is inside a try/finally block to ensure it's shut down properly.

Performance Profiling

PART A: Before and after benchmark numbers
Ran some timing tests on both the compute engine and the coordinator. 
For computeSum, small numbers are basically instant, but once you hit 50,000 or higher, the CPU time jumps a lot(5-6 ms). 
For the multi-threaded coordinator, the same thing happens — small inputs finish fast, big numbers take most of the time. This shows the main bottleneck is the prime sum calculation.

Benchmark Numbers
|   Input | Original Engine (ms) | Fast Engine (ms) | Speed-up (%) |
| ------: | -------------------: | ---------------: | -----------: |
|   1,000 |                    3 |                0 |          100 |
|   5,000 |                    0 |                0 |          NaN |
|  10,000 |                    0 |                0 |          NaN |
|  50,000 |                   10 |                1 |           90 |
| 100,000 |                    5 |                0 |          100 |
| 200,000 |                   11 |                0 |          100 |

PART B: Link to the benchmark test
https://github.com/CPS353-Suny-New-Paltz/project-starter-code-Andradem11/blob/main/test/performance/ComputeEngineBenchmark.java

PART C: Performance Issue and Fix 
Issue: The old ComputeEngineImpl checks every number up to sqrt(n) to see if it's prime. For small numbers, that's fine, but once you hit 50,000 or higher, it takes way too long. CPU time shoots up because the runtime is basically O(n * sqrt(n)).
Fix: Created ComputeEngineFastImpl using the Sieve of Eratosthenes. Now it marks multiples of each prime and sums them in O(n log log n), which is way faster. Benchmarks show 40–100% speed-up for the big numbers.


