package org.codetome.zircon.api.builder

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.font.impl.FontSettings
import org.codetome.zircon.internal.graphics.DefaultLayer
import org.codetome.zircon.api.util.Maybe

/**
 * Use this to build [Layer]s. Defaults are:
 * - size: [Size.one()]
 * - filler: [TextCharacterBuilder.empty()]
 * - offset: [Position.defaultPosition()]
 * - has no text image by default
 */
data class LayerBuilder(private var font: Font = DEFAULT_FONT,
                        private var size: Size = DEFAULT_SIZE,
                        private var filler: TextCharacter = DEFAULT_FILLER,
                        private var offset: Position = Position.defaultPosition(),
                        private var textImage: Maybe<TextImage> = Maybe.empty()) : Builder<Layer> {

    /**
     * Sets the [Font] to use with the resulting [Layer].
     */
    fun font(font: Font) = also {
        this.font = font
    }

    /**
     * Sets the size for the new [org.codetome.zircon.api.graphics.Layer].
     * Default is 1x1.
     */
    fun size(size: Size) = also {
        this.size = size
    }

    /**
     * The new [org.codetome.zircon.api.graphics.Layer] will be filled by this [TextCharacter].
     * Defaults to `EMPTY`.
     */
    fun filler(filler: TextCharacter) = also {
        this.filler = filler
    }

    /**
     * Sets the `offset` for the new [org.codetome.zircon.api.graphics.Layer].
     * Default is 0x0.
     */
    fun offset(offset: Position) = also {
        this.offset = offset
    }

    /**
     * Uses the given [TextImage] and converts it to a [Layer].
     */
    fun textImage(textImage: TextImage) = also {
        this.textImage = Maybe.of(textImage)
    }

    override fun build(): Layer = if (textImage.isPresent) {
        DefaultLayer(size = textImage.get().getBoundableSize(),
                filler = filler,
                offset = offset,
                textImage = textImage.get(),
                initialFont = font)
    } else {
        DefaultLayer(
                size = size,
                filler = filler,
                offset = offset,
                initialFont = font)
    }

    override fun createCopy() = copy()

    companion object {

        val DEFAULT_FONT = FontSettings.NO_FONT

        val DEFAULT_SIZE = Size.one()

        val DEFAULT_FILLER = TextCharacterBuilder.empty()

        fun newBuilder() = LayerBuilder()
    }
}