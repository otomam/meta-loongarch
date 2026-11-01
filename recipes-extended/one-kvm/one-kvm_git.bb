SUMMARY = "One-KVM Rust is a lightweight IP-KVM solution written in Rust. \
           It lets you manage servers and workstations over the network, \
           including at BIOS level."
HOMEPAGE = "https://docs.one-kvm.cn/"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=eb1e647870add0502f8f010b19de32af"

SRC_URI = " \
    git://github.com/mofeng-git/One-KVM.git;protocol=https;branch=main \
    file://0001-compile-for-loongarch.patch \
    file://0001-for-loong2k300.patch \
"

SRC_URI += " \
    git://github.com/otomam/serialport-rs.git;protocol=https;branch=main;name=serialport;destsuffix=serialport;type=git-dependency \
    git://github.com/otomam/rust-turbojpeg.git;protocol=https;branch=main;name=turbojpeg;destsuffix=turbojpeg;type=git-dependency \
    git://github.com/loong64/libjpeg-turbo.git;protocol=https;branch=loong64/backport;name=libjpeg-turbo;destsuffix=turbojpeg/turbojpeg-sys/libjpeg-turbo;type=git-dependency \
    git://github.com/otomam/webrtc-util.git;protocol=https;branch=util;name=webrtc-util;destsuffix=webrtc-util;type=git-dependency \
"

SRCREV = "cb19b2567b62beb89a8f90beb465370853dc2ae5"

SRCREV_FORMAT .= "_serialport_turbojpeg_webrtc-util"
SRCREV_serialport = "f4fb5e6a1ac5d41f775905e7d60e80bf00c7ff1f"
SRCREV_turbojpeg = "d718a968c7332fbe3e0192c432e731000c5ce5ea"
SRCREV_libjpeg-turbo = "9b2a91e6d7a7f60481e15d66817884c0f22e3b48"
SRCREV_webrtc-util = "ee756daacfaff50c3babcf83c2baf6f348cc6b58"

inherit cargo cargo-update-recipe-crates ptest-cargo

require ${BPN}-crates.inc

inherit pkgconfig
inherit python3native

DEPENDS += "\
    autoconf-native automake-native libtool-native \
    clang-native bindgen-cli-native \
    nodejs-native rpm-native \
    libopus \
    libyuv \
    libdrm \
    udev \
    alsa-lib \
    ffmpeg \
"

RDEPENDS:${PN} += " udev libopus libyuv libdrm libavcodec alsa-lib"

# QA Issue
export CARGO_PROFILE_RELEASE_STRIP = "false"
export CFLAGS:append = " -fdebug-prefix-map=${TMPDIR}=/usr/src/debug"
export CXXFLAGS:append = " -fdebug-prefix-map=${TMPDIR}=/usr/src/debug"

BINDGEN_EXTRA_CLANG_ARGS = "${HOST_CC_ARCH}${TOOLCHAIN_OPTIONS} --target=${TARGET_SYS} --sysroot=${STAGING_DIR_HOST} -I${STAGING_INCDIR}"
export BINDGEN_EXTRA_CLANG_ARGS

# debug
export CARGO_PROFILE_RELEASE_OVERFLOW_CHECKS = "true"
export CARGO_PROFILE_RELEASE_OPT_LEVEL = "1"
export RUSTFLAGS:append = " -C force-frame-pointers=yes"

# 禁用 LSX 和 LASX
export RUSTFLAGS:append = " -C target-feature=-lsx,-lasx"

# webui-vue_git.bb
EXTRA_OENPM ?= ""
NPM_CONFIG_CACHE ?= "${WORKDIR}/npm-cache"

do_compile[network] = "1"
do_compile:prepend() {
    sed -i 's|"https://github.com/otomam/webrtc-util.git"|crates-io|' ${CARGO_HOME}/config.toml
    sed -i '/libjpeg-turbo/d' ${CARGO_HOME}/config.toml

    export NPM_CONFIG_CACHE="${NPM_CONFIG_CACHE}"
    cd ${S}/web
    rm -rf node_modules
    npm install --registry=https://registry.npmmirror.com/ --loglevel info
    npm run build ${EXTRA_OENPM}
    cd -

    # 318b58b26a33185b46e5bae583cafff494c4b706 会从源码编译openssl失败
    export OPENSSL_NO_VENDOR=1
}

do_install:append() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${UNPACKDIR}/${BPN}-${PV}/build/one-kvm.service ${D}${systemd_system_unitdir}/

    sed -i 's|ExecStart=/usr/bin/one-kvm|ExecStart=/usr/bin/one-kvm -d /data/one-kvm|' ${D}${systemd_system_unitdir}/one-kvm.service
}

inherit systemd
SYSTEMD_SERVICE:${PN} = "one-kvm.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
