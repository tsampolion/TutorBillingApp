#!/usr/bin/env bash
###############################################################################
# Android SDK bootstrap for ChatGPT Codex & CI runners
# Works on Debian 12, Ubuntu 22/24, WSL 2, GitHub Actions and Codex sandboxes.
###############################################################################
set -euo pipefail

# ---------- 0. Tunables -------------------------------------------------------
ANDROID_SDK_ROOT="${HOME}/android-sdk"
CMDLINE_VERSION="11076708"        # r12b (May 2025) :contentReference[oaicite:5]{index=5}
API_LEVEL="34"                    # Android 14
BUILD_TOOLS="35.0.0"              # latest preview build-tools 35  :contentReference[oaicite:6]{index=6}
NDK_VERSION="27.0.11718014"       # NDK 27 LTS                       :contentReference[oaicite:7]{index=7}
CMAKE_VERSION=""                  # leave blank → auto-detect
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

echo ">>>> 3. Selecting best available OpenJDK (21 preferred, else 17)"
if apt-cache show openjdk-21-jdk >/dev/null 2>&1 ; then
  JDK_PACKAGE=openjdk-21-jdk     # Ubuntu 22/24 :contentReference[oaicite:8]{index=8}
else
  JDK_PACKAGE=openjdk-17-jdk     # Debian 12 default                :contentReference[oaicite:9]{index=9}
fi

echo ">>>> 4. Installing OS prerequisites ($JDK_PACKAGE)"
apt-get install -y --no-install-recommends \
  "$JDK_PACKAGE" \
  curl unzip git build-essential libglu1-mesa

###############################################################################
# 5. Download Android command-line tools and place them in <sdk>/cmdline-tools/latest
###############################################################################
echo ">>>> 5. Fetching Android command-line tools r${CMDLINE_VERSION}"
mkdir -p "${ANDROID_SDK_ROOT}/cmdline-tools"
cd /tmp
curl -sSLo cmdline-tools.zip \
  "https://dl.google.com/android/repository/commandlinetools-linux-${CMDLINE_VERSION}_latest.zip"
unzip -q cmdline-tools.zip            # produces ./cmdline-tools/…  :contentReference[oaicite:10]{index=10}
mv cmdline-tools "${ANDROID_SDK_ROOT}/cmdline-tools/latest"

# Flatten only if the legacy inner folder exists
if [ -d "${ANDROID_SDK_ROOT}/cmdline-tools/latest/cmdline-tools" ]; then
  mv "${ANDROID_SDK_ROOT}/cmdline-tools/latest/cmdline-tools"/* \
     "${ANDROID_SDK_ROOT}/cmdline-tools/latest/"
  rmdir "${ANDROID_SDK_ROOT}/cmdline-tools/latest/cmdline-tools"
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
# 7. Helper: run sdkmanager via `yes`, swallow benign SIGPIPE 141
###############################################################################
run_sdk() {
  yes | "$@"
  rc=$?
  [[ $rc -eq 0 || $rc -eq 141 ]] && return 0
  return "$rc"
}

###############################################################################
# 8. Determine the newest available CMake and install all SDK packages
###############################################################################
echo ">>>> 7. Determining best CMake version on stable channel"
if [[ -z "${CMAKE_VERSION}" ]]; then
  CMAKE_LATEST=$(sdkmanager --list --channel=0 | \
                 awk -F'|' '/cmake;[0-9]+\.[0-9]+\.[0-9]+/ \
                 {gsub(/^[ \t]+|[ \t]+$/, "", $1); print $1}' | \
                 sort -t';' -k2V | tail -n1)
  CMAKE_VERSION=${CMAKE_LATEST#cmake;}
fi
echo ">>>> 8. Installing SDK platform ${API_LEVEL}, build-tools ${BUILD_TOOLS}, NDK ${NDK_VERSION}, CMake ${CMAKE_VERSION}"
run_sdk sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" \
    "platform-tools" \
    "platforms;android-${API_LEVEL}" \
    "build-tools;${BUILD_TOOLS}" \
    "cmake;${CMAKE_VERSION}" \
    "ndk;${NDK_VERSION}" \
    "extras;android;m2repository" \
    "extras;google;m2repository"

echo ">>>> 9. Accepting Android licences"
run_sdk sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" --licenses

###############################################################################
# 10. Gradle fallback (local.properties)
###############################################################################
echo ">>>> 10. Writing local.properties"
echo "sdk.dir=${ANDROID_SDK_ROOT}" > "${CODING_PROJECT_ROOT:-$PWD}/local.properties"

###############################################################################
# 11. Clean-up to shrink container layers
###############################################################################
echo ">>>> 11. Cleaning up APT cache"
apt-get clean
rm -rf /var/lib/apt/lists/* /tmp/*

echo ">>>> Android SDK bootstrap complete!"
