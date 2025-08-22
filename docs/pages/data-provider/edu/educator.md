---
title: educator
faker: edu
---

## `Faker().educator`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/educator.yml:educator_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    Faker().educator.schoolName() // => Bluemeadow
    Faker().educator.secondary() // => High School
    Faker().educator.primary() // => Elementary School
    Faker().educator.secondarySchool() // => Bluemeadow High School
    Faker().educator.primarySchool() // => Bluemeadow Elementary School
    Faker().educator.campus() // => Bluemeadow Campus
    Faker().educator.subject() // => Applied Science (Psychology)
    Faker().educator.universityType() // => College
    Faker().educator.tertiaryDegreeType() // => Master of
    Faker().educator.tertiaryDegreeCourseNumber() // => 306
    ```

'''

.Non-implemented Functions:
[%collapsible]
====
[source,kotlin]
----
Faker().educator.university() // =>
Faker().educator.degree() // =>
Faker().educator.courseName() // =>
----
====
