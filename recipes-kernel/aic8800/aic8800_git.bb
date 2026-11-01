SUMMARY = "Aicsemi aic8800 Wi-Fi driver"
HOMEPAGE = "https://github.com/radxa-pkg/aic8800"

LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/GPL-3.0-only;md5=c79ff39f19dfec6d293b95dea7b07891"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI = " \
    git://github.com/radxa-pkg/aic8800.git;branch=main;protocol=https \
    file://0001-fix-compile.patch;striplevel=6 \
"

SRCREV = "fce88024ed40279def034e203128e0892fdbcac3"
DEPENDS += "virtual/kernel"

S = "${UNPACKDIR}/${BP}/src/SDIO/driver_fw/driver/aic8800"

inherit linux-kernel-base module kernel-module-split

EXTRA_OEMAKE = " KDIR=${STAGING_KERNEL_DIR} MODDESTDIR=${D}/${libdir}/modules/${KERNEL_VERSION}/kernel/drivers/net/wireless/aic8800"
MODULES_INSTALL_TARGET = "install"

do_install:append() {
    install -d ${D}/${libdir}/firmware/aic8800_fw/SDIO
    cp -rf ${UNPACKDIR}/${BP}/src/SDIO/driver_fw/fw/* ${D}/${libdir}/firmware/aic8800_fw/SDIO
    rm -rf ${D}/${libdir}/firmware/aic8800_fw/SDIO/*.7z
}

FILES:${PN} += " ${libdir}/firmware/ "

RRECOMMENDS:${PN} = " \
    kernel-module-aic8800-bsp \
    kernel-module-aic8800-fdrv \
    kernel-module-aic8800-btlpm \
"

# autoload if installed
KERNEL_MODULE_AUTOLOAD += "aic8800_bsp aic8800_fdrv"
