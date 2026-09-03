SUMMARY = "A small image just capable of allowing a device to boot."

IMAGE_INSTALL = "packagegroup-core-boot "

IMAGE_LINGUAS = " "

LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL += " \
    wget \
    curl \
    file \
    ldd \
    mtd-utils \
    mmc-utils \
    e2fsprogs \
    parted \
    ethtool \
    iproute2 \
    iproute2-ss \
    util-linux-lsblk \
    util-linux-lscpu \
    one-kvm \
    ttyd \
    dropbear \
    aic8800 \
    "

# IMAGE_FSTYPES += " wic"
# WKS_FILE = "loongson2k0300.wks"

# IMAGE_ROOTFS_SIZE ?= "8192"
# IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"
