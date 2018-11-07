#!/bin/bash
# extract raw data from YCSB output

. scripts/util.sh

datadir="data"

[ -d "${1}" ] && echo "exists!" && echo $(date) ||
    error "${1} directory does not exist!"

if [ -d "$datadir" ]; then
    echo "already exists! use -f to force overwrite"
else
    mkdir $datadir
    echo "created new $datadir"
fi


cd ${1}

for file in *; do
    #a=$(
    ##a=
    #echo $a
    if fgrep -q [OVERALL] $file; then
        fields=`echo $file | cut -d . -f 1 | awk -F_ '{print $1 " " $2 " " $3 " " $4}'`
        echo $fields
        touch ../$datadir/`echo $fields | awk '{print $1 "_" $4}'`.txt

        continue
    else
        warn "Bad $file..."
        #error "exit"
    fi
done

echo $?
