LICENSE = "CLOSED"

FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

# 配置wifi密码
WIFI_SSID ?= "WIFI"
WIFI_PSK ?= "PWSSWORD"

export WIFI_SSID
export WIFI_PSK

SRC_URI = " \
           file://switch_root.sh \
           file://wpa_supplicant.conf-wlan0 \
           file://wlan0-pre-up.conf \
           file://wlan0.network \
           file://eth0-static.network \
           file://data.mount \
           "

do_install () {
	install -m 700 ${UNPACKDIR}/switch_root.sh ${D}/switch_root.sh

    install -d ${D}${sysconfdir}/wpa_supplicant
	install -m 600 ${UNPACKDIR}/wpa_supplicant.conf-wlan0 ${D}${sysconfdir}/wpa_supplicant/wpa_supplicant-wlan0.conf
    sed -i '/ssid=/c\    ssid="'"${WIFI_SSID}"'"'   ${D}${sysconfdir}/wpa_supplicant/wpa_supplicant-wlan0.conf
    sed -i '/psk=/c\    psk="'"${WIFI_PSK}"'"'      ${D}${sysconfdir}/wpa_supplicant/wpa_supplicant-wlan0.conf

    install -d ${D}${systemd_system_unitdir}/wpa_supplicant@wlan0.service.d
    install -m 0644 ${UNPACKDIR}/wlan0-pre-up.conf ${D}${systemd_system_unitdir}/wpa_supplicant@wlan0.service.d/wlan0-pre-up.conf

    install -d ${D}${sysconfdir}/systemd/network/
	install -m 0644 ${UNPACKDIR}/wlan0.network ${D}${sysconfdir}/systemd/network/70-wlan0.network
	install -m 0644 ${UNPACKDIR}/eth0-static.network ${D}${sysconfdir}/systemd/network/70-eth0-static.network

    install -d ${D}/data
    install -d ${D}${sysconfdir}/systemd/system/
    install -m 0644 ${UNPACKDIR}/data.mount ${D}${sysconfdir}/systemd/system/data.mount
}

FILES:${PN} += " /data /usr /switch_root.sh "

inherit systemd

SYSTEMD_SERVICE:${PN} = "data.mount"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"
