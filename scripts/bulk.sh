mvn -pl com.yahoo.ycsb:rocksdb-binding \
    -pl com.yahoo.ycsb:leveldb-binding \
    -am clean package || error "compilation failed"
for t in fieldlength keylength; do

    for db in rocksdb leveldb; do
        for l in a b c d e f; do
            echo $t $db $l
            #./scripts/run.sh -w workloads/workload${l} -db $db -i \
                #${db}_w${l} -s
        done

        #./scripts/run.sh -w workloads/facebookworkload -db \
            #$db -i ${db}_fb -s
    done
done 
