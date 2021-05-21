snapshot-major:
	./gradlew clean test \
	printVersion \
	nativeImage \
	publishFakerCorePublicationToSonatypeRepository \
	-PbumpComponent=major \
	--info

snapshot-minor:
	./gradlew clean test \
	printVersion \
	nativeImage \
	publishFakerCorePublicationToSonatypeRepository \
	-PbumpComponent=minor \
	--info

snapshot-patch:
	./gradlew clean test \
	printVersion \
	nativeImage \
	publishFakerCorePublicationToSonatypeRepository \
	-PbumpComponent=patch \
	--info

pre-release-major:
	./gradlew clean test \
	tag \
	nativeImage \
	publishFakerCorePublicationToSonatypeRepository \
	-Prelease -PnewPreRelease -PbumpComponent=major \
	--info

	git push origin --tags

pre-release-minor:
	./gradlew clean test \
	tag \
	nativeImage \
	publishFakerCorePublicationToSonatypeRepository \
	-Prelease -PnewPreRelease -PbumpComponent=minor \
	--info

	git push origin --tags

pre-release-patch:
	./gradlew clean test \
	tag \
	nativeImage \
	publishFakerCorePublicationToSonatypeRepository \
	-Prelease -PnewPreRelease -PbumpComponent=patch \
	--info

	git push origin --tags

next-pre-release:
	./gradlew clean test \
	tag \
	nativeImage \
	publishFakerCorePublicationToSonatypeRepository \
	-Prelease -PpreRelease \
	--info

	git push origin --tags

promote-to-release:
	./gradlew clean test \
	tag \
	nativeImage \
	publishFakerCorePublicationToSonatypeRepository \
	-Prelease -PpromoteToRelease \
	--info

	git push origin --tags

release-major:
	./gradlew clean test \
	tag \
	nativeImage \
	publishFakerCorePublicationToSonatypeRepository \
	-Prelease -PbumpComponent=major \
	--info

	git push origin --tags

release-minor:
	./gradlew clean test \
	tag \
	nativeImage \
	publishFakerCorePublicationToSonatypeRepository \
	-Prelease -PbumpComponent=minor \
	--info

	git push origin --tags

release-patch:
	./gradlew clean test \
	tag \
	nativeImage \
	publishFakerCorePublicationToSonatypeRepository \
	-Prelease -PbumpComponent=patch \
	--info

	git push origin --tags
