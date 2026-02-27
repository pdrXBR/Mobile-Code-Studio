# Mobile-Code-Studio
O Mobile Code Studio é um Editor de Códigos/IDE feita totalmente para rodar em Celulares. É leve, possui diversas linguagens como Python, C , C++ JavaScript e entre outras, Contando também com um terminal integrado para você rodar os seus códigos sem sair do app.

# VSCodeMobile v1.0

Um editor de código mobile inspirado no VS Code, desenvolvido em Kotlin com Jetpack Compose.

## Requisitos

- Android Studio 2024.1+
- JDK 17+
- Android SDK (Min SDK 24, Target SDK 34, Compile SDK 34)
- Gradle 8.7+

## Configuração do Projeto

### 1. Setup do Android SDK

```bash
# Configure o caminho do SDK no local.properties
sdk.dir=/path/to/android/sdk
```

### 2. Compilação

```bash
# Build de debug
./gradlew assembleDebug

# Build de release
./gradlew assembleRelease

# Rodar testes
./gradlew test
```

### 3. Instalação no Device/Emulator

```bash
# Instalar APK
./gradlew installDebug

# Instalar e rodar
./gradlew installDebug -Pdev

# Rodar aplicação
adb shell am start -n com.vscodemobile/.MainActivity
```

## Estrutura do Projeto

```
VSCodeMobile/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/vscodemobile/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── data/
│   │   │   │   │   └── CodeFile.kt
│   │   │   │   ├── ui/
│   │   │   │   │   ├── VSCodeMobileApp.kt
│   │   │   │   │   ├── components/
│   │   │   │   │   │   ├── CodeEditor.kt
│   │   │   │   │   │   ├── FileExplorer.kt
│   │   │   │   │   │   ├── NewFileDialog.kt
│   │   │   │   │   │   └── TopBar.kt
│   │   │   │   │   └── theme/
│   │   │   │   │       ├── Color.kt
│   │   │   │   │       ├── Theme.kt
│   │   │   │   │       └── Type.kt
│   │   │   │   ├── viewmodel/
│   │   │   │   │   └── EditorViewModel.kt
│   │   │   │   ├── pythonsupport/
│   │   │   │   │   ├── LanguageServerBridge.kt
│   │   │   │   │   ├── PythonFileHandler.kt
│   │   │   │   │   ├── PythonSyntaxHighlighter.kt
│   │   │   │   │   └── PythonAnalyzer.kt
│   │   │   │   └── storage/
│   │   │   │       └── FileRepository.kt
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   └── build.gradle.kts
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Funcionalidades v1.0

### Editor
- ✅ Editor de texto com Jetpack Compose
- ✅ Árvore de arquivos (sidebar)
- ✅ Tema escuro estilo VS Code
- ✅ Botão "Novo arquivo"
- ✅ Botão "Salvar"
- ✅ Suporte a múltiplas linguagens

### Python Support
- ✅ Identificação automática de arquivos .py
- ✅ Syntax highlighting básico para:
  - Palavras-chave (def, class, import, return, if/else/elif, try/except)
  - Strings e comentários
  - Números e variáveis
- ✅ Análise de código:
  - Verificação de parênteses
  - Validação de indentação
  - Análise de imports
  - Extração de estrutura (classes, funções)
- ✅ Interface preparada para Language Server (LSP)

### Storage
- ✅ Salvamento de arquivos em storage local
- ✅ Carregamento de arquivos salvos
- ✅ Deletar arquivos

## Arquitetura

### MVVM (Model-View-ViewModel)

- **EditorViewModel**: Gerencia o estado da aplicação
  - Lista de arquivos
  - Arquivo atual aberto
  - Conteúdo do editor
  - Operações de CRUD

- **Componentes Compose**:
  - `VSCodeMobileApp`: Tela principal
  - `CodeEditor`: Editor com line numbers
  - `FileExplorer`: Explorador de arquivos
  - `EditorTopBar`: Barra de ações
  - `NewFileDialog`: Diálogo para criar novo arquivo

- **Python Support Module**:
  - `PythonFileHandler`: Identifica e categoriza arquivos Python
  - `PythonSyntaxHighlighter`: Highlight de syntax
  - `PythonAnalyzer`: Análise de código
  - `LanguageServerBridge`: Interface para LSP externo

## Preparação para Futuras Versões

### v1.1
- Syntax highlight avançado (color picker real)
- Temas customizáveis
- Preferências do usuário

### v2.0
- Terminal integrado
- Connection com Termux (shell remoto)
- Integração com LSP (Pyright/pylsp)
- Debugging básico
- Git integration

### v3.0
- Multi-editor (abas)
- Search & Replace
- Extensões
- Collaboration

## Stack Tecnológico

- **Kotlin 2.1+**
- **Android Gradle Plugin 8.5+**
- **Gradle 8.7+**
- **Jetpack Compose**
- **Material3**
- **Architecture Components (ViewModel, LiveData)**
- **Coroutines**

## Dependências

Ver [app/build.gradle.kts](app/build.gradle.kts) para versões exatas.

## Build Moderno (Kotlin DSL)

- ✅ Sem buildscript antigo
- ✅ Sem allprojects
- ✅ Plugins DSL moderno
- ✅ Type-safe dependency management

## Compatibilidade

- MinSDK: 24 (Android 7.0)
- TargetSDK: 34 (Android 14)
- CompileSDK: 34
- JVM Target: 17

## GitHub Actions

O projeto é compatível com CI/CD via GitHub Actions.

Exemplo: [.github/workflows/build.yml](.github/workflows/build.yml)

## Licença

Este projeto é open source. Veja LICENSE para detalhes.

## Contato

Para dúvidas ou sugestões, abra uma issue no repositório.
>>>>>>> eccf85f ( Versão 1.0 (Ainda em beta mas usável.))
