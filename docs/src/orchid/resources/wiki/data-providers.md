---
---

# Data Providers

Below is the list of available data providers (aka "categories" or "domains") that correspond to the yaml dictionary files found in [core/locales/en](https://github.com/serpro69/kotlin-faker/tree/master/core/src/main/resources/locales/en)

<i>Note that not all (although most) of the providers and/or their functions are implemented at this point. For more details on available functions see the documentation for each provider below.</i>

{% for page in findAll(collectionType='pages', collectionId='data-provider') %}
- [{{ page.title }}]({{ page.link }})
{% endfor %}
