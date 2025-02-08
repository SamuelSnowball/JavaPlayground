
Make 2 service classes with 2 methods.

Make each method take 10 seconds to complete.

Test running seqeuntially and test different parallel execution modes.

Add a test config file, or make 2 profiles, each with different modes.

# Example 1, runs classes/tests concurrently
```junit.jupiter.execution.parallel.mode.default = concurrent```

Testing Service1, with methods 1 and 2. The methods are executed in different threads (t1, t2).
Testing Service1 and Service 2, each with methods 1 and 2. All the methods are executed in different threads (t1, t2, t3, t4).

# Example 2
```junit.jupiter.execution.parallel.mode.default = same_thread```
```junit.jupiter.execution.parallel.mode.classes.default = concurrent```

Testing Service1 and Service 2, each with methods 1 and 2. This causes everything to run in the same thread, what's the concurrent property for then?
