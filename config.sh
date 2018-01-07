#!/bin/bash
# config vars for run.sh

# Maven
MAVEN_GROUP_ID="com.yahoo.ycsb"
DB_INTERFACE_SUFFIX="-binding"

# YCSB
YCSB_COMMAND_PATH="bin/ycsb"
YCSB_FLAGS="-s"

LOCALHOST="127.0.0.1"
KEYSIZE_FILE="temp_keysizes.txt"

# redis
REDIS_PORT="6379"
