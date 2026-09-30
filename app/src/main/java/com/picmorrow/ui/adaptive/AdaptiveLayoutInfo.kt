package com.picmorrow.ui.adaptive

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.window.core.layout.WindowSizeClass.Companion.HEIGHT_DP_MEDIUM_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_EXPANDED_LOWER_BOUND
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

@Immutable
internal data class AdaptiveLayoutInfo(
    val widthClass: AdaptiveWidthClass,
    val isShortHeight: Boolean,
    val isTabletop: Boolean,
    val hasVerticalHinge: Boolean,
) {
    val useWideLayout: Boolean
        get() = widthClass != AdaptiveWidthClass.Compact || hasVerticalHinge
}

internal enum class AdaptiveWidthClass {
    Compact,
    Medium,
    Expanded,
}

internal val LocalAdaptiveLayoutInfo = staticCompositionLocalOf {
    AdaptiveLayoutInfo(
        widthClass = AdaptiveWidthClass.Compact,
        isShortHeight = false,
        isTabletop = false,
        hasVerticalHinge = false,
    )
}

@Composable
internal fun ProvideAdaptiveLayoutInfo(content: @Composable () -> Unit) {
    val windowInfo = currentWindowAdaptiveInfo(supportLargeAndXLargeWidth = true)
    val sizeClass = windowInfo.windowSizeClass
    val layoutInfo = AdaptiveLayoutInfo(
        widthClass = when {
            sizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_EXPANDED_LOWER_BOUND) -> AdaptiveWidthClass.Expanded
            sizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND) -> AdaptiveWidthClass.Medium
            else -> AdaptiveWidthClass.Compact
        },
        isShortHeight = !sizeClass.isHeightAtLeastBreakpoint(HEIGHT_DP_MEDIUM_LOWER_BOUND),
        isTabletop = windowInfo.windowPosture.isTabletop,
        hasVerticalHinge = windowInfo.windowPosture.hingeList.any { hinge ->
            hinge.isVertical && (hinge.isSeparating || hinge.isOccluding)
        },
    )
    ProvideAdaptiveLayoutInfo(layoutInfo, content)
}

@Composable
internal fun ProvideAdaptiveLayoutInfo(
    layoutInfo: AdaptiveLayoutInfo,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalAdaptiveLayoutInfo provides layoutInfo, content = content)
}
