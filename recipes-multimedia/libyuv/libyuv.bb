SUMMARY = "libyuv is an open source project that includes YUV scaling and conversion functionality"
HOMEPAGE = "https://chromium.googlesource.com/libyuv/libyuv"
SECTION = "libs"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=464282cfb405b005b9637f11103a7325"

SRC_URI = "git://chromium.googlesource.com/libyuv/libyuv;protocol=https;branch=main"
SRCREV = "26872d1da3ce70099c6386aa155661f5060c430d"
PV = "0.1+git"

inherit cmake

PACKAGECONFIG ??= "jpeg"
PACKAGECONFIG[jpeg] = ",,libjpeg-turbo"

EXTRA_OECMAKE += "-DUNIT_TEST=OFF"

# QA Issue: -dev package libyuv-dev contains non-symlink .so '/usr/lib/libyuv.so' [dev-elf]
SOLIBS = ".so"
FILES_SOLIBSDEV = ""
