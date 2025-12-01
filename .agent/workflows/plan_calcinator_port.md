---
description: Analyze Calcinator code in main and plan port to 3.3.1
---

// turbo-all

1. Switch to the main branch to find the original code.
   ```bash
   git checkout main
   ```

2. Find all files related to the Calcinator to identify what needs porting.
   ```bash
   find . -name "*Calcinator*"
   ```

3. **Read Files**: Read the content of the files found in step 2 (Block, BlockEntity, Screen, Menu, Recipe, etc.) to understand the logic.

4. Switch back to the target branch (3.3.1).
   ```bash
   git checkout 3.3.1
   ```

5. **Create Plan**: Create a detailed `implementation_plan` artifact for porting the Calcinator to Architectury on 3.3.1.
