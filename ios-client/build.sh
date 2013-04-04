#! /bin/sh
#script for building ios client from console.
set -e

BUILD_DIR="build"
APP_NAME="CaptureTheFlag"
PROVISION="Capture_the_Flag.mobileprovision"

echo `pwd`/$APP_NAME.ipa

mkdir -p $BUILD_DIR
export CONFIGURATION_BUILD_DIR=$BUILD_DIR
xcodebuild -target $APP_NAME -sdk iphoneos6.0 -configuration Release
/usr/bin/xcrun -sdk iphoneos6.0 PackageApplication -v build/Release-iphoneos/$APP_NAME.app -o `pwd`/$APP_NAME.ipa
# --embed $PROVISION
