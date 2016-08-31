lsusb command for Mac OS X
==========================

This is an utility to easily list USB devices in Mac OS X, just like the `lsusb` command in Linux. It leverages the data from the `system_profiler SPUSBDataType` built-in command available in Mac. You can type `lsusb -v` to get the unmodified output of `system_profiler SPUSBDataType`.

To install, clone the repository and copy to a location available in your PATH, for example `sudo cp lsusb /usr/sbin`. You can also install it with [Homebrew](http://brew.sh):

```
brew update             && \
brew tap jlhonora/lsusb && \
brew install lsusb
```

Here's an output example:

```
Bus 036 Device 003: ID 04e8:6860 Samsung Electronics Co., Ltd. SAMSUNG_Android  Serial: 323062d3f6738057
Bus 036 Device 002: ID 05ac:8507 Apple Inc. Built-in iSight  Serial: 8J97P2KF16V13A00
Bus 038 Device 003: ID 1a40:0101 TERMINUS TECHNOLOGY INC. USB 2.0 Hub [MTT] 
Bus 038 Device 004: ID 0403:6001 Future Technology Devices International Limited FT232R USB UART  Serial: A601EFG9
Bus 038 Device 005: ID 12d1:1038 Huawei Technologies Co., Ltd. Android Adapter  Serial: 509F2735096D
Bus 038 Device 002: ID 05ac:8403 Apple Inc. Internal Memory Card Reader  Serial: 000000009833
Bus 004 Device 003: ID 05ac:8242 Apple Inc. IR Receiver 
Bus 004 Device 002: ID 05ac:0236 Apple Inc. Apple Internal Keyboard / Trackpad 
Bus 006 Device 002: ID 0a5c:4500 Broadcom Corp. BRCM2046 Hub 
Bus 006 Device 003: ID 05ac:8213 Apple Inc. Bluetooth USB Host Controller  Serial: 002608CCAC6F
Bus 036 Device 001: ID 05ac:8006 Apple Inc. EHCI Root Hub Simulation 
Bus 038 Device 001: ID 05ac:8006 Apple Inc. EHCI Root Hub Simulation 
Bus 004 Device 001: ID 05ac:8005 Apple Inc. OHCI Root Hub Simulation 
Bus 006 Device 001: ID 05ac:8005 Apple Inc. OHCI Root Hub Simulation
```

## Usage ##

```
List USB devices
  -v  Increase verbosity (show output of "system_profiler SPUSBDataType")
  -s  [[bus]:][devnum]
       Show only devices with specified device and/or
       bus numbers (in decimal)
  -d  [vendor]:[product]
       Show only devices with the specified vendor and
       product ID numbers (in hexadecimal)
  -p  Display manufacturer names in parentheses
  -t  Dump the physical USB device hierarchy as a tree
  -V  Show version of program
  -h  Show usage and help
```

For a more accurate usage description type `man lsusb` or `man man/lsusb.8` if inside the script's directory.
