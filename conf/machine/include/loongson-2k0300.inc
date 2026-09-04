require conf/machine/include/loongarch/tune-loongarch.inc

PREFERRED_PROVIDER_virtual/kernel = "linux-loongson2"

PREFERRED_PROVIDER_virtual/bootloader = "u-boot"
PREFERRED_VERSION_u-boot = "2024.04"

EXTRA_IMAGEDEPENDS += " u-boot "

INIT_MANAGER = "systemd"

# 注释掉会编译不带initramfs的fitimage
# INITRAMFS_IMAGE = "core-image-minimal-initramfs"
# INIT_MANAGER = "mdev-busybox"

KERNEL_CLASSES += "kernel-fit-extra-artifacts"
MACHINE_ESSENTIAL_EXTRA_RDEPENDS += "linux-yocto-fitimage"

KERNEL_IMAGETYPE = "vmlinux"
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

UBOOT_RD_LOADADDRESS = "0x9000000006000000"
UBOOT_RD_ENTRYPOINT = "0x9000000006000000"

IMAGE_FSTYPES = "tar.gz"

SERIAL_CONSOLES ?= "115200;ttyS0"

ERROR_QA:remove = "patch-status"

DISTRO_FEATURES:remove = " 3g alsa bluetooth pulseaudio vulkan wayland x11 ptest"

EXTRA_IMAGE_FEATURES += "allow-empty-password empty-root-password allow-root-login"
