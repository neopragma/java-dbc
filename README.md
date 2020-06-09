# Java DbC

[Design by Contract](https://wiki.c2.com/?DesignByContract) support for Java.

Note: This is not on Maven Central yet.

## Why this? 

This project started life as a self-learning exercise for me, as much about writing Java annotations as about DbC as such, but it may actually prove to be useful for some people. 

I've long considered DbC a useful technique for services (as well as library functions), as it helps the individual services to be less vulnerable to unexpected inputs and hidden side effects. I consider it one among a handful of key techniques that help us create resilient and reliable solutions. 

As people embrace the idea of service-based solutions, sometimes taking the idea as far as "microservices", we have a larger number of small pieces and a larger number of interactions with code we don't control, all in an elastic environment in which server instances come and go while our applications remain available to users more-or-less continuously. 

If each small piece is as solid as we can make it, then at least when something unexpected happens in production the small piece will behave in a predictable way. We probably will never achieve a zero-defect outcome, but we can at least apply all the skills and tools we know of to do the best we can.

It's also a reality now, if it wasn't already, that we can't configure a test environment to be close enough to live production to provide a high degree of confidence our application will behave in a reasonable way once it's out in the world and in use by humans and by client systems. DbC is not a testing technique; contracts are enforced at run time. That should make it appealing even for developers who consider unit testing a bad idea. 

If we add to Design by Contract the idea of [_observability_](https://thenewstack.io/monitoring-and-observability-whats-the-difference-and-why-does-it-matter/) and we instrument our code to work with a proactive monitoring tool such as Honeycomb, (while remembering everything else we know about general software design principles, of course), then I would say we've exercised professional due diligence to make our application as reliable as possible. 

## Usage 

To check inbound argument values to enforce _preconditions_, annotate the arguments of the method that receives requests from client code (or method close to the beginning of the processing your application performs). Call _Constraints.check(...)_ to have the library validate the inbound arguments to ensure they comply with the contract for calling your service. 

To check your service's state to enforce _preconditions_ or _postconditions_ or to check _invariants_, define a method that accepts as arguments the values you need to check. Annotate those arguments appropriately, and call _Constraints.check(...)_ on them. 

When a value does not conform with a check, the library throws _ConstraintViolationException_. Catch that exception and deal with it however you need to. 

Here is an example from the unit test suite: 

```java
    String blend(@NotBlank String val1, @NotBlank String val2) {
        Constraints.check(val1, val2);
        return val1 + val2;
    }
```

Client code calls the method this way: 

```java
    blend("something", "somethingElse");
```

Here is a more complicated example: 

```java
    void processMultipleConstraints(
            @NotNull @MinimumLength(value=5) @MaximumLength(value=20) @MustMatch(regex="^[a-zA-Z]+$") String someString,
            @NotNull @NotEmpty @MustContainKey(key="key1") @MustNotContainKey(key="key2") Map<String, String> testMap,
            @NotNull @MustBeInRangeExclusive(min=50, max=60) Integer someNumber,
            @NotNull @MinimumValue(value=10) Double someDouble) {
        Constraints.check(someString, testMap, someNumber, someDouble);
    }
```

Client code calls the method this way:

```java
    Map<String, String> testMap = new HashMap();
    testMap.put("key1", "value1");
    processMultipleConstraints(
        "Happy",
        testMap,
        55,
        14.89);
```

There is a known problem: You can't skip arguments on the call to _Constraints.check()_ or the library will get confused.
An example: In principle, you should be able to write this:

```java
    String add(@CustomConstraint(String msg, className="com.neopragma.dbc.EvenNumbersOnlyRule") int val1, int val2) {
        Constraints.check(val1);
        return msg + ": " + (val1 + val2);
    }
```

Instead, you must write this, even though your intention is only to check the second argument:

```java
    String add(@CustomConstraint(String msg, className="com.neopragma.dbc.EvenNumbersOnlyRule") int val1, int val2) {
        Constraints.check(msg, val1);
        return msg + ": " + (val1 + val2);
    }
```

You can omit trailing arguments after the last one that has an annotation on it, but otherwise the arguments passed to _Constraints.check()_ must align one-for-one with the arguments passed into the enclosing method.

## Annotations 

There are several annotations that apply to _parameters_ passed into methods. These can be used to enforce _preconditions_ and _postconditions_ and to check _invariants_ as defined in the general model of DbC. Here is an alphabetical list. The _Constraints.check()_ method will throw _ConstraintViolationException_ (unchecked) when any constraint is not met. You can specify multiple annotations on the same parameter. 

### @Blank 

Argument of type _String_ must be an empty string or a null reference. 

### @CustomConstraint(className = _classname_) 

You provide a class that implements interface _Rule_ and provide the class name in the _className_ attribute of the annotation. Annotate a parameter that you need to validate using custom logic. 

### @Empty 

Argument of type _Collection_ must be empty and not a null reference. This does not check for _null_ and will throw _NullPointerException_ when the argument is a null reference.

This and @NotEmpty work for _Map_ types because the code recognizes the _Map_ type and performs the "empty" test on just the _keySet()_. 

### @MaximumLength(value = _n_) 

Argument of type _String_ must not be longer than _n_.

### @MaximumValue(value = _n_)

Argument of type _Number_ must not exceed the value of _n_.

### @MinimumEntries(value = _n_) 

Argument of type _Collection_ must contain at least _n_ entries. 

### @MinimumLength(value _n_)

Argument of type _String_ must be at least _n_ characters long. 

### @MinimumValue(value = _n_)

Argument of type _Number_ must not be less than the value of _n_.

### @MustBeInRangeExclusive(min = _min_, max = _max_)

Argument of type _Number_ must be between _min_ and _max_ exclusive. 

### @MustBeInRangeInclusive(min = _min_, max = _max_)

Argument of type _Number_ must be between _min_ and _max_ inclusive. 

### @MustBeOutsideRange(min = _min_, max = _max_)

Argument of type _Number_ must be outside the range between _min_ and _max_ inclusive. 

### @MustContainKey(key = _value_) 

Argument of type _Map_ must contain an entry whose key is _value_. 

### @MustMatch(regex = _value_) 

Argument of type _String_ must match the regular expression. This does not check for _null_. 

### @MustNotContainKey(key = _value_)

Argument of type _Map_ must not contain an entry whose key is _value_. 

### @MustNotMatch(regex = _value_) 

Argument of type _String_ must not match the regular expression. This does not check for _null_. 

### @NotBlank 

Argument of type _String_ must be a string with a length of at least 1. 

### @NotEmpty 

Argument of type _Collection_ must contain at least one entry. This does not check for _null_ and will throw _NullPointerException_ when the argument is a null reference. Use in conjunction with @NotNull for cleaner behavior and better readability. 

### @NotNull 

Argument of type _Object_ must not be a null reference. 

### @Null 

Argument of type _Object_ must be a null reference. 

## Arguments of type _Array_ 

The method client code calls to trigger verifying the contract is _Constraints.check(Object... args)_. Because of the way _varargs_ are supported in Java, all the arguments get flattened into a single one-dimensional array. An effect is that the _check()_ method can't distinguish between these two constructions: 

```java
void methodName(Integer a, Date d, String[4] s, Integer b)

void methodName(Object[] o) 
```

So if you call _methodName_ like this: 

```java
methodName(16, new Date(), new String[] { "alpha", "beta", "delta", "gamma" }, 37)
```

The _check()_ method sees this argument list:

```java
Object [16, _date_, "alpha", "beta", "delta", "gamma", 37]
```

To enforce a contract involving an array argument, I suggest first converting the array into a _Collection_ such as _List_. Unfortunately, you might have to extract a method just for the purpose of enforcing the contract on the array argument in order to avoid messing up the other arguments passed to your public method. 

If the array argument is the only argument passed to the public method, then _check()_ will only "see" the first entry in the array. If the array contains no entries, you'll get a _NullPointerException_ because Java will think there is an argument but its value is _null_. So, the behavior with array arguments is inconsistent and almost certainly will not be what you want. 

## Handling JSON input 

JSON documents are basically _String_ values unless they are parsed or interpreted by code that understands the JSON format. To enforce a contract pertaining to a JSON document, I suggest first converting the document into a _Collection_ such as _Map_, using your favorite JSON library. Then you can use the annotations that operate on _Collection_ types. 

I considered building this into the tool, but decided not to impose a dependency on a JSON library that might not be the one you prefer to use, or that might introduce a transitive dependency that causes problems later on as various other dependencies move to new versions.

