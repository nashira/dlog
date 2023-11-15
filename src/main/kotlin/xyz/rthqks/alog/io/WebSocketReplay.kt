package xyz.rthqks.alog.io

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import xyz.rthqks.alog.model.AlogDocument

class WebSocketReplay {



    operator fun invoke(alogDocument: AlogDocument): Flow<AlogDocument> = flow {
        alogDocument.timex.forEach {

        }
    }
}
