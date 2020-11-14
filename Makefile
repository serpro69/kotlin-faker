pre-release-major:
	./gradlew clean tag -Prelease -PnewPreRelease -PbumpComponent=major

pre-release-minor:
	./gradlew clean tag -Prelease -PnewPreRelease -PbumpComponent=minor

pre-release-patch:
	./gradlew clean tag -Prelease -PnewPreRelease -PbumpComponent=patch

next-pre-release:
	./gradlew clean tag -Prelease -PpreRelease

promote-to-release:
	./gradlew clean tag -Prelease -PpromoteToRelease

release-major:
	./gradlew clean tag -Prelease -PbumpComponent=major

release-minor:
	./gradlew clean tag -Prelease -PbumpComponent=minor

release-patch:
	./gradlew clean tag -Prelease -PbumpComponent=patch
