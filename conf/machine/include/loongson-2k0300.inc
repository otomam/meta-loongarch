require conf/machine/include/loongarch/tune-loongarch.inc

PREFERRED_PROVIDER_virtual/kernel = "linux-loongson2"

PREFERRED_PROVIDER_virtual/bootloader = "u-boot"
PREFERRED_VERSION_u-boot = "2024.04"

EXTRA_IMAGEDEPENDS += " u-boot "

# INIT_MANAGER = "systemd"

# PREFERRED_PROVIDER_virtual/base-utils = "packagegroup-core-base-utils"
# VIRTUAL-RUNTIME_base-utils = "packagegroup-core-base-utils"
# VIRTUAL-RUNTIME_base-utils-hwclock = "util-linux-hwclock"
# VIRTUAL-RUNTIME_base-utils-syslog = ""

KERNEL_IMAGETYPE = "fitImage"
KERNEL_IMAGETYPES = "${KERNEL_IMAGETYPE} "
KERNEL_CLASSES = "kernel-fitimage"
KERNEL_DEVICETREE = " \
    loongson_2k0300_99_pai_wifi.dtb \
    loongson_2k0300_99_pai_tfcard.dtb \
    "

FIT_CONF_DEFAULT_DTB ?= "loongson_2k0300_99_pai_wifi.dtb"
FIT_ADDRESS_CELLS = "2"
FIT_HASH_ALG = "sha1"

UBOOT_MACHINE ?= "loongson_2k0300_defconfig"

# by u-boot configure
UBOOT_DTB_LOADADDRESS = "0x900000000a000000"
UBOOT_LOADADDRESS = "0x9000000000200000"
UBOOT_ENTRYSYMBOL = "kernel_entry"

IMAGE_FSTYPES += " tar.gz"

SERIAL_CONSOLES ?= "115200;ttyS0"
