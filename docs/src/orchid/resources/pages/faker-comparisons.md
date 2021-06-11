---
title: Faker Comparisons
---

# JVM-targeted Faker Libs Comparison

On the surface <strong>kotlin-faker</strong> isn't "unique" by any means and there exist other JVM-targeted libraries out there with similar functionalities, so you have several options to choose from. 

So why use this one instead? I've decided to make a comparison between <strong>kotlin-faker</strong>, and other JVM-based libs that have been out there for quite some time.

{% info %}
The benchmarks time is an average execution time of 10 consecutive runs. Each run includes creating a new Faker instance and generating a 1_000_000 values with the function returning a person's full name.
<br>
<br>
<i>Note: benchmarks for `blocoio/faker` could not be done due to unexpected exceptions coming from the lib, benchmarks for `moove-it/fakeit` could not be done due to android dependencies in the lib</i>
{% endinfo %}


{% filter compileAs('html') %}
<table class="table is-striped is-hoverable is-fullwidth">
  <thead>
    <tr>
      <th></th>
      <th>
        <span class="iconify-inline" data-icon="raphael:github"></span>
        <a href="https://github.com/serpro69/kotlin-faker"><strong>kotlin-faker</strong></a>
      </th>
      <th>
        <span class="iconify-inline" data-icon="raphael:github"></span>
        <a href="https://github.com/DiUS/java-faker">DiUS/java-faker</a>
      </th>
      <th>
        <span class="iconify-inline" data-icon="raphael:github"></span>
        <a href="https://github.com/Devskiller/jfairy">Devskiller/jfairy</a>
      </th>
      <th>
        <span class="iconify-inline" data-icon="raphael:github"></span>
        <a href="https://github.com/blocoio/faker">blocoico/faker</a>
      </th>
      <th>
        <span class="iconify-inline" data-icon="raphael:github"></span>
        <a href="https://github.com/moove-it/fakeit">moove-it/fakeit</a>
      </th>
    </tr>
  </thead>
  <tfoot>
    <tr>
      <th></th>
      <th>kotlin-faker</th>
      <th>DiUS/java-faker</th>
      <th>Devskiller/jfairy</th>
      <th>blocoico/faker</th>
      <th>moove-it/fakeit</th>
    </tr>
  </tfoot>
  <tbody>
    <tr>
      <td><strong>Language</strong></td>
      <td><span class="iconify-inline" data-icon="simple-icons:kotlin"></span></td>
      <td><span class="iconify-inline" data-icon="simple-icons:java"></span></td>
      <td><span class="iconify-inline" data-icon="simple-icons:java"></span></td>
      <td><span class="iconify-inline" data-icon="simple-icons:java"></span></td>
      <td><span class="iconify-inline" data-icon="simple-icons:kotlin"></span></td>
    </tr>
    <tr>
      <td>{{ anchor(title='Available Data Providers', collectionType='wiki', collectionId='', itemId='Data Providers') }} (<code>address</code>, <code>name</code>, etc.)</td>
      <td>171</td>
      <td>73</td>
      <td>8</td>
      <td>21</td>
      <td>36</td>
    </tr>
    <tr>
      <td>{{ anchor(title='Available Locales', collectionType='wiki', collectionId='', itemId='Available Locales') }} (<code>nb-NO</code>, <code>uk</code>, <code>es</code> etc.)</td>
      <td>55</td>
      <td>47</td>
      <td>10</td>
      <td>46</td>
      <td>44</td>
    </tr>
    <tr>
      <td>{{ anchor(title='Generator of Unique Values', collectionType='wiki', collectionId='', itemId='Generator of Unique Values') }}</td>
      <td>&#10004;</td>
      <td>&#10007;</td>
      <td>&#10007;</td>
      <td>&#10007;</td>
      <td>&#10007;</td>
    </tr>
    <tr>
      <td>{{ anchor(title='Extra Functionality', collectionType='wiki', collectionId='', itemId='Extras') }}</td>
      <td>&#10004;</td>
      <td>&#10007;</td>
      <td>&#10004;</td>
      <td>&#10007;</td>
      <td>&#10007;</td>
    </tr>
    <tr>
      <td>{{ anchor(title='Kotlin DSL', collectionType='wiki', collectionId='', itemId='Faker DSL') }}</td>
      <td>&#10004;</td>
      <td>&#10007;</td>
      <td>&#10007;</td>
      <td>&#10007;</td>
      <td>&#10007;</td>
    </tr>
    <tr>
      <td>{{ anchor(title='CLI App', collectionType='wiki', collectionId='', itemId='Faker Bot CLI') }}</td>
      <td>&#10004;</td>
      <td>&#10007;</td>
      <td>&#10007;</td>
      <td>&#10007;</td>
      <td>&#10007;</td>
    </tr>
    <tr>
      <td><strong>Actively maintained</strong></td>
      <td>&#10004;</td>
      <td>&#10004;</td>
      <td>&#10004;</td>
      <td>&#10004;</td>
      <td>&#10007;</td>
    </tr>
    <tr>
      <td><strong>Benchmarks</strong></td>
      <td>5482ms</td>
      <td>17529.9ms</td>
      <td>15036.5ms</td>
      <td>NA</td>
      <td>NA</td>
    </tr>
  </tbody>
</table>
{% endfilter %}
