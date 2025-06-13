#!/usr/bin/env bash
###############################################################################
# Android SDK bootstrap for ChatGPT Codex & CI runners
# Handles JDK choice, deb/apt quirks, cmdline-tools layout, SIGPIPE 141, etc.
# Works on Debian 12, Ubuntu 22/24, WSL 2, GitHub Actions and Codex sandboxes.
###############################################################################
set -euo pipefail

# ---------- 0. Tunables -------------------------------------------------------
ANDROID_SDK_ROOT="${HOME}/android-sdk"
CMDLINE_VERSION="11076708"        # r12b (May 2025)
API_LEVEL="34"                    # Android 14
BUILD_TOOLS="35.0.0"
NDK_VERSION="27.0.11718014"
CMAKE_VERSION="3.28.0"
###############################################################################

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
if apt-cache show openjdk-21-jdk >/dev/null 2>&1 ; then
  JDK_PACKAGE=openjdk-21-jdk     # Ubuntu 22/24
else
  JDK_PACKAGE=openjdk-17-jdk     # Debian 12 default, AGP 8.x minimum :contentReference[oaicite:1]{index=1}
fi

echo ">>>> 4. Installing OS prerequisites: $JDK_PACKAGE"
apt-get install -y --no-install-recommends \
  "$JDK_PACKAGE" \
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
# 7. Helper to ignore harmless SIGPIPE 141 from `yes | sdkmanager`
###############################################################################
run_sdk() {
  yes | "$@"
  rc=$?
  if [[ "$rc" -eq 0 || "$rc" -eq 141 ]]; then
    return 0              # success or benign SIGPIPE, continue script
  fi
  return "$rc"            # propagate real failures
}

###############################################################################
# 8. Install SDK packages non-interactively
###############################################################################
echo ">>>> 7. Installing SDK platforms & tools (may take a few minutes)"
run_sdk sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" \
    "platform-tools" \
    "platforms;android-${API_LEVEL}" \
    "build-tools;${BUILD_TOOLS}" \
    "ndk;${NDK_VERSION}" \
    "cmake;${CMAKE_VERSION}" \
    "extras;android;m2repository" \
    "extras;google;m2repository"

echo ">>>> 8. Accepting Android licences"
run_sdk sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" --licenses

###############################################################################
# 9. Gradle fallback (local.properties)
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
