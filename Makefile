SHELL  := env ORCHID_DIAGNOSE=$(ORCHID_DIAGNOSE) $(SHELL)
ORCHID_DIAGNOSE ?= false
.DEFAULT_GOAL := help

# https://stackoverflow.com/a/10858332
# Check that given variables are set and all have non-empty values,
# die with an error otherwise.
#
# Params:
#   1. Variable name(s) to test.
#   2. (optional) Error message to print.
check_defined = \
    $(strip $(foreach 1,$1, \
        $(call __check_defined,$1,$(strip $(value 2)))))
__check_defined = \
    $(if $(value $1),, \
      $(error Undefined $1$(if $2, ($2))))

.PHONY: deploy-docs
deploy-docs: ## deploys documentation with orchid
	sed -i 's/^\s\sbaseUrl:\shttp:\/\/localhost:8080/  baseUrl: https:\/\/serpro69.github.io\/kotlin-faker/' ./docs/src/orchid/resources/config.yml
	sed -i 's/^\s\shomePageOnly:.*/#/' ./docs/src/orchid/resources/config.yml
	./gradlew :docs:orchidDeploy -PorchidEnvironment=prod -PorchidDiagnose=$(ORCHID_DIAGNOSE)
	git checkout ./docs/src/orchid/resources/config.yml

.PHONY: snapshot-in-pre-release
_snapshot-in-pre-release: ## (DEPRECATED) publishes next snapshot in current pre-release version
	./gradlew clean test integrationTest \
	printVersion \
	nativeImage \
	publishToSonatype \
	-PpromoteToRelease \
	--info

.PHONY: snapshot-major
_snapshot-major: ## (DEPRECATED) publishes next snapshot with a major version bump
	./gradlew clean test integrationTest \
	printVersion \
	nativeImage \
	publishToSonatype \
	-PbumpComponent=major \
	--info

.PHONY: snapshot-minor
_snapshot-minor: ## (DEPRECATED) publishes next snapshot with a minor version bump
	./gradlew clean test integrationTest \
	printVersion \
	nativeImage \
	publishToSonatype \
	-PbumpComponent=minor \
	--info

.PHONY: snapshot-patch
_snapshot-patch: ## (DEPRECATED) publishes next snapshot with a patch version bump
	./gradlew clean test integrationTest \
	printVersion \
	nativeImage \
	publishToSonatype \
	-PbumpComponent=patch \
	--info

.PHONY: pre-release-major
_pre-release-major: ## (DEPRECATED) publishes next pre-release version with a major version bump
	./gradlew clean test integrationTest \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PnewPreRelease -PbumpComponent=major \
	--info

	git push origin --tags

.PHONY: pre-release-minor
_pre-release-minor: ## (DEPRECATED) publishes next pre-release with a minor version bump
	./gradlew clean test integrationTest \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PnewPreRelease -PbumpComponent=minor \
	--info

	git push origin --tags

.PHONY: pre-release-patch
_pre-release-patch: ## (DEPRECATED) publishes next pre-release with a patch version bump
	./gradlew clean test integrationTest \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PnewPreRelease -PbumpComponent=patch \
	--info

	git push origin --tags

.PHONY: next-pre-release
_next-pre-release: ## (DEPRECATED) publishes next pre-release version
	./gradlew clean test integrationTest \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PpreRelease \
	--info

	git push origin --tags

.PHONY: promote-to-release
_promote-to-release: ## (DEPRECATED) publishes next release from the current pre-release version
	./gradlew clean test integrationTest \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PpromoteToRelease \
	--info

	git push origin --tags

.PHONY: release-major
_release-major: ## (DEPRECATED) publishes next major release version
	./gradlew clean test integrationTest \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PbumpComponent=major \
	--info

	git push origin --tags

.PHONY: release-minor
_release-minor: ## (DEPRECATED) publishes next minor release version
	./gradlew clean test integrationTest \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PbumpComponent=minor \
	--info

	git push origin --tags

.PHONY: release-patch
_release-patch: ## (DEPRECATED) publishes next patch release version
	./gradlew clean test integrationTest \
	tag \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -PbumpComponent=patch \
	--info

	git push origin --tags

.PHONY: release
release: ## publishes the next release with a specified VERSION
	@:$(call check_defined, VERSION, semantic version string - 'X.Y.Z(-rc.\d+)?')

	./gradlew clean test integrationTest \
	nativeImage \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Pversion=$(VERSION) \
	--info

	git tag v$(VERSION)
	git push origin --tags

.PHONY: help
help: ## Displays this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
