plugins {
    application
}

dependencies {
    implementation(project(":models"))
    implementation("com.google.inject:guice:5.1.0")
    implementation("com.google.code.gson:gson:2.9.1")

    testImplementation("name.falgout.jeffrey.testing.junit5:guice-extension:1.2.1")
    testImplementation("org.mockito:mockito-inline:4.8.0")
}

application {
    mainClass.set("ru.mail.Main")
}