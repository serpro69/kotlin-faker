---
---

# Data Providers

Below is the list of available Fakers domains and their data providers (data generators) that correspond to the yaml dictionary files found in [core/locales/en](https://github.com/serpro69/kotlin-faker/tree/master/core/src/main/resources/locales/en)

{% info %}
Not all (although most) of the providers and/or their functions are implemented at this point. For more details on available functions see the documentation for each provider below, or go to the {{ anchor(title='Core API', collectionType='kotlindoc', collectionId='modules-core', itemId='Core API') }} page to browse the API reference documentation.
{% endinfo %}

{% warn %}
Below pages contain code blocks with inlined yaml dictionaries, some of them can be quite big. This could affect your internet traffic if you're viewing these docs on a mobile device.
{% endwarn %}

{% set pages = findAll(collectionType='pages', collectionId='data-provider') %}

"Core" `Faker`

{% for page in pages %}
{% if page.link | split('/') contains "core" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`BooksFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Books') }})

{% for page in pages %}
{% if page.link | split('/') contains "books" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`CommerceFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Commerce') }})

{% for page in pages %}
{% if page.link | split('/') contains "commerce" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`CreaturesFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Creatures') }})

{% for page in pages %}
{% if page.link | split('/') contains "creatures" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`DatabasesFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Databases') }})

{% for page in pages %}
{% if page.link | split('/') contains "databases" %}
- [{{ page.title }}]({{ page.link }})
  {% endif %}
  {% endfor %}

[`EduFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Edu') }})

{% for page in pages %}
{% if page.link | split('/') contains "edu" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`GamesFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Games') }})

{% for page in pages %}
{% if page.link | split('/') contains "games" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`HumorFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Humor') }})

{% for page in pages %}
{% if page.link | split('/') contains "humor" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`JapaneseMediaFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Japmedia') }})

{% for page in pages %}
{% if page.link | split('/') contains "japmedia" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`LoremFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Lorem') }})

{% for page in pages %}
{% if page.link | split('/') contains "lorem" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`MiscFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Misc') }})

{% for page in pages %}
{% if page.link | split('/') contains "misc" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`MoviesFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Movies') }})

{% for page in pages %}
{% if page.link | split('/') contains "movies" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`MusicFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Music') }})

{% for page in pages %}
{% if page.link | split('/') contains "music" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`SportsFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Sports') }})

{% for page in pages %}
{% if page.link | split('/') contains "sports" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`TechFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Tech') }})

{% for page in pages %}
{% if page.link | split('/') contains "tech" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`TravelFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Travel') }})

{% for page in pages %}
{% if page.link | split('/') contains "travel" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}

[`TvShowsFaker`]({{ link(collectionType='pages', collectionId='fakers', itemId='Tvshows') }})

{% for page in pages %}
{% if page.link | split('/') contains "tvshows" %}
- [{{ page.title }}]({{ page.link }})
{% endif %}
{% endfor %}
