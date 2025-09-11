plugins { `faker-ext-conventions` }

kotlin { sourceSets { jvmMain { dependencies { implementation(libs.bundles.jackson) } } } }
