grep -lr --exclude-dir=".git" -e "recordcount=1000" . | xargs sed -i "s/recordcount=1000/recordcount=10000000/g"
grep -lr --exclude-dir=".git" -e "operationcount=1000" . | xargs sed -i "s/operationcount=1000/operationcount=10000000/g"

