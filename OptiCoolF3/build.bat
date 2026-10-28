@echo off
setlocal
set GRADLE_VERSION=9.7.1
if "%GRADLE_USER_HOME%"=="" set "GRADLE_USER_HOME=%USERPROFILE%\.gradle"
set "BASE=%GRADLE_USER_HOME%\F3OptiCool"
set "GRADLE=%BASE%\gradle-%GRADLE_VERSION%\bin\gradle.bat"
if not exist "%GRADLE%" (
  if not exist "%BASE%" mkdir "%BASE%"
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $u='https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip'; $z=Join-Path '%BASE%' 'gradle-%GRADLE_VERSION%-bin.zip'; Invoke-WebRequest $u -OutFile $z; Expand-Archive -LiteralPath $z -DestinationPath '%BASE%' -Force"
  if errorlevel 1 exit /b 1
)
call "%GRADLE%" --no-daemon :v1.21.6-1.21.8:build
