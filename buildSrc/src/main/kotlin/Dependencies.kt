object Dependencies {
    val kotlinCoroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}" }
    val kotlin by lazy { "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}" }
    val okhttp3LoggingInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp3LoggingInterceptor}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofit}" }
    val retrofitConverterGson by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofit}" }
    val junitApi by lazy { "org.junit.jupiter:junit-jupiter-api:${Versions.jUnit}" }
    val junitParams by lazy { "org.junit.jupiter:junit-jupiter-params:${Versions.jUnit}" }
    val junitEngine by lazy { "org.junit.jupiter:junit-jupiter-engine:${Versions.jUnit}" }
    val quickbooksData by lazy { "com.intuit.quickbooks-online:ipp-v3-java-data:${Versions.quickbooks}" }
    val quickbooksDevKit by lazy { "com.intuit.quickbooks-online:ipp-v3-java-devkit:${Versions.quickbooks}" }
    val quickbooksOAuth by lazy { "com.intuit.quickbooks-online:oauth2-platform-api:${Versions.quickbooks}" }
    val koinCore by lazy { "io.insert-koin:koin-core:${Versions.koinCore}" }
    val koinAnnotations by lazy { "io.insert-koin:koin-annotations:${Versions.koinAnnotations}" }
    val koinKsp by lazy { "io.insert-koin:koin-ksp-compiler:${Versions.koinAnnotations}" }
    val mockk by lazy { "io.mockk:mockk:${Versions.mockk}" }
    val slf4j by lazy { "org.slf4j:slf4j-simple:${Versions.slf4j}" }
    val swingCoroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-swing:${Versions.coroutines}" }
    val javaFxCoroutines by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-javafx:${Versions.coroutines}" }
    val featherIcons by lazy { "br.com.devsrsouza.compose.icons.jetbrains:feather:${Versions.featherIcons}" }
    val exposedCore by lazy { "org.jetbrains.exposed:exposed-core:${Versions.exposed}" }
    val exposedDao by lazy { "org.jetbrains.exposed:exposed-dao:${Versions.exposed}" }
    val exposedJdbc by lazy { "org.jetbrains.exposed:exposed-jdbc:${Versions.exposed}" }
    val exposedJodaTime by lazy { "org.jetbrains.exposed:exposed-jodatime:${Versions.exposed}" }
    val coroutinesTest by lazy { "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}" }
    val h2Database by lazy { "com.h2database:h2:${Versions.h2Database}" }
}