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

# --- 1. Make sure apt has the latest index -----------------------------------
export DEBIAN_FRONTEND=noninteractive
apt-get update -y

# --- 2. (Ubuntu only) turn on the “universe” repo so Java 21 & graphics libs are visible
if grep -qi ubuntu /etc/os-release ; then
  apt-get install -y --no-install-recommends software-properties-common
  add-apt-repository -y universe
  apt-get update -y
fi

# --- 3. Choose a JDK that actually exists in the repo ------------------------
# Prefer 21 if available; otherwise fall back to 17.
if apt-cache show openjdk-21-jdk > /dev/null 2>&1 ; then
  JDK_PACKAGE=openjdk-21-jdk
else
  JDK_PACKAGE=openjdk-17-jdk   # Debian 12 default
fi

# --- 4. Install base packages ------------------------------------------------
apt-get install -y --no-install-recommends \
  "$JDK_PACKAGE" \
  curl unzip git build-essential libglu1-mesa

echo ">>>> Fetching Android command-line tools r${CMDLINE_VERSION}"
# (unchanged code for downloading cmdline tools …)

# >>>> Setting environment variables
echo "export ANDROID_SDK_ROOT=${ANDROID_SDK_ROOT}" >> "${HOME}/.profile"
export ANDROID_HOME="${ANDROID_SDK_ROOT}"
echo "export ANDROID_HOME=${ANDROID_SDK_ROOT}" >> "${HOME}/.profile"
export PATH=$PATH:${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin:${ANDROID_SDK_ROOT}/platform-tools

echo ">>>> Installing SDK platforms / tools"
yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" \
    "platform-tools" \
    "platforms;android-${API_LEVEL}" \
    "build-tools;${BUILD_TOOLS}" \
    "ndk;${NDK_VERSION}" \
    "cmake;${CMAKE_VERSION}" \
    "extras;android;m2repository" \
    "extras;google;m2repository"

echo ">>>> Accepting licences"
yes | sdkmanager --sdk_root="${ANDROID_SDK_ROOT}" --licenses \
      || echo "Some licences were already accepted"

echo ">>>> Cleaning up"
apt-get clean
rm -rf /var/lib/apt/lists/* /tmp/*

# Gradle fallback for SDK location
echo "sdk.dir=${ANDROID_SDK_ROOT}" > "${CODING_PROJECT_ROOT:-$PWD}/local.properties"
