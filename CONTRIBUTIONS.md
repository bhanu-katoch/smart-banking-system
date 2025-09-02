# Contributing to Smart Banking System & Dashboard

Thank you for your interest in contributing! To maintain a smooth workflow and clean repository, please follow the guidelines below.

---

## Branching Strategy

1. **Create a personal branch** for yourself:

   ```bash
   git checkout -b <your-name>
   ```

2. **Create feature branches** inside your personal branch for any new feature or fix:

   ```bash
   git checkout -b <your-name>/feature/<feature-name>
   ```

   Example:

   ```bash
   git checkout -b bhanu/feature/dashboard-dark-mode
   ```

3. **Always branch off from your personal branch** rather than main.

4. **Do not commit directly to `main` or other collaborator branches**.

---

## Commit Guidelines

* Use **clear and descriptive commit messages**.
* Include the type of change:

  * `feat:` for new features
  * `fix:` for bug fixes
  * `docs:` for documentation
  * `style:` for formatting changes
  * `refactor:` for code restructuring
  * `test:` for adding or updating tests

Example:

```bash
git commit -m "feat: add dark mode toggle to Navbar component"
```

---

## Pull Requests

1. **Push your feature branch** to the remote repository:

   ```bash
   git push origin <your-name>/feature/<feature-name>
   ```

2. **Open a Pull Request (PR)** from your feature branch to your personal branch first.

3. **Once reviewed and tested**, merge your personal branch into `main` (or coordinate with project lead).

4. Include screenshots or descriptions of your changes for review.

---

## Code Standards

<!-- * Use **ESLint** or any linting rules provided. -->
* Ensure **consistent formatting** (use Prettier if configured).
* Keep **components modular and reusable**.
* Follow **React best practices** and separation of concerns.

---

## Issues

* Before creating a new issue, check if a similar issue already exists.
* Use descriptive titles and provide steps to reproduce (if applicable).
* Assign yourself to the issue you are working on.

---

## Communication

* Coordinate changes through GitHub Issues and Pull Requests.
* Discuss major architectural changes with the team before implementing.

---

Thank you for contributing to the Smart Banking System & Dashboard! Your efforts keep the project organized, maintainable, and collaborative.
