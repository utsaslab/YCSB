mvn -pl com.yahoo.ycsb:rocksdb-binding \
    -pl com.yahoo.ycsb:leveldb-binding \
    -pl com.yahoo.ycsb:pebblesdb-binding \
    -am clean package || error "compilation failed"

for t in fieldlength keylength arrival default; do

    #for db in rocksdb leveldb pebblesdb; do
    for db in pebblesdb; do
        for l in a b c d e f; do
            echo $t $db $l
            ./scripts/run.sh -w workloads/${t}/workload${l} -db $db -i ${db}_${t}_w${l} -s
        done

        ./scripts/run.sh -w workloads/${t}/facebookworkload -db $db -i ${db}_${t}_fb -s
    done
done 
