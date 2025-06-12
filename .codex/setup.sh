#!/usr/bin/env bash
###############################################################################
# Fast Android SDK bootstrap for ChatGPT Codex / CI containers
# Installs ONLY what a pure Kotlin/Java project needs:
#   • OpenJDK 21 (if available) → fallback 17
#   • CLI tools r12b  (11076708, May 2025)
#   • platform-tools + API 34 platform + Build-Tools 35
# Optional NDK install: set  INSTALL_NDK=true  in the environment.
###############################################################################
set -euo pipefail

# ---------- Tunables ---------------------------------------------------------
ANDROID_SDK_ROOT="${HOME}/android-sdk"
CMDLINE_VERSION="11076708"   # r12b
API_LEVEL="34"               # Android 14
BUILD_TOOLS="35.0.0"
INSTALL_NDK="${INSTALL_NDK:-false}"   # export INSTALL_NDK=true if you need C++
NDK_VERSION="27.0.11718014"  # used only when INSTALL_NDK=true
# ------------------------------------------------------------------------------

echo ">>>> 1. apt update & universe repo (for OpenJDK 21)"
export DEBIAN_FRONTEND=noninteractive
apt-get update -y
if grep -qi ubuntu /etc/os-release ; then
  apt-get install -y --no-install-recommends software-properties-common
  add-apt-repository -y universe     # OpenJDK 21 lives here :contentReference[oaicite:7]{index=7}
  apt-get update -y
fi

echo ">>>> 2. Install OpenJDK"
if apt-cache show openjdk-21-jdk >/dev/null 2>&1 ; then
  JDK=openjdk-21-jdk
else
  JDK=openjdk-17-jdk               # Debian 12 default
fi
apt-get install -y --no-install-recommends "$JDK" curl unzip git build-essential libglu1-mesa

echo ">>>> 3. Download command-line tools r${CMDLINE_VERSION}"
mkdir -p "${ANDROID_SDK_ROOT}/cmdline-tools"
cd /tmp
curl -sSLo cmdline.zip \
  "https://dl.google.com/android/repository/commandlinetools-linux-${CMDLINE_VERSION}_latest.zip"
unzip -q cmdline.zip
mv cmdline-tools "${ANDROID_SDK_ROOT}/cmdline-tools/latest"

# Flatten if Google’s zip still contains an extra layer
NESTED="${ANDROID_SDK_ROOT}/cmdline-tools/latest/cmdline-tools"
if [ -d "${NESTED}" ]; then
  echo ">>>> Flattening nested cmdline-tools folder"
  mv "${NESTED}"/* "${ANDROID_SDK_ROOT}/cmdline-tools/latest/"
  rmdir "${NESTED}"
fi
rm cmdline.zip
cd -

echo ">>>> 4. Set ANDROID_* variables"
{
  echo "export ANDROID_SDK_ROOT=${ANDROID_SDK_ROOT}"
  echo "export ANDROID_HOME=${ANDROID_SDK_ROOT}"
  echo 'export PATH=$PATH:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools'
} >> "${HOME}/.profile"
export ANDROID_HOME="${ANDROID_SDK_ROOT}"
export PATH=$PATH:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools

echo ">>>> 5. Install SDK packages"
yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" \
  "platform-tools" \
  "platforms;android-${API_LEVEL}" \
  "build-tools;${BUILD_TOOLS}"

if [ "${INSTALL_NDK}" = "true" ]; then
  echo ">>>> Installing NDK ${NDK_VERSION} (≈1 GB, slower)  :contentReference[oaicite:8]{index=8}"
  yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" "ndk;${NDK_VERSION}"
fi

echo ">>>> 6. Accept licences"
yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" --licenses || true

echo ">>>> 7. Gradle fallback local.properties"
echo "sdk.dir=${ANDROID_SDK_ROOT}" > "${CODING_PROJECT_ROOT:-$PWD}/local.properties"

echo ">>>> 8. Clean up APT cache"
apt-get clean
rm -rf /var/lib/apt/lists/* /tmp/*

echo ">>>> Android SDK ready!"
