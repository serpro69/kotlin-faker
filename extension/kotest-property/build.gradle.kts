plugins { `faker-ext-conventions` }

kotlin { sourceSets { jvmMain { dependencies { compileOnly(libs.test.kotest.property) } } } }
