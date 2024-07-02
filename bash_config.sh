#!/bin/bash

unalias -a

# any code you only wish to execute
# the first time should be placed
# within this if statement
if [ "$_already_run" != 1 ]; then 
    export base_dir=`pwd`
    echo now defined: config_bash, "$"base_dir, "$"bash_config
fi
export _already_run=1
alias config_bash=". $base_dir/bash_config.sh"
export bash_config="$base_dir/bash_config.sh"


# variable that stores classpath
export _temp_CLASSPATH=".:$base_dir/libs"

# aliases
alias javac="javac -classpath $_temp_CLASSPATH"
alias java="java -classpath $_temp_CLASSPATH"
alias jdb="jdb -sourcepath $_temp_CLASSPATH"


echo success