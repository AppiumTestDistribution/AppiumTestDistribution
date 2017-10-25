#!/usr/bin/env bash

nohup emulator -avd 'Device1' &
nohup emulator -avd 'Device2' &
echo 'Booting two devices'
nohup adb wait-for-device shell 'while [[ -z $(getprop sys.boot_completed) ]]; do sleep 1; done; input keyevent 82'
echo 'Device booted'