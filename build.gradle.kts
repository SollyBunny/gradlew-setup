plugins {
	kotlin("jvm") version "2.2.0"
	application
}

group = "ticket-system"

repositories {
	mavenCentral()
}

dependencies {
	implementation(kotlin("stdlib"))
	testImplementation(kotlin("test"))
}

application {
	mainClass.set("main.MainKt")
}

tasks.named<JavaExec>("run") {
	standardInput = System.`in`
	standardOutput = System.out
	errorOutput = System.err
}

tasks.named<Jar>("jar") {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
	manifest {
		attributes["Main-Class"] = "main.MainKt"
	}
	from(sourceSets.main.get().output)
	from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}

tasks.test {
	useJUnitPlatform()
	testLogging {
		events("passed", "skipped", "failed", "standardOut", "standardError")
	}
}
