#!/usr/bin/env bash
###############################################################################
# Android SDK bootstrap for ChatGPT Codex & CI runners
# Works on fresh Debian 12, Ubuntu 22/24, WSL2, GitHub Actions, and Codex sandboxes
# Installs: OpenJDK 21 if available, falls back to 17; Android CLI tools r12b;
#           Platform-Tools, API 34 Platform, Build-Tools 35, NDK 27, CMake 3.28
# Author:   <you@your-org.com>
# License:  Apache-2.0
###############################################################################
set -euo pipefail

# ---------- 0. Tweakables -----------------------------------------------------
ANDROID_SDK_ROOT="${HOME}/android-sdk"
CMDLINE_VERSION="11076708"        # r12b (May 2025)  📄 docs: dl.google.com/android/repository
API_LEVEL="34"                    # Android 14
BUILD_TOOLS="35.0.0"
NDK_VERSION="27.0.11718014"       # NDK 27 LTS
CMAKE_VERSION="3.28.0"
# ------------------------------------------------------------------------------

echo ">>>> 1. Refreshing APT index"
export DEBIAN_FRONTEND=noninteractive
apt-get update -y

echo ">>>> 2. (Ubuntu only) enable 'universe' repo so Java 21 & graphics libs exist"
if grep -qi ubuntu /etc/os-release ; then
  apt-get install -y --no-install-recommends software-properties-common
  add-apt-repository -y universe
  apt-get update -y
fi

echo ">>>> 3. Selecting best available OpenJDK (21 > 17)"
if apt-cache show openjdk-21-jdk > /dev/null 2>&1 ; then
  JDK_PACKAGE=openjdk-21-jdk     # Ubuntu 22/24
else
  JDK_PACKAGE=openjdk-17-jdk     # Debian 12 default
fi

echo ">>>> 4. Installing OS prerequisites: ${JDK_PACKAGE}"
apt-get install -y --no-install-recommends \
  "${JDK_PACKAGE}" \
  curl unzip git build-essential libglu1-mesa

###############################################################################
# 5. Download Android command-line tools and flatten the directory layout
###############################################################################
echo ">>>> 5. Fetching Android command-line tools r${CMDLINE_VERSION}"
mkdir -p "${ANDROID_SDK_ROOT}/cmdline-tools"
cd /tmp
curl -sSLo cmdline-tools.zip \
  "https://dl.google.com/android/repository/commandlinetools-linux-${CMDLINE_VERSION}_latest.zip"
unzip -q cmdline-tools.zip

# Move into canonical <sdk>/cmdline-tools/latest layout
mv cmdline-tools "${ANDROID_SDK_ROOT}/cmdline-tools/latest"
mv "${ANDROID_SDK_ROOT}/cmdline-tools/latest/cmdline-tools"/* \
   "${ANDROID_SDK_ROOT}/cmdline-tools/latest/"
rmdir "${ANDROID_SDK_ROOT}/cmdline-tools/latest/cmdline-tools"
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
echo ">>>> 7. Installing SDK platforms & tools (this can take a few minutes)"
yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" \
    "platform-tools" \
    "platforms;android-${API_LEVEL}" \
    "build-tools;${BUILD_TOOLS}" \
    "ndk;${NDK_VERSION}" \
    "cmake;${CMAKE_VERSION}" \
    "extras;android;m2repository" \
    "extras;google;m2repository"

echo ">>>> 8. Accepting Android licences"
yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" --licenses \
  || echo "Some licences were already accepted"

###############################################################################
# 9. Gradle fallback (local.properties) so older plug-ins always find the SDK
###############################################################################
echo ">>>> 9. Writing local.properties"
echo "sdk.dir=${ANDROID_SDK_ROOT}" > "${CODING_PROJECT_ROOT:-$PWD}/local.properties"

###############################################################################
# 10. Clean-up to shrink container layers
###############################################################################
echo ">>>> 10. Cleaning up APT cache"
apt-get clean
rm -rf /var/lib/apt/lists/* /tmp/*

echo ">>>> Android SDK bootstrap complete!"
