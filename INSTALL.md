# Setup e Compilação do VSCodeMobile

## Pré-requisitos

1. **Android Studio** (Versão 2024.1 ou superior)
2. **Java Development Kit (JDK) 17+**
3. **Android SDK 34**

## Instalação Passo a Passo

### 1. Android Studio

1. Baixar e instalar [Android Studio](https://developer.android.com/studio)
2. Durante a instalação, selecionar instalar Android SDK 34
3. Após instalação, abrir Android Studio

### 2. Android SDK

Se não tiver SDK 34 instalado:

1. Abrir: `Tools → Device Manager (ou SDK Manager)`
2. Ir para a aba "SDK Platforms"
3. Selecionar "Android 14" (API Level 34)
4. Clicar "Apply" e aceitar as licenças

### 3. Variáveis de Ambiente (Opcional)

Pode ser necessário configurar `ANDROID_HOME`:

**Windows**: 
```powershell
$env:ANDROID_HOME = "C:\Users\[Usuário]\AppData\Local\Android\Sdk"
[Environment]::SetEnvironmentVariable("ANDROID_HOME", $env:ANDROID_HOME, "User")
```

**Linux/Mac**:
```bash
export ANDROID_HOME=~/Android/Sdk
echo 'export ANDROID_HOME=~/Android/Sdk' >> ~/.bashrc
```

### 4. Configurar local.properties

1. Abrir o arquivo `local.properties` na raiz do projeto
2. Adicionar o caminho do Android SDK:

```properties
sdk.dir=C:\Users\[Usuário]\AppData\Local\Android\Sdk
```

ou

```properties
sdk.dir=/home/[usuario]/Android/Sdk
```

## Compilação

### Opção 1: Usando Android Studio (Recomendado para Desenvolvimento)

1. Abrir o projeto em Android Studio
2. Aguardar sincronização do Gradle
3. Clicar em "Build" → "Make Project"
4. Ou clicar em "Run" para compilar e rodar no emulador/device

### Opção 2: Usando Terminal/Command Line

#### Windows (PowerShell)

```powershell
cd C:\Users\[Usuario]\Documents\pedro\VSCodeMobile

# Build de debug
./gradlew.bat assembleDebug

# Build de release
./gradlew.bat assembleRelease

# Instalar em device/emulator
./gradlew.bat installDebug

# Rodar testes
./gradlew.bat test
```

#### Linux/Mac (Bash)

```bash
cd ~/Documents/pedro/VSCodeMobile

# Build de debug
./gradlew assembleDebug

# Build de release
./gradlew assembleRelease

# Instalar em device/emulator
./gradlew installDebug

# Rodar testes
./gradlew test
```

### Opção 3: Usando IDE (VS Code)

1. Instalar extensão "Gradle for Java" no VS Code
2. Abrir a paleta de comandos (Ctrl+Shift+P)
3. Pesquisar "Gradle: Run Gradle Task"
4. Selecionar a tarefa (ex: assembleDebug)

## Troubleshooting

### Erro: "SDK location not found"

**Solução**: Verificar o arquivo `local.properties` e garantir que o caminho está correto.

```bash
# Verificar o caminho do SDK
echo %ANDROID_HOME%  # Windows
echo $ANDROID_HOME   # Linux/Mac
```

### Erro: "No matching client version"

**Solução**: Atualizar o Android Gradle Plugin. Editar `build.gradle.kts`:

```kotlin
plugins {
    id("com.android.application") version "8.5.0" apply false
}
```

### Erro: "Could not find com.android.tools.build:gradle"

**Solução**: Limpar cache e tentar novamente:

```bash
./gradlew clean
./gradlew build
```

### Erro: "Java 17 is required"

**Solução**: Verificar versão do Java:

```bash
java -version
```

Se não tiver Java 17+, instalar [OpenJDK 17](https://jdk.java.net/17/) ou utilizar a versão bundled do Android Studio.

## Emulator/Device Setup

### Para rodar a aplicação

1. **Abrir Emulator** (Android Studio → Device Manager → Launch)

   OU

2. **Conectar Device físico**:
   - Ativar Developer Mode (Settings → About Phone → Build Number (7x tap))
   - USB Debugging: (Settings → Developer Options → USB Debugging)
   - Conectar via USB

3. **Compilar e Rodar**:

```bash
# Ver devices conectados
adb devices

# Instalar APK
./gradlew installDebug

# Rodar app
adb shell am start -n com.vscodemobile/.MainActivity

# Ver logs
adb logcat | grep VSCodeMobile
```

## Comandos Úteis do Gradle

```bash
# Listar todas as tasks
./gradlew tasks

# Executar task específica
./gradlew [taskName]

# Rebuild completo
./gradlew clean build

# Dependency tree
./gradlew dependencies

# Task tree (mostra dependências entre tasks)
./gradlew taskTree

# Build com saída detalhada
./gradlew assembleDebug --info
```

## Build Variants

```bash
# Debug
./gradlew assembleDebug

# Release (requer assinatura)
./gradlew assembleRelease
```

## Próximos Passos após Build Successful

1. ✅ Abrir a aplicação no emulador/device
2. ✅ Testar funcionalidades básicas
3. ✅ Criar novo arquivo Python (main.py)
4. ✅ Editar código e salvar
5. ✅ Reabrir arquivo

## Recursos Adicionais

- [Android Developer Guide](https://developer.android.com/docs)
- [Kotlin Documentation](https://kotlinlang.org/docs/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Gradle Documentation](https://docs.gradle.org/)
