#!/usr/bin/env bash
###############################################################################
# Android SDK bootstrap for ChatGPT Codex & CI runners
# Works on Debian 12, Ubuntu 22/24, WSL2, GitHub Actions, Codex sandboxes
###############################################################################
set -euo pipefail

# ---------- 0. Tweakables -----------------------------------------------------
ANDROID_SDK_ROOT="${HOME}/android-sdk"
CMDLINE_VERSION="11076708"        # r12b (May 2025 release notes)
API_LEVEL="34"                    # Android 14
BUILD_TOOLS="35.0.0"
NDK_VERSION="27.0.11718014"       # NDK 27 LTS
CMAKE_VERSION="3.28.0"
# ------------------------------------------------------------------------------

echo ">>>> 1. Refreshing APT index"
export DEBIAN_FRONTEND=noninteractive
apt-get update -y

echo ">>>> 2. (Ubuntu only) enable 'universe' repo so Java 21 exists"
if grep -qi ubuntu /etc/os-release ; then
  apt-get install -y --no-install-recommends software-properties-common
  add-apt-repository -y universe
  apt-get update -y
fi

echo ">>>> 3. Selecting best available OpenJDK (21 > 17)"
if apt-cache show openjdk-21-jdk > /dev/null 2>&1 ; then
  JDK_PACKAGE=openjdk-21-jdk
else
  JDK_PACKAGE=openjdk-17-jdk
fi

echo ">>>> 4. Installing OS prerequisites"
apt-get install -y --no-install-recommends \
  "$JDK_PACKAGE" \
  curl unzip git build-essential libglu1-mesa

###############################################################################
# 5. Download Android command-line tools and (optionally) flatten layout
###############################################################################
echo ">>>> 5. Fetching Android command-line tools r${CMDLINE_VERSION}"
mkdir -p "${ANDROID_SDK_ROOT}/cmdline-tools"
cd /tmp
curl -sSLo cmdline-tools.zip \
  "https://dl.google.com/android/repository/commandlinetools-linux-${CMDLINE_VERSION}_latest.zip"
unzip -q cmdline-tools.zip

# Move into <sdk>/cmdline-tools/latest
mv cmdline-tools "${ANDROID_SDK_ROOT}/cmdline-tools/latest"

# NEW: Flatten only if a second nested 'cmdline-tools' folder exists
NESTED="${ANDROID_SDK_ROOT}/cmdline-tools/latest/cmdline-tools"
if [ -d "${NESTED}" ]; then
  echo ">>>> Detected nested cmdline-tools folder — flattening"
  mv "${NESTED}"/* "${ANDROID_SDK_ROOT}/cmdline-tools/latest/"
  rmdir "${NESTED}"
fi

rm cmdline-tools.zip
cd -

###############################################################################
# 6. Environment variables & PATH
###############################################################################
echo ">>>> 6. Setting ANDROID_* environment variables"
{
  echo "export ANDROID_SDK_ROOT=${ANDROID_SDK_ROOT}"
  echo "export ANDROID_HOME=${ANDROID_SDK_ROOT}"
  echo 'export PATH=$PATH:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools'
} >> "${HOME}/.profile"

export ANDROID_HOME="${ANDROID_SDK_ROOT}"
export PATH=$PATH:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools

###############################################################################
# 7. Install SDK packages non-interactively
###############################################################################
echo ">>>> 7. Installing SDK platforms & tools (grab a coffee ☕)"
yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" \
    "platform-tools" \
    "platforms;android-${API_LEVEL}" \
    "build-tools;${BUILD_TOOLS}" \
    "ndk;${NDK_VERSION}" \
    "cmake;${CMAKE_VERSION}" \
    "extras;android;m2repository" \
    "extras;google;m2repository"

echo ">>>> 8. Accepting Android licences"
yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" --licenses || true

###############################################################################
# 9. Gradle fallback file
###############################################################################
echo ">>>> 9. Writing local.properties"
echo "sdk.dir=${ANDROID_SDK_ROOT}" > "${CODING_PROJECT_ROOT:-$PWD}/local.properties"

###############################################################################
# 10. Clean-up
###############################################################################
echo ">>>> 10. Cleaning up"
apt-get clean
rm -rf /var/lib/apt/lists/* /tmp/*

echo ">>>> Android SDK bootstrap complete!"
