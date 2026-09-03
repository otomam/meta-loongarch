SUMMARY = "Aicsemi aic8800 Wi-Fi driver"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = " \
    git://github.com/radxa-pkg/aic8800.git;branch=main;protocol=https \
    file://0001-fix-compile.patch \
"

SRCREV = "516e3b087763d80c44f5e3b6d2dd63e0d925c91d"
DEPENDS += "virtual/kernel"

S = "${UNPACKDIR}/${BP}/src/SDIO/driver_fw/driver/aic8800"

inherit module

EXTRA_OEMAKE = " KDIR=${STAGING_KERNEL_DIR} MODDESTDIR=${D}/${libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/aic8800"
MODULES_INSTALL_TARGET = "install"

do_install:append() {
    install -d ${D}/vendor/etc/firmware
    cp -rf ${UNPACKDIR}/${BP}/src/SDIO/driver_fw/fw/aic8800D80 ${D}/vendor/etc/firmware
}
FILES:${PN} = " /vendor/etc/firmware "
