# PebblesDB Wrapper

## Description

This is a YCSB database abstraction for PebblesDB, which is a write-optimized key-value store built with FLSM (Fragmented Log-Structured Merge Tree) data structure. FLSM is a modification of the standard log-structured merge tree data structure which aims at achieving higher write throughput and lower write amplification without compromising on read throughput.

## Usage

Clone this repository.

    git clone https://github.com/abhijith97/YCSB
    export YCSB_HOME=`cd YCSB; pwd`
	cd ${YCSB_HOME}


Copy the jars created by LevelDbJni. Follow procedure at :
https://github.com/abhijith97/leveldbjni

	mkdir ${YCSB_HOME}/pebblesdb/lib
	cp ${LEVELDBJNI_HOME}/leveldbjni/target/*.jar ${YCSB_HOME}/pebblesdb/lib
	cp ${LEVELDBJNI_HOME}/leveldbjni-{platform}/target/*.jar ${YCSB_HOME}/pebblesdb/lib

Build the PebblesDB binding.

	mvn -pl com.yahoo.ycsb:pebblesdb-binding -am clean package

Run the YCSB workloads. Example command :-

	 java -cp pebblesdb/target/*:pebblesdb/target/dependency/*:pebblesdb/lib/*: com.yahoo.ycsb.Client -load -db com.yahoo.ycsb.db.PebblesDbClient -P workloads/workloada