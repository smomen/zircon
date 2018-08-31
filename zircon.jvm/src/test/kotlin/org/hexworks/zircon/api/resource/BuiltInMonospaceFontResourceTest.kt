package org.hexworks.zircon.api.resource

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.internal.util.AwtFontUtils
import org.junit.Test
import java.awt.Font
import java.awt.GraphicsEnvironment

class BuiltInMonospaceFontResourceTest {

    @Test
    fun test() {

        BuiltInMonospaceFontResource.values().forEach {
            val fr = it.toTilesetResource(20)
            val f = Font.createFont(
                    Font.TRUETYPE_FONT,
                    this::class.java.getResourceAsStream(fr.path))
            val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
            ge.registerFont(f)

            val font = f.deriveFont(fr.height.toFloat())

            assertThat(AwtFontUtils.isFontMonospaced(font)).isTrue()
        }
    }
}
