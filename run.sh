#!/bin/bash
# load and run the workload

# source config and util
. config.sh
. util.sh

usage() {
  cat <<EOF
Usage: ${0##*/}
    load and run the workload

    options:
        -v    verbose output

        -s, --skip_compilation 
              skip compilation

        -w, --workload $(underline "FILE") 
              workload file

        -db, --database $(underline "NAME") 
              database tested

        -i, --id $(underline "NAME")
              identifier for this run
EOF
}

main() {
  local verbose=false;
  local database="";
  local compile=true;
  local workload="";
  local id="0";

  while [ ${#} -ne 0 ]; do
    case ${1} in
      -v) verbose=true; shift;;
      -s | --skip_compilation) compile=false; shift;;
      -w | --workload) workload=${2}; shift 2;;
      -db | --database) database=${2}; shift 2;;
      -i | --id) id=${2}; shift 2;;
      --) shift; break;;
      *) warn "bad command ${1}"; usage 1>&2; exit 64;
    esac
  done

  if $verbose; then
    set -x
  fi

  case ${database} in
    redis) properties="-p redis.host=${LOCALHOST} -p 
      redis.port=${REDIS_PORT}";;
    rocksdb) properties="-p rocksdb.dir=tmp/rocksdb-ycsb";;
    *) error "unsupported database: ${database}";
  esac

  if $compile; then
    # compile the correct database interface layer
    mvn -pl ${MAVEN_GROUP_ID}:${database}${DB_INTERFACE_SUFFIX} \
      -am clean package || error "compilation failed"
  fi

  # remove temp file
  rm ${KEYSIZE_FILE}

  echo "FLUSHDB" | redis-cli 

  # load data
  ./${YCSB_COMMAND_PATH} load ${database} ${YCSB_FLAGS} \
    -P ${workload} ${properties} > ${id}_load.txt

  # run workload
  ./${YCSB_COMMAND_PATH} run ${database} ${YCSB_FLAGS} \
    -P ${workload} ${properties} > ${id}_run.txt

  echo "FLUSHDB" | redis-cli 
}

main "${@}"
