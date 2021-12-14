package io.spinnaker.releaseinfo

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.context
import com.github.ajalt.clikt.output.CliktHelpFormatter
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import mu.KotlinLogging
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


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

    private val tag by option(help = "the tag that triggered the build")
        .required()

    private val repository by option(help = "the repository to gather release info for")
        .required()

    private val repoOwner by option(help = "the owner of the repositories to modify")
        .required()

    private val oauthToken by lazy {
        System.getenv(GITHUB_OAUTH_TOKEN_ENV_NAME)
            ?: throw UsageError("A GitHub OAuth token must be provided in the $GITHUB_OAUTH_TOKEN_ENV_NAME environment variable")
    }

    private val githubUrl by option(help = "the root URL of the github instance to use")
        .default("https://github.com")

    private val githubApiEndpoint by option(help = "the API endpoint of the github instance to use")
        .default("https://api.github.com")

    override fun run() {
        logger.info { "Tag: " + tag }
        logger.info { "RepoOnwer: " + repoOwner }
        logger.info { "Repository: " + repository }

        val authorization = "Bearer " + oauthToken
        val baseUrl = "https://api.github.com/repos/spinnaker/orca/releases"
        val objectMapper = ObjectMapper()

        var request = HttpRequest.newBuilder().uri(URI.create(baseUrl))
            .setHeader("Authorization", authorization)
            .GET()
            .build();

        var response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        var jsonNode = objectMapper.readTree(response.body())
        logger.info { response.body() }

    }
}

fun main(args: Array<String>) {
    ReleaseInfo().main(args)
}
