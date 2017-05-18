
DIR=./webContent/app/screenshot
if [ -d "$DIR" ]; then
    printf '%s\n' "Removing files and folders under ($DIR)"
    rm -rf "$DIR"
fi
echo "Copying screenshot in target inside webContent->app"
cp -R ./target/screenshot ./webContent/app/public