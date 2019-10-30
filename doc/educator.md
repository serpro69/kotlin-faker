# `Faker().educator`

[Dictionary file](../src/main/resources/locales/en/educator.yml)

Available Functions:  
```kotlin
Faker().educator.name() // => Marblewald

Faker().educator.secondary() // => High School

Faker().educator.tertiaryType() // => University
```

Non-implemented Functions:  
```kotlin
// Random tertiary degree information based on type
Faker().educator.tertiaryDegree("subject") // => Arts
Faker().educator.tertiaryDegree("type") // => Master of
Faker().educator.tertiaryDegree("course_number") // => 168
// or a random one
Faker().educator.tertiaryDegree("") // => Bachelor of
```
