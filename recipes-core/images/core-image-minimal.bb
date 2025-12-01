SUMMARY = "A small image just capable of allowing a device to boot."

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL}"

IMAGE_LINGUAS = " "

LICENSE = "MIT"

inherit core-image

IMAGE_INSTALL += " \
    wget \
    mtd-utils \
    mmc-utils \
    e2fsprogs \
    parted \
    util-linux-lsblk \
    "

# IMAGE_FSTYPES += " wic"
# WKS_FILE = "loongson2k0300.wks"

# IMAGE_ROOTFS_SIZE ?= "8192"
# IMAGE_ROOTFS_EXTRA_SPACE:append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"
