# Makefile: description file for make

SHELL = /bin/sh

top:
	./gradlew assembleRelease
	mv app/build/outputs/apk/app-release.apk ./gprs.apk
clean:
	rm -rf build/ app/build/ .idea/libraries .gradle
spotless: clean
	rm -f gprs.apk
