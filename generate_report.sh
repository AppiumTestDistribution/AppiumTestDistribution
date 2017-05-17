./screenshot_copy.sh
echo "Kill all processes running on PORT: 3000"
sudo lsof -t -i tcp:3000 -s tcp:listen | sudo xargs kill
echo "Installing node packages."
$(cd webContent/app && npm install)
echo "Start Website"
$(cd webContent/app && npm start)

