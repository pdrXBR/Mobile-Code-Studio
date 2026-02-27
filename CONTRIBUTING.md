# Contributing to VSCodeMobile

Obrigado por seu interesse em contribuir para o VSCodeMobile! Este documento fornece diretrizes e instruções para contribuir com o projeto.

## Como Contribuir

### 1. Relatando Bugs

Ao reportar um bug, inclua:
- **Versão do Android**: (ex: 7.0, 14)
- **Dispositivo/Emulador**: (ex: Pixel 4, Samsung Galaxy S20)
- **Passos para reproduzir**: Instruções claras
- **Comportamento esperado**: O que deveria acontecer
- **Comportamento observado**: O que realmente aconteceu
- **Screenshots/Logs**: Se aplicável

### 2. Sugerindo Melhorias

Antes de começar a codificar, abra uma issue para discutir:
- Descrição clara da feature
- Casos de uso
- Possíveis implementações
- Impacto no projeto

### 3. Enviando Pull Requests

#### Preparação

1. **Fork o repositório**
2. **Clone seu fork**:
   ```bash
   git clone https://github.com/seu-usuario/VSCodeMobile.git
   cd VSCodeMobile
   ```

3. **Crie uma branch para sua feature**:
   ```bash
   git checkout -b feature/sua-feature-descriptiva
   ```

#### Desenvolvimento

1. **Siga as convenções de código**:
   - Kotlin DSL para Gradle
   - Jetpack Compose para UI
   - MVVM architecture
   - Nomes descritivos para variáveis e funções

2. **Organização de arquivos**:
   ```
   app/src/main/java/com/vscodemobile/
   ├── ui/
   │   ├── components/     ← Componentes Compose
   │   └── theme/          ← Tema e cores
   ├── viewmodel/          ← ViewModels
   ├── viewmodel/          ← Models e data classes
   ├── pythonsupport/      ← Python-related code
   ├── storage/            ← Persistence
   └── MainActivity.kt
   ```

3. **Commits claros**:
   ```bash
   git add .
   git commit -m "feat: Adicionar feature XYZ"
   # ou
   git commit -m "fix: Corrigir bug em..."
   git commit -m "docs: Atualizar README"
   ```

4. **Push para seu fork**:
   ```bash
   git push origin feature/sua-feature-descriptiva
   ```

5. **Abra um Pull Request**:
   - Descreva as mudanças claramente
   - Referencie issues relacionadas (ex: Closes #123)
   - Inclua screenshots/GIFs se houver mudanças visuais

#### Revisão de Código

- Responda aos comentários dos revisores
- Faça ajustes conforme solicitado
- Mantenha a branch atualizada com main

## Convenções de Código

### Kotlin Style Guide

```kotlin
// ✅ Use nomes descritivos
val editorContent = "fun main() { }"

// ✅ Prefer data classes for immutable data
data class CodeFile(
    val id: String,
    val name: String,
    val content: String
)

// ✅ Use sealed classes for state
sealed class UIState {
    object Loading : UIState()
    data class Success(val data: String) : UIState()
    data class Error(val message: String) : UIState()
}

// ❌ Evitar nomes genéricos
val x = "code"

// ❌ Evitar mutation
var mutableState = "oldValue"
mutableState = "newValue"  // ❌ Use copy() instead
```

### Composables

```kotlin
// ✅ Use @Composable annotation
@Composable
fun MyComponent(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(text, modifier = modifier)
}

// ✅ Default parameters for modifier
fun MyComponent(modifier: Modifier = Modifier) { }

// ❌ Evitar lógica complexa em Composables
@Composable
fun BadComponent() {
    LaunchedEffect(...) {
        // Muita lógica aqui
    }
}
// ✅ Mover para ViewModel
```

### File Naming

- Classes: `PascalCase` (ex: `CodeEditor.kt`)
- Functions: `camelCase` (ex: `loadFile()`)
- Constants: `UPPER_SNAKE_CASE` (ex: `MAX_FILE_SIZE`)
- Packages: `lowercase.with.dots` (ex: `com.vscodemobile.ui`)

## Adicionando Python Features

Ao adicionar features relacionadas a Python:

1. **Use a pasta `pythonsupport/`**
2. **Implemente a interface `LanguageServerBridge` quando necessário**
3. **Adicione testes unitários**
4. **Documente no README**

Exemplo: Adicionar novo analyzer

```kotlin
// pythonsupport/NewAnalyzer.kt
class NewAnalyzer {
    fun analyze(content: String): AnalysisResult {
        // Implementação
    }
}

// Use em PythonAnalyzer.kt
val newAnalyzer = NewAnalyzer()
val result = newAnalyzer.analyze(content)
```

## Adicionando UI Components

1. **Crie em `ui/components/`**
2. **Use cores do tema** (`VSCodeColors`)
3. **Adicione `Modifier = Modifier` como parâmetro padrão**
4. **Mantenha componentes reutilizáveis**

```kotlin
@Composable
fun MyComponent(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(onClick = onClick, modifier = modifier) {
        Text(title, color = VSCodeColors.EditorText)
    }
}
```

## Testing

```bash
# Rodar todos os testes
./gradlew test

# Rodar testes específicos
./gradlew test --tests "com.vscodemobile.MyTest"

# Com detailed output
./gradlew test --info
```

Exemplo de teste:

```kotlin
@Test
fun testFileCreation() {
    val file = CodeFile(
        id = "1",
        name = "test.py",
        content = "print('hello')"
    )
    assertEquals("test.py", file.name)
}
```

## Documentation

- Mantenha README.md atualizado
- Adicione javadoc para funções públicas
- Documente decisões arquitetônicas

```kotlin
/**
 * Analyzes Python code for syntax errors and issues
 * 
 * @param content The Python source code to analyze
 * @return An AnalysisResult containing errors and warnings
 */
fun analyze(content: String): AnalysisResult {
    // ...
}
```

## Versioning

Seguimos [Semantic Versioning](https://semver.org/):

- **MAJOR**: Breaking changes
- **MINOR**: New features
- **PATCH**: Bug fixes

Exemplo: `v1.0.0` → `v1.1.0` (new feature) → `v1.1.1` (bug fix)

## Release Process

1. Atualizar versão em `build.gradle.kts`
2. Merger todas PullRequests
3. Criar release tag no GitHub
4. GitHub Actions compila e publica APK

## Comunidade

- 💬 Abra issues para discussões
- 📋 Respeite outros contribuidores
- 🆘 Peça ajuda se necessário
- 🎉 Dê créditos quando apropriado

## Código de Conduta

- Ser respeitoso
- Acolher diversos pontos de vista
- Fornecer feedback construtivo
- Reportar comportamentos inadequados

## Licença

Ao contribuir, você concorda que suas contribuições serão licenciadas sob a mesma licença do projeto.

## Dúvidas?

Abra uma issue com tag `question` ou entre em contato através do repositório.

---

**Muito obrigado por contribuir! 🚀**
