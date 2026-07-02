# treinamento-refatoração-spring

## Copilot skill (Spring Boot)

This repository now includes a repository-scoped Copilot skill in English:

- `.github/copilot/skills/github-awesome-copilot-skills-java-springboot-skill-md.md`

The skill content is the original `java-springboot` guidance from `github/awesome-copilot`.

## How to use it

- Open Copilot Chat in your IDE while this repository is open.
- Ask for code changes normally (controller/service/repository/test requests).
- Copilot should use `.github/copilot-instructions.md` and the skill file as guidance.

Example prompts:

- `Create a new endpoint in BookResource following Spring Boot best practices.`
- `Refactor this service to use constructor injection and add unit tests.`
- `Add request validation and global exception handling for this endpoint.`
