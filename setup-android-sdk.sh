#!/usr/bin/env bash
###############################################################################
# Android SDK bootstrap for ChatGPT Codex & CI runners
# Works on Debian 12, Ubuntu 22/24, WSL2, GitHub Actions, Codex sandboxes
###############################################################################
set -euo pipefail

# ---------- 0. Tweakables -----------------------------------------------------
ANDROID_SDK_ROOT="${HOME}/android-sdk"
# Using a more reliable version - adjust as needed
CMDLINE_VERSION="9477386"         # This is a known working version
API_LEVEL="34"                    # Android 14
BUILD_TOOLS="35.0.0"
NDK_VERSION="27.0.11718014"       # NDK 27 LTS
CMAKE_VERSION="3.28.0"
# ------------------------------------------------------------------------------

echo ">>>> 1. Refreshing APT index"
export DEBIAN_FRONTEND=noninteractive
sudo apt-get update -y || apt-get update -y

echo ">>>> 2. (Ubuntu only) enable 'universe' repo so Java 21 exists"
if grep -qi ubuntu /etc/os-release ; then
  sudo apt-get install -y --no-install-recommends software-properties-common || \
    apt-get install -y --no-install-recommends software-properties-common
  sudo add-apt-repository -y universe || add-apt-repository -y universe
  sudo apt-get update -y || apt-get update -y
fi

echo ">>>> 3. Selecting best available OpenJDK (21 > 17)"
if apt-cache show openjdk-21-jdk > /dev/null 2>&1 ; then
  JDK_PACKAGE=openjdk-21-jdk
elif apt-cache show openjdk-17-jdk > /dev/null 2>&1 ; then
  JDK_PACKAGE=openjdk-17-jdk
else
  JDK_PACKAGE=openjdk-11-jdk  # Fallback to Java 11 if neither 21 nor 17 available
fi

echo ">>>> 4. Installing OS prerequisites"
sudo apt-get install -y --no-install-recommends \
  "$JDK_PACKAGE" \
  curl unzip git build-essential libglu1-mesa || \
apt-get install -y --no-install-recommends \
  "$JDK_PACKAGE" \
  curl unzip git build-essential libglu1-mesa

###############################################################################
# 5. Download Android command-line tools
###############################################################################
echo ">>>> 5. Fetching Android command-line tools"
mkdir -p "${ANDROID_SDK_ROOT}"
cd /tmp

# Clean up any previous downloads
rm -f cmdline-tools.zip

# Try to download with the specified version, fallback to latest if it fails
CMDLINE_URL="https://dl.google.com/android/repository/commandlinetools-linux-${CMDLINE_VERSION}_latest.zip"
echo "Attempting to download from: ${CMDLINE_URL}"

if ! curl -sSLfo cmdline-tools.zip "${CMDLINE_URL}"; then
  echo "WARNING: Specified version not found, downloading latest version..."
  # Fallback to a known working URL pattern
  curl -sSLo cmdline-tools.zip \
    "https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip" || {
    echo "ERROR: Failed to download command-line tools"
    exit 1
  }
fi

# Extract the tools
unzip -q cmdline-tools.zip

# Handle the directory structure properly
# The extracted content is in a 'cmdline-tools' directory
if [ -d "cmdline-tools" ]; then
  # Create the proper directory structure
  mkdir -p "${ANDROID_SDK_ROOT}/cmdline-tools"
  
  # Move to 'latest' subdirectory as recommended by Google
  mv cmdline-tools "${ANDROID_SDK_ROOT}/cmdline-tools/latest"
else
  echo "ERROR: Unexpected archive structure"
  exit 1
fi

# Clean up
rm -f cmdline-tools.zip
cd -

###############################################################################
# 6. Environment variables & PATH
###############################################################################
echo ">>>> 6. Setting ANDROID_* environment variables"

# Update shell profile
PROFILE_FILE="${HOME}/.profile"
if [ -f "${HOME}/.bashrc" ]; then
  PROFILE_FILE="${HOME}/.bashrc"
fi

# Remove any existing Android SDK entries to prevent duplicates
sed -i '/ANDROID_SDK_ROOT/d' "${PROFILE_FILE}" 2>/dev/null || true
sed -i '/ANDROID_HOME/d' "${PROFILE_FILE}" 2>/dev/null || true

# Add new entries
{
  echo ""
  echo "# Android SDK"
  echo "export ANDROID_SDK_ROOT=${ANDROID_SDK_ROOT}"
  echo "export ANDROID_HOME=${ANDROID_SDK_ROOT}"
  echo 'export PATH=$PATH:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools'
} >> "${PROFILE_FILE}"

# Export for current session
export ANDROID_SDK_ROOT="${ANDROID_SDK_ROOT}"
export ANDROID_HOME="${ANDROID_SDK_ROOT}"
export PATH=$PATH:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools

###############################################################################
# 7. Install SDK packages non-interactively
###############################################################################
echo ">>>> 7. Installing SDK platforms & tools (grab a coffee ☕)"

# First, try to update repository info
"${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager" --list > /dev/null 2>&1 || true

# Install packages
yes | "${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager" \
    "platform-tools" \
    "platforms;android-${API_LEVEL}" \
    "build-tools;${BUILD_TOOLS}" \
    "ndk;${NDK_VERSION}" \
    "cmake;${CMAKE_VERSION}" \
    "extras;android;m2repository" \
    "extras;google;m2repository" || {
    echo "WARNING: Some packages may have failed to install"
}

echo ">>>> 8. Accepting Android licenses"
yes | "${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager" --licenses || true

###############################################################################
# 9. Gradle fallback file
###############################################################################
echo ">>>> 9. Writing local.properties"

# Use current directory if CODING_PROJECT_ROOT is not set
PROJECT_ROOT="${CODING_PROJECT_ROOT:-$PWD}"

# Only create local.properties if we're in a project directory
if [ -f "${PROJECT_ROOT}/build.gradle" ] || [ -f "${PROJECT_ROOT}/build.gradle.kts" ] || [ -f "${PROJECT_ROOT}/settings.gradle" ] || [ -f "${PROJECT_ROOT}/settings.gradle.kts" ]; then
  echo "sdk.dir=${ANDROID_SDK_ROOT}" > "${PROJECT_ROOT}/local.properties"
  echo "Created local.properties in ${PROJECT_ROOT}"
else
  echo "Not in an Android project directory, skipping local.properties creation"
fi

###############################################################################
# 10. Clean-up
###############################################################################
echo ">>>> 10. Cleaning up"
if command -v sudo > /dev/null 2>&1; then
  sudo apt-get clean || true
  sudo rm -rf /var/lib/apt/lists/* || true
else
  apt-get clean || true
  rm -rf /var/lib/apt/lists/* || true
fi
rm -rf /tmp/cmdline-tools* 2>/dev/null || true

###############################################################################
# 11. Verification
###############################################################################
echo ">>>> 11. Verifying installation"
echo "ANDROID_SDK_ROOT: ${ANDROID_SDK_ROOT}"
echo "ANDROID_HOME: ${ANDROID_HOME}"
echo "PATH includes: ${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin"

# Test sdkmanager
if "${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager" --version > /dev/null 2>&1; then
  echo "✓ sdkmanager is working"
  "${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager" --version
else
  echo "✗ sdkmanager test failed"
fi

# Test if platform-tools was installed
if [ -d "${ANDROID_SDK_ROOT}/platform-tools" ]; then
  echo "✓ platform-tools directory exists"
else
  echo "✗ platform-tools directory missing"
fi

echo ""
echo ">>>> Android SDK bootstrap complete!"
echo "Please run 'source ${PROFILE_FILE}' or start a new shell to use the SDK tools."
