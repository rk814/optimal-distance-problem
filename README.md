# Optimal Distance Problem
## Overview
I encountered this problem while planning a multi-day bicycle expedition. 
Actually, the path of the trip wasn't the real issue; I knew where I wanted to go. 
The problem started when I tried to divide it into daily stages.


My assumption was that each day should cover a distance between 30 to 50 km, and there were certain places along the stageRoute where I could stay overnight.
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

It worked fine; I actually found a better solution than what I could identify manually. However, it's not the most efficient algorithm. This is an `O(2^n)` exponential algorithm, and while it worked well for my specific scale problem, during more extensive tests, it could easily overwhelm the processor. Therefore, I decided to improve it.

## **Generic algorithm** - second solution
In my first attempt to solve this problem, I used a two-stage approach. First, I iterated through all possible points to generate every combination. Then, I searched for the best one among them.

The idea behind using a genetic algorithm is to skip the first step entirely and try to find the optimal solution from the beginning.

//todo - how it works
## Third solution - **Treads**
//todo ???
## Requirements
+ Java 17 or higher