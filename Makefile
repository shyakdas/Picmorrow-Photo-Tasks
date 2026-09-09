.PHONY: lint test coverage ci

lint:
	./gradlew ktlintCheck detekt :app:lintDebug

test:
	./gradlew testDebugUnitTest

coverage:
	./gradlew testDebugUnitTest jacocoTestReport

ci:
	./gradlew ktlintCheck detekt :app:lintDebug testDebugUnitTest jacocoTestReport
