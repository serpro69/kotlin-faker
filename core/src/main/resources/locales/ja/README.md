To keep the Japanese locale file from getting unwieldy, this directory is
used for the translations that you might expect to find in `ja.yml` in
the parent directory.  Each file in this directory corresponds to the
Faker class of the same name.  That is, `internet.yml` in this directory
contains the data for the methods in `Faker::Internet`.

Use the following YAML as the beginning of any new file you add to this
directory:

```
ja:
  faker:
```

---

***NB! If a new file is added, the [`Constants#jaFileNames`](../../../kotlin/io/github/serpro69/kfaker/Constants.kt) property needs to be updated, so that the file is picked up by the `FakerService`.***
