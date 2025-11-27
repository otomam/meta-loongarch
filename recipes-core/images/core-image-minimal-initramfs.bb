SUMMARY = "Basic initramfs to boot rootfs"
LICENSE = "MIT"

inherit core-image

COMPATIBLE_HOST = '(loongarch64.*)-(linux.*)'

# Don't allow the initramfs to contain a kernel, as kernel modules will depend
# on the kernel image.
PACKAGE_EXCLUDE = "kernel-image-*"

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"
IMAGE_NAME_SUFFIX ?= ""

IMAGE_INSTALL = "packagegroup-core-boot ${CORE_IMAGE_EXTRA_INSTALL} \
    wget \
    mtd-utils \
    mmc-utils \
    e2fsprogs \
    parted \
    util-linux-lsblk \
    "
