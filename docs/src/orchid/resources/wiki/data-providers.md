---
---

# Data Providers

Below is the list of available data providers (aka "categories" or "domains") that correspond to the yaml dictionary files found in [core/locales/en](https://github.com/serpro69/kotlin-faker/tree/master/core/src/main/resources/locales/en)

{% info %}
Not all (although most) of the providers and/or their functions are implemented at this point. For more details on available functions see the documentation for each provider below.
{% endinfo %}

{% warn %}
Below pages contain code blocks with inlined yaml dictionaries, some of them can be quite big. This could affect your internet traffic if you're viewing these docs on a mobile device.
{% endwarn %}

{% for page in findAll(collectionType='pages', collectionId='data-provider') %}
- [{{ page.title }}]({{ page.link }})
{% endfor %}
