SUMMARY = "A small image just capable of allowing a device to boot."

IMAGE_INSTALL = "packagegroup-core-boot "

IMAGE_LINGUAS = " "

LICENSE = "MIT"

inherit core-image

PACKAGE_EXCLUDE = "kernel-vmlinux kernel-image-vmlinux"

IMAGE_INSTALL += " \
    curl \
    lrzsz \
    file \
    ldd \
    mtd-utils \
    mmc-utils \
    e2fsprogs \
    parted \
    ethtool libubootenv-bin \
    iproute2 iproute2-ss \
    util-linux-lsblk util-linux-lscpu \
    one-kvm ttyd \
    dropbear \
    aic8800 wpa-supplicant wireless-regdb-static \
    kernel-module-cfg80211 kernel-module-rfkill kernel-module-cfg80211 kernel-module-rfkill \
    setup \
    "

# IMAGE_FSTYPES += " wic"
# WKS_FILE = "loongson2k0300.wks"

# IMAGE_ROOTFS_SIZE ?= "8192"
# IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"
