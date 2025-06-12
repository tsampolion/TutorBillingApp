#!/usr/bin/env bash
###############################################################################
# Fast Android SDK bootstrap for ChatGPT Codex / CI containers
###############################################################################
set -euo pipefail

# ---------- Tunables ---------------------------------------------------------
ANDROID_SDK_ROOT="${HOME}/android-sdk"
CMDLINE_VERSION="11076708"           # r12b (May-2025)
API_LEVEL="34"                       # Android 14
BUILD_TOOLS="35.0.0"
INSTALL_NDK="${INSTALL_NDK:-false}"  # export INSTALL_NDK=true if you need C++
NDK_VERSION="27.0.11718014"
###############################################################################

echo ">>>> 1. Refresh APT and (Ubuntu) enable universe repo"
export DEBIAN_FRONTEND=noninteractive
apt-get update -y
if grep -qi ubuntu /etc/os-release ; then
  apt-get install -y --no-install-recommends software-properties-common
  add-apt-repository -y universe
  apt-get update -y
fi

echo ">>>> 2. Install OpenJDK"
if apt-cache show openjdk-21-jdk >/dev/null 2>&1 ; then
  JDK=openjdk-21-jdk
else
  JDK=openjdk-17-jdk
fi
apt-get install -y --no-install-recommends "$JDK" curl unzip git build-essential libglu1-mesa

echo ">>>> 3. Download command-line tools r${CMDLINE_VERSION}"
mkdir -p "${ANDROID_SDK_ROOT}/cmdline-tools"
cd /tmp
curl -sSLo cmdline.zip \
  "https://dl.google.com/android/repository/commandlinetools-linux-${CMDLINE_VERSION}_latest.zip"
unzip -q cmdline.zip
mv cmdline-tools "${ANDROID_SDK_ROOT}/cmdline-tools/latest"

# Flatten the occasional extra folder layer
NESTED="${ANDROID_SDK_ROOT}/cmdline-tools/latest/cmdline-tools"
if [ -d "${NESTED}" ]; then
  mv "${NESTED}"/* "${ANDROID_SDK_ROOT}/cmdline-tools/latest/"
  rmdir "${NESTED}"
fi
rm cmdline.zip
cd -

echo ">>>> 4. Export ANDROID_* vars"
{
  echo "export ANDROID_SDK_ROOT=${ANDROID_SDK_ROOT}"
  echo "export ANDROID_HOME=${ANDROID_SDK_ROOT}"
  echo 'export PATH=$PATH:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools'
} >> "${HOME}/.profile"
export ANDROID_HOME="${ANDROID_SDK_ROOT}"
export PATH=$PATH:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools

echo ">>>> 5. Install core SDK packages"
yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" \
  "platform-tools" \
  "platforms;android-${API_LEVEL}" \
  "build-tools;${BUILD_TOOLS}"

if [ "${INSTALL_NDK}" = "true" ]; then
  echo ">>>> Installing NDK ${NDK_VERSION}"
  yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" "ndk;${NDK_VERSION}"
fi

echo ">>>> 6. Accept licences"
yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" --licenses || true

echo ">>>> 7. Write Gradle local.properties fallback"
echo "sdk.dir=${ANDROID_SDK_ROOT}" > "${CODING_PROJECT_ROOT:-$PWD}/local.properties"

echo ">>>> 8. Clean APT cache"
apt-get clean
rm -rf /var/lib/apt/lists/* /tmp/*

echo ">>>> Android SDK ready!"
