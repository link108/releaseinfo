package io.spinnaker.releaseinfo

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import mu.KotlinLogging

class ReleaseInfo : CliktCommand() {

    init {
        context { helpFormatter = CliktHelpFormatter(showDefaultValues = true) }
    }

    private val logger = KotlinLogging.logger {}

    companion object {
        const val REF_PREFIX = "refs/tags/v"
        const val BRANCH_PREFIX = "origin/"
        const val GITHUB_OAUTH_TOKEN_ENV_NAME = "GITHUB_OAUTH"
        const val MAVEN_REPO_USERNAME_ENV_NAME = "NEXUS_USERNAME"
        const val MAVEN_REPO_PASSWORD_ENV_NAME = "NEXUS_PASSWORD"
        const val BASE_BRANCH = "master"
    }

//    private val version by option("--ref", help = "the release ref triggering this dependency bump").convert { ref ->
//        if (!ref.startsWith(REF_PREFIX)) {
//            fail("Ref '$ref' is not a valid release ref")
//        }
//        ref.removePrefix(REF_PREFIX)
//    }.required()

//    private val tag by option(help = "the tag that triggered the build")
//        .required()
//
//    private val repository by option(help = "the repository to gather release info for")
//        .required()
//
//    private val repoOwner by option(help = "the owner of the repositories to modify")
//        .required()

    private val oauthToken by lazy {
        System.getenv(GITHUB_OAUTH_TOKEN_ENV_NAME)
            ?: throw UsageError("A GitHub OAuth token must be provided in the $GITHUB_OAUTH_TOKEN_ENV_NAME environment variable")
    }

    private val githubUrl by option(help = "the root URL of the github instance to use")
        .default("https://github.com")

    private val githubApiEndpoint by option(help = "the API endpoint of the github instance to use")
        .default("https://api.github.com")

    override fun run() {
//        logger.info { "Tag: " + tag }
//        logger.info { "RepoOnwer: " + repoOwner }
//        logger.info { "Repository: " + repository }
        logger.info { "HELLO WORLD" }
    }
}

fun main(args: Array<String>) {
    ReleaseInfo().main(args)
}
