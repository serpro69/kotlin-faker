package io.github.serpro69.kfaker.docs

import com.eden.orchid.api.compilers.TemplateTag
import com.eden.orchid.api.registration.OrchidModule
import com.eden.orchid.utilities.addToSet

@Suppress("unused")
class FakerModule : OrchidModule() {
    override fun configure() {
        addToSet<TemplateTag, NoteTag>()
    }
}
