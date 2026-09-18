PACKAGECONFIG:remove = " \
    backlight \
    binfmt \
    gshadow \
    hibernate \
    ima \
    machined \
    nss \
    nss-mymachines \
    nss-resolve \
    osc-context \
    quotacheck \
    userdb \
    vconsole \
"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += "file://wait-online-override.conf"

do_install:append() {
    install -d ${D}${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d

    install -m 0644 ${UNPACKDIR}/wait-online-override.conf \
                    ${D}${sysconfdir}/systemd/system/systemd-networkd-wait-online.service.d/override.conf
}
