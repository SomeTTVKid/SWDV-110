1. Why did Java replace `java.util.Date` and `Calendar` with `java.time`? Give two concrete problems the old API had.  
1a. Java replaced the old api for a few reasons. One problem was that the data was mutable, which is very problematic when working with something as complex as timezones and dates. Another problem was the numbering of months, in the old api they were using the zero based counting system, which can lead to confusion. They updated the new api to numbers months 1-12 instead of 0-11.  

2. When do you use `Period`, and when `Duration`? Use an example from this project.  
2a. Period is used for dates, where as Duration is used for time. In this project we used Duration for finding the length of our classes, and we used Period to find the length of the semester.  

3. In the `daylightSavingTime` test, Boise and Phoenix show the *same* clock time in January but are an hour apart in April. Explain why.  
3a. When Boise switches to Mountain Daylight time, Phoenix stays on Mountain Standard Time because Arizona itself does not go into daylight savings time.  

4. Your program uses `LocalDate` for class dates but `ZonedDateTime` for the online-student times. Why not use `ZonedDateTime` everywhere?  
4a. Because online students may not be located in the same area geographically. There might be students in different states or even countries, so we have to account for those cases. As well as holidays and breaks using a local calendar.  
