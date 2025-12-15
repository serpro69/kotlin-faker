SHELL  := env ORCHID_DIAGNOSE=$(ORCHID_DIAGNOSE) $(SHELL)
ORCHID_DIAGNOSE ?= false
.SHELLFLAGS := -ec
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

# use java 17 to build because 'org.graalvm.buildtools.native' in cli-bot requires java version >= 11
# both the libs and the cli app use java toolchains and will be built with java compatibility of version 8
__java_version_ok := $(shell java -version 2>&1|grep '17.0' >/dev/null; printf $$?)

.PHONY: check_java
check_java: ## check current java version (mostly used in other targets)
ifneq ($(__java_version_ok),$(shell echo 0))
	$(error "Expected java 17")
endif

.PHONY: docs
docs: ## serve documentation on the localhost
	@mkdocs serve

# .PHONY: deploy-docs
# deploy-docs: ## deploys documentation with orchid
# 	cd docs; \
# 	sed -i 's/^\s\sbaseUrl:\shttp:\/\/localhost:8080/  baseUrl: https:\/\/serpro69.github.io\/kotlin-faker/' ./src/orchid/resources/config.yml; \
# 	sed -i 's/^\s\shomePageOnly:.*/#/' ./src/orchid/resources/config.yml; \
# 	./gradlew orchidDeploy -PorchidEnvironment=prod -PorchidDiagnose=$(ORCHID_DIAGNOSE); \
# 	git checkout ./src/orchid/resources/config.yml

.PHONY: snapshot-minor
snapshot-minor: ## publishes next snapshot with a minor version bump
	./gradlew clean test spotlessCheck
	./gradlew nativeCompile
	./gradlew publishToSonatype --info

.PHONY: pre-release-major
pre-release-major: ## publishes next pre-release version with a major version bump
	./gradlew test spotlessCheck \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	tag \
	-Prelease -PpreRelease -Pincrement=major \
	--info

.PHONY: pre-release-minor
pre-release-minor: ## publishes next pre-release with a minor version bump
	./gradlew test spotlessCheck \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	tag \
	-Prelease -PpreRelease -Pincrement=minor \
	--info

.PHONY: pre-release-patch
pre-release-patch: ## publishes next pre-release with a patch version bump
	./gradlew test spotlessCheck \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	tag \
	-Prelease -PpreRelease -Pincrement=patch \
	--info

.PHONY: next-pre-release
next-pre-release: ## publishes next pre-release version
	./gradlew test spotlessCheck \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	tag \
	-Prelease -Pincrement=pre_release \
	--info

.PHONY: promote-to-release
promote-to-release: ## publishes next release from the current pre-release version
	./gradlew test spotlessCheck \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	tag \
	-Prelease -PpromoteRelease \
	--info

.PHONY: release-major
release-major: ## publishes next major release version
	./gradlew test spotlessCheck \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	tag \
	-Prelease -Pincrement=major \
	--info

.PHONY: release-minor
release-minor: ## publishes next minor release version
	./gradlew test spotlessCheck \
	tag \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	-Prelease -Pincrement=minor \
	--info

.PHONY: release-patch
release-patch: ## publishes next patch release version
	./gradlew test spotlessCheck \
	nativeCompile \
	publishToSonatype \
	closeSonatypeStagingRepository \
	tag \
	-Prelease -Pincrement=patch \
	--info

.PHONY: release
release: check_java ## publishes the next release with a specified VERSION
	@:$(call check_defined, VERSION, semantic version string - 'X.Y.Z(-rc.\d+)?')

	# run tests
	./gradlew test spotlessCheck -Pversion=$(VERSION)
	# build and test native image
	./gradlew nativeCompile -Pversion=$(VERSION) --info
	./cli-bot/build/native/nativeCompile/faker-bot_$(VERSION) list --verbose >/dev/null || false
	./cli-bot/build/native/nativeCompile/faker-bot_$(VERSION) lookup a --verbose >/dev/null || false
	# publish to sonatype and close staging repo
	./gradlew publishToSonatype closeSonatypeStagingRepository -Pversion=$(VERSION) --info
	# create and push git tag
	git tag v$(VERSION)

.PHONY: _deploy_docs
_deploy_docs: # temporary target to deploy docs with mike, TODO: move to github actions
	@./gradlew dokkaGfmMultiModule; \
	mv build/dokka/gfmMultiModule docs/api; \
	MKDOCS_SITE_URL="https://serpro69.github.io/kotlin-faker/" mike deploy 1.6 latest -t '1.6.0 (latest)' -u; \
	MKDOCS_SITE_URL="https://serpro69.github.io/kotlin-faker/" mike deploy 2.0 dev -t '2.1.0-SNAPSHOT' -u

.PHONY: help
help: ## Displays this help
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'
