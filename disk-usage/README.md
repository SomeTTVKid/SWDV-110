1. Copy your **race** results into a table. Each time `n` doubles, about how many times longer does selection sort take? Merge sort? Explain how those ratios match O(n²) and O(n log n). 

1a.

| n | Selection (ms) | Merge (ms) |
|---:|---:|---:|
| 1000 | 2.5 | 0.2 |
| 2000 | 11.1 | 0.4 |
| 4000 | 46.8 | 1.0 |
| 8000 | 175.6 | 2.2 |
| 16000 | 765.3 | 5.1 |

1a. Each time n double, selection sort gets ROUGHLY 4 times longer hence O(n^2) where 2^2 = 4
    Merge sort gets ROUGHLY 2 times longer, keeping in line with O(n log n) where the first n doubles, log n only increases a little.   

2. What is the Big-O of `totalSize` in terms of *f*, the number of files and folders? Why?  

2a. The notation would be O(f), because it visits each entry once. The more files, the more it scales linearly.  

3. Binary search needs a sorted list. If you only need to search **once**, is it worth sorting first? What if you search 1,000 times?  

3a. If you're only searching once, then it's not worth the extra computation to sort it. However, if you're searching it 1,000 times then it's very worthwhile to sort it once.  

4. What would happen if `maxDepth` had no base case? What error would Java throw, and why? 

4a. If there was no base case in maxDepth, it would throw a StackOverFlowError because it would continuously call itself until the application ran out of stack space.  

5. Name one more real-world problem (not folders) that has a recursive structure.  

5a. Certain pathfinding algorithms in video games!  