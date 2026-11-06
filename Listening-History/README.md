1. Pick one method in `MusicStats` and rewrite it with a `for` loop in your README.
Which version is easier to read? Which is easier to change?  

1a. I would choose totalMinutes to change to a for loop:
```java
public static long totalMinutes(List<Play> plays, Predicate<Play> filter) {
    int secondsInMinute = 60;
    int totalSeconds = 0;
    for(Play play : plays){
        if(filter.test(play)){
            totalMinutes += play.seconds();
        }
    }
    return totalSeconds / secondsInMinute;
}
```
1a. This is much easier to read but would require more steps to change.  

2. `PlayFormatter` and `Function<Play, String>` have the same shape. Why might a
team still create its own functional interface?  

2a. A team might create its own interface to give it a name that fits the project and make it more clear what it is doing.  

3. Why must the list passed to `PlayCleaner.clean` be an `ArrayList` and not the
result of `List.of(...)` or `stream().toList()`?  

3a. The list needs to be modifiable since we are removing and replacing elements within it, both of those other examples returns a immutable list.

4. A stream does nothing until a *terminal* operation runs. Name three terminal
operations you used.  

4a. I used .sum() within the totalMinutes method, .toList() in the topArtists method, and .count() in the skipRate method.  