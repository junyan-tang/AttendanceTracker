#!/bin/bash
# Install the StarNet repos

cmd=$(basename "$0")

usage() {
	echo "Usage: $cmd [-q] [-p] [-s] [-a key]" >&2
	echo "    -q: Don't prompt (may still produce output)" >&2
	echo "    -p: Enable the StarNet Preview repository" >&2
	echo "    -s: Disable the StarNet Preview repository (if it's currently enabled)" >&2
	echo "    -a: Specify an activation key (which may enable the advanced repository)" >&2
	exit
}

while [[ $# -gt 0 ]]; do
  case $1 in
    -q)
      QUIET=1
      shift
      ;;
    -p)
      PREVIEW=1
      shift
      ;;
    -s)
      PREVIEW=0
      shift
      ;;
    -a)
      shift
      AKEY=$1
      shift
      ;;
	-h|-\?)
	  usage
	  shift
	  ;;
    -*)
      echo "Unknown option $1" >&2
	  usage
      shift
      ;;
    *)
      shift	# Ignore
      ;;
  esac
done

is_yesno () {
	case "$1" in
		Y*) return 0 ;;
		y*) return 0 ;;
		N*) return 0 ;;
		n*) return 0 ;;
	esac
	return 1
}

is_yes () {
	case "$1" in
		Y*) return 0 ;;
		y*) return 0 ;;
	esac
	return 1
}

KEYRINGS=${KEYRINGS:-/usr/share/keyrings}

if [ ! -f /etc/os-release ]; then
	echo "/etc/os-release is missing. This might be a very old dist." >&2
	exit 1
fi
. /etc/os-release
OS_TYPE="${ID_LIKE:-$ID}"
OS_VERSION=${VERSION_ID%.*}

case "$OS_TYPE" in
	*fedora*) PM=dnf ;;
	*debian*) PM=apt ;;
	*) echo "Can't handle distribution \"$OS_TYPE\"" >&2; exit 1 ;;
esac

NODE_VERS=
if [ $PM = "dnf" ]; then
	if ! which dnf >/dev/null 2>&1; then
		which yum >/dev/null || exit $?
		PM=yum
	fi
	DIST_FILE=/etc/yum.repos.d/starnet.repo
	OLD_DIST=$(sed -e '/^baseurl/s/.*\starnet-repo/\([^/]\{1,\}\)\/\{0,1\}$/\1/p' -e d $DIST_FILE 2>/dev/null)
	if [ "$OLD_DIST" == preview ]; then
		PREV_PREVIEW=1
	fi
	if [ "$PM" = dnf ] && dnf repolist --enabled | grep -q starnet-preview; then
		PREV_PREVIEW=1
	fi
	if [ "$PM" = yum ] && yum repolist enabled | grep -q '^starnet-preview'; then
		PREV_PREVIEW=1
	fi
	PREV_PREVIEW=${PREV_PREVIEW:-0}
	if [ "$PM" = dnf ] && dnf repolist --enabled | grep -q starnet-advanced; then
		PREV_ADVANCED=1
	fi
	PREV_ADVANCED=${PREV_ADVANCED:-0}

	cat > "$DIST_FILE" <<END || exit $?
[starnet]
name=StarNet Stable Repository
baseurl=https://www.starnet.com/rpm/stable/
gpgkey=https://www.starnet.com/files/keys/starnet-key.pub
gpgcheck=1
enabled=1
username=\$starnetkey

[starnet-preview]
name=StarNet Preview Repository
baseurl=https://www.starnet.com/rpm/preview/
gpgkey=https://www.starnet.com/files/keys/starnet-key.pub
gpgcheck=1
enabled=$PREV_PREVIEW
username=\$starnetkey

[starnet-advanced]
name=StarNet Advanced Repository
baseurl=https://www.starnet.com/rpm/advanced/
gpgkey=https://www.starnet.com/files/keys/starnet-key.pub
gpgcheck=1
enabled=$PREV_ADVANCED
username=\$starnetkey
END
	chmod 644 "$DIST_FILE"

	2>/dev/null read PREV_AKEY < /etc/dnf/vars/starnetkey
	if [ "$OS_VERSION" -gt 8 ]; then
		VIRTUALGL=${VIRTUALGL-1}
		NODE_VERS=16.20 # at least
	fi
	if [ "$OS_VERSION" -eq 8 ]; then
		VIRTUALGL=${VIRTUALGL-1}
		if dnf module -y enable nodejs:20; then
			NODE_VERS=20
		fi
	fi
	VIRTUALGL=${VIRTUALGL-1}
	DIST_FILE=/etc/yum.repos.d/VirtualGL.repo
	if [ "$VIRTUALGL" = 1 ] && [ "$PM" == dnf ] && ! [ -f "$DIST_FILE" ] && ! rpm -q VirtualGL >/dev/null 2>&1; then
		cat > "$DIST_FILE" <<'END' || exit $?
[VirtualGL]
name=VirtualGL official RPMs
baseurl=https://packagecloud.io/dcommander/virtualgl/rpm_any/rpm_any/$basearch
gpgcheck=1
gpgkey=https://packagecloud.io/dcommander/virtualgl/gpgkey
       https://raw.githubusercontent.com/VirtualGL/repo/main/VGL-GPG-KEY
       https://raw.githubusercontent.com/VirtualGL/repo/main/VGL-GPG-KEY-1024
enabled=1
sslverify=1
sslcacert=/etc/pki/tls/certs/ca-bundle.crt
# The following "exclude" line filters beta releases.
exclude=VirtualGL-*.*.9[0-9]-*

[VirtualGL-source]
name=VirtualGL official SRPMs
baseurl=https://packagecloud.io/dcommander/virtualgl/rpm_any/rpm_any/SRPMS
gpgcheck=1
gpgkey=https://packagecloud.io/dcommander/virtualgl/gpgkey
       https://raw.githubusercontent.com/VirtualGL/repo/main/VGL-GPG-KEY
       https://raw.githubusercontent.com/VirtualGL/repo/main/VGL-GPG-KEY-1024
enabled=1
sslverify=1
sslcacert=/etc/pki/tls/certs/ca-bundle.crt
# If you want beta releases, comment-out this line, and the "exclude" line above.
exclude=VirtualGL-*.*.9[0-9]-*
END
	fi
else
	NODE_VERS=`apt-cache show nodejs | sed -e '/^Version/s/^Version: *//p' -e '$c 14.0.0' -e d | sort --version-sort | tail -1`
	if [ "$NODE_VERS" == "14.0.0" ]; then
		echo "Creating Nodesource repository..." >&2
		curl -fsSL https://deb.nodesource.com/setup_lts.x | bash -
		NODE_VERS=`apt-cache show nodejs | sed -e '/^Version/s/^Version: *//p' -e '$c 14.0.0' -e d | sort --version-sort | tail -1`
	fi
	if [ "$NODE_VERS" == "14.0.0" ]; then
		# Node.JS 14 or later is not available.
		NODE_VERS=
	fi
	PREV_PREVIEW=0
	if grep -q preview /etc/apt/sources.list.d/starnet*.list 2>/dev/null; then
		PREV_PREVIEW=1
	fi
	if grep -q advanced /etc/apt/sources.list.d/starnet*.list 2>/dev/null; then
		PREV_ADVANCED=1
	fi

	PREV_AKEY=$(sed -e '/^login /s/^login *//p' -e d /etc/apt/auth.conf.d/55starnet 2>/dev/null)

	if [ "$VIRTUALGL" = 1 ] && ! [ -f "$KEYRINGS/VirtualGL.gpg" ]; then
		echo "Importing VirtualGL's public key..." >&2
		curl -fsSL https://packagecloud.io/dcommander/virtualgl/gpgkey |
		gpg --dearmor > "$KEYRINGS/VirtualGL.gpg"
		chmod 644 "$KEYRINGS/VirtualGL.gpg"
	fi
	if [ ! -f "$KEYRINGS/starnet.gpg" ]; then
		echo "Importing StarNet's public key..." >&2
		curl -fsSL https://www.starnet.com/files/keys/starnet-key.pub |
		gpg --dearmor > "$KEYRINGS/starnet.gpg" || exit $?
		chmod 644 "$KEYRINGS/starnet.gpg"
	fi
fi
# End of DEB code

# Common code
if [ -z "$QUIET" ] && [ -z "$PREVIEW" ]; then
	if [ "$PREV_PREVIEW" = 1 ]; then
		PROMPT='(Y/n)'
	else
		PROMPT='(y/N)'
	fi
    while [ -z "$PREVIEW" ]; do
	read -e -p "Enable Preview Repository $PROMPT? " ANS || exit $?
	if [ -z "$ANS" ]; then
		PREVIEW=$PREV_PREVIEW
	elif ! is_yesno "$ANS"; then
		echo "Please answer \"y(es)\" or \"n(o)\"." >&2
	elif is_yes "$ANS"; then
		PREVIEW=1
	else
		PREVIEW=0
	fi
    done
elif [ -z "$PREVIEW" ] && [ -n "$PREV_PREVIEW" ]; then
	PREVIEW=$PREV_PREVIEW
fi
if [ -z "$QUIET" ] && [ -z "$AKEY" ] && [ "$PM" != yum ]; then
	read -e -p "Activation Key (optional): " -i "$PREV_AKEY" AKEY
elif [ -z "$AKEY" ] && [ -n "$PREV_AKEY" ]; then
	AKEY=$PREV_AKEY
fi
if [ -n "$AKEY" ]; then
	ADVANCED=
	shopt -s extglob
	key=$(curl -k -s -S "https://license.starnet.com/query/akey?akey=$AKEY")
	if [[ $key == +(*\"Error\":\"akey\"*) ]]; then
		echo "Activation key does not exist." >&2
		key=
	fi
	if [ -n "$key" ] && [[ $key != +(*\"products\":*) ]]; then
		echo "Unknown result from akey query:" >&2
		echo "$key" >&2
		key=
	fi
	if [[ $key == +(*\"FastX\"*\"options\":\"*advanced*\"*) ]]; then
		ADVANCED=1
	fi
fi

if [ $PM = "dnf" ]; then
	if [ -n "$AKEY" ]; then
		echo $AKEY > /etc/dnf/vars/starnetkey
	fi
	if [ -n "$ADVANCED" ]; then
		dnf config-manager --set-enabled starnet-advanced
		echo dnf makecache -y --repo=starnet-advanced >&2
		dnf makecache -y --repo=starnet-advanced || exit $?
	else
		dnf config-manager --set-disabled starnet-advanced
	fi
	if [ "$PREVIEW" = 1 ]; then
		dnf config-manager --set-enabled starnet-preview
		echo dnf makecache -y --repo=starnet-preview >&2
		dnf makecache -y --repo=starnet-preview || exit $?
	elif [ "$PREVIEW" = 0 ]; then
		dnf config-manager --set-disabled starnet-preview
	fi
	echo dnf makecache -y --repo=VirtualGL >&2
	dnf makecache -y --repo=VirtualGL
	echo dnf makecache -y --repo=starnet >&2
	dnf makecache -y --repo=starnet || exit $?
elif [ $PM = yum ]; then
	if [ "$PREVIEW" = 1 ]; then
		yum-config-manager --enable starnet-preview > /dev/null
		$RPREVIEW=--enablerepo=starnet-preview
	elif [ "$PREVIEW" = 0 ]; then
		yum-config-manager --disable starnet-preview > /dev/null
	fi
	echo yum makecache -y --disablerepo='*' --enablerepo=starnet $RPREVIEW
	yum makecache -y --disablerepo='*' --enablerepo=starnet $RPREVIEW
elif [ "$PM" = apt ]; then
	if [ -n "$ADVANCED" ]; then
		RADVANCED=advanced
	fi
	{ 
		echo deb '[' signed-by=$KEYRINGS/starnet.gpg arch=amd64 ']' https://www.starnet.com/deb stable main $RADVANCED
	if [ "$PREVIEW" = 1 ]; then
		echo deb '[' signed-by=$KEYRINGS/starnet.gpg arch=amd64 ']' https://www.starnet.com/deb preview main $RADVANCED
	fi
	} > /etc/apt/sources.list.d/starnet.list || exit $?
	if [ -n "$AKEY" ]; then
		{
		echo machine www.starnet.com
		echo login "$AKEY"
		} > /etc/apt/auth.conf.d/55starnet
	fi
	chmod 644 /etc/apt/sources.list.d/starnet.list
	if [ "$VIRTUALGL" = 1 ]; then
		echo deb '[' signed-by=$KEYRINGS/VirtualGL.gpg ']' https://packagecloud.io/dcommander/virtualgl/any/ any main > /etc/apt/sources.list.d/VirtualGL.list
	fi
	echo apt-get update >&2
	apt-get update
fi

echo
echo "The StarNet repository has been configured."
if [ "$NODE_VERS" ] && [ "$PREVIEW" = 1 ]; then
        echo If there were no errors, this system should be capable of
        echo FastX 4 server. To install it, run this line:
        echo "    $PM install fastx4-server"
		echo "The previous version (FastX 3) is also available as \"fastx-server\"."
else
        echo If there were no errors, the FastX server should be
        echo ready to be installed. To install it, run this line:
        echo $PM install fastx-server
fi
