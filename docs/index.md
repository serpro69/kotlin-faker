---
template: overrides/home.html
title: kotlin-faker
---

<section class="w-100">
  <!--We fake it. You make it-->
  <div class="md-grid container-row pt-4 my-4">
    <div class="card-white py-3 mx-xs-3 mx-sm-3 mx-4 mx-xl-0 ">
      <div class="container-row-responsive">
        <div class="container ctx-card-white-1 pt-4" style="flex: 1;">
          <div class="text-align-center">
            <p class="display-3 Telegraf-UltraBold text-dark mx-auto mt-4">We fake it.</p>
            <p class="display-3 Telegraf-UltraBold text-pink mx-auto my-4">You make it.</p>
          </div>
        </div>
        <div class="container ctx-card-white-1 text-dark h3" style="flex: 1;">
          <p class="text-content-padding">
            In fact, kotlin-faker is so good at generating fake data,
            <br>
            it almost fooled us into thinking it was real! 😱
            <br><br>
            From names that sound like they belong to secret agents 🕴️
            <br>
            to addresses where superheroes 🦸 might live.
          </p>
        </div>
      </div>
    </div>
  </div>

  <div class="md-grid container pt-5 my-0 my-xl-4 my-lg-4">
    <p class="display-3 text-dark Telegraf-UltraBold mt-4 text-align-center text-start-xl text-start-lg">What kind of data?</p>
    <p class="h2 my-3 text-dark">
      <i>Our focus is on creating realistically-looking fake data that is most suitable for development, testing,
      and data anonymization purposes through data generators that provide...</i>
    </p>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>...a plethora of real-looking data in various domains:</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "Homepage.kt:data_provider_zero"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>Names</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "Homepage.kt:data_provider_one"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>Addresses</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "Homepage.kt:data_provider_two"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>Internet</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "Homepage.kt:data_provider_three"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>Banking</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "faker/commerce/src/integration/kotlin/io/github/serpro69/kfaker/commerce/docs/Homepage.kt:commerce_faker_one"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        Books
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "faker/books/src/integration/kotlin/io/github/serpro69/kfaker/books/docs/Homepage.kt:books_faker_one"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>Movies and TV</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "faker/movies/src/integration/kotlin/io/github/serpro69/kfaker/movies/docs/Homepage.kt:movies_faker_one"
        --8<-- "faker/tvshows/src/integration/kotlin/io/github/serpro69/kfaker/tv/docs/Homepage.kt:tvshows_faker_one"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>...and many more!</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "./Homepage.kt:data_provider_seven"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div style="order:0; flex: 20%;">
        <button
          onclick="location.href='{{ config.site_url }}/wiki/data-providers/'"
          title="Data Providers"
          type="button"
          class="btn-dark btn-xl-large btn-lg-large my-4 box-shadow ml-auto mx-xs-auto mx-sm-auto mx-md-auto"
        >Data Generators</button>
      </div>
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 80%;">
      </div>
    </div>
  </div>
</section>

<section class="w-100">
  <!-- Locales and other features -->
  <div class="md-grid container pt-5 my-0 my-xl-4 my-lg-4">
    <p class="display-3 text-dark Telegraf-UltraBold mt-4 text-align-center text-start-xl text-start-lg">Tell me more...</p>
    <p class="h2 my-3 text-dark">
      <i>…data in 60+ locales:</i>
    </p>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>name</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "Homepage.kt:faker_locale_zero"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>city</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "Homepage.kt:faker_locale_one"
        ```
      </div>
    </div>
    <p class="h2 my-3 text-dark">
      <i>…unique data generation capabilities:</i>
    </p>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>Unique values within a domain</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "Homepage.kt:unique_data_one"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>Unique values within a function</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "Homepage.kt:unique_data_two"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 40%;">
      </div>
      <div style="order:0; flex: 30%;">
        <button
          onclick="location.href='{{ config.site_url }}/wiki/available-locales/'"
          title="Locales"
          type="button"
          class="btn-dark btn-xl-large btn-lg-large my-4 box-shadow ml-auto mx-xs-auto mx-sm-auto mx-md-auto"
        >Locales</button>
      </div>
      <div style="order:0; flex: 30%;">
        <button
          onclick="location.href='{{ config.site_url }}/wiki/unique-generator/'"
          title="Unique Data"
          type="button"
          class="btn-dark btn-xl-large btn-lg-large my-4 box-shadow ml-auto mx-xs-auto mx-sm-auto mx-md-auto"
        >Unique Data </button>
      </div>
    </div>
    <p class="h2 my-3 text-dark">
      <i>…cli app for generator functionality lookup:</i>
    </p>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```text
        --8<-- "docs/snippets/cli-snip.txt:cli_app_zero"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 80%;">
      </div>
      <div style="order:0; flex: 20%;">
        <button
          onclick="location.href='{{ config.site_url }}/wiki/available-locales/'"
          title="Available Locales"
          type="button"
          class="btn-dark btn-xl-large btn-lg-large my-4 box-shadow ml-auto mx-xs-auto mx-sm-auto mx-md-auto"
        >CLI App</button>
      </div>
    </div>
    <p class="h2 my-3 text-dark">
      <i>…and more:</i>
    </p>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>Random instance of any class…</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "Homepage.kt:random_class_instance_one"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>…with pre-configured type generation</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "Homepage.kt:random_class_instance_two"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>String templating</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "Homepage.kt:string_provider_one"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 30%;">
        <p>Random numbers, strings, enums, UUIDs, and more…</p>
      </div>
      <div markdown="1" class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 50%;">
        ```kotlin
        --8<-- "Homepage.kt:random_service_one"
        ```
      </div>
    </div>
    <div class="container container-row-lg container-row-xl my-4">
      <div style="order:0; flex: 20%;">
        <button
          onclick="location.href='{{ config.site_url }}/wiki/unique-generator/'"
          title="Unique Data Generation"
          type="button"
          class="btn-dark btn-xl-large btn-lg-large my-4 box-shadow ml-auto mx-xs-auto mx-sm-auto mx-md-auto"
        >Extras</button>
      </div>
      <div class="text-dark h3 mx-4 my-3 mx-lg-0 my-xl-0" style="order:0; flex: 80%;">
      </div>
    </div>
  </div>
  <div class="md-grid container-row pt-4 my-4">
    <div class="card-white py-3 mx-xs-3 mx-sm-3 mx-4 mx-xl-0 ">
      <div class="container-row-responsive">
        <div class="container ctx-card-white-1 pt-4" style="flex: 1;">
          <div class="text-align-center">
            <p class="display-3 Telegraf-UltraBold text-dark mx-auto mt-4">WARNING</p>
            <p class="display-3 Telegraf-UltraBold text-pink mx-auto my-4">⚠️ ⚠️ ⚠️</p>
          </div>
        </div>
        <div class="container ctx-card-white-1 text-dark h3" style="flex: 1;">
          <p class="text-content-padding">
            <i>All names, addresses, bank accounts, and other data generated by this library - even those based on real strings - are entirely fictional.</i> 🦹
            <br>
            <i>All produced data is fake and generated... poorly</i> 💩
            <br>
            <i>The data is completely made up, but the problems that it tries to solve are real</i> 👽
            <br>
            <i>We take no responsibility for any existential crises caused by the eerily convincing fake data.</i>
            <br>
            <i>Use with caution!</i>
          </p>
        </div>
      </div>
    </div>
  </div>
</section>
