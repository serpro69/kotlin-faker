---
---

# Getting Started

## Installing

### Releases

Installation is as simple as adding `kotlin-faker` dependency to your build configuration file:

{% include 'includes/wiki/dependencyTabs.peb' %}

Release artifacts are available for download from maven central, and you usually don't need to add any additional repositories information.

### Snapshots

Snapshot are automatically published on each commit to master. If you want to try out the latest functionality - add the dependency the same way as described above, but change the version to the current snapshot version, and add the sonatype snapshots repository to your repositories block in the build configuration file:

{% include 'includes/wiki/repositoriesTabs.peb' %}

### Faker BOM

See {{ anchor(title='Kotlin-faker BOM', collectionType='wiki', collectionId='', itemId='Kotlin-faker BOM') }} page for details on how to use a Bill-of-Materials to simplify dependency management.

{% btc %}{% endbtc %}

<br>

## Generating Data

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

Or by using the {{ anchor(title='Faker DSL', collectionType='wiki', collectionId='', itemId='Faker DSL') }} (Which also gives you dsl-like access to {{ anchor(title='Faker Configuration', collectionType='wiki', collectionId='', itemId='Faker Configuration') }}
.)

{% tabs %}

{% kotlin "Kotlin" %} {% filter compileAs('md') %}
```kotlin
val faker = faker {
    // faker config
}

faker.name.firstName()
faker.address.city()
```
{% endfilter %} {% endkotlin %}

{% fjava "FunJava" %} {% filter compileAs('md') %}
```java
Faker faker = faker(fromConsumer(f -> {
    // faker config
}));

faker.getName().firstName()
faker.getAddress().city()
```
{% endfilter %} {% endfjava %}

{% java "Java" %} {% filter compileAs('md') %}
```java
Faker faker = faker(f -> {
    // faker config
    return Unit.INSTANCE;
});

faker.getName().firstName()
faker.getAddress().city()
```
{% endfilter %} {% endjava %}

{% endtabs %}

{% tip %}
{% filter compileAs('html') %}
<section class="accordions">
  <article class="accordion">
    <div class="accordion-header toggle">
      <p>For Java users (clickable)</p>
    </div>
    <div class="accordion-body">
      <div class="accordion-content">
Notice usage of <code>FunctionalUtil.fromConsumer</code> method in "FunJava" tab. If this is not used, then an explicit return must be specified at the end of the lambda (See "Java" tab instead).
See also {{ anchor(title='Java Interop', collectionType='wiki', collectionId='', itemId='Java Interop') }} for more details on using kotlin-faker from Java.
      </div>
    </div>
  </article>
</section>
{% endfilter %}
{% endtip %}

<br>

This concludes this short "getting started" guide. Jump to {{ anchor(title='Faker Configuration', collectionType='wiki', collectionId='', itemId='Faker Configuration') }} page to learn how to configure `Faker` to generate localized data, ensure deterministic random data generation and other configuration options or just click the next button to go to next wiki page.

{% btc %}{% endbtc %}

<br>
