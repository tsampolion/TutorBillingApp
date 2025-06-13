#!/usr/bin/env bash
###############################################################################
# Android SDK bootstrap for ChatGPT Codex & CI runners
# Handles JDK choice, cmdline-tools layout quirks, SIGPIPE 141, auto-CMake, etc.
# Works on Debian 12, Ubuntu 22/24, WSL 2, GitHub Actions and Codex sandboxes.
###############################################################################
set -euo pipefail

# ---------- 0. Tunables -------------------------------------------------------
ANDROID_SDK_ROOT="${HOME}/android-sdk"
CMDLINE_VERSION="11076708"        # cmdline-tools r12b :contentReference[oaicite:0]{index=0}
API_LEVEL="34"                    # Android 14
BUILD_TOOLS="35.0.0"
NDK_VERSION="27.0.11718014"       # NDK 27 LTS :contentReference[oaicite:1]{index=1}
CMAKE_VERSION=""                  # blank → auto-detect stable newest
###############################################################################

echo ">>>> 1. Refreshing APT index"
export DEBIAN_FRONTEND=noninteractive
apt-get update -y

echo ">>>> 2. (Ubuntu only) enable universe repo so Java 21 is visible"
if grep -qi ubuntu /etc/os-release ; then
  apt-get install -y --no-install-recommends software-properties-common
  add-apt-repository -y universe
  apt-get update -y
fi

echo ">>>> 3. Selecting best OpenJDK (21 preferred, else 17)"
if apt-cache show openjdk-21-jdk >/dev/null 2>&1 ; then
  JDK_PACKAGE=openjdk-21-jdk        # In Ubuntu universe :contentReference[oaicite:2]{index=2}
else
  JDK_PACKAGE=openjdk-17-jdk        # Debian 12 default, AGP ≥ 8 needs 17 :contentReference[oaicite:3]{index=3}
fi

echo ">>>> 4. Installing OS prerequisites: $JDK_PACKAGE"
apt-get install -y --no-install-recommends \
  "$JDK_PACKAGE" curl unzip git build-essential libglu1-mesa

###############################################################################
# 5. Download cmdline-tools and put them in <sdk>/cmdline-tools/latest
###############################################################################
echo ">>>> 5. Fetching Android cmdline-tools r${CMDLINE_VERSION}"
mkdir -p "${ANDROID_SDK_ROOT}/cmdline-tools"
cd /tmp
curl -sSLo cmdline-tools.zip \
  "https://dl.google.com/android/repository/commandlinetools-linux-${CMDLINE_VERSION}_latest.zip"
unzip -q cmdline-tools.zip
mv cmdline-tools "${ANDROID_SDK_ROOT}/cmdline-tools/latest"

# Flatten only if the legacy inner folder exists (newer zips don’t nest) :contentReference[oaicite:4]{index=4}
if [ -d "${ANDROID_SDK_ROOT}/cmdline-tools/latest/cmdline-tools" ]; then
  mv "${ANDROID_SDK_ROOT}/cmdline-tools/latest/cmdline-tools"/* \
     "${ANDROID_SDK_ROOT}/cmdline-tools/latest/"
  rmdir "${ANDROID_SDK_ROOT}/cmdline-tools/latest/cmdline-tools"
fi
rm cmdline-tools.zip
cd -

###############################################################################
# 6. Environment variables
###############################################################################
echo ">>>> 6. Exporting ANDROID_* vars"
{
  echo "export ANDROID_SDK_ROOT=${ANDROID_SDK_ROOT}"
  echo "export ANDROID_HOME=${ANDROID_SDK_ROOT}"
  echo 'export PATH=$PATH:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools'
} >> "${HOME}/.profile"

export ANDROID_HOME="${ANDROID_SDK_ROOT}"
export PATH=$PATH:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools

###############################################################################
# 7. Helper: run sdkmanager safely, swallow harmless SIGPIPE 141
###############################################################################
run_sdk() {
  set +e                     # turn off immediate-exit
  yes | "$@"                 # run pipeline
  local yes_rc=${PIPESTATUS[0]}
  local cmd_rc=${PIPESTATUS[1]}
  set -e
  # ignore SIGPIPE (141) from 'yes' if sdkmanager succeeded
  if [[ $cmd_rc -eq 0 && ($yes_rc -eq 0 || $yes_rc -eq 141) ]]; then
    return 0
  fi
  return ${cmd_rc:-$yes_rc}
}

###############################################################################
# 8. Choose newest available CMake on stable channel
###############################################################################
echo ">>>> 7. Determining latest stable CMake via sdkmanager --list"
if [[ -z "$CMAKE_VERSION" ]]; then
  CMAKE_VERSION=$(sdkmanager --list --channel=0 | \
      awk -F'|' '/cmake;[0-9]+\.[0-9]+\.[0-9]+/ {gsub(/^[ \t]+|[ \t]+$/, "", $1); \
      sub(/^cmake;/,"", $1); print $1}' | sort -V | tail -n1)
  echo ">>>>    Found CMake ${CMAKE_VERSION}"   # 3.22.1 as of 2025-06 :contentReference[oaicite:5]{index=5}
fi

###############################################################################
# 9. Install platform, tools, NDK, CMake, Google & Android repos
###############################################################################
echo ">>>> 8. Installing all SDK components"
run_sdk sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" \
  "platform-tools" \
  "platforms;android-${API_LEVEL}" \
  "build-tools;${BUILD_TOOLS}" \
  "cmake;${CMAKE_VERSION}" \
  "ndk;${NDK_VERSION}" \
  "extras;android;m2repository" \
  "extras;google;m2repository"

echo ">>>> 9. Accepting SDK licences"
run_sdk sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" --licenses   # :contentReference[oaicite:6]{index=6}

###############################################################################
# 10. Gradle fallback (local.properties)
###############################################################################
echo ">>>> 10. Writing local.properties"
echo "sdk.dir=${ANDROID_SDK_ROOT}" > "${CODING_PROJECT_ROOT:-$PWD}/local.properties"

###############################################################################
# 11. Clean-up
###############################################################################
echo ">>>> 11. Cleaning APT cache"
apt-get clean
rm -rf /var/lib/apt/lists/* /tmp/*

echo ">>>> Android SDK bootstrap complete!"
