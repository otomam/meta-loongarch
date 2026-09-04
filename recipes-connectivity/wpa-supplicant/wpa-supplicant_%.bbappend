FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

SRC_URI += " \
           file://wpa_supplicant.conf-wlan0 \
           file://wlan0-pre-up.conf \
           file://wlan0.network \
           file://eth0-static.network \
           "

do_install:append () {
    install -d ${D}${sysconfdir}/wpa_supplicant
	install -m 600 ${UNPACKDIR}/wpa_supplicant.conf-wlan0 ${D}${sysconfdir}/wpa_supplicant/wpa_supplicant-wlan0.conf

    install -d ${D}${systemd_system_unitdir}/wpa_supplicant@wlan0.service.d
    install -m 0644 ${UNPACKDIR}/wlan0-pre-up.conf ${D}${systemd_system_unitdir}/wpa_supplicant@wlan0.service.d/wlan0-pre-up.conf

    install -d ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${UNPACKDIR}/wlan0.network ${D}${sysconfdir}/systemd/network/70-wlan0.network
	install -m 0644 ${UNPACKDIR}/eth0-static.network ${D}${sysconfdir}/systemd/network/70-eth0-static.network
}

SYSTEMD_SERVICE:${PN} = "wpa_supplicant@wlan0.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
