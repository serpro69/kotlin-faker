- Update functionality to use `AbstractFaker` instead of `Faker`
- Add new filter option - `--faker` (e.g. `--faker core` or `--faker books`)
  - Support multiple values in the filter
- Refactor commands to use `AbstractFaker` implementation based on filters, or use all implementations by default
  - Each provider implementation should have it's own "root-node" name in the output, according to the class name of the faker, e.g. 
    ```text
    Faker()
    └── address
        ├── buildingNumber()
        ├── city()
        └── cityName()

    BooksFaker()
    └── dune
        ├── characters()
        └── quotes
    
    ...
    ```
