---
---

# Getting Started

### Installing

#### Releases

Installation is as simple as adding `kotlin-faker` dependency to your build file configuration.

{% include 'includes/wiki/dependencyTabs.peb' %}

Release artifacts are available for download from maven central, and you usually don't need to add any additional repositories information.

#### Snapshots

Snapshots are also available from sonatype snapshots repository, which needs to be added explicitly.

{% include 'includes/wiki/repositoriesTabs.peb' %}

### Generating Data

Creating a `Faker` instance can be done either by creating a class instance directly:

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
val faker = Faker()

faker.name.firstName()
faker.address.city()
```
{% endfilter %}
{% endkotlin %}

{% java "Java" %}
{% filter compileAs('md') %}
```java
Faker faker = new Faker();

faker.getName().firstName()
faker.getAddress().city()
```
{% endfilter %}
{% endjava %}

{% endtabs %}

<br>

Or by using the faker dsl (Which also gives you access to {{ anchor(title='Faker Configuration', collectionType='wiki', collectionId='', itemId='Faker Configuration') }}
.)

{% filter compileAs('html') %}
<section class="accordions">
  <article class="accordion">
    <div class="accordion-header toggle">
      <p>For Java users</p>
    </div>
    <div class="accordion-body">
      <div class="accordion-content">
Notice usage of <code>FunctionalUtil.fromConsumer</code> method in "FunJava" tab. If this is not used, then an explicit return must be specified at the end of the lambda (See "Java" tab instead).
      </div>
    </div>
  </article>
</section>
{% endfilter %}

{% tabs %}

{% kotlin "Kotlin" %}
{% filter compileAs('md') %}
```kotlin
val faker = faker {
    // faker config
}

faker.name.firstName()
faker.address.city()
```
{% endfilter %}
{% endkotlin %}

{% fjava "FunJava" %}
{% filter compileAs('md') %}
```java
Faker faker = faker(fromConsumer(f -> {
    // faker config
}));

faker.getName().firstName()
faker.getAddress().city()
```
{% endfilter %}
{% endfjava %}

{% java "Java" %}
{% filter compileAs('md') %}
```java
Faker faker = faker(f -> {
    // faker config
    return Unit.INSTANCE;
});

faker.getName().firstName()
faker.getAddress().city()
```
{% endfilter %}
{% endjava %}

{% endtabs %}

<br>

Jump to {{ anchor(title='Faker Configuration', collectionType='wiki', collectionId='', itemId='Faker Configuration') }} page to learn how to configure `Faker` to generate localized data, ensure deterministic random data generation and other configuration options. 
