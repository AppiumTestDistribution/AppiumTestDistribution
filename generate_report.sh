#!/usr/bin/env bash
echo "Copying generated output in target inside webContent->app"
FILE=./webContent/app/src/data/Report.json
if [ -d "$FILE" ]; then
    printf '%s\n' "Removing file ($FILE)"
    rm -rf "$FILE"
fi
cp -f ./target/Report.json ./webContent/app/src/data
./screenshot_copy.sh
echo "Kill all processes running on PORT: 3000"
lsof -t -i tcp:3000 -s tcp:listen | xargs kill
echo "Installing node packages."
$(cd webContent/app && npm install)
echo "Start Website"
$(cd webContent/app && npm start)

