#!/usr/bin/env bash

export RUN_PATH="`dirname \"$0\"`"

cd $RUN_PATH


run_module=$1

if [[ ! $run_module ]]; then
    echo "need to specify run module"
    exit -1
fi

echo "CURRENT RUN PATH:"

pwd

pip install -r requirements.txt > /dev/null

start=`date +%s`

echo "Start run python module..."
echo ""
echo ""
echo ""

export MODEL_HOME="/content/"
export OUTPUT_HOME="/content/train_output/"
#PYTHONPATH=. python3 $run_module --data_path=/content/model/tensorflow/rnn/ptb/simple-examples/data/ --save_path=/content/train_output/ptb/

PYTHONPATH=. python3 $run_module

echo ""
echo ""
echo ""
echo "End run python module"

end=`date +%s`

((count = end - start))

echo "Total Elapsed: $count s"
