package utils

import javax.xml.parsers.DocumentBuilderFactory
import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.w3c.dom.Element

/**
 * Enforce measured lines/branches for handwritten common sources, independently of JVM legacy code.
 */
abstract class VerifyCommonCoverage : DefaultTask() {
    @get:InputFile @get:PathSensitive(PathSensitivity.NONE) abstract val report: RegularFileProperty

    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val commonSources: ConfigurableFileCollection

    @TaskAction
    fun verify() {
        val sourceKeys =
            commonSources.files
                .map { source ->
                    val packageName =
                        Regex("(?m)^package\\s+([\\w.]+)")
                            .find(source.readText())
                            ?.groupValues
                            ?.get(1)
                            .orEmpty()
                            .replace('.', '/')
                    "$packageName/${source.name}"
                }
                .toSet()
        check(sourceKeys.isNotEmpty()) { "No handwritten common sources to measure" }
        val factory =
            DocumentBuilderFactory.newInstance().apply {
                setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
                setFeature("http://xml.org/sax/features/external-general-entities", false)
                setFeature("http://xml.org/sax/features/external-parameter-entities", false)
            }
        val document = factory.newDocumentBuilder().parse(report.get().asFile)
        val files = document.getElementsByTagName("sourcefile")
        var coveredLines = 0
        var coveredBranches = 0
        val misses = mutableListOf<String>()
        for (index in 0 until files.length) {
            val file = files.item(index) as Element
            val packageName = (file.parentNode as Element).getAttribute("name")
            val key = "$packageName/${file.getAttribute("name")}"
            if (key !in sourceKeys) continue
            val counters = file.getElementsByTagName("counter")
            for (counterIndex in 0 until counters.length) {
                val counter = counters.item(counterIndex) as Element
                val type = counter.getAttribute("type")
                if (type != "LINE" && type != "BRANCH") continue
                val missed = counter.getAttribute("missed").toInt()
                val covered = counter.getAttribute("covered").toInt()
                if (type == "LINE") coveredLines += covered else coveredBranches += covered
                if (missed != 0) misses.add("$key: $missed missed $type units")
            }
        }
        check(coveredLines > 0) { "Common coverage is empty or has no executed lines" }
        check(misses.isEmpty()) { "Common coverage must be 100%: ${misses.joinToString()}" }
        logger.lifecycle(
            "Common JVM coverage: $coveredLines lines, $coveredBranches branches; zero missed"
        )
    }
}
