#!/bin/bash

ROOT="0"
SUDO=""

REPLACE=""

if [ "$1" == "-y" ]
then
	REPLACE="1" # indicating true
fi

if test x"`id -u`" != x"$ROOT"
then
	SUDO="sudo"
fi

# precheck

if [ -z "`which wget`" ]
then
	echo "\"wget\" not found"
	exit 1
fi

VERSION=0.0.1
LOCATION="/usr/local/bin"

# check whether string exists
libpath=`which string`

if [ "$libpath" ]
then
	echo "Already installed at $libpath, checking version"
	version=`"$libpath" -v`
	if [ "$version" == "$VERSION" ]
	then
		echo "The version is $version, no need to replace."
		exit 0
	else
		if [ -z "$REPLACE" ]
		then
			read -r -p "The version is $version, replace? [y/N]: " response
			case "$response" in
				[yY][eE][sS]|[yY])
					;;
				*)
					echo "Abort"
					exit 1
					;;
			esac
		fi
		LOCATION=`dirname $libpath`
	fi
fi

echo "Installing string $VERSION to $LOCATION"

os=`uname -s`

if [ "Darwin" == "$os" ]
then
	$SUDO wget -O "$LOCATION/string" "https://github.com/wkgcass/string/releases/download/$VERSION/string-macos"
else
	$SUDO wget -O "$LOCATION/string" "https://github.com/wkgcass/string/releases/download/$VERSION/string-linux"
fi

if test $? -ne 0
then
	echo "Downloading failed"
	exit 1
fi

$SUDO chmod +x "$LOCATION/string"

if test $? -ne 0
then
	echo "chmod +x $LOCATION/string failed"
	exit 1
fi

"$LOCATION/string" -v
echo "Done"

exit 0
