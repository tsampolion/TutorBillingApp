#!/usr/bin/env bash
set -euo pipefail

# -------- Settings you might want to tweak --------
ANDROID_SDK_ROOT="${HOME}/android-sdk"
CMDLINE_VERSION="11076708"        # r12b, May-2025
API_LEVEL="34"                    # Android 14 (stable)
BUILD_TOOLS="35.0.0"              # matching API 34
NDK_VERSION="27.0.11718014"       # latest LTS
CMAKE_VERSION="3.28.0"
# --------------------------------------------------

echo ">>>> Installing OS packages"
export DEBIAN_FRONTEND=noninteractive
apt-get update -y
apt-get install -y --no-install-recommends \
    openjdk-21-jdk \             # Java 21 LTS :contentReference[oaicite:0]{index=0}
    curl unzip git build-essential libglu1-mesa

echo ">>>> Fetching Android command-line tools r${CMDLINE_VERSION}"
mkdir -p "${ANDROID_SDK_ROOT}/cmdline-tools"
cd /tmp
curl -sSLo cmdline.zip \
    "https://dl.google.com/android/repository/commandlinetools-linux-${CMDLINE_VERSION}_latest.zip"
unzip -q cmdline.zip
# Move into canonical <sdk>/cmdline-tools/latest layout:contentReference[oaicite:1]{index=1}
mv cmdline-tools "${ANDROID_SDK_ROOT}/cmdline-tools/latest"
rm cmdline.zip
cd -

# >>>> Setting environment variables
echo "export ANDROID_SDK_ROOT=${ANDROID_SDK_ROOT}" >> "${HOME}/.profile"
export ANDROID_HOME="${ANDROID_SDK_ROOT}"
echo "export ANDROID_HOME=${ANDROID_SDK_ROOT}" >> "${HOME}/.profile"
export PATH=$PATH:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools

echo ">>>> Installing SDK platforms / tools"
yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" \
    "platform-tools" \                                        # adb/fastboot :contentReference[oaicite:2]{index=2} \
    "platforms;android-${API_LEVEL}" \                        # API 34 :contentReference[oaicite:3]{index=3} \
    "build-tools;${BUILD_TOOLS}" \                            # Build-Tools 35 :contentReference[oaicite:4]{index=4} \
    "ndk;${NDK_VERSION}" \                                    # NDK 27 :contentReference[oaicite:5]{index=5} \
    "cmake;${CMAKE_VERSION}" \
    "extras;android;m2repository" \
    "extras;google;m2repository"

echo ">>>> Accepting licences"
yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" --licenses \
      || echo "Some licenses were already accepted"           # CI-safe :contentReference[oaicite:6]{index=6}

echo ">>>> Cleaning up"
apt-get clean
rm -rf /var/lib/apt/lists/* /tmp/*

echo "sdk.dir=${ANDROID_SDK_ROOT}" > "${CODING_PROJECT_ROOT:-$PWD}/local.properties"
