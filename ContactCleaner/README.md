Answer in 2–4 sentences each:

1. Strings are **immutable**. Name a place in your code where forgetting that would have caused a bug.

   In the `normalizeEmail` method, calling `.trim()` or `.toLowerCase()` does not modify the original string because strings are immutable. I needed to save the returned string in a variable.

2. Why does `split("|")` behave differently from `split("\\|")`?

   The `split` method takes a regular expression, and in a regex, `|` means “or,” so it is treated as an operator. In `"\\|"`, the backslash escapes the operator so that it matches a literal pipe.

3. Where did you choose `StringBuilder` instead of `+`, and why?

   I chose to use `StringBuilder` in `titleCase`, `normalizeEmail`, `formatPhone`, and `makeUsername`. It lets me build and edit strings character by character without creating a new string for every change.

4. Paste the output of `mvn test` (the summary line is enough).

   ```text
   Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
   BUILD SUCCESS
   ```