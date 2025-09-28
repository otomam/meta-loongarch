require recipes-bsp/u-boot/u-boot-common.inc
require recipes-bsp/u-boot/u-boot.inc

DEPENDS += "bc-native dtc-native gnutls-native python3-pyelftools-native"

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://gitee.com/otomam/u-boot.git;protocol=https;branch=2024.04"
SRCREV = "9b2dc78d7a519bff625e32ec9f14bcf6b34550ea"

UBOOT_MACHINE = "loongson_2k0300_defconfig"

do_compile:prepend() {
    if [ ! -d "${B}/drivers/ram/loongson" ]; then
        mkdir -p "${B}/drivers/ram/loongson"
    fi

    cp ${S}/drivers/ram/loongson/libmem_config_new_abi.a ${B}/drivers/ram/loongson/libmem_config_new_abi.a
    cp ${S}/drivers/ram/loongson/libmem_config.a ${B}/drivers/ram/loongson/libmem_config.a
}

do_deploy:append() {
    install -m 644 ${B}/u-boot-spl-gz.bin ${DEPLOYDIR}/
}