---
title: bird
---

## `Faker().bird`

??? example "dictionary file"
    === "yaml :simple-yaml:"
        ```yaml
        --8<-- "core/src/main/resources/locales/en/bird.yml:bird_provider_dict"
        ```

=== "kotlin :material-language-kotlin:"
    ```kotlin
    CreaturesFaker().bird.orderCommonMap.Accipitriformes() // => Bateleur

    CreaturesFaker().bird.orderCommonMap.Anseriformes() // => Brant

    CreaturesFaker().bird.orderCommonMap.Apterygiformes() // => Kiwi

    CreaturesFaker().bird.orderCommonMap.Bucerotiformes() // => Hoopoe

    CreaturesFaker().bird.orderCommonMap.Caprimulgiformes() // => Avocetbill

    CreaturesFaker().bird.orderCommonMap.Cariamiformes() // => Seriema

    CreaturesFaker().bird.orderCommonMap.Casuariiformes() // => Cassowary

    CreaturesFaker().bird.orderCommonMap.Cathartiformes() // => Condor

    CreaturesFaker().bird.orderCommonMap.Charadriiformes() // => Auk

    CreaturesFaker().bird.orderCommonMap.Ciconiiformes() // => Adjutant

    CreaturesFaker().bird.orderCommonMap.Coliiformes() // => Mousebird

    CreaturesFaker().bird.orderCommonMap.Columbiformes() // => Bronzewing

    CreaturesFaker().bird.orderCommonMap.Coraciiformes() // => Dollarbird

    CreaturesFaker().bird.orderCommonMap.Cuculiformes() // => Ani

    CreaturesFaker().bird.orderCommonMap.Eurypygiformes() // => Kagu

    CreaturesFaker().bird.orderCommonMap.Falconiformes() // => Caracara

    CreaturesFaker().bird.orderCommonMap.Galbuliformes() // => Jacamar

    CreaturesFaker().bird.orderCommonMap.Galliformes() // => Argus

    CreaturesFaker().bird.orderCommonMap.Gaviiformes() // => Loon

    CreaturesFaker().bird.orderCommonMap.Gruiformes() // => Brolga

    CreaturesFaker().bird.orderCommonMap.Mesitornithiformes() // => Mesite

    CreaturesFaker().bird.orderCommonMap.Musophagiformes() // => Turaco

    CreaturesFaker().bird.orderCommonMap.Opisthocomiformes() // => Hoatzin

    CreaturesFaker().bird.orderCommonMap.Otidiformes() // => Bustard

    CreaturesFaker().bird.orderCommonMap.Passeriformes() // => Accentor

    CreaturesFaker().bird.orderCommonMap.Pelecaniformes() // => Bittern

    CreaturesFaker().bird.orderCommonMap.Phaethontiformes() // => Tropicbird

    CreaturesFaker().bird.orderCommonMap.Phoenicopteriformes() // => Flamingo

    CreaturesFaker().bird.orderCommonMap.Piciformes() // => Aracari

    CreaturesFaker().bird.orderCommonMap.Podicipediformes() // => Grebe

    CreaturesFaker().bird.orderCommonMap.Procellariiformes() // => Albatross

    CreaturesFaker().bird.orderCommonMap.Psittaciformes() // => Bluebonnet

    CreaturesFaker().bird.orderCommonMap.Pterocliformes() // => Sandgrouse

    CreaturesFaker().bird.orderCommonMap.Rheiformes() // => Rhea

    CreaturesFaker().bird.orderCommonMap.Sphenisciformes() // => Penguin

    CreaturesFaker().bird.orderCommonMap.Strigiformes() // => Boobook

    CreaturesFaker().bird.orderCommonMap.Struthioniformes() // => Ostrich

    CreaturesFaker().bird.orderCommonMap.Suliformes() // => Anhinga

    CreaturesFaker().bird.orderCommonMap.Tinamiformes() // => Nothura

    CreaturesFaker().bird.orderCommonMap.Trogoniformes() // => Quetzal

    CreaturesFaker().bird.anatomy() // => back

    CreaturesFaker().bird.anatomyPastTense() // => backed

    CreaturesFaker().bird.geo() // => African

    CreaturesFaker().bird.colors() // => ash

    CreaturesFaker().bird.emotionalAdjectives() // => angry

    CreaturesFaker().bird.sillyAdjectives() // => anarchist

    CreaturesFaker().bird.adjectives() // => banded

    CreaturesFaker().bird.plausibleCommonNames() // => Egyptian lazuli Herons, Egrets, and Bitterns

    CreaturesFaker().bird.implausibleCommonNames() // => Waters's furious Limpkin

    CreaturesFaker().bird.commonFamilyName() // => Accentors
    ```
