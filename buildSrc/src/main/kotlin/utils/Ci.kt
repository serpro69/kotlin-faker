object Ci {

    // The build number from GitHub Actions, if available, which is used to create a unique snapshot
    // version.
    private val githubBuildNumber = System.getenv("GITHUB_RUN_NUMBER")

    /** Is the build currently running on CI. */
    private val isCI = System.getenv("CI").toBoolean()

    /**
     * Property to flag the build as JVM only, can be used to run checks on local machine much
     * faster.
     */
    const val JVM_ONLY = "jvmOnly"
}
