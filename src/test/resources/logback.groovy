import ch.qos.logback.classic.AsyncAppender

scan("30 seconds")
def LOG_PATH = "logs"
def LOG_ARCHIVE = "${LOG_PATH}/archive"

appender("Console-Appender", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "[%t] [%-5level] %date{ISO8601} %c{0} %ex - %msg%n"
    }
}

appender("File-Appender", FileAppender) {
    file = "${LOG_PATH}/logfile.log"

    encoder(PatternLayoutEncoder) {
        pattern = "[%t] [%-5level] %date{ISO8601} %c{0} %ex - %msg%n"
        outputPatternAsHeader = true
    }
}

appender("RollingFile-Appender", RollingFileAppender) {
    file = "${LOG_PATH}/rollingfile.log"

    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_ARCHIVE}/rollingfile.log%d{yyyy-MM-dd}.log"
        maxHistory = 30
        totalSizeCap = "1KB"
    }

    encoder(PatternLayoutEncoder) {
        pattern = "[%t] [%-5level] %date{ISO8601} %c{0} %ex - %msg%n"
    }
}

appender("Async-Appender", AsyncAppender) {
    appenderRef("RollingFile-Appender")
}

root(DEBUG, ["Console-Appender"])
logger("io.github.serpro69.kfaker", DEBUG, ["Console-Appender", "File-Appender", "Async-Appender"], false)
