<!-- 
Copilot Custom Instructions for VSCodeMobile Project
This file provides workspace-specific guidance to GitHub Copilot
-->

## Project Overview
VSCodeMobile v1.0 - A mobile code editor inspired by VS Code, built with Kotlin and Jetpack Compose. Includes Python support with syntax highlighting and basic code analysis.

## Tech Stack
- Kotlin 2.1+
- Android Gradle Plugin 8.5+
- Gradle 8.7+
- Jetpack Compose (Modern Android UI)
- Material3
- MVVM Architecture
- Min SDK 24, Target SDK 34

## Key Guidelines for AI Assistance

### Code Style
- Use Kotlin DSL (.kts) for all Gradle files
- Follow Kotlinlang official code style
- UI code must use Jetpack Compose (no XML layouts)
- Use `@Composable` for UI components
- Prefer immutable data classes with `copy()` for updates

### Architecture Rules
- **MVVM Pattern**: ViewModels manage state, Composables render UI
- **No buildscript blocks**: Use new plugins {} DSL only
- **No allprojects block**: Configure each module independently
- **Type-safe dependencies**: Use Gradle version catalogs where possible

### Python Support Module
Located in `pythonsupport/`:
- `PythonFileHandler` - Identifies .py files
- `PythonSyntaxHighlighter` - Basic syntax highlighting
- `PythonAnalyzer` - Code analysis (parentheses, indentation, imports)
- `LanguageServerBridge` - Interface for future LSP integration (Pyright, pylsp)

### UI Components Location
All Compose components in `ui/components/`:
- `CodeEditor.kt` - Main editor with line numbers
- `FileExplorer.kt` - File tree sidebar
- `TopBar.kt` - Action buttons (new, save)
- `NewFileDialog.kt` - Create file dialog
- `theme/` - Color, Typography, Theme definitions

### Storage
- `storage/FileRepository.kt` - Handles local file persistence
- Uses Android Context.filesDir for storage
- Files named as `{id}_{filename}`

### Dependencies to Maintain
- androidx.lifecycle:lifecycle-viewmodel-compose
- androidx.navigation:navigation-compose
- androidx.compose.material3:material3
- No native dependencies (no NDK, CMake)

### When Adding Features
1. Keep MVVM separation (logic in ViewModel, UI in Composables)
2. Add new features to appropriate modules (Python support → pythonsupport/)
3. Update tests when adding new functionality
4. Ensure backward compatibility with v1.0 features
5. Document LSP interface extensions

### Future Versions Consideration
Design all modules considering:
- v1.1: Advanced theme support, user preferences
- v2.0: Terminal integration, Termux connection, LSP connection
- v3.0: Multi-editor tabs, Git integration, debugging

### GitHub Actions Compatibility
Project must build successfully via:
```bash
./gradlew assembleDebug
./gradlew test
```

All workflows defined in `.github/workflows/`

### Version Info
- Current: 1.0.0
- versionCode: 1
- Min API: 24 (Android 7.0)
- Target API: 34 (Android 14)

### Common Tasks
- **Add UI Component**: Create in `ui/components/`, add @Composable function, use theme colors
- **Add Python Feature**: Extend appropriate class in `pythonsupport/`
- **Add Storage**: Extend `FileRepository` with new methods
- **Add ViewModel Logic**: Add method to `EditorViewModel`, emit state via StateFlow
- **Build**: Run `./gradlew assembleDebug`
- **Tests**: Run `./gradlew test`

## Documentation References
- README.md - Project overview and structure
- INSTALL.md - Setup and build instructions
- See in-code comments for component-specific details
