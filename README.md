# Software Engineering Project Starter Code

The system will find all the prime numbers less than or equal to a given number positive number and then it will calculate the sum of all prime numbers.
Example if the input number is 10: The system will find the prime numbers (2,3,5,7) , add them and print a sum = 17.

![System Diagram](https://github.com/CPS353-Suny-New-Paltz/project-starter-code-Andradem11/blob/main/DIAGRAM%20PRIME%20COMPUTATION.drawio.png?raw=true)

Multi-Threaded Implementation
I added a multi-threaded version of the user compute class (UserComputeMultiThreaded, it uses a fixed thread pool created with Executors.newFixedThreadPool(maxThreads). It sets the threads to 4, gives good performance without using too many system resources, and allows several user requests to be processed at the same time. The executor is inside a try/finally block to ensure it's shut down properly.
