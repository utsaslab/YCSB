grep -lr --exclude-dir=".git" -e "recordcount="${1} . | xargs sed -i "s/recordcount="${1}"/recordcount="${2}"/g"
grep -lr --exclude-dir=".git" -e "operationcount="${1} . | xargs sed -i "s/operationcount="${1}"/operationcount="${2}"/g"

