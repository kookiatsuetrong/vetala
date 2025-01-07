Avoid using "else".

```java
Avoid using "else"

// Code 1: Avoid using "else"
if (passed) {

} else {

}

// Code 2: Use double "if" instead
if (passed == true) { }
if (passed == false) { }

// Code 1 is faster.
// Code 1 is more complicated.
// Code 2 is slower.
// Code 2 is simpler.
```

Avoid using "else if".
```java
// Code 1: Avoid using "else if"
if ( ... ) { }
else if ( ... ) { }
else if ( ... ) { }
else { }

// Code 2: Use fallback condition (or temporary result) instead
String f(double value) {
	if ( ... ) return "";
	if ( ... ) return "";
	if ( ... ) return "";
	if ( ... ) return "";
	return "";
}

// Code 2 is simpler and yield the same performance.
```

Avoid naming variable negatively.
```java
// Code 1:
boolean error = false;    // Avoid

if (!error) { }           // Avoid
if (error == false) { }

// Code 2:
boolean passed = true;    // Prefer
if (passed) { }
if (passed == true) { }
if (passed == false) { }

// Code 2 is easier to understand for lazy people.
```









