answer in 2–4 sentences each:

1. Strings are **immutable**. Name a place in your code where forgetting that would have caused a bug.
1a. In the normalizeEmail method, when using the .trim() or .toLowerCase() it does not modify the original string since they are immutable. 
1a. Therefore, I needed to save the string they return inside of a variable.

2. Why does `split("|")` behave differently from `split("\\|")`?
2a. The method split takes a regualar expression and in a regex | means or so its treated as an operator.
2a. When using "\\|" we are escaping the or operator, telling it to instead use it as a literal.

3. Where did you choose `StringBuilder` instead of `+`, and why?
3a. I chose to use StringBuilder in many methods, titleCase, normalizeEmail, formatPhone, and makeUsername.
3a. Instead of using concatenation, StringBuilder allows me to build and edit strings character by character, as well as appending to the string without creating a new string

4. Paste the output of `mvn test` (the summary line is enough).
4a. Tests run: 20, Failures: 0, Errors: 0, Skipped: 0 BUILD SUCCESS