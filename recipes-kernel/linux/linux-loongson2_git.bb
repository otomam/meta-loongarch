SUMMARY = "Linux Kernel"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"

DESCRIPTION = "Linux kernel for Loongson2"

require recipes-kernel/linux/linux-yocto.inc

LINUX_VERSION_EXTENSION = ""
LINUX_VERSION = "6.9"

KERNEL_VERSION_SANITY_SKIP = "1"

COMPATIBLE_MACHINE = "^(loongson-2k0300-99pai)$"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

KBRANCH = "6.9"
SRCREV = "ace567fddffbeb902ba3742e67ecc957958ec949"

SRC_URI = " \
            git://gitee.com/otomam/linux-loongson.git;protocol=https;branch=${KBRANCH} \
           "

SRC_URI:append:loongson-2k0300-99pai = " \
            file://fragment.cfg \
            file://0001-fit-image-pass-dtb.patch \
            file://0001-fix-compile.patch \
            file://0001-usb-otg.patch \
            file://0001-sdio-wifi.patch \
           "

PV = "6.9"
PR = "r0"

KBUILD_DEFCONFIG ?= "ls2k0300_defconfig"

do_compile:prepend() {
    if [ ! -d "${B}/drivers/net/can/ls_can" ]; then
        mkdir -p "${B}/drivers/net/can/ls_can"
    fi

    cp ${S}/drivers/net/can/ls_can/lscanfd_dma.elf ${B}/drivers/net/can/ls_can/lscanfd_dma.elf
    cp ${S}/drivers/net/can/ls_can/lscanfd_platform.elf ${B}/drivers/net/can/ls_can/lscanfd_platform.elf
}
