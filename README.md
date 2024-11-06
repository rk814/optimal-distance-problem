# Optimal Distance Problem
## Summary
The application calculates all possible multi-day route variants based on given criteria and identifies the optimal one. It uses recursive and iterative algorithms and also provides a tool for generating random data.
## Overview
I encountered this problem while planning a multi-day bicycle expedition. 
Actually, the path of the trip wasn't the real issue; I knew where I wanted to go. 
The problem started when I tried to divide it into daily stages.


My assumption was that each day should cover a distance between 30 to 50 km, and there were certain places along the route where I could stay overnight.
### Day one
I started from point A and explored every possible option.
Let's say there were 3 accommodation options at 30 km, 40 km, and 50 km (keeping it simple for this example).

![first-step](img%2Fpng%2Ffirst-step-dark.png#gh-dark-mode-only)
![first-step](img%2Fpng%2Ffirst-step-light.png#gh-light-mode-only)

So far, so good.
### Day two
For each of these scenarios, I had further variations. 
For the first option (30 km), there were options at 60 km, 70 km, and 80 km; for the second (40 km), 70 km, 80 km, and 90 km; and for the third (50 km), 80 km, 90 km, and 100 km. 
This resulted in a total of 9 variants for this day — quite a lot to consider.

![second-step-dark.png](img%2Fpng%2Fsecond-step-dark.png#gh-dark-mode-only)
![second-step-light.png](img%2Fpng%2Fsecond-step-light.png#gh-light-mode-only)

### Day three
Continuing this pattern, on day three, with 3 new variants for each branch from day two, there would be a total of 27 combinations.
### Conclusion
In my actual scenario, there were obviously gaps and denser areas of accommodation. 
But just like in this example, I was unable to track all of them. So, somewhere on day third I selected what I considered the best option up to that point and restarted the whole process.

Of course, in this simplified solution, I might easily miss the best scenario. By the best I consider one where each day's ride is closest to the average distance, in my case, 40 km.

## **Recurrency** - first solution
The first algorithm I built was a simple recursive algorithm. 
This type of problem forms a tree structure, and by iterating through all branches, I could find all possible scenarios.

![tree-dark.png](img%2Fpng%2Ftree-dark.png#gh-dark-mode-only)
![tree-light.png](img%2Fpng%2Ftree-light.png#gh-light-mode-only)

It worked fine; I actually found a better solution than what I could identify manually. However, it's not the most efficient algorithm. This is an $`O(2^n)`$ exponential algorithm, and while it worked well for my specific scale problem, during more extensive tests, it could easily overwhelm the processor. Therefore, I decided to improve it.
## **Iterator** - Second solution
Iterator approach is very similar to the previous one. The main difference is that it doesn't use recursion but instead relies on Java's Iterator. Obviously, any recursive algorithm can also be solved in a traditional way. Typically, this should be a faster method, thought not in this case. My testing showed that recursion was approximately twice faster in any example I tried. 
## Future plans
### **Generic algorithm**
In all attempt to solve this problem so far, I used a two-stage approach. First, I iterated through all possible points to generate every combination. Then, I searched for the best one among them.

The idea behind using a genetic algorithm is to skip the first step entirely and try to find the optimal solution directly from the beginning.
### **Threads**
Since this problem is kind of problem can be reduced to sub-problems, similar to divide-and-conquer algorithms, it is natural candidate to using multi-thread environment. 
## Requirements
+ Java 17 or higher
+ Maven 3.9.9 (or higher) or an IDE with build-in Maven support
### Starting app locally
Argument setup can be configured in the Main class.  
The app can be run with a proper IDE or with Maven by running:

`mvn clean package`  

and  

```shell
 java -jar target/optimal-distance-problem-1.0-SNAPSHOT.jar --calc recurrent --min 20 --max 60 --sample="sample2.csv"
 ```
from the main directory. All arguments description list can be found below: 
```
usage: optimal-distance-problem [-c <type>] [-h] [-m <int>] [-sn <file>] [-x <int>]
 -c,--calc <type>      Specifies the calculator type, either 'iterator' or 'recurrent' (required).
 -h,--help             Display this help message.
 -m,--min <int>        Sets the minimum allowable distance (in days) for route calculations (required).
 -sn,--sample <file>   Specifies the file containing sample points for route calculations.
 -x,--max <int>        Sets the maximum allowable distance (in days) for route calculations (required).
```
### Preparing samples
Points for route calculations are loaded from a CSV file in following format:

`0, 43, 59, 64, 77, 90, 110, 115, 124, 142, 161, 183`

The first and last points in this list represent the start and end points of the route, respectively. 
Therefore, the point list must contain at least two points.
All values in the list must be non-negative, although the starting point does not have to be zero.

Alternatively, you can use the random points generator by running:
```shell
 java -cp target/optimal-distance-problem-1.0-SNAPSHOT.jar furczak.Generator --points 25 --start 0 --end 150 --sample="random.csv"
 ```
from the main directory. All arguments description list can be found below:

```
usage: generator [-e <int>] [-h] [-p <int>] [-s <int>] [-sn <file>] 
 -e,--end <int>        Sets the destination point, which must be at least 2 and greater than the start point.
 -h,--help             Display this help message.
 -p,--points <int>     Specifies the number of random intermediate points to generate between the start and end points.
 -s,--start <int>      Sets the departure point, which must be at least 0.
 -sn,--sample <file>   Specifies the file containing sample points for route calculations.
```

### Performing testing
All public methods are fully tested with unit tests, including parameterized tests.  
Test can be performed by:

`mvn clean test`

The tests are written in AssertJ, with support from Mockito and Instancio.
