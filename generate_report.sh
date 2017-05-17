./screenshot_copy.sh
echo "Installing node packages."
$(cd webContent/app && npm install)
echo "Start Website"
$(cd webContent/app && npm start)