package xyz.rthqks.dlog.io

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WebSocketReplay {


    operator fun invoke(alogDocument: AlogDocument): Flow<AlogDocument> = flow {
        alogDocument.timex.forEach {

        }
    }
}
