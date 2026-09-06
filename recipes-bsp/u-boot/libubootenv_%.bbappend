FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
           file://fw_env.config \
           "

do_install:append () {
    install -d ${D}/etc
    install -m 0644 ${UNPACKDIR}/fw_env.config ${D}/etc/fw_env.config
}
