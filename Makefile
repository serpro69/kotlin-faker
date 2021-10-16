deploy-docs:
	sed -i 's/^\s\sbaseUrl:\shttp:\/\/localhost:8080/  baseUrl: https:\/\/serpro69.github.io\/kotlin-faker/' ./docs/src/orchid/resources/config.yml
	./gradlew :docs:orchidDeploy -PorchidEnvironment=prod
	sed -i 's/^\s\sbaseUrl:\shttps:\/\/serpro69.github.io\/kotlin-faker/  baseUrl: http:\/\/localhost:8080/' ./docs/src/orchid/resources/config.yml

snapshot-in-pre-release:
	./gradlew clean test \
	printVersion \
	nativeImage \
	publishToSonatype \
	-PpromoteToRelease \
	--info

snapshot-major:
	./gradlew clean test \
	printVersion \
	nativeImage \
	publishToSonatype \
	-PbumpComponent=major \
	--info

snapshot-minor:
	./gradlew clean test \
	printVersion \
	nativeImage \
	publishToSonatype \
	-PbumpComponent=minor \
	--info

snapshot-patch:
	./gradlew clean test \
	printVersion \
	nativeImage \
	publishToSonatype \
	-PbumpComponent=patch \
	--info

pre-release-major:
	./gradlew clean test \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PnewPreRelease -PbumpComponent=major \
	--info

	git push origin --tags

pre-release-minor:
	./gradlew clean test \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PnewPreRelease -PbumpComponent=minor \
	--info

	git push origin --tags

pre-release-patch:
	./gradlew clean test \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PnewPreRelease -PbumpComponent=patch \
	--info

	git push origin --tags

next-pre-release:
	./gradlew clean test \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PpreRelease \
	--info

	git push origin --tags

promote-to-release:
	./gradlew clean test \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PpromoteToRelease \
	--info

	git push origin --tags

release-major:
	./gradlew clean test \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PbumpComponent=major \
	--info

	git push origin --tags

release-minor:
	./gradlew clean test \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PbumpComponent=minor \
	--info

	git push origin --tags

release-patch:
	./gradlew clean test \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PbumpComponent=patch \
	--info

	git push origin --tags
