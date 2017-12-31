APP_NAME="example-springboot"
APP_HOME=`cd $(dirname $0)/..;pwd`

cd $APP_HOME

CONF_DIR="${APP_HOME}"/config/
LOG_DIR=$APP_HOME"/logs"

echo $LOG_DIR

PID_FILE="${LOG_DIR}"/"${APP_NAME}".pid
echo $PID_FILE
SHUTDOWN_MAX_WAIT_SECONDS=10

function killProcess() {
    local pid=$1
    local maxWaitSeconds=$2
    local waitedSeconds=0
    local success=0
    if [ ${maxWaitSeconds} -lt 1 ]; then
        maxWaitSeconds=1
    fi

    while [ ${waitedSeconds} -lt ${maxWaitSeconds} ]; do
        kill -0 "${pid}"
        if [ $? -ne 0 ]; then
            success=1
            break;
        fi
        if [ ${waitedSeconds} -eq 0 ]; then
            kill "${pid}"
            echo "kill pid ""${pid}"" gracefully"
        fi

        sleep 1
        let waitedSeconds+=1
    done

    if [ ${success} -ne 1 ]; then
        kill -9 "${pid}"
        echo "kill pid ""${pid}"" now"
    fi
}

function shutdown() {
    local pidFile=$1
    local maxWaitSeconds=$2
    if [ -f "${pidFile}" ];then
        local pid=`cat ${pidFile}`
        killProcess "${pid}" ${maxWaitSeconds}

        rm -f "${pidFile}"
        echo "shutdown success."
    else
        echo "pid file not found, shutdown failed."
        exit 1
    fi
}

shutdown "${PID_FILE}" ${SHUTDOWN_MAX_WAIT_SECONDS}

