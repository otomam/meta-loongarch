SUMMARY = "Basic initramfs to boot rootfs"
LICENSE = "MIT"

inherit core-image

# Don't allow the initramfs to contain a kernel, as kernel modules will depend
# on the kernel image.
PACKAGE_EXCLUDE = "kernel-image-*"

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"
IMAGE_NAME_SUFFIX ?= ""

PACKAGE_INSTALL = " \
    base-files \
    base-passwd \
    netbase \
    ${VIRTUAL-RUNTIME_base-utils} \
    ${VIRTUAL-RUNTIME_login_manager} \
    ${VIRTUAL-RUNTIME_init_manager} \
    ${VIRTUAL-RUNTIME_dev_manager} \
    wget \
    mtd-utils \
    mmc-utils \
    e2fsprogs \
    parted \
    util-linux-lsblk \
    "
