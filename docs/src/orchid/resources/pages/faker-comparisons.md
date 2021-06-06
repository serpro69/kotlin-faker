---
title: Faker Comparisons
---

# JVM-targeted Faker Libs Comparison

On the surface **kotlin-faker** isn't "unique" by any means and there exist several other JVM-targeted libraries out there with similar functionalities, so you have several options to choose from. 

So why use this one instead? I've decided to make a comparison between kotlin-faker, and others libs that have been out there for quite some time.

<i>The benchmarks time is an average execution time of 10 consecutive runs. Each run includes creating a new Faker instance and generating a 1_000_000 values with the function returning a person's full name.

Note: benchmarks for `blocoio/faker` could not be done due to unexpected exceptions coming from the lib, benchmarks for `moove-it/fakeit` could not be done due to android dependencies in the lib</i>

{% filter compileAs('html') %}
<table class="table is-striped is-hoverable is-fullwidth">
  <thead>
    <tr>
      <th><abbr></abbr></th>
      <th><abbr><a href="https://github.com/serpro69/kotlin-faker"><strong>kotlin-faker</strong></a></abbr></th>
      <th><abbr><a href="https://github.com/DiUS/java-faker">DiUS/java-faker</a></abbr></th>
      <th><abbr><a href="https://github.com/Devskiller/jfairy">Devskiller/jfairy</a></abbr></th>
      <th><abbr><a href="https://github.com/blocoio/faker">blocoico/faker</a></abbr></th>
      <th><abbr><a href="https://github.com/moove-it/fakeit">moove-it/fakeit</a></abbr></th>
    </tr>
  </thead>
  <tfoot>
    <tr>
      <th></th>
      <th><abbr>kotlin-faker</abbr></th>
      <th><abbr>DiUS/java-faker</abbr></th>
      <th><abbr>Devskiller/jfairy</abbr></th>
      <th><abbr>blocoico/faker</abbr></th>
      <th><abbr>moove-it/fakeit</abbr></th>
    </tr>
  </tfoot>
  <tbody>
    <tr>
      <td><strong>Language</strong></td>
      <td><span class="icon"><i class="mdi mdi-language-kotlin"></i></span></td>
      <td><span class="icon"><i class="mdi mdi-language-java"></i></span></td>
      <td><span class="icon"><i class="mdi mdi-language-java"></i></span></td>
      <td><span class="icon"><i class="mdi mdi-language-java"></i></span></td>
      <td><span class="icon"><i class="mdi mdi-language-kotlin"></i></span></td>
    </tr>
    <tr>
      <td>{{ anchor(title='Available Data Providers', collectionType='wiki', collectionId='', itemId='Data Providers') }} (<code>address</code>, <code>name</code>, <code>internet</code>, etc.)</td>
      <td>171</td>
      <td>73</td>
      <td>8</td>
      <td>21</td>
      <td>36</td>
    </tr>
    <tr>
      <td>{{ anchor(title='Available Locales', collectionType='wiki', collectionId='', itemId='Locales') }} (<code>nb-NO</code>, <code>uk</code>, <code>es</code>, <code>zh-CN</code>, etc.)</td>
      <td>55</td>
      <td>47</td>
      <td>10</td>
      <td>46</td>
      <td>44</td>
    <tr>
      <td>{{ anchor(title='Extra Functionality', collectionType='wiki', collectionId='', itemId='Extra Functionality') }}</td>
      <td>&#10004;</td>
      <td>&#10007;</td>
      <td>&#10004;</td>
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
      <td>{{ anchor(title='CLI App', collectionType='wiki', collectionId='', itemId='CLI Application') }}</td>
      <td>&#10004;</td>
      <td>&#10007;</td>
      <td>&#10007;</td>
      <td>&#10007;</td>
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
