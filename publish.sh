RED='\033[0;31m'
NC='\033[0m' # No Color

git add .
git commit -m "$1"
git push -u origin master

echo -e "PUSH ${RED}DONE${NC}!"