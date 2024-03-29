# releaseinfo [wip]

Releaseinfo is a github action to gather information required for releasing the project. It requires the following inputs:

* **ref**: The git ref that triggered this workflow run. This should almost always be `${{ github.ref }}`
* **key**: The key in the `gradle.properties` file that will be modified (e.g. `korkVersion`)
* **repositories**: The list of repositories to modify (i.e. the ones that depend on this repository)

An example workflow looks something like this:

```yaml
name: Release

on:
  push:
    tags:
    - "v.+"

jobs:
  release-info:
    runs-on: ubuntu-latest
    steps:
    - uses: link108/releaseinfo@master
      with:
        ref: ${{ github.ref }}
        key: korkVersion
        repository: fiat
      env:
        GITHUB_OAUTH: ${{ secrets.REPO_OAUTH_TOKEN }}
```
